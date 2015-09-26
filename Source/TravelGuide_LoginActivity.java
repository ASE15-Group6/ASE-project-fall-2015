        package com.travelguide.travelguide;

        import android.animation.Animator;
        import android.animation.AnimatorListenerAdapter;
        import android.annotation.TargetApi;

        import android.content.Intent;
        import android.content.IntentSender;
    import android.support.v4.app.Fragment;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.media.Image;
        import android.os.AsyncTask;
        import android.os.Build;
        import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.ViewGroup;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.inputmethod.EditorInfo;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ScrollView;
        import android.widget.TextView;

        import com.facebook.login.LoginManager;
        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.GooglePlayServicesUtil;
        import com.google.android.gms.common.SignInButton;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.drive.Drive;
        import com.google.android.gms.plus.Plus;
        import com.google.android.gms.plus.*;
        import com.google.android.gms.plus.model.people.Person;
    import com.facebook.FacebookSdk;
    import com.facebook.AccessToken;
    import com.facebook.AccessTokenTracker;
    import com.facebook.CallbackManager;
    import com.facebook.FacebookCallback;
    import com.facebook.FacebookException;
    import com.facebook.FacebookSdk;
    import com.facebook.Profile;
    import com.facebook.ProfileTracker;
    import com.facebook.login.LoginResult;
    import com.facebook.login.widget.LoginButton;
        import java.io.InputStream;
        import java.util.ArrayList;
        import java.util.List;

        /**
         * A login screen that offers login via email/password and via Google+ sign in.
         * <p/>
         * ************ IMPORTANT SETUP NOTES: ************
         * In order for Google+ sign in to work with your app, you must first go to:
         * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
         * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
         */
        public class TravelGuide_LoginActivity extends PlusBaseActivity implements GoogleApiClient.ConnectionCallbacks,
                GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {

           /**
             * Keep track of the login task to ensure we can cancel it if requested.
             */
            private GoogleApiClient mPlusClient;
            // UI references.
     private TextView mtextView;
            private View plusSignInButton;
            private View plusSignOutButton;
            private View userProfileInfo;
            private static final String TAG = PlusBaseActivity.class.getSimpleName();
            private boolean mIsResolving = false;
            private boolean mShouldResolve = false;
            private static final int RC_SIGN_IN = 0;
            private enum modesOfSignIn {google(0), fb(1);
                private int value; private modesOfSignIn(int value) {
            this.value=value;

            }
            public int getValue()
            {
                return value;
            }
            };
            private int currentModeOfSignIn;
    LoginButton loginButton;
            Profile profile;
     private CallbackManager callbackManager;
        private AccessTokenTracker mTokenTracker;
      private ProfileTracker mProfileTracker;
     private FacebookCallback<LoginResult> mCallback=new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken=loginResult.getAccessToken();

                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        Log.v("facebook - profile", currentProfile.getFirstName());
                        mProfileTracker.stopTracking();
                        profile = currentProfile;
                      //  displayWelcomeMessage(profile);
                    }
                };
                mProfileTracker.startTracking();



            }


    @Override
            public void onCancel() {

            }
     @Override
            public void onError(FacebookException e) {

            }
    };
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                FacebookSdk.sdkInitialize(getApplicationContext());
                callbackManager=CallbackManager.Factory.create();
                mTokenTracker=new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

                    }
                };
                mProfileTracker=new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                        if(newProfile!=null)
                        displayWelcomeMessage(newProfile);
                    }
                };
                mTokenTracker.startTracking();
                mProfileTracker.startTracking();
                setContentView(R.layout.activity_travel_guide__login);
                loginButton=(LoginButton)findViewById(R.id.fb_login_button);
                loginButton.setReadPermissions("user_friends");
                loginButton.registerCallback(callbackManager, mCallback);

                userProfileInfo=findViewById(R.id.plus_UserHome);
               // ScrollView loginView = (ScrollView)findViewById(R.id.login_form);
               // LinearLayout googlePLusContainer = (LinearLayout)loginView.findViewById(R.id.googlePlusLogin);
                plusSignInButton=findViewById(R.id.plus_sign_in_button);
                plusSignInButton.setOnClickListener(this);
                plusSignOutButton=findViewById(R.id.plus_sign_out_button);
                plusSignOutButton.setOnClickListener(this);
                mPlusClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this).addApi(Plus.API)
                        .addScope(Plus.SCOPE_PLUS_LOGIN).build();



            }



            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                callbackManager.onActivityResult(requestCode,resultCode,data);
                Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

                if (requestCode == RC_SIGN_IN) {
                    // If the error resolution was not successful we should not resolve further.
                    if (resultCode != RESULT_OK) {
                        mShouldResolve = false;
                    }

                    mIsResolving = false;
                    mPlusClient.connect();
                }
            }



            /**
             * Attempts to sign in or register the account specified by the login form.
             * If there are form errors (invalid email, missing fields, etc.), the
             * errors are presented and no actual login attempt is made.
             */

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.plus_sign_in_button) {
                    onSignInClicked();
                }
                if (v.getId() == R.id.plus_sign_out_button) {
                    if (mPlusClient.isConnected() && currentModeOfSignIn==0) {
                        Plus.AccountApi.clearDefaultAccount(mPlusClient);
                        mPlusClient.disconnect();

                    }
                    else if(currentModeOfSignIn==1)
                    {
                        LoginManager.getInstance().logOut();

                    }
                    showSignedOutUI();
                }
            }
            public GoogleApiClient getPlusClient() {
                return mPlusClient;
            }
            private void onSignInClicked() {
                // User clicked the sign-in button, so begin the sign-in process and automatically
                // attempt to resolve any errors that occur.
                mShouldResolve = true;
                mPlusClient.connect();

                // Show a message to the user that we are signing in.
                //mStatusTextView.setText(R.string.signing_in);
            }
            /**
             * Try to sign in the user.
             */
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                // Could not connect to Google Play Services.  The user needs to select an account,
                // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
                // ConnectionResult to see possible error codes.
                Log.d(TAG, "onConnectionFailed:" + connectionResult);

                if (!mIsResolving && mShouldResolve) {
                    if (connectionResult.hasResolution()) {
                        try {
                            connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                            mIsResolving = true;
                        } catch (IntentSender.SendIntentException e) {
                            Log.e(TAG, "Could not resolve ConnectionResult.", e);
                            mIsResolving = false;
                            mPlusClient.connect();
                        }
                    } else {
                        // Could not resolve the connection result, show the user an
                        // error dialog.
                        //showErrorDialog(connectionResult);
                    }
                } else {
                    // Show the signed-out UI
                    if(currentModeOfSignIn==0)
                    showSignedOutUI();
                }
            }
            public void showSignedInUI() {
                LinearLayout loginOptions=(LinearLayout)findViewById(R.id.user_Login_Options);
                loginOptions.setVisibility(View.GONE);
                //plusSignInButton.setVisibility(View.GONE);
                userProfileInfo.setVisibility(View.VISIBLE);

            }
            public void showSignedOutUI() {
                LinearLayout loginOptions=(LinearLayout)findViewById(R.id.user_Login_Options);
        userProfileInfo.setVisibility(View.GONE);
                loginOptions.setVisibility(View.VISIBLE);
            }

        private void displayWelcomeMessage(Profile profile) {

                TextView userName = (TextView)findViewById(R.id.plus_Username);
                userName.setText(profile.getName());
                String userPicURL = profile.getProfilePictureUri(50, 50).toString();
                ImageView profile_Pic= (ImageView)findViewById(R.id.plus_User_Pic);
                new LoadProfileImage(profile_Pic).execute(userPicURL);
               currentModeOfSignIn = modesOfSignIn.fb.getValue();
                showSignedInUI();

        }




            @Override
            protected void onStart() {
                super.onStart();
                mPlusClient.connect();
            }

            @Override
    protected void onResume()
    {
        super.onResume();
        if(currentModeOfSignIn==1) {
            Profile profile = Profile.getCurrentProfile();
            if (profile != null)
                displayWelcomeMessage(profile);
        }
    }
            @Override
            protected void onStop() {
                super.onStop();
                mTokenTracker.stopTracking();
            mProfileTracker.startTracking();
                mPlusClient.disconnect();
            }
            @Override
            public void onConnected(Bundle bundle) {
                // onConnected indicates that an account was selected on the device, that the selected
                // account has granted any requested permissions to our app and that we were able to
                // establish a service connection to Google Play services.
                Log.d(TAG, "onConnected:" + bundle);
                mShouldResolve = false;
                currentModeOfSignIn = modesOfSignIn.google.getValue();
                generateUserProfileInfo();
                showSignedInUI();
            }
        //This method would get the information of the user who has logged in.##
        private void generateUserProfileInfo()
        {
            Person currentUser= Plus.PeopleApi.getCurrentPerson(mPlusClient);
            if(currentUser!=null)
            {
                TextView userName = (TextView)findViewById(R.id.plus_Username);
                userName.setText(currentUser.getDisplayName());
                ImageView profile_Pic= (ImageView)findViewById(R.id.plus_User_Pic);
                String userPicURL = currentUser.getImage().getUrl().toString();
                userPicURL =userPicURL.substring(0,userPicURL.length());
                new LoadProfileImage(profile_Pic).execute(userPicURL);

            }
        }
            private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
                ImageView userImage;

                public LoadProfileImage(ImageView usrImage) {
                    this.userImage = usrImage;
                }

                protected Bitmap doInBackground(String... urls) {
                    String urldisplay = urls[0];
                    Bitmap profilePic = null;
                    try {
                        InputStream in = new java.net.URL(urldisplay).openStream();
                        profilePic = BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }
                    return profilePic;
                }

                protected void onPostExecute(Bitmap result) {
                    userImage.setImageBitmap(result);
                }
            }
            /**
             * Shows the progress UI and hides the login form.
             */



            @Override
            protected void onPlusClientSignIn() {
                //Set up sign out and disconnect buttons.
                Button signOutButton = (Button) findViewById(R.id.plus_sign_out_button);
                signOutButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // signOut();
                    }
                });

            }

            @Override
            protected void onPlusClientBlockingUI(boolean show) {
              //  showProgress(show);
            }

            @Override
            protected void updateConnectButtonState() {
                //TODO: Update this logic to also handle the user logged in by email.
                boolean connected = getPlusClient().isConnected();
        //
        //        mSignOutButtons.setVisibility(connected ? View.VISIBLE : View.GONE);
        //        mPlusSignInButton.setVisibility(connected ? View.GONE : View.VISIBLE);
        //        mEmailLoginFormView.setVisibility(connected ? View.GONE : View.VISIBLE);
            }

            @Override
            protected void onPlusClientRevokeAccess() {
                // TODO: Access to the user's G+ account has been revoked.  Per the developer terms, delete
                // any stored user data here.
            }

            @Override
            protected void onPlusClientSignOut() {

            }

            /**
             * Check if the device supports Google Play Services.  It's best
             * practice to check first rather than handling this as an error case.
             *
             * @return whether the device supports Google Play Services
             */
            private boolean supportsGooglePlayServices() {
                return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) ==
                        ConnectionResult.SUCCESS;
            }


            /**
             * Represents an asynchronous login/registration task used to authenticate
             * the user.
             */

        }

