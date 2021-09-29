package com.example.parkingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView newsRecyclerView;
    ArrayList<NewsModel> newsModelArrayList ;


    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_news, container, false);

        newsRecyclerView =view.findViewById(R.id.news_recyclerview);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsModelArrayList= new ArrayList<>();
        NewsModel obj1 = new NewsModel(R.drawable.ic_parking , "new1" , "descripton 1" );
        newsModelArrayList.add(obj1);

        NewsModel obj2 = new NewsModel(R.drawable.ic_parking , "new2" , "descripton 2" );
        newsModelArrayList.add(obj2);

        NewsModel obj3 = new NewsModel(R.drawable.ic_parking , "new3" , "descripton 3" );
        newsModelArrayList.add(obj3);
        NewsModel obj4 = new NewsModel(R.drawable.ic_parking , "new4" , "descripton 4" );
        newsModelArrayList.add(obj4);

        NewsModel obj5 = new NewsModel(R.drawable.ic_parking , "new5" , "descripton 5" );
        newsModelArrayList.add(obj5);
        NewsModel obj6 = new NewsModel(R.drawable.ic_parking , "new6" , "descripton 6" );
        newsModelArrayList.add(obj6);
        NewsModel obj7 = new NewsModel(R.drawable.ic_parking , "new7" , "descripton 7" );
        newsModelArrayList.add(obj7);


        newsRecyclerView.setAdapter(new newsAdapter(newsModelArrayList));


        return view ;
    }
}