package com.example.mymovieshow;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mymovieshow.databinding.FragmentLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_SIGN_IN = 100;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
    private FragmentLoginBinding binding;
    private View view;
    private FirebaseAuth auth;
    private DatabaseReference myRef;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "LoginFragment";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        inti();
        onpressback();

        return view;

    }

    private void inti(){
        binding.SignInLogin.setOnClickListener(this);
        binding.SignUpLogin.setOnClickListener(this);
        binding.forgetpasswordLogin.setOnClickListener(this);
        binding.imageofsigninwithgoogleLogin.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Saved");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Sign_in_login:
                checkuserandpassord();
                if(binding.keepMeSigninLogin.isChecked()){
                    keepmesignin();
                }
                break;
            case R.id.Sign_Up_login:
                Navigation.findNavController(view).navigate(R.id.action_LoginFragment_to_signUpFragment);
                break;
            case R.id.forgetpassword_login:
                Navigation.findNavController(view).navigate(R.id.action_LoginFragment_to_forgetPasswordFragment);
                break;
            case R.id.imageofsigninwithgoogle_login:
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("28590144541-b93pm2it5dcagmd4s3g5d0bifos0bbtt.apps.googleusercontent.com")
                        .requestEmail()
                        .build();

                mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                signIngoogle();
                if(binding.keepMeSigninLogin.isChecked()){
                    keepmesignin();
                }
                break;


        }
    }

    private void onpressback(){

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
    private void checkuserandpassord(){
        if(binding.emailLogin.getText().toString().trim().equals("")){
            binding.emailLogin.setError("Email Required");
            return;
        }
        if(binding.passwordLogin.getText().toString().trim().equals("")){
            binding.passwordLogin.setError("Password Required");
            return;
        }
        auth.signInWithEmailAndPassword(binding.emailLogin.getText().toString().trim(),binding.passwordLogin.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(auth.getCurrentUser().isEmailVerified()){
                                Intent intent = new Intent(getActivity(),SecondActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }else{
                                Toast.makeText(getActivity(), "please verify your email address", Toast.LENGTH_LONG).show();
                            }

                        }else{
                            if(task.getException().getMessage().equals("The email address is badly formatted.")){
                                binding.emailLogin.setError("The email address is badly formatted");
                            }else{
                            Toast.makeText(getActivity(), ""+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }}
                    }
                });

    }
    private void signIngoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:"+ account.getId());

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            myRef.child("Users").child(user.getUid()).child("user").setValue(user.getDisplayName());
                            Intent intent = new Intent(getActivity(),SecondActivity.class);
                            startActivity(intent);
                            getActivity().finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), ""+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private void keepmesignin(){
        sharedPreferences = getActivity().getSharedPreferences("data",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("key",true).apply();
    }


}