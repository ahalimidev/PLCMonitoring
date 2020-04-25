package id.poter.PLCMonitoring.notifaction


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log

import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import id.poter.PLCMonitoring.R
import id.poter.PLCMonitoring.view.menu


import org.json.JSONException
import org.json.JSONObject

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(s: String) {
        Log.e("NEW_TOKEN", s)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val params = remoteMessage.data
        val `object` = JSONObject(params as Map<*, *>)
        Log.e("JSON_OBJECT", `object`.toString())
        var str_msg = "default"
        var str_title = "title"
        try {
            str_msg = `object`.getString("message")
            str_title = `object`.getString("title")
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("parse fail ", e.toString())
        }

        // do all the data operation
        // saving to local file
        val NOTIFICATION_CHANNEL_ID = "TEAM_CS_CHANNEL"
        val pattern = longArrayOf(0, 1000, 500, 1000)
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, "Your Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = str_title
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = pattern
            notificationChannel.enableVibration(true)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
        // to diaplay notification in DND Mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
            channel.canBypassDnd()
        }
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        notificationBuilder.setAutoCancel(true)
            .setColor(ContextCompat.getColor(this, R.color.colorAccent))
            .setContentTitle(str_title)
            .setContentText(str_msg)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)

        // Create pending intent, mention the Activity which needs to be
        //triggered when user clicks on notification(StopScript.class in this case)

        val contentIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, menu::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        notificationBuilder.setContentIntent(contentIntent)

        mNotificationManager.notify(NotificationID.id, notificationBuilder.build())
    }
}
