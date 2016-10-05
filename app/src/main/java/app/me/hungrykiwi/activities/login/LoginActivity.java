package app.me.hungrykiwi.activities.login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.activities.main.MainActivity;
import app.me.hungrykiwi.http.Config;
import app.me.hungrykiwi.service.UserService;
import app.me.hungrykiwi.utils.Utility;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    // --- FACEBOOK --- ///
    CallbackManager mFaceManager;
    AccessTokenTracker mFaceTokenTracker;

    // --- GOOGLE --- ///
    GoogleApiClient mGoogleClient;
    int RC_GOOGLE_SIGN_IN = 1; // google sign in request code for onResultActivity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        this.initGoogle(); // init google log in
        this.initFace(); // init facebook log in

    }

    /**
     * init google login configuration
     */
    public void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestIdToken(this.getString(R.string.google_web_client_id))
                .requestEmail()
                .build();

        this.mGoogleClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        this.mGoogleClient.connect();

        SignInButton btnGoogle = (SignInButton) this.findViewById(R.id.btnGoogle);
        btnGoogle.setScopes(gso.getScopeArray());
        btnGoogle.setOnClickListener(this);
        int count = btnGoogle.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = btnGoogle.getChildAt(i);
            if (v instanceof TextView) {
                TextView text = (TextView) v;
                text.setText("Log in with Google ");
                text.setTextSize(16f);
            }
        }
        this.autoGoogleSignin();
    }

    /**
     * log-in with google
     */
    public void autoGoogleSignin() {
        try {
            OptionalPendingResult<GoogleSignInResult> pendingResult =
                    Auth.GoogleSignInApi.silentSignIn(this.mGoogleClient);
            if (pendingResult.isDone()) { // if user is logined with google
                this.handleGoogleResult(pendingResult.get());
            }
        } catch (Exception ex) {

        }
    }

    /**
     * auto login with facebook
     */
    public void autoFaceSignin() {
        try {
            if (AccessToken.getCurrentAccessToken() != null) // auto login
                this.requestFaceEmail(AccessToken.getCurrentAccessToken());
        } catch (Exception ex) {

        }
    }

    /**
     * when signed in successfully with facebook
     *
     * @param value
     */
    public void handleFaceResult(JSONObject value, AccessToken token) {
        try {
            Profile profile = Profile.getCurrentProfile();
            if (value != null && profile != null) {
                String email = value.getString("email");
                String fName = value.getString("first_name");
                String lName = value.getString("last_name");
                String imgCover = value.getJSONObject("cover").getString("source");
                String imgPath = profile.getProfilePictureUri(120, 120).getPath();
                new UserService(this).login(email, fName, lName, "http://graph.facebook.com/" + imgPath, imgCover, Config.LoginProvider.FACEBOOK, token.getToken());
            }
        } catch (Exception ex) {
            Log.d("INFO", "FACE LOGIN ERROR : " + ex.getMessage());
        }
    }

    /**
     * facebook sdk init and set configuration
     */
    public void initFace() {
        LoginButton btnFace = (LoginButton) this.findViewById(R.id.btnFace);
        btnFace.setReadPermissions(Arrays.asList("public_profile", "email"));
        this.mFaceManager = CallbackManager.Factory.create();

        btnFace.registerCallback(this.mFaceManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                requestFaceEmail(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("INFO", "cancel");
                new Utility().toast(LoginActivity.this, getString(R.string.cancel_login));
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("INFO", "error");
                new Utility().toast(LoginActivity.this, getString(R.string.facebook_login_fail));
            }
        });

        this.mFaceTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                // TODO : how do I deal with old token ?
            }
        };
        this.autoFaceSignin();

    }

    /**
     * request email address from facebook
     *
     * @param token
     */
    public void requestFaceEmail(final AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                handleFaceResult(object, token);
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name, email, cover");
        request.setParameters(parameters);
        request.executeAsync();
    }


    public void logout() {

    }


    @Override
    protected void onStop() {
        super.onStop();

        if (this.mFaceTokenTracker != null) // facebook token tracker
            this.mFaceTokenTracker.stopTracking();

        if (this.mGoogleClient.isConnected()) // disconnect google
            mGoogleClient.disconnect();
    }

    /**
     * google & facebook login result
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            this.handleGoogleResult(result);
        } else {
            this.mFaceManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * handle google sign in result
     *
     * @param result
     */
    private void handleGoogleResult(GoogleSignInResult result) {
        if (result.isSuccess() == true) { // sucess to login
            GoogleSignInAccount account = result.getSignInAccount();
            String email = account.getEmail();
            String fName = account.getGivenName();
            String lName = account.getFamilyName();
            String imgPath = account.getPhotoUrl().toString();
            String token = account.getIdToken();
            new UserService(this).login(email, fName, lName, imgPath, null, Config.LoginProvider.GOOGLE, token);
        } else {

            // TODO : Signed out, show unauthenticated UI.
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGoogle: // goolg sign in
                startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.mGoogleClient), this.RC_GOOGLE_SIGN_IN);
//                    startActivity(new Intent(this, MainActivity.class));
                break;
//                Auth.GoogleSignInApi.signOut(mGoogleClient).setResultCallback(
//                        new ResultCallback<Status>() {
//                            @Override
//                            public void onResult(Status status) {
//                                // ...
//                            }
//                        });
//                break;
        }
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        new Utility().toast(this, getString(R.string.google_login_fail));
    }
}
