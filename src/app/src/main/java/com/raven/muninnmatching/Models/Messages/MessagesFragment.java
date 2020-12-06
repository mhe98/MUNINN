package com.raven.muninnmatching.Models.Messages;
/**
 * This class displays the Messages page which displays the private messaging system.
 * @author Lewi Ayun, Mark He, Daniel Frye
 * Last Updated: 12/10/2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.raven.muninnmatching.R;

public class MessagesFragment extends Fragment implements View.OnClickListener {
    private Button messageListBtn;
    FirebaseAuth firebaseAuth;

    public View onCreateView(@NonNull LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {

        View root = _inflater.inflate(R.layout.fragment_messages, _container, false);

        messageListBtn = root.findViewById(R.id.message_list);

        firebaseAuth = FirebaseAuth.getInstance();

        //Calls setOnClick() method to sets the onClickListener to this class.
        setOnClick();

        return root;
    }

    /**
     * Links a button to an intent when the button is clicked on.
     * @param _view is the button that is being defined.
     */
    public void onClick(View _view){
        int clickID = _view.getId();

        switch (clickID) {
//            case R.id.message_start:
//                Intent intent = new Intent(getActivity(), ConversationActivity.class);
//                intent.putExtra(ConversationUIService.USER_ID, "receiveruserid123");
//                intent.putExtra(ConversationUIService.DISPLAY_NAME, "Receiver display name"); //put it for displaying the title.
//                intent.putExtra(ConversationUIService.TAKE_ORDER,true); //Skip chat list for showing on back press
//                startActivity(intent);
//                break;
            case R.id.message_list:
                toClass(ConversationActivity.class);
                break;
        }
    }

    /**
     * Sets the onClickListener to this class.
     */
    private void setOnClick() {
        messageListBtn.setOnClickListener(this);
    }

    /**
     * Starts an activity to go to the class that is specified in the parameter.
     * @param _next is the class the intent is going to.
     */
    private void toClass(Class _next) {

        startActivity(new Intent(getActivity(), _next));
    }
}