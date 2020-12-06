package com.raven.muninnmatching.Models;
/**
 * This class lets users enter their email in to reset their passwords.
 * @author Lewi Ayun, Mark He, Daniel Frye
 * Last Updated: 12/10/2019
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.raven.muninnmatching.R;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {
    Button backBtn;
    Button resetBtn;
    EditText enterEmail;

    FirebaseAuth firebaseAuth;

    /**
     * Loads view based on layout file specified in this method and sets buttons.
     * @param _savedInstanceState Loads previous data if available.
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.forgot_password);

        //Initialize buttons and input fields
        backBtn = findViewById(R.id.back);
        resetBtn = findViewById(R.id.reset_password);
        enterEmail = findViewById(R.id.forgot_email);

        firebaseAuth = FirebaseAuth.getInstance();

        //Calls setOnClick() method to sets the onClickListener to this class.
        setOnClick();
    }

    /**
     * Links a button to an intent when the button is clicked on.
     * @param _view is the button that is being defined.
     */
    public void onClick(View _view){
        int clickID = _view.getId();

        switch (clickID) {
            case R.id.back:
                toClass(WelcomePage.class);
                break;
            case R.id.reset_password:
                String email = enterEmail.getText().toString();
                //Checks for no input
                if (email.equals("")) {
                    Toast.makeText(ForgotPassword.this, "Please Enter Your Email!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Check Your Email to Reset Password.",
                                        Toast.LENGTH_SHORT).show();
                                toClass(LoginPage.class);
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(ForgotPassword.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
        }
    }

    /**
     * Sets the onClickListener to this class.
     */
    private void setOnClick() {
        backBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
    }

    /**
     * Starts an activity to go to the class that is specified in the parameter.
     * @param _next is the class the intent is going to.
     */
    private void toClass(Class _next) {
        finish();
        startActivity(new Intent(ForgotPassword.this, _next));
    }
}
