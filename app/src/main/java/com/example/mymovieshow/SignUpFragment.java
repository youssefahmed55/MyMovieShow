package com.example.mymovieshow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mymovieshow.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private FragmentSignUpBinding binding;
    private View view;
    private FirebaseAuth auth;
    private DatabaseReference myRef ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        inti();
        onclickonregisterSignupbutton();
        return view;
    }
    private void inti(){
        auth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Saved");
    }
    private void onclickonregisterSignupbutton(){
        binding.registerSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.usernameSignup.getText().toString().trim().equals("")){
                    binding.usernameSignup.setError("Username is Required");
                    return;
                }
                if(binding.passwordSignup.getText().toString().trim().equals("")){
                    binding.passwordSignup.setError("Password is Required");
                    return;
                }
                if(binding.confirmpasswordSignup.getText().toString().trim().equals("")){
                    binding.confirmpasswordSignup.setError("Confirm Password is Required");
                    return;
                }
                if(binding.emailSignup.getText().toString().trim().equals("")){
                    binding.emailSignup.setError("Email is Required");
                    return;
                }
                if(!binding.passwordSignup.getText().toString().trim().equals(binding.confirmpasswordSignup.getText().toString().trim())){
                    binding.confirmpasswordSignup.setError("Password Don't match");
                    return;
                }




                auth.createUserWithEmailAndPassword(binding.emailSignup.getText().toString().trim(),binding.passwordSignup.getText().toString().trim()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        myRef.child("Users").child(auth.getCurrentUser().getUid()).child("user").setValue(binding.usernameSignup.getText().toString().trim());
                                        Toast.makeText(getActivity(), "please verify your email address,it may take few minuits", Toast.LENGTH_SHORT).show();
                                        Navigation.findNavController(view).popBackStack();
                                    }else {
                                        Toast.makeText(getActivity(), "Email not Sent :" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            if(task.getException().getMessage().equals("The email address is badly formatted.")){
                                binding.emailSignup.setError("The email address is badly formatted");
                            }else if(task.getException().getMessage().equals("The given password is invalid. [ Password should be at least 6 characters ]")) {
                                binding.passwordSignup.setError("Password should be at least 6 characters");

                            }else{
                            Toast.makeText(getActivity(), "" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                        }
                    }
                });
            }
        });

    }

}