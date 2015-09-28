package com.travelguide.travelguide;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.plus.PlusClient;

/**
 * A base class to wrap communication with the Google Play Services PlusClient.
 */
public abstract class PlusBaseActivity extends AppCompatActivity
        implements ConnectionCallbacks,
        OnConnectionFailedListener {

    private static final String TAG = PlusBaseActivity.class.getSimpleName();
    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;
    private static final int RC_SIGN_IN = 0;
    // A magic number we will use to know that our sign-in error resolution activity has completed
    private static final int OUR_REQUEST_CODE = 49404;

    // A flag to stop multiple dialogues appearing for the user
    private boolean mAutoResolveOnFail;

    // A flag to track when a connection is already in progress
    public boolean mPlusClientIsConnecting = false;

    // This is the helper object that connects to Google Play Services.


    // The saved result from {@link #onConnectionFailed(ConnectionResult)}.  If a connection
    // attempt has been made, this is non-null.
    // If this IS null, then the connect method is still running.
    private ConnectionResult mConnectionResult;

    /**
     * Called when the {@link PlusClient} revokes access to this app.
     */
    protected abstract void onPlusClientRevokeAccess();

    /**
     * Called when the PlusClient is successfully connected.
     */
    protected abstract void onPlusClientSignIn();

    /**
     * Called when the {@link PlusClient} is disconnected.
     */
    protected abstract void onPlusClientSignOut();

    /**
     * Called when the {@link PlusClient} is blocking the UI.  If you have a progress bar widget,
     * this tells you when to show or hide it.
     */
    protected abstract void onPlusClientBlockingUI(boolean show);

    /**
     * Called when there is a change in connection state.  If you have "Sign in"/ "Connect",
     * "Sign out"/ "Disconnect", or "Revoke access" buttons, this lets you know when their states
     * need to be updated.
     */
    protected abstract void updateConnectButtonState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the PlusClient connection.
        // Scopes indicate the information about the user your application will be able to access.
      // findViewById(R.id.plus_sign_in_button).setOnClickListener(this);
       // mPlusClient = new GoogleApiClient.Builder(this).addApi(Drive.API).addScope(Drive.SCOPE_FILE).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();


    }



    /**
     * Connect the {@link PlusClient} only if a connection isn't already in progress.  This will
     * call back to {@link #onConnected(android.os.Bundle)} or
     * {@link #onConnectionFailed(com.google.android.gms.common.ConnectionResult)}.
     */


    /**
     * Disconnect the {@link PlusClient} only if it is connected (otherwise, it can throw an error.)
     * This will call back to {@link #onDisconnected()}.
     */


    /**
     * Sign out the user (so they can switch to another account).
     */


    /**
     * Revoke Google+ authorization completely.
     */



    public boolean isPlusClientConnecting() {
        return mPlusClientIsConnecting;
    }

    private void setProgressBarVisible(boolean flag) {
        mPlusClientIsConnecting = flag;
        onPlusClientBlockingUI(flag);
    }

    /**
     * A helper method to flip the mResolveOnFail flag and start the resolution
     * of the ConnectionResult from the failed connect() call.


    /**
     * An earlier connection failed, and we're now receiving the result of the resolution attempt
     * by PlusClient.
     *
     * @see #onConnectionFailed(ConnectionResult)
     */
//    @Override
//    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
//        updateConnectButtonState();
//        if (requestCode == OUR_REQUEST_CODE && responseCode == RESULT_OK) {
//            // If we have a successful result, we will want to be able to resolve any further
//            // errors, so turn on resolution with our flag.
//            mAutoResolveOnFail = true;
//            // If we have a successful result, let's call connect() again. If there are any more
//            // errors to resolve we'll get our onConnectionFailed, but if not,
//            // we'll get onConnected.
//            initiatePlusClientConnect();
//        } else if (requestCode == OUR_REQUEST_CODE && responseCode != RESULT_OK) {
//            // If we've got an error we can't resolve, we're no longer in the midst of signing
//            // in, so we can stop the progress spinner.
//            setProgressBarVisible(false);
//        }
//    }

    /**
     * Successfully connected (called by PlusClient)
     */
//    @Override
//    public void onConnected(Bundle connectionHint) {
//        updateConnectButtonState();
//        setProgressBarVisible(false);
//        onPlusClientSignIn();
//    }

    /**
     * Successfully disconnected (called by PlusClient)
//     */
//    @Override
//    public void onDisconnected() {
//        updateConnectButtonState();
//        onPlusClientSignOut();
//    }

    /**
     * Connection failed for some reason (called by PlusClient)
     * Try and resolve the result.  Failure here is usually not an indication of a serious error,
     * just that the user's input is needed.
     *
     * @see #onActivityResult(int, int, Intent)
     */
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//        updateConnectButtonState();
//
//        // Most of the time, the
//        // connection will fail with a user resolvable result. We can store
//        // that in our mConnectionResult property ready to be used when the user clicks the
//        // sign-in button.
//        if (result.hasResolution()) {
//            mConnectionResult = result;
//            if (mAutoResolveOnFail) {
//                // This is a local helper function that starts the resolution of the problem,
//                // which may be showing the user an account chooser or similar.
//                startResolution();
//            }
//        }
//    }
    @Override
    public void onConnectionSuspended(int i) {
    }




}
