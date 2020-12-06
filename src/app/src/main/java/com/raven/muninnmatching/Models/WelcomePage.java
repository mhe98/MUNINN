package com.raven.muninnmatching.Models;
/**
 * This class displays the welcome page with functioning login and sign up buttons.
 * @author Lewi Ayun, Mark He, Daniel Frye
 * Last Updated: 12/10/2019
 */

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.raven.muninnmatching.Controllers.HomePage;
import com.raven.muninnmatching.R;

public class WelcomePage extends AppCompatActivity implements View.OnClickListener {
    Button loginBtn, signUpBtn;

    FirebaseUser firebaseUser;

    /**
     * Loads view based on layout file specified in this method and sets buttons.
     * @param _savedInstanceState Loads previous data if available.
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.welcome_page);

        //Initialize buttons and input fields
        loginBtn = findViewById(R.id.login1);
        signUpBtn = findViewById(R.id.signup1);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Checks if user is null. Used for auto login.
        if (firebaseUser != null) {
            toClass(HomePage.class);
        }

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
            case R.id.login1:
                toClass(LoginPage.class);
                break;
            case R.id.signup1:
                toClass(SignUp.class);
                break;
        }
    }

    /**
     * Sets the onClickListener to this class.
     */
    private void setOnClick() {
        loginBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
    }

    /**
     * Starts an activity to go to the class that is specified in the parameter.
     * @param _next is the class the intent is going to.
     */
    private void toClass(Class _next) {
        finish();
        startActivity(new Intent(this, _next));
    }
}