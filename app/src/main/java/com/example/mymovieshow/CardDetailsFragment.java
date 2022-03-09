package com.example.mymovieshow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mymovieshow.databinding.FragmentCardDetailsBinding;
import com.example.mymovieshow.pojo.Movies;
import com.example.mymovieshow.pojo.Result2;
import com.example.mymovieshow.viewmodel.MoviesViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CardDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardDetailsFragment newInstance(String param1, String param2) {
        CardDetailsFragment fragment = new CardDetailsFragment();
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

    private FragmentCardDetailsBinding binding;
    private View view;
    private Result2 result2;
    private MoviesViewModel moviesViewModel;
    private Movies moviess;
    private FirebaseDatabase database ;
    private DatabaseReference myRef ;
    private FirebaseAuth mAuth;
    private ArrayList<Result2> savemodels;
    private static final String TAG = "CardDetailsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCardDetailsBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        inti();
        onpressback();

        result2 = (Result2) getArguments().getSerializable("keys");


        if (result2 != null) {
            observesaveddata();
            Log.d(TAG, "onCreateView: " + result2.getId() + " " + result2.getFie_id());
            setitemslist();
            observeslectedcard(result2.getId());
            onclicktwobuttonslisteners();
        }

        return view;
    }

    private void onclicktwobuttonslisteners() {
        binding.watchTrialCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moviess != null) {
                    if (moviess.getResults().size() > 0) {
                        Intent intent = new Intent(getActivity(), ThirdActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("key", moviess.getResults().get(0).getKey());
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);

                    } else {
                        Toast.makeText(getActivity(), "Not Found Trialer Videos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.saveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        if(ifthismovieinsaved()){
                            Log.d(TAG, "onClick: yes, remove movie");
                            checkthenremove(savemodels);
                            myRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("movies_saved").setValue(savemodels).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        binding.saveCard.setBackgroundColor(Color.parseColor("#47A891"));
                                        Toast.makeText(getActivity(), "UnSaved", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getActivity(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Log.d(TAG, "onClick: no, add movie");
                            savemodels.add(result2);
                            myRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("movies_saved").setValue(savemodels).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        binding.saveCard.setBackgroundColor(Color.parseColor("#A80000"));
                                        Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getActivity(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }


            }
        });

    }

    private void inti(){
        moviesViewModel= new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Saved");
        mAuth = FirebaseAuth.getInstance();
        savemodels = new ArrayList<>();

   }
    private void onpressback() {

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
    private void observeslectedcard(Long mid) {
        moviesViewModel.getmoviesDetails(mid);

        moviesViewModel.getArrayListMutableLiveData5().observe(getViewLifecycleOwner(), new Observer<Movies>() {
            @Override
            public void onChanged(Movies movies) {
                moviess = movies;
            }
        });

    }

    private void setitemslist() {

        Glide.with(this).load(result2.getBackdropPath()).into(binding.imageCard);
        binding.titleCard.setText(result2.getOriginalTitle());
        binding.textstar.setText(String.valueOf(result2.getVoteAverage()));
        binding.textlanguage.setText(result2.getOriginalLanguage());
        binding.textaveragevote.setText(String.valueOf(result2.getVoteCount()));
        binding.textdate.setText(result2.getReleaseDate());
        binding.text3Card.setText(result2.getOverview() + "\n \n \n");

    }

    private boolean ifthismovieinsaved(){
        if(savemodels.size() > 0) {
            for (int i = 0; i < savemodels.size(); i++) {
                if (savemodels.get(i).getId().equals(result2.getId()))
                    return true;
            }
        }

        return false ;
    }
    private void checkthenremove(ArrayList<Result2> models){
        for(int i = 0 ; i < models.size();i++){
            if(models.get(i).getId().equals(result2.getId()))
                models.remove(models.get(i));
        }

    }
    private void observesaveddata() {
        moviesViewModel.getSavedMovies();
        moviesViewModel.getArrayListMutableLiveData6().observe(getViewLifecycleOwner(), new Observer<ArrayList<Result2>>() {
            @Override
            public void onChanged(ArrayList<Result2> models) {
                savemodels = models;

                     if(savemodels != null) {
                         if (ifthismovieinsaved()) {
                             Log.d(TAG, "setitemslist: " + savemodels.size());
                             binding.saveCard.setBackgroundColor(Color.parseColor("#A80000"));
                         } else {
                             Log.d(TAG, "setitemslist: " + savemodels.size());
                             Log.d(TAG, "setitemslist: Not Saved ");

                         }
                     }
            }
        });
    }
}