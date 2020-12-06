package com.raven.muninnmatching.Models.Account;
/**
 * 11/19/2019
 * This class displays the Accounts page which lets users edit their account information.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.listners.AlLogoutHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raven.muninnmatching.Data.UserInformation;
import com.raven.muninnmatching.R;
import com.raven.muninnmatching.Models.WelcomePage;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private TextView txt_username, txt_email, txt_city, txt_state, pc_username, pc_email, pc_city, pc_state;
    private EditText et_username, et_city;
    private String newState,newUsername, newCity;
    private Spinner spinner_state;
    private ArrayAdapter<CharSequence> adapter_state;

    private Button logoutBtn;
    private Button editProfile_btn;
    private Button confirmBtn;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private UserInformation userInfo;
    /**
     * This method loads the view for this fragment on create.
     *
     * @param _inflater
     * @param _container
     * @param _savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {

        View root = _inflater.inflate(R.layout.fragment_account, _container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        txt_username = root.findViewById(R.id.account_name);
        txt_email = root.findViewById(R.id.account_email);
        txt_city = root.findViewById(R.id.account_city);
        txt_state = root.findViewById(R.id.account_state);

        pc_username = root.findViewById(R.id.placeholder_username);
        pc_email = root.findViewById(R.id.placeholder_email);
        pc_city= root.findViewById(R.id.placeholder_city);
        pc_state = root.findViewById(R.id.placeholder_state);


        et_username = root.findViewById(R.id.edit_name);
        et_city = root.findViewById(R.id.edit_city);

        confirmBtn = root.findViewById(R.id.confirm_button);
        editProfile_btn = root.findViewById(R.id.edit_account);
        logoutBtn = root.findViewById(R.id.account_logout);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getUid();

        spinner_state = root.findViewById(R.id.edit_state_list);
        adapter_state = ArrayAdapter.createFromResource(getActivity(), R.array.state_names, android.R.layout.simple_spinner_dropdown_item);
        spinner_state.setAdapter(adapter_state);
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> _parent, View _view, int _position, long _id) {
                newState = _parent.getItemAtPosition(_position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> _parent) {
            }
        });

        loadUserInfo();
//        loadListeners();
        logoutBtn.setOnClickListener(this);
        editProfile_btn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        return root;
    }

    private void loadUserInfo(){

//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        ValueEventListener accountListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
//                String email = dataSnapshot.child("prod").child("users").getValue(userID).getValue("email").toString;
//                pc_username.setText(user.getUsername().trim());
//                pc_email.setText(user.getEmail().trim());
//                pc_city.setText( user.getCity().trim());
//                pc_state.setText(user.getState().trim());
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {databaseError.toException();
//            }
//        };
    }


    /**
     * Changing User's information loads the edit textboxes and buttons needed to edit information.
     */
    private void changeUserInfo() {
        et_username.setVisibility(View.VISIBLE);
        et_city.setVisibility(View.VISIBLE);
        spinner_state.setVisibility(View.VISIBLE);
        editProfile_btn.setVisibility(View.GONE);
        confirmBtn.setVisibility(View.VISIBLE);
        confirmBtn.setOnClickListener(this);

        pc_username.setVisibility(View.GONE);
        pc_city.setVisibility(View.GONE);
        pc_state.setVisibility(View.GONE);
    }

    /**
     * Overwrites new information into database and saves user's new information.
     */
    private void confirmChange(){
        // Here lies the functioning code to save user's information

        newUsername = et_username.getText().toString().trim();
        newCity = et_city.getText().toString().trim();

        if (TextUtils.isEmpty(newUsername)){
            Toast.makeText(getActivity(), "Please enter a username.", Toast.LENGTH_SHORT).show();
            return;
        } else if (newUsername.length() > 12){
            Toast.makeText(getActivity(),"Username must be less than 12 characters long.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newCity)){
            Toast.makeText(getActivity(),"Please enter your city.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newState.equals("")){
            Toast.makeText(getActivity(), "Please select a state.", Toast.LENGTH_SHORT).show();
            return;
        }

//        String userID = firebaseAuth.getUid();
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
//        String username = databaseReference.child("users").child(userID).child("username").setValue(newUsername).toString();
//        String city = databaseReference.child("users").child(userID).child("city").setValue(newCity).toString();
//        String state = databaseReference.child("users").child(userID).child("state").setValue(newState).toString();


        et_username.setVisibility(View.GONE);
        et_city.setVisibility(View.GONE);
        spinner_state.setVisibility(View.GONE);
        editProfile_btn.setVisibility(View.VISIBLE);
        confirmBtn.setVisibility(View.GONE);

        pc_username.setVisibility(View.VISIBLE);
        pc_city.setVisibility(View.VISIBLE);
        pc_state.setVisibility(View.VISIBLE);

//        pc_username.setText(username.trim());
//        pc_city.setText(city.trim());
//        pc_state.setText(state.trim());

        Toast.makeText(getActivity(), "Information Successfully Updated!",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Sign out user from Firebase database, then sends them back to the Welcome Page class
     */
    private void logOut() {
//        if (firebaseAuth != null) {
//            firebaseAuth.signOut();
//            Intent loggedOut = new Intent(getActivity(), WelcomePage.class);
//            startActivity(loggedOut);
//        }

        FirebaseAuth.getInstance().signOut();
        Applozic.logoutUser(getActivity(), new AlLogoutHandler() {
            @Override
            public void onSuccess(Context context) {
                Intent intent = new Intent(context, WelcomePage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }

            @Override
            public void onFailure(Exception exception) {
            }
        });
    }

    @Override
    public void onClick(View _view) {
        int clickId = _view.getId();
        switch (clickId) {
            case R.id.edit_account:
                changeUserInfo();
                break;
            case R.id.confirm_button:
                confirmChange();
                break;
            case R.id.account_logout:
                logOut();
                break;
        }
    }
}