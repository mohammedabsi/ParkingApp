package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText email_login, password_login;
    private ProgressBar progressBarlogin;
    private FirebaseAuth mAuth;
    private ConstraintLayout loginConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        progressBarlogin = findViewById(R.id.progressBarlogin);
        loginConstraintLayout = findViewById(R.id.loginConstraintLayout);
        mAuth = FirebaseAuth.getInstance();


    }

    public void goHome(View view) {
        closeKeyboard();

        String email = email_login.getText().toString().trim();
        String password = password_login.getText().toString().trim();

        if (email.isEmpty()) {
            email_login.setError("Enter e-mail");
            email_login.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            password_login.setError("Enter e-mail");
            password_login.setError("Enter your password");
            return;
        }
        progressBarlogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));



                    finish();
                } else {
                    Snackbar snackbar = Snackbar.make(loginConstraintLayout, "Login Failed , try again", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    progressBarlogin.setVisibility(View.GONE);

                }
            }
        });


    }

    public void returnRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}