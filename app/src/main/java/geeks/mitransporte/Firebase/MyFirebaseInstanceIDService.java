package geeks.mitransporte.Firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import geeks.mitransporte.Utils.SharedPrefManager;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    String recent_token;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        recent_token = FirebaseInstanceId.getInstance().getToken();

        storeToken(recent_token);
    }

    private void storeToken(String recent_token) {
        SharedPrefManager.getInstance(getApplicationContext()).storeToken(recent_token);
    }
}
