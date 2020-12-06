package com.raven.muninnmatching.Models;
/**
 * This class lets users register a new account.
 * @author Lewi Ayun, Mark He, Daniel Frye
 * Last Updated: 12/10/2019
 */

import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.raven.muninnmatching.API.ApplozicAPI;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raven.muninnmatching.R;
import java.util.HashMap;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private Button registerBtn, loginBtn, backBtn;
    EditText name, email, password1, password2;

    FirebaseAuth auth;
    DatabaseReference db;
    private UserLoginTask mAuthTask = null;

    public static final int minPassLength = 6;

    /**
     * Loads view based on layout file specified in this method and sets buttons.
     * @param _savedInstanceState Loads previous data if available.
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.signup_page);

        //Initialize buttons and input fields
        registerBtn = findViewById(R.id.register);
        loginBtn = findViewById(R.id.goto_login);
        backBtn = findViewById(R.id.back);
        name = findViewById(R.id.login_name);
        email = findViewById(R.id.login_email);
        password1 = findViewById(R.id.login_password);
        password2 = findViewById(R.id.login_password2);

        auth = FirebaseAuth.getInstance();

        //Checks for correct input fields when creating an account.
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _v) {
                String nameText = name.getText().toString();
                String emailText = email.getText().toString();
                String passText1 = password1.getText().toString().trim();
                String passText2 = password2.getText().toString().trim();

                //Calls checkEmail method to check for existing email
                checkEmail(_v);
                if (TextUtils.isEmpty(nameText) || TextUtils.isEmpty(emailText) ||
                        TextUtils.isEmpty(passText1) || TextUtils.isEmpty(passText2)) {
                    Toast.makeText(SignUp.this,"All fields are required.", Toast.LENGTH_SHORT).show();
                } else if (!passText1.matches(passText2)) {
                    Toast.makeText(SignUp.this,"Passwords do not match.", Toast.LENGTH_SHORT).show();
                } else if (passText1.length() < minPassLength) {
                    Toast.makeText(SignUp.this,"Password must be at least 6 characters.", Toast.LENGTH_SHORT).show();
                } else if (passText1.matches(passText2)) {
                    register(nameText, emailText, passText1);
                }
            }
        });

        //Calls setOnClick() method to sets the onClickListener to this class.
        setOnClick();
    }

    /**
     * Registers new users to Firebase database as well as to AppLozic for messaging API.
     * @param _name users name
     * @param _email users email
     * @param _pass users password
     */
    private void register(String _name, String _email, String _pass) {
        auth.createUserWithEmailAndPassword(_email, _pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userId = firebaseUser.getUid();

                            db = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("name", _name);
                            hashMap.put("imageURL", "default");

                            Toast.makeText(SignUp.this, "Successfully Registered.", Toast.LENGTH_SHORT).show();

//                            attemptLoginAPI(User.AuthenticationType.APPLOZIC, userId, _name, _email, _pass);

                            //Calls ApplozicAPI class to register users to AppLozic
                            ApplozicAPI api = new ApplozicAPI();
                            api.attemptLoginAPI(User.AuthenticationType.APPLOZIC, userId, _name, _email, _pass);

                            //Takes users to SetProfile class which allows users to set up their profile.
                            Intent mainActvity = new Intent(SignUp.this, SetProfile.class);
                            startActivity(mainActvity);
                            finish();
                        }
                    }
                });
    }

    /**
     * Checks if email already exists on signup.
     * @param _v
     */
    public void checkEmail(View _v) {
        auth.fetchSignInMethodsForEmail(email.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        //Checks if email does not exist.
                        boolean isNewEmail = task.getResult().getSignInMethods().isEmpty();

                        //Email already exists
                        if (!isNewEmail) {
                            Toast.makeText(SignUp.this, "Email Already Exists.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Links a button to an intent when the button is clicked on.
     * @param _view is the button that is being defined.
     */
    public void onClick(View _view){
        int clickID = _view.getId();

        switch (clickID) {
            case R.id.goto_login:
                toClass(LoginPage.class);
                break;
            case R.id.back:
                toClass(WelcomePage.class);
                break;
            }
    }

    /**
     * Sets the onClickListener to this class.
     */
    private void setOnClick() {
        loginBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    /**
     * Starts an activity to go to the class that is specified in the parameter.
     * @param _next is the class the intent is going to.
     */
    private void toClass(Class _next) {
        finish();
        startActivity(new Intent(SignUp.this, _next));
    }
}