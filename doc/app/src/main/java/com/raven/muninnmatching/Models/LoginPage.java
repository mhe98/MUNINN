package com.raven.muninnmatching.Models;
/**
 * This class lets users log into the app with their account.
 * @author Lewi Ayun, Mark He, Daniel Frye
 * Last Updated: 12/10/2019
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.raven.muninnmatching.Controllers.HomePage;
import com.raven.muninnmatching.R;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {
    Button loginBtn, backBtn, forgotPassBtn;
    EditText email, password;

    FirebaseAuth auth;

    /**
     * Loads view based on layout file specified in this method and sets buttons.
     * @param _savedInstanceState Loads previous data if available.
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.login_page);

        //Initialize buttons and input fields
        loginBtn = findViewById(R.id.login2);
        backBtn = findViewById(R.id.back);
        forgotPassBtn = findViewById(R.id.forgot_password);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        auth = FirebaseAuth.getInstance();

        //Checks for valid input for login before signing users in
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passText = password.getText().toString();

                if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passText)) {
                    Toast.makeText(LoginPage.this, "All fields are required.", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(emailText, passText)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginPage.this, "Failed to login.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        //Calls setOnClick() method to sets the onClickListener to this class.
        setOnClick();
    }

    /**
     * Links a button to an intent when the button is clicked on.
     * @param _view is the button that is being defined.
     */
    public void onClick(View _view) {
        int clickID = _view.getId();

        switch (clickID) {
            case R.id.back:
                toClass(WelcomePage.class);
                break;
            case R.id.forgot_password:
                toClass(ForgotPassword.class);
                break;
        }
    }

    /**
     * Sets the onClickListener to this class.
     */
    private void setOnClick() {
        //loginBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        forgotPassBtn.setOnClickListener(this);
    }

    /**
     * Starts an activity to go to the class that is specified in the parameter.
     * @param _next is the class the intent is going to.
     */
    private void toClass(Class _next) {
        finish();
        startActivity(new Intent(LoginPage.this, _next));
    }
}