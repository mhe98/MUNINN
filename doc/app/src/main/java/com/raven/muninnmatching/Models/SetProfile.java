package com.raven.muninnmatching.Models;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raven.muninnmatching.R;
import com.raven.muninnmatching.Controllers.HomePage;
import com.raven.muninnmatching.Data.UserInformation;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SetProfile extends AppCompatActivity implements View.OnClickListener{

    private EditText editUsername, editCity;
    private TextView viewEmail, viewState;
    private TextView displayTag1, displayTag2, displayTag3;
    private TextView selectPlatformTxt, selectGenreTxt;
    private TextView onClickNewTag;

    private Spinner spinnerState, spinnerPlatform, spinnerGenre;
    ArrayAdapter<CharSequence> stateAdapter, platformAdapter, genreAdapter;

    private Button saveTag, confirmInfo;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    String currentUid;

    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private String uid = "";
    private String username = "";
    private String email;
    private String displayEmail;
    private String city = "";
    private String state = "";
    private String getPlatform = "";
    private String getGenre = "";
    private String tag1 = "";
    private String tag2 = "";
    private String tag3 = "";

    private String temp = "";

    /**
     * Loads edit textboxes & spinners for users to fill in profile information.
     * @param _savedInstanceState
     */
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUid = firebaseUser.getUid();
        displayEmail = firebaseUser.getEmail();

        loadViews();
        loadSpinners();
        loadListeners();
    }

    public void loadViews(){
        editUsername = (EditText) findViewById(R.id.editTextUsername);
        viewEmail = (TextView) findViewById(R.id.email_display);
        editCity = (EditText) findViewById(R.id.editTextCity);
        viewState = (TextView) findViewById (R.id.state_text);
        selectPlatformTxt = (TextView) findViewById(R.id.select_platformtxt);
        selectGenreTxt = (TextView) findViewById(R.id.select_genretxt);
        displayTag1 = (TextView)  findViewById(R.id.placeholdertag1);
        displayTag2 = (TextView)  findViewById(R.id.placeholdertag2);
        displayTag3 = (TextView)  findViewById(R.id.placeholdertag3);
        onClickNewTag = (TextView) findViewById (R.id.add_tag);
        confirmInfo = (Button) findViewById(R.id.confirmButton);
        saveTag = (Button) findViewById(R.id.save_tag);
        displayTag1.setText("Tag 1: "+ tag1);
        displayTag2.setText("Tag 2: "+ tag2);
        displayTag3.setText("Tag 3: "+ tag3);
        viewEmail.setText("Email: "+ displayEmail);

    }

    public void loadSpinners(){
        // Load State Selection:
        spinnerState = (Spinner) findViewById(R.id.state_list);
        stateAdapter = ArrayAdapter.createFromResource(SetProfile.this, R.array.state_names, android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(stateAdapter);
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> _parent, View _view, int _position, long _id){
                state = _parent.getItemAtPosition(_position).toString();
                if (!state.equals("")){
                    Toast.makeText(getBaseContext(), _parent.getItemAtPosition(_position) + " selected!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> _parent){
            }
        });

        // Load Platform Selection:
        spinnerPlatform = (Spinner) findViewById(R.id.platform_btn);
        platformAdapter = ArrayAdapter.createFromResource(this, R.array.tagPlatform_list, android.R.layout.simple_spinner_dropdown_item);
        spinnerPlatform.setAdapter(platformAdapter);
        spinnerPlatform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> _parent, View _view, int _position, long _id){
                getPlatform = _parent.getItemAtPosition(_position).toString();
                if (!getPlatform.equals("")){
                    Toast.makeText(getBaseContext(), _parent.getItemAtPosition(_position) + " selected!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> _parent){
            }
        });

        // Load Genre Selection:
        spinnerGenre = (Spinner) findViewById(R.id.genre_btn);
        genreAdapter = ArrayAdapter.createFromResource(this, R.array.tagGenre_list, android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(genreAdapter);
        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> _parent, View _view, int _position, long _id){
                getGenre = _parent.getItemAtPosition(_position).toString();
                if (!getGenre.equals("")){
                    Toast.makeText(getBaseContext(), _parent.getItemAtPosition(_position) + " selected!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> _parent){
            }
        });

    }

    public void loadTagsOptions(){
        selectPlatformTxt.setVisibility(TextView.VISIBLE);
        spinnerPlatform.setVisibility(View.VISIBLE);

        selectGenreTxt.setVisibility(TextView.VISIBLE);
        spinnerGenre.setVisibility(View.VISIBLE);

        saveTag.setVisibility(View.VISIBLE);
        onClickNewTag.setVisibility(View.GONE);
        confirmInfo.setVisibility(View.GONE);
    }

    public void loadListeners(){
        onClickNewTag.setOnClickListener(this);
        saveTag.setOnClickListener(this);
        confirmInfo.setOnClickListener(this);
    }

    /**
     * Helper method to guard against duplicate tags.
     * @return
     */
    private boolean isDupe(){
        temp = getPlatform + " - " + getGenre;
        if (tag1.isEmpty()){
            return false;
        } else if (temp.equals(tag1)){
            return true;
        } else if (temp.equals(tag2)) {
            return true;
        }
        return false;
    }

    /**
     * Saves user selected Tag.
     */
    private void setTag(){
        if (getPlatform.isEmpty()){
            Toast.makeText(this, "Please select a platform.", Toast.LENGTH_SHORT).show();
        } else if (getGenre.isEmpty()){
            Toast.makeText(this, "Please select a genre.", Toast.LENGTH_SHORT).show();
        } else if (isDupe()){
            Toast.makeText(this, "Cannot create duplicate tags.", Toast.LENGTH_SHORT).show();
        } else {
            selectPlatformTxt.setVisibility(View.GONE);
            selectGenreTxt.setVisibility(View.GONE);
            spinnerPlatform.setVisibility(View.GONE);
            spinnerGenre.setVisibility(View.GONE);
            saveTag.setVisibility(View.INVISIBLE);

            if (tag1.isEmpty()){
                onClickNewTag.setVisibility(View.VISIBLE);
                tag1 = getPlatform + " - " + getGenre;
                displayTag1.setText("Tag 1: "+ tag1);
            } else if (tag2.isEmpty()){
                tag2 = getPlatform + " - " + getGenre;
                displayTag2.setText("Tag 2: "+ tag2);
                onClickNewTag.setVisibility(View.VISIBLE);
            }  else if (tag3.isEmpty()) {
                tag3 = getPlatform + " - " + getGenre;
                displayTag3.setText("Tag 3: " + tag3);

                confirmInfo.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Saves username, email, city, state, and tags into Firebase database.
     */
    private void saveUserInfo(){

        username = editUsername.getText().toString();
        city = editCity.getText().toString();
        email = displayEmail;

        // Multiple checks on user input.
        if (TextUtils.isEmpty(username)){
            Toast.makeText(this, "Please enter your username.", Toast.LENGTH_SHORT).show();
            return;
        } else if (username.length() > 12){
            Toast.makeText(this, "Username must be less than 12 characters long.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(city)){
            Toast.makeText(this, "Please enter your city.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (state.equals("")){
            Toast.makeText(this, "Please select a state.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tag1.isEmpty()){
            Toast.makeText(this, "Please fill Tag 1.", Toast.LENGTH_SHORT).show();
            return;
        } else if (tag2.isEmpty()){
            Toast.makeText(this, "Please fill Tag 2.", Toast.LENGTH_SHORT).show();
            return;
        } else if (tag3.isEmpty()){
            Toast.makeText(this, "Please fill Tag 3.", Toast.LENGTH_SHORT).show();
            return;
        }


        Toast.makeText(this, "Saving information...", Toast.LENGTH_SHORT).show();

        //Stores user's info into UserInformation Class & Firebase database
        UserInformation userInfo = new UserInformation(username, city, state, tag1, tag2, tag3);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUid);
        databaseReference.setValue(userInfo);

        if (userInfo!=null) {
            finish();
            startActivity(new Intent(this, HomePage.class));
        } else {
            Toast.makeText(this, "Check information again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick (View _view) {
        int clickId = _view.getId();
        switch (clickId) {
            case R.id.add_tag:
                loadTagsOptions();
                break;
            case R.id.save_tag:
                setTag();
                break;
            case R.id.confirmButton:
                saveUserInfo();
                break;
        }
    }

    /**
     * Prevents the user from going back to the previous screen.
     */
    @Override
    public void onBackPressed() {
    }
}