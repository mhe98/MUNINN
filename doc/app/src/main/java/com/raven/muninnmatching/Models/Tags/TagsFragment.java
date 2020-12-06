package com.raven.muninnmatching.Models.Tags;
/**
 * 11/19/2019
 * This class displays the Tags page which lets users edit their tags.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raven.muninnmatching.R;

import java.util.ArrayList;

public class TagsFragment extends Fragment implements View.OnClickListener{

    private int changing = 0;
    private String temp = "";
    private TextView tag_header, txt_tag1, txt_tag2, txt_tag3, txt_platform, txt_genre;
    private Button changeTag1, changeTag2, changeTag3, saveTag1, saveTag2, saveTag3;
    private Spinner spinner_platform, spinner_genre;
    ArrayAdapter<CharSequence> adapter_platform, adapter_genre;
    private String getPlatform, getGenre, tag1, tag2, tag3;
    private Button confirmBtn;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    String currentUid;

    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public View onCreateView(@NonNull LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {

        View root = _inflater.inflate(R.layout.fragment_tags, _container, false);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUid = firebaseUser.getUid();
        tag_header = root.findViewById(R.id.tags_title);
        txt_tag1 = root.findViewById(R.id.account_tag1);
        txt_tag2 = root.findViewById(R.id.account_tag2);
        txt_tag3 = root.findViewById(R.id.account_tag3);

        txt_platform = root.findViewById(R.id.platform_edit);
        txt_genre = root.findViewById(R.id.genre_edit);

        spinner_platform = root.findViewById(R.id.edit_platform);
        spinner_genre = root.findViewById(R.id.edit_genre);

        changeTag1 = root.findViewById(R.id.change_tags1);
        changeTag2 = root.findViewById(R.id.change_tags2);
        changeTag3 = root.findViewById(R.id.change_tags3);

        saveTag1 = root.findViewById(R.id.save_tag1);
        saveTag2 = root.findViewById(R.id.save_tag2);
        saveTag3 = root.findViewById(R.id.save_tag3);

        databaseReference.child(currentUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tag1 = dataSnapshot.child("Users").child(currentUid).child("tag1").getValue(String.class);
                tag2 = dataSnapshot.child("Users").child(currentUid).child("tag2").getValue(String.class);
                tag3 = dataSnapshot.child("Users").child(currentUid).child("tag3").getValue(String.class);

                txt_tag1.setText("Tag 1: "+ tag1);
                txt_tag2.setText("Tag 2: "+ tag2);
                txt_tag3.setText("Tag 3: "+ tag3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        ValueEventListener accountListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
//
//                UserInformation userData = dataSnapshot.child(firebaseUser.getUid()).getValue(UserInformation.class);
//                tag1 = userData.getTag1();
//                tag2 = userData.getTag2();
//                tag3 = userData.getTag3();
//
//                txt_tag1.setText("Tag 1: "+ tag1);
//                txt_tag2.setText("Tag 2: "+ tag2);
//                txt_tag3.setText("Tag 3: "+ tag3);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {databaseError.toException();
//            }
//        };


        changeTag1.setOnClickListener(this);
        changeTag2.setOnClickListener(this);
        changeTag3.setOnClickListener(this);

        // Load Platform Selection:

        spinner_platform = root.findViewById(R.id.edit_platform);
        adapter_platform = ArrayAdapter.createFromResource(getActivity(), R.array.tagPlatform_list, android.R.layout.simple_spinner_dropdown_item);
        spinner_platform.setAdapter(adapter_platform);
        spinner_platform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> _parent, View _view, int _position, long _id) {
                getPlatform = _parent.getItemAtPosition(_position).toString();
                if (!getPlatform.equals("")){

                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> _parent){
            }
        });

        // Load Genre Selection:

        spinner_genre = root.findViewById(R.id.edit_genre);
        adapter_genre = ArrayAdapter.createFromResource(getActivity(), R.array.tagGenre_list, android.R.layout.simple_spinner_dropdown_item);
        spinner_genre.setAdapter(adapter_genre);
        spinner_genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> _parent, View _view, int _position, long _id){
                getGenre = _parent.getItemAtPosition(_position).toString();
                if (!getGenre.equals("")){

                }
            }
            @Override
            public void onNothingSelected (AdapterView<?> _parent){
            }
        });

        saveTag1.setEnabled(false);
        saveTag2.setEnabled(false);
        saveTag3.setEnabled(false);
        setOnClick();
        return root;
    }

    private void editTag1(){

        tag1 = "";
        changing = 1;
        changeTag1.setEnabled(false);
        txt_platform.setVisibility(View.VISIBLE);
        txt_genre.setVisibility(View.VISIBLE);

        spinner_platform.setVisibility(View.VISIBLE);
        spinner_genre.setVisibility(View.VISIBLE);

        saveTag1.setEnabled(true);
        saveTag1.setOnClickListener(this);

    }
    private void editTag2(){

        tag2 = "";
        changing = 2;
        changeTag2.setEnabled(false);
        txt_platform.setVisibility(View.VISIBLE);
        txt_genre.setVisibility(View.VISIBLE);

        spinner_platform.setVisibility(View.VISIBLE);
        spinner_genre.setVisibility(View.VISIBLE);

        saveTag2.setEnabled(true);
        saveTag2.setOnClickListener(this);

    }
    private void editTag3(){

        tag3 = "";
        changing = 3;
        changeTag3.setEnabled(false);
        txt_platform.setVisibility(View.VISIBLE);
        txt_genre.setVisibility(View.VISIBLE);

        spinner_platform.setVisibility(View.VISIBLE);
        spinner_genre.setVisibility(View.VISIBLE);

        saveTag3.setEnabled(true);
        saveTag3.setOnClickListener(this);
    }

    /**
     * @return False if duplicate tags exist
     */
    private boolean isDupe(){
        temp = getPlatform + " - " + getGenre;
        if (temp.equals(tag1)){
            return false;
        } else if (temp.equals(tag2)){
            return true;
        } else if (temp.equals(tag3)) {
            return true;
        }
        return false;
    }


    private void setTag(){
        if (getPlatform.isEmpty()){
            Toast.makeText(getActivity(), "Please select a platform.", Toast.LENGTH_SHORT).show();
        } else if (getGenre.isEmpty()){
            Toast.makeText(getActivity(), "Please select a genre.", Toast.LENGTH_SHORT).show();
        } else if (isDupe()){
            Toast.makeText(getActivity(), "Tags cannot be the same.", Toast.LENGTH_SHORT).show();
        } else {
            ArrayList<Float> tempArrayList = new ArrayList<Float>();
            tempArrayList.add(5.0f);


            txt_platform.setVisibility(View.GONE);
            spinner_platform.setVisibility(View.GONE);

            txt_genre.setVisibility(View.GONE);
            spinner_genre.setVisibility(View.GONE);

            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUid);

            if (changing == 1){
                tag1 = getPlatform + " - " + getGenre;
                saveTag1.setEnabled(false);
                changeTag1.setEnabled(true);
                databaseReference.child("Users").child(currentUid).child("tag1").setValue(tag1);
                txt_tag1.setText("Tag 1: "+ tag1);
            } else if (changing == 2){
                tag2 = getPlatform + " - " + getGenre;
                saveTag2.setEnabled(false);
                changeTag2.setEnabled(true);
                databaseReference.child("Users").child(currentUid).child("tag2").setValue(tag2);
                txt_tag2.setText("Tag 2: "+ tag2);
            } else if (changing == 3) {
                tag3 = getPlatform + " - " + getGenre;
                saveTag3.setEnabled(false);
                changeTag3.setEnabled(true);
                databaseReference.child("Users").child(currentUid).child("tag3").setValue(tag3);
                txt_tag3.setText("Tag 3: " + tag3);
            }
        }
    }

    /**
     * Links a button to an intent when the button is clicked on.
     * @param _view is the button that is being defined.
     */
    public void onClick(View _view){
        int clickId = _view.getId();
        switch (clickId) {
            case R.id.change_tags1:
                editTag1();
                break;
            case R.id.change_tags2:
                editTag2();
                break;
            case R.id.change_tags3:
                editTag3();
                break;
            case R.id.save_tag1:
            case R.id.save_tag2:
            case R.id.save_tag3:
                setTag();
                break;
        }
    }

    /**
     * Assists onClick(View _view) method to get rid of a lot of copy paste code.
     * @param _view passes in view parameter from onClick.
     * @param btn passes in the button to be defined.
     * @param _next is the class the intent will go to.
     */
    private void onClickView(View _view, Button btn, Class _next) {
        if (_view == btn) {
            toClass(_next);
        }
    }

    /**
     * Sets the onClickListener to this class.
     */
    private void setOnClick() {
    }

    /**
     * Starts an activity to go to the class that is specified in the parameter.
     * @param _next is the class the intent is going to.
     */
    private void toClass(Class _next) {
        startActivity(new Intent(getActivity(), _next));
    }
}
