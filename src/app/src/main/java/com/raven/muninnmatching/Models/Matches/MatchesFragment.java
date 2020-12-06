package com.raven.muninnmatching.Models.Matches;
/**
 * 11/19/2019
 * This class displays the Matches page which lets users view their new and old matches.
 * Users can also choose to message their matches from this page.
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import com.raven.muninnmatching.R;



public class MatchesFragment extends Fragment implements View.OnClickListener {

    //buttons
    private Button matchBtn;
    private Button backBtn;

    //firebase
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    //globals
    private String tag1, tag2, tag3, key;
    private String uTag1, uTag2, uTag3;
    private Boolean isMatched;
    private Double dontBeMad = 0.67;
    private Double matchPercent;
    private int countM, tagNum;
    private String currentUid, currentEmail, uid, email;
    private ArrayList<String> matchedUsers;

    //TAG
    private final String TAG = "MatchesFragment";


    public View onCreateView(@NonNull LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {

        //gets current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUid = currentUser.getUid();
        currentEmail = currentUser.getEmail();

        databaseReference.child(currentUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uTag1 = dataSnapshot.child("tag1").getValue(String.class);
                uTag2 = dataSnapshot.child("tag2").getValue(String.class);
                uTag3 = dataSnapshot.child("tag3").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //preset values and user values
        tag1 = "";
        tag2 = "";
        tag3 = "";


        View root = _inflater.inflate(R.layout.fragment_matches, _container, false);

        //matchBtn = matchBtn.findViewById(R.id.matches_title);
        //matchBtn = root.findViewById(R.id.matches_title);
        matchBtn = root.findViewById(R.id.matches_btn);
        //matchBtn.setOnClickListener(this);
        //Calls setOnClick() method to sets the onClickListener to this class.
        setOnClick();

        return root;
    }

    //helper method for matching method within onclick method
    private int isMatched(String _tag1A, String _tag1B){
        int count;
        if(_tag1A == _tag1B && _tag1A != null){
            count = 1;
            return count;
        }else{
            count = 0;
            return count;
        }
    }

    //helper method that returns a double value
    public double matchPercent(int _countM, int _tagNum){
        if(_countM > _tagNum){
            return ((double) _tagNum)/ (double) _tagNum;
        }else {
            return ((double) _countM / (double) _tagNum);
        }
    }

    //method that adds user emails into an arraylist matchedUsers that meet the match criteria
    private void usersMatched(double _matchPercent, String _email){
        if(_matchPercent > dontBeMad){
            matchedUsers.add(_email);
        }
    }

    //here to see what gets feed to the console--for testing purposes
    private void printMatchedUsers(){
        for(String elements: matchedUsers){
            Log.d(TAG, "The Matched Users");
            System.out.println(elements);
        }
    }
    /**
     void searchForMatches(Iterator<DataSnapshot> _iter){
     User user;
     while(_iter.hasNext()){
     DataSnapshot child = _iter.next();
     }
     }
     **/
    //just used to be called for the datasnapshot firebase method
    private void dataReference(){
        FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot _dataSnapshot) {
                key = databaseReference.child(firebaseUser.getUid()).getKey();
                if(_dataSnapshot.exists()){

                    //this is a place holder till I figure out how to do it properly
                    tagNum = 3;

                    //iterates through firebase database to get tags from other users
                    for(DataSnapshot childSnapshot : _dataSnapshot.getChildren()){
                        _dataSnapshot.getKey();
                        //gets user info from snapshot
//                        User user = childSnapshot.getValue(User.class);
//                        email = childSnapshot.child("email").getValue(String.class);
                        if(childSnapshot.child("email").getValue() != null){
                            email = childSnapshot.child("email").getValue(String.class);
                        }
                        //checks if tag1 is in database and compares
                        if(childSnapshot.child("tag1").getValue() != null){
                            tag1 = childSnapshot.child("email").getKey();
                            //sends to isMatched and gets value of one or zero
                            countM += isMatched(tag1, uTag1);
                        }

                        //compares tag2
                        if(childSnapshot.child("tag2").getValue() != null){
                            tag2 = childSnapshot.child("tag2").getKey();
                            countM += isMatched(tag2, uTag2);
                        }
                        //compares tag3
                        if(childSnapshot.child("tag3").getValue() != null){
                            tag3 = childSnapshot.child("tag3").getKey();
                            countM += isMatched(tag3, uTag3);
                        }
                        //checks if to ensure that user is not being compared to itself for final stage

//                        if(!email.equals(currentEmail)) {
                        //gets match percent in dec
                        matchPercent = matchPercent(countM, tagNum);
                        //checks if users are a good match if so adds user's email to list
                        usersMatched(matchPercent, email);
//                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.d(TAG, "We Failed");
                //System.out.println("We Failed at MatchUser:(line 167)");
            }
        });
    }

    /**
     * Links a button to an intent when the button is clicked on.
     * @param _view is the button that is being defined.
     *              Once the button is clicked this method will iterate throughout the firebase database and match
     *                  based on existing tags if the matched percent is greater than 67% then that user will be added
     *                  to the matched users list
     */
    public void onClick(View _view){
        if(_view == matchBtn) {
            dataReference();
        }
    }

    private void setOnClick(){
        matchBtn.setOnClickListener(this);
    }




    /**
     * Starts an activity to go to the class that is specified in the parameter.
     * @param _next is the class the intent is going to.
     */
    private void toClass(Class _next) {
        startActivity(new Intent(getActivity(), _next));
    }
}
