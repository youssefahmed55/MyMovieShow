package com.example.mymovieshow;

import static android.content.Context.MODE_PRIVATE;


import android.content.Context;
import android.content.SharedPreferences;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;


import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mymovieshow.databinding.FragmentHomeBinding;
import com.example.mymovieshow.pojo.Model;

import com.example.mymovieshow.pojo.Result2;
import com.example.mymovieshow.viewmodel.MoviesViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    private FragmentHomeBinding binding;
    private View view;
    private MyRecycleAdapter myRecycleAdapter;
    private MyRecycleAdapter2 myRecycleAdapter2,myRecycleAdapter2_2;
    private ArrayList<Result2> arrayList,arrayList2,arrayList3;
    private MoviesViewModel moviesViewModel;
    private ArrayList<Result2> moviesarray;
    private static final String TAG = "HomeFragment";
    private SharedPreferences sharedPreferences;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        inti();
        onpressback();
        if(!isNetworkAvailable()){
            Toast.makeText(getActivity(), "Internet Not Connected", Toast.LENGTH_LONG).show();
            observedataroom();
       }

        observedatafromapi();
        Searchmovies();
        onitemclicklistener();



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

    private void observedatafromapi() {
        moviesViewModel.getDatafromPopularMoviestoUpcoming();
        moviesViewModel.getArrayListMutableLiveDataa().observe(getViewLifecycleOwner(), new Observer<Model>() {
            @Override
            public void onChanged(Model model) {
                arrayList.clear();
                arrayList2.clear();
                arrayList3.clear();
                Log.d(TAG, "onChanged: "+ model.getResult2ArrayList().size());
                for(int i = 0 ; i < Integer.parseInt(model.getNum().get(0)) ; i++){
                    arrayList.add(model.getResult2ArrayList().get(i));
                }
                for(int i = Integer.parseInt(model.getNum().get(0)) ; i < Integer.parseInt(model.getNum().get(1)) ; i++){
                    arrayList2.add(model.getResult2ArrayList().get(i));
                }
                for(int i = Integer.parseInt(model.getNum().get(1)) ; i < Integer.parseInt(model.getNum().get(2)) ; i++){
                    arrayList3.add(model.getResult2ArrayList().get(i));
                }

                myRecycleAdapter.setArrayList(arrayList);
                binding.recycle1Home.setAdapter(myRecycleAdapter);


                myRecycleAdapter2.setArrayList(arrayList2);
                binding.recycle2Home.setAdapter(myRecycleAdapter2);


                myRecycleAdapter2_2.setArrayList(arrayList3);
                binding.recycle3Home.setAdapter(myRecycleAdapter2_2);

                save(model.getNum());

            }
        });
    }

    private void observedataroom() {
        if(load() != null){
            moviesViewModel.getdata(load());
        }



    }


    private void onitemclicklistener() {

        myRecycleAdapter.setOnClickListeners(new MyRecycleAdapter.OnmyClickListenerrr() {
            @Override
            public void onclick2(Result2 resultt2) {
                intenttodetailsfragment(resultt2);

            }
        });

        myRecycleAdapter2.setOnmyClickListenerrr(new MyRecycleAdapter2.OnmyClickListenerrr2() {
            @Override
            public void onclick22(Result2 resultt2) {
                intenttodetailsfragment(resultt2);
            }
        });

        myRecycleAdapter2_2.setOnmyClickListenerrr(new MyRecycleAdapter2.OnmyClickListenerrr2() {
            @Override
            public void onclick22(Result2 resultt2) {
                intenttodetailsfragment(resultt2);
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


    private void Searchmovies() {
        binding.searchHome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().isEmpty()) {
                    binding.populartext.setVisibility(View.GONE);
                    moviesViewModel.SearchMovie("" + charSequence);
                    moviesViewModel.getArrayListMutableLiveData4().observe(getViewLifecycleOwner(), new Observer<ArrayList<Result2>>() {
                        @Override
                        public void onChanged(ArrayList<Result2> result2s) {
                            moviesarray = result2s;
                            myRecycleAdapter.setArrayList(moviesarray);
                            binding.recycle1Home.setAdapter(myRecycleAdapter);
                        }
                    });
                }else{
                    binding.populartext.setVisibility(View.VISIBLE);
                    observedatafromapi();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private void inti(){
        myRecycleAdapter = new MyRecycleAdapter(getActivity());
        myRecycleAdapter2 = new MyRecycleAdapter2(getActivity());
        myRecycleAdapter2_2= new MyRecycleAdapter2(getActivity());
        arrayList = new ArrayList<>();
        arrayList2=new ArrayList<>();
        arrayList3=new ArrayList<>();
        moviesarray=new ArrayList<>();
        moviesViewModel = new ViewModelProvider(requireActivity()).get(MoviesViewModel.class);
        sharedPreferences = getActivity().getSharedPreferences("data",MODE_PRIVATE);


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void save(ArrayList<String> arrayList){
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        sharedPreferences.edit().putString("keynum",json).apply();


    }
    private ArrayList<String> load(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("keynum", null);
        //Type type = new TypeToken<ArrayList<String>>() {}.getType();
        Type type2 = TypeToken.getParameterized(ArrayList.class, String.class).getType();
        if (gson.fromJson(json, type2) != null) {
            return (gson.fromJson(json, type2));
        }else {
            return null;
        }

    }
}