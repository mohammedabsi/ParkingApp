package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private TextInputEditText username_reg, user_id_reg, password_reg, email_reg;
    private RadioButton StudentRadioButton, TeacherRadioButton;
    private ProgressBar progressBarRegister;
    ConstraintLayout registerConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();


        mAuth = FirebaseAuth.getInstance();

        username_reg = findViewById(R.id.username_reg);
        user_id_reg = findViewById(R.id.user_id_reg);
        password_reg = findViewById(R.id.password_reg);
        email_reg = findViewById(R.id.email_reg);
        StudentRadioButton = findViewById(R.id.StudentRadioButton);
        TeacherRadioButton = findViewById(R.id.teacherRadioButton);
        progressBarRegister = findViewById(R.id.progressBarRegister);
        registerConstraintLayout = findViewById(R.id.registerConstraintLayout);


    }

    public void returnLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void Register(View view) {
        closeKeyboard();

        String username = username_reg.getText().toString().trim();
        String user_id = user_id_reg.getText().toString().trim();
        String email = email_reg.getText().toString().trim();
        String password = password_reg.getText().toString().trim();


        boolean studentrad = StudentRadioButton.isChecked();
        boolean teacherrad = TeacherRadioButton.isChecked();

        if (username.isEmpty()) {
            username_reg.setError("Empty Field");
            username_reg.requestFocus();
            return;
        }
        if (user_id.isEmpty()) {
            user_id_reg.setError("Empty Field");
            user_id_reg.requestFocus();
            return;
        }
        if (studentrad == false && teacherrad == false) {

            TeacherRadioButton.setError("");
            StudentRadioButton.setError("");


        } else {
            TeacherRadioButton.setError(null);
            StudentRadioButton.setError(null);

        }
        if (password.isEmpty() || password.length() < 6) {

            password_reg.setError("The password should contain more than 6 symbols");
            password_reg.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            email_reg.setError("Please provide valid email");
            email_reg.requestFocus();
            return;

        }

        progressBarRegister.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {


                    if (studentrad == true) {
                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                        User studentSide = new User(username, user_id, password, email, false);

                        firestore.collection("Student Side").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(studentSide).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Register success :)", Toast.LENGTH_SHORT).show();
                                    progressBarRegister.setVisibility(View.VISIBLE);
                                    progressBarRegister.setVisibility(View.GONE);
                                    goHome();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Register Failed :(", Toast.LENGTH_SHORT).show();

                                    progressBarRegister.setVisibility(View.VISIBLE);
                                    progressBarRegister.setVisibility(View.GONE);


                                }
                            }
                        });

                    } else if (TeacherRadioButton.isChecked()) {

                        User teacherSide = new User(username, user_id, password, email, true);
                        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                        firestore.collection("Teacher_Side").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(teacherSide).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Register success :)", Toast.LENGTH_SHORT).show();
                                    progressBarRegister.setVisibility(View.GONE);

                                    // todo : it should return to the Home screen ;)
                                    goHome();
                                } else {
                                    Snackbar snackbar = Snackbar.make(registerConstraintLayout, "Register  Failed , try again", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    progressBarRegister.setVisibility(View.GONE);


                                }
                            }
                        });


                    }

                }
            }
        });


    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}