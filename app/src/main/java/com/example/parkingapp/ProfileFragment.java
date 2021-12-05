package com.example.parkingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CircularImageView profile_photo;
    ImageView edit_image;
    TextView profile_type;
    TextInputEditText username_profile, email_profile, password_profile, id_profile;
    Button update_profile;
    ProgressBar progressBarprofile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        profile_photo = v.findViewById(R.id.profile_photo);
        edit_image = v.findViewById(R.id.edit_image);
        profile_type = v.findViewById(R.id.profile_type);
        username_profile = v.findViewById(R.id.username_profile);
        email_profile = v.findViewById(R.id.email_profile);
        password_profile = v.findViewById(R.id.password_profile);
        id_profile = v.findViewById(R.id.id_profile);
        update_profile = v.findViewById(R.id.update_profile);
        progressBarprofile = v.findViewById(R.id.progressBarprofile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();


       // String currentid = user.getUid();
        String currentemail= user.getEmail();
        DocumentReference reference1 , reference2 ,reference3;

        progressBarprofile.setVisibility(View.VISIBLE);

        reference1 = firestore.collection("Student_Side").document(currentemail);
        reference2 = firestore.collection("Teacher_Side").document(currentemail);
        reference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                        String nameResult = task.getResult().getString("username");
                        String emailResult = task.getResult().getString("email");
                        String passwordResult = task.getResult().getString("password");
                        String userIdResult = task.getResult().getString("userId");
                        profile_type.setText("Student");
                        username_profile.setText(nameResult);
                        email_profile.setText(emailResult);
                        password_profile.setText(passwordResult);
                        id_profile.setText(userIdResult);
                        progressBarprofile.setVisibility(View.INVISIBLE);

             


                } else   {
                    reference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task2) {
                            String nameResult = task2.getResult().getString("username");
                            String emailResult = task2.getResult().getString("email");
                            String passwordResult = task2.getResult().getString("password");
                            String userIdResult = task2.getResult().getString("userId");
                            profile_type.setText("Faculty");
                            username_profile.setText(nameResult);
                            email_profile.setText(emailResult);
                            password_profile.setText(passwordResult);
                            id_profile.setText(userIdResult);
                            progressBarprofile.setVisibility(View.INVISIBLE);
                            //todo : second try
//                            Boolean x = task2.getResult().getBoolean("type_s");
//                            if (x ==false ){
//
//
//                            }
                        }
                    });

                    progressBarprofile.setVisibility(View.INVISIBLE);

                }
            }
        });
        return v;
    }

}