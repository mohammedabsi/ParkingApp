package com.example.parkingapp;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText add_news, add_description;
    private ImageView add_image;
    private ProgressBar progressBarAdminAddnews;
    private Button submit_news;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageReference mstorageReference;
    private StorageTask mUploadTask;


    public AddNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddNewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewsFragment newInstance(String param1, String param2) {
        AddNewsFragment fragment = new AddNewsFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_news, container, false);
        add_news = view.findViewById(R.id.add_news);
        add_description = view.findViewById(R.id.add_description);
        add_image = view.findViewById(R.id.add_image);
        progressBarAdminAddnews = view.findViewById(R.id.progressBarAdminAddnews);
        submit_news = view.findViewById(R.id.submit_news);

        mstorageReference = FirebaseStorage.getInstance().getReference("Uploads");

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        submit_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress", Toast.LENGTH_SHORT).show();

                } else {
                    SubmitNews();
                }
            }
        });


        return view;
    }

    private void SubmitNews() {

        String headerNew = add_news.getText().toString().trim();
        String descNew = add_description.getText().toString().trim();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        closeKeyboard();


        if (headerNew.isEmpty()) {
            add_news.setError("Empty Field");
            add_news.requestFocus();
            return;
        }
        if (descNew.isEmpty()) {
            add_description.setError("Empty Field");
            add_description.requestFocus();
            return;
        }
//   toDO: CHECK img if exist or not
//        if (imgNew == null ){}
        if (mImageUri != null) {
            final StorageReference fileref = mstorageReference.child(mImageUri.getLastPathSegment());
            fileref.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            AddNew addNew = new AddNew(add_news.getText().toString().trim()
                                    , add_description.getText().toString()
                                    , url);

                            firestore.collection("News")
                                    .document()
                                    .set(addNew)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBarAdminAddnews.setVisibility(View.VISIBLE);
                                            if (task.isSuccessful()) {
                                                progressBarAdminAddnews.setVisibility(View.GONE);
                                                Toast.makeText(getActivity(), "News Added", Toast.LENGTH_SHORT).show();


                                            } else {
                                                progressBarAdminAddnews.setVisibility(View.GONE);
                                                Toast.makeText(getActivity(), "News failed to upload", Toast.LENGTH_SHORT).show();


                                            }

                                        }
                                    });

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBarAdminAddnews.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), "News failed to upload", Toast.LENGTH_SHORT).show();

                }
            });


        } else {
            add_image.requestFocus();
            add_image.setBackground(getResources().getDrawable(R.color.pink_light));
            Toast.makeText(getActivity(), "No file selected ,Press on image and add picture", Toast.LENGTH_SHORT).show();
            progressBarAdminAddnews.setVisibility(View.GONE);
        }


    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(add_image);
        }
    }


}