package com.modayakamoz.lenovo_.trendmoda.Helper;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Lenovo- on 31.1.2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {



    }

    private void sendNotification(String messageTitle,String messageBody) {
    }

}
