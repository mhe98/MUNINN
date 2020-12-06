package com.raven.muninnmatching.API;
/**
 * This class lets users register with AppLozic, which is required to use their messaging API.
 * @author Lewi Ayun, Mark He, Daniel Frye
 * Last Updated: 12/10/2019
 */

import android.app.Activity;

import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.listners.AlLoginHandler;
import com.applozic.mobicomkit.listners.AlPushNotificationHandler;
import com.applozic.mobicomkit.uiwidgets.ApplozicSetting;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.raven.muninnmatching.R;

import java.util.HashMap;
import java.util.Map;

public class ApplozicAPI extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {
    FirebaseUser firebaseUser;

    private UserLoginTask mAuthTask = null;

    /**
     * This method is called when creating the view.
     * @param _savedInstanceState loads previous saved data.
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     * Registers new users to AppLozic database to use their messaging API. If user exists, it logs them in.
     * @param authenticationType The authentication type used is the default from AppLozic
     * @param _userId The users UUID String
     * @param _name The users name String
     * @param _email The users email String
     * @param _pass The users password String
     */
    public void attemptLoginAPI(User.AuthenticationType authenticationType, String _userId, String _name, String _email, String _pass) {
        if (mAuthTask != null) {
            return;
        }

        // callback for login process
        User user = new User();
        user.setUserId(_userId);
        user.setEmail(_email);
        user.setPassword(_pass);
        user.setDisplayName(_name);
        user.setAuthenticationTypeId(authenticationType.getValue());

        //Registers user to AppLozic
        Applozic.connectUser(this, user, new AlLoginHandler() {
            @Override
            public void onSuccess(RegistrationResponse registrationResponse, final Context context) {
                // After successful registration with Applozic server the callback will come here
                mAuthTask = null;
//                showProgress(false);

                Map<ApplozicSetting.RequestCode, String> activityCallbacks = new HashMap<ApplozicSetting.RequestCode, String>();
                activityCallbacks.put(ApplozicSetting.RequestCode.USER_LOOUT, ApplozicAPI.class.getName());
                ApplozicSetting.getInstance(context).setActivityCallbacks(activityCallbacks);

                //Start FCM registration....

                Applozic.registerForPushNotification(context, Applozic.getInstance(context).getDeviceRegistrationId(), new AlPushNotificationHandler() {
                    @Override
                    public void onSuccess(RegistrationResponse registrationResponse) {

                    }

                    @Override
                    public void onFailure(RegistrationResponse registrationResponse, Exception exception) {

                    }
                });
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                // If any failure in registration the callback  will come here
                mAuthTask = null;

                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                alertDialog.setMessage(exception == null ? registrationResponse.getMessage() : exception.getMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok_alert),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                if (!isFinishing()) {
                    alertDialog.show();
                }
            }
        });
    }
}