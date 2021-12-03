package com.example.parkingapp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcceptRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcceptRequestFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView accept_requests_recycler;
    ArrayList<User> requestUserArrayList;
    RequestsAdapter requestsAdapter;
    ProgressBar AcceptReqsprogressBar;
    FirebaseFirestore firestore;


    public AcceptRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcceptRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcceptRequestFragment newInstance(String param1, String param2) {
        AcceptRequestFragment fragment = new AcceptRequestFragment();
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
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accept_request, container, false);
        accept_requests_recycler = view.findViewById(R.id.accept_requests_recycler);
        AcceptReqsprogressBar = view.findViewById(R.id.AcceptReqsprogressBar);
        AcceptReqsprogressBar.setVisibility(View.VISIBLE);
        accept_requests_recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        accept_requests_recycler.setHasFixedSize(true);
        List<String> keys = new ArrayList<>();

        firestore = FirebaseFirestore.getInstance();
        requestUserArrayList = new ArrayList<User>();



        RetrieveDataFirestore();
        requestsAdapter = new RequestsAdapter(getActivity().getApplicationContext(), requestUserArrayList, keys, firestore);
        accept_requests_recycler.setAdapter(requestsAdapter);

        return view;
    }

    private void RetrieveDataFirestore() {

        firestore.collection("Teacher_Side").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    if (AcceptReqsprogressBar.isShown()) {
                        AcceptReqsprogressBar.setVisibility(View.GONE);
                    }
                    Log.d("Fire Store Error", error.getMessage());
                    return;
                }
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {

                        requestUserArrayList.add(documentChange.getDocument().toObject(User.class));

                    }
                    requestsAdapter.notifyDataSetChanged();
                    if (AcceptReqsprogressBar.isShown()) {
                        AcceptReqsprogressBar.setVisibility(View.GONE);
                    }

                }
            }
        });


    }

}