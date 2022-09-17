package net.smallacademy.authenticatorapp.services;

import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NotNull String token) {
        super.onNewToken(token);
        Log.e("newToken", token);
//Add your token in your share preferences.
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcm_token", token).apply();
    }

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
    //When ever you need FCM token, just call this static method to get it.
    public String getToken (Context context) {
     return context.getSharedPreferences("save token", MODE_PRIVATE).getString("fcm_token", "empty");
        }
}