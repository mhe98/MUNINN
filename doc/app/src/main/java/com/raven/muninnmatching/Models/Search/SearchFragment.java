package com.raven.muninnmatching.Models.Search;
/**
 * This class displays the Search page which lets users start a search to match them with
 * other people who share similar interests.
 *
 * @author Lewi Ayun, Mark He, Daniel Frye
 * Last Updated: 12/10/2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.raven.muninnmatching.R;

public class SearchFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {

        View root = _inflater.inflate(R.layout.fragment_search, _container, false);

        //Calls setOnClick() method to sets the onClickListener to this class.
        setOnClick();

        return root;
    }

    /**
     * Links a button to an intent when the button is clicked on.
     * @param _view is the button that is being defined.
     */
    public void onClick(View _view) {

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