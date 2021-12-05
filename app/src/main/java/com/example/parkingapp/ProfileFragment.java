package com.example.parkingapp;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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
    Uri nImageUri;
    CircularImageView mImageView;
    TextView profile_type;
    TextInputEditText username_profile, email_profile, password_profile, id_profile;
    Button update_profile;
    ProgressBar progressBarprofile;
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("UsersImages");
    StorageTask mUploadTask;
    Map<String, Object> map = new HashMap<>();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String currentemail = user.getEmail();
    DocumentReference userprofilenode = FirebaseFirestore.getInstance().collection("User").document(currentemail);
    DocumentReference userprofilenode2 = FirebaseFirestore.getInstance().collection("Teacher").document(currentemail);
    private static final int PICK_IMAGE_REQUEST = 1;
    private Dialog dialog;
    private ConstraintLayout profileParent;


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
        mImageView = v.findViewById(R.id.profile_photo);
        profileParent = v.findViewById(R.id.profileParent);
        edit_image = v.findViewById(R.id.edit_image);
        update_profile = v.findViewById(R.id.update_profile);


        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_updatepassword);
        dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.dialog_margin));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        Button updatePassBtn = dialog.findViewById(R.id.updatePassBtn);
        Button cancelUpdate = dialog.findViewById(R.id.cancelUpdate);
        EditText oldPass = dialog.findViewById(R.id.oldPass);
        EditText newPass = dialog.findViewById(R.id.newPass);

        password_profile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        updatePassBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getEmail();
                String old_Pass = oldPass.getText().toString().trim();
                String new_Pass = newPass.getText().toString().trim();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email, old_Pass);

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(new_Pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                password_profile.setText(new_Pass);
                                                Toast.makeText(getActivity(), "Password updated", Toast.LENGTH_SHORT).show();
                                                Log.d("pas", "Password updated");
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(getActivity(), "Error password not updated", Toast.LENGTH_SHORT).show();
                                                Log.d("pas", "Error password not updated");
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), "Error auth failed", Toast.LENGTH_SHORT).show();
                                    Log.d("pas", "Error auth failed");
                                    dialog.dismiss();

                                }
                            }
                        });

            }
        });
        cancelUpdate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        DocumentReference reference1, reference2;

        progressBarprofile.setVisibility(View.VISIBLE);

        reference1 = firestore.collection("User").document(currentemail);
        reference2 = firestore.collection("Teacher").document(currentemail);
        reference1.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.getResult().exists()) {
                    if (task.getResult().get("status_s").toString().equalsIgnoreCase("0")) {
                        String nameResult = task.getResult().getString("username");
                        String emailResult = task.getResult().getString("email");
                        String passwordResult = task.getResult().getString("password");
                        String userIdResult = task.getResult().getString("userId");
                        if (task.getResult().getString("imageprofile") != null) {
                            Picasso.get().load(task.getResult().getString("imageprofile")).into(mImageView);


                        }
                        profile_type.setText("Student");
                        username_profile.setText(nameResult);
                        email_profile.setText(emailResult);
                        password_profile.setText(passwordResult);
                        id_profile.setText(userIdResult);
                        profile_type.setEnabled(false);
                        email_profile.setEnabled(false);
                        id_profile.setEnabled(false);
                        progressBarprofile.setVisibility(View.INVISIBLE);

                    } else if (task.getResult().get("status_s").toString().equalsIgnoreCase("2")) {
                        String nameResult = task.getResult().getString("username");
                        String emailResult = task.getResult().getString("email");
                        String passwordResult = task.getResult().getString("password");
                        String userIdResult = task.getResult().getString("userId");
                        if (task.getResult().getString("imageprofile") != null) {
                            Picasso.get().load(task.getResult().getString("imageprofile")).into(mImageView);


                        }
                        profile_type.setText("Visitor");
                        username_profile.setText(nameResult);
                        email_profile.setText(emailResult);
                        password_profile.setText(passwordResult);
                        id_profile.setText(userIdResult);
                        progressBarprofile.setVisibility(View.INVISIBLE);
                        profile_type.setEnabled(false);
                        email_profile.setEnabled(false);
                        id_profile.setEnabled(false);


                    }


                } else {
                    reference2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task2) {
                            String nameResult = task2.getResult().getString("username");
                            String emailResult = task2.getResult().getString("email");
                            String passwordResult = task2.getResult().getString("password");
                            String userIdResult = task2.getResult().getString("userId");


                            if (task2.getResult().getString("imageprofile") != null) {
                                Picasso.get().load(task2.getResult().getString("imageprofile")).into(mImageView);

                            }
                            profile_type.setText("Faculty");
                            username_profile.setText(nameResult);
                            email_profile.setText(emailResult);
                            password_profile.setText(passwordResult);
                            id_profile.setText(userIdResult);
                            profile_type.setEnabled(false);
                            email_profile.setEnabled(false);
                            id_profile.setEnabled(false);
                            progressBarprofile.setVisibility(View.INVISIBLE);

                        }
                    });

                    progressBarprofile.setVisibility(View.INVISIBLE);

                }
            }
        });

        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage(view);
                update_profile.setVisibility(View.VISIBLE);
            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarprofile.setVisibility(View.VISIBLE);
                String usernameupdate = username_profile.getText().toString();
                String passwordupdate = password_profile.getText().toString();
                String userState = profile_type.getText().toString();

                map.put("username", usernameupdate);
                map.put("password", passwordupdate);

                if (userState.equalsIgnoreCase("faculty")) {
                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(getContext(), "update Done for Email and password only", Toast.LENGTH_SHORT).show();
                        return ;
                    } else {
                        uploadFile2();
                    }

                    userprofilenode2.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressBarprofile.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "Update is success", Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            progressBarprofile.setVisibility(View.INVISIBLE);
                        }
                    });


                } else {
                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(getContext(), "update Done for Email and password only", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "update Done for Email and password only", Toast.LENGTH_SHORT).show();

                        uploadFile();
                    }
                    userprofilenode.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            progressBarprofile.setVisibility(View.INVISIBLE);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            progressBarprofile.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                updatePassword();
            }
        });


        return v;
    }

    private void uploadFile() {
        if (nImageUri != null) {
            StorageReference fileReference = mStorageRef.child(nImageUri.getLastPathSegment());

            mUploadTask = fileReference.putFile(nImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBarprofile.setVisibility(View.INVISIBLE);
                                }
                            }, 500);
                            Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String ImageUrl = uri.toString();
                                    map.put("imageprofile", ImageUrl);
                                    Toast.makeText(getContext(), "Update success", Toast.LENGTH_SHORT).show();

                                    userprofilenode.update(map);

                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBarprofile.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
//            Toast.makeText(this, "No file selected For Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFile2() {
        if (nImageUri != null) {
            StorageReference fileReference2 = mStorageRef.child(nImageUri.getLastPathSegment());

            mUploadTask = fileReference2.putFile(nImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBarprofile.setVisibility(View.INVISIBLE);
                                }
                            }, 500);
                            Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                            fileReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String ImageUrl = uri.toString();
                                    map.put("imageprofile", ImageUrl);
                                    Toast.makeText(getContext(), "Update success", Toast.LENGTH_SHORT).show();
                                    userprofilenode2.update(map);

                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBarprofile.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
//            Toast.makeText(this, "No file selected For Image", Toast.LENGTH_SHORT).show();
        }
    }

    public void UploadImage(View view) {
        openFileChooser();
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
            nImageUri = data.getData();
            Picasso.get().load(nImageUri).into(mImageView);
        }
    }

    public void updatePassword() {
        final String email = user.getEmail();

        String newPass = password_profile.getText().toString();
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, newPass);

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("password", "Password updated");
                                    } else {
                                        Log.d("password", "Error password not updated");
                                    }
                                }
                            });
                        } else {
                            Log.d("password", "Error auth failed");
                        }
                    }
                });
    }


}