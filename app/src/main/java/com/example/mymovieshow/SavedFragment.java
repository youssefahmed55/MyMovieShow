package com.example.mymovieshow;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mymovieshow.pojo.Result2;
import com.example.mymovieshow.viewmodel.MoviesViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SavedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedFragment newInstance(String param1, String param2) {
        SavedFragment fragment = new SavedFragment();
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
    private RecyclerView recyclerView;
    private MyRecycleAdapter3 myRecycleAdapter;
    private View view;
    private MoviesViewModel moviesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_saved, container, false);
        inti();
        onpressback();
        if(!isNetworkAvailable()){
            Toast.makeText(getActivity(), "Internet Not Connected", Toast.LENGTH_LONG).show();
        }
        observesaveddata();
        setonitemclicklistener();
        return view;
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
    private void setonitemclicklistener() {
        myRecycleAdapter.setOnmyClickListenerrr(new MyRecycleAdapter3.OnmyClickListenerrr2() {
            @Override
            public void onclick22(Result2 resultt2) {
                intenttodetailsfragment(resultt2);
            }
        });
    }


    private void inti() {
        recyclerView = view.findViewById(R.id.recycle_saved);
        myRecycleAdapter = new MyRecycleAdapter3(getActivity());
        moviesViewModel= new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);
    }
    private void observesaveddata() {
      moviesViewModel.getSavedMovies();
      moviesViewModel.getArrayListMutableLiveData6().observe(getViewLifecycleOwner(), new Observer<ArrayList<Result2>>() {
                  @Override
                  public void onChanged(ArrayList<Result2> models) {
                      myRecycleAdapter.setArrayList(models);
                      recyclerView.setAdapter(myRecycleAdapter);
                  }
              });

    }
    private void intenttodetailsfragment(Result2 resultt2) {
        if(isNetworkAvailable()) {
            Bundle args = new Bundle();
            args.putSerializable("keys", resultt2);
            CardDetailsFragment cardDetailsFragment = new CardDetailsFragment();
            cardDetailsFragment.setArguments(args);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, cardDetailsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            Toast.makeText(getActivity(), "Internet Not Connected", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}