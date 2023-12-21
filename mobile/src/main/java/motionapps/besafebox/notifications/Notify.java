package motionapps.besafebox.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


import motionapps.besafebox.activities.main.Main;

import motionapps.besafebox.models.detectors.Detector;
import motionapps.besafebox.R;


import java.util.ArrayList;


/**
 * class to create notifications and send email/sms with autogenerated message
 */
public class Notify {


    public final static String
            CHANNEL_ID_FOREGROUND_IMPORTANT = "Important channel",
            CHANNEL_ID_FOREGROUND_UPDATES = "Update channel";

    private static int notificationId = 321;

    /**
     * able to send notifications from everywhere, if context is available
     *
     * @param context       - any
     * @param channelID     - channel to use - UPDATE / IMPORTANT
     * @param pendingIntent - intent to launch after click on notification,  can be null
     * @param s             - [0] - title, [1] - content
     * @param onDelete      - intent to launch, if the notification is swiped - can be null
     * @param onGoing       - the notification cannot be dismissed
     * @param autoCancel    - if user touches it, the notification dismiss itself
     * @param priority      - int for priority
     * @param id            - unique number for update of the notification - can be null and the method returns new
     */
    public static int sendNotification(Context context, String channelID, PendingIntent pendingIntent, PendingIntent onDelete,
                                       String[] s, boolean onGoing, boolean autoCancel, int priority, Integer id) {

        createNotificationChannels(context);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.warning)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(s[0])
                .setContentText(s[1])
                .setPriority(priority)
                .setOngoing(onGoing)
                .setAutoCancel(autoCancel);

        if (pendingIntent != null) {
            mBuilder.setContentIntent(pendingIntent);
        }

        if (onDelete != null) {
            mBuilder.setDeleteIntent(onDelete);
        }


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        Notification notification = mBuilder.build();
        if (id != null) {
            notificationManager.notify(id, notification);
            return id;
        } else {
            notificationManager.notify(notificationId, notification);
        }

        return notificationId++;
    }

    /**
     * notification will be deleted
     *
     * @param context any
     * @param id      - of the notification
     */
    public static void cancelNotification(Context context, int id) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(id);
    }

    /**
     * creates 2 channels, one for updates of the service and other
     * with important notifications like GPS is off and similar
     *
     * @param context - any
     */
    private static void createNotificationChannels(Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;

        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

        if (notificationManager.getNotificationChannel(CHANNEL_ID_FOREGROUND_IMPORTANT) != null
                && notificationManager.getNotificationChannel(CHANNEL_ID_FOREGROUND_UPDATES) != null)
            return;

        // important channel
        CharSequence name = context.getString(R.string.channel_name_important);
        String description = context.getString(R.string.channel_description_important);

        NotificationChannel channelImportant = new NotificationChannel(CHANNEL_ID_FOREGROUND_IMPORTANT, name, NotificationManager.IMPORTANCE_HIGH);
        channelImportant.setDescription(description);
        channelImportant.setLightColor(Color.RED);

        // update channel
        name = context.getString(R.string.channel_name_updates);
        description = context.getString(R.string.channel_description_updates);

        NotificationChannel channelUpdates = new NotificationChannel(CHANNEL_ID_FOREGROUND_UPDATES, name, NotificationManager.IMPORTANCE_LOW);
        channelUpdates.setDescription(description);
        channelUpdates.setLightColor(Color.BLUE);
        channelUpdates.setSound(null, null);

        // creation of channels
        notificationManager.createNotificationChannels(new ArrayList<NotificationChannel>() {{
            add(channelImportant);
            add(channelUpdates);
        }});

    }

    /**
     * @param context any
     * @param text - String - content of the notifiaction
     * @param type - Detector.NONE for sleep, otherwise fall detection
     * @return notification object
     */
    public static Notification createNotificationForDetection(Context context, String text, int type) {
        createNotificationChannels(context);


        Intent cancel = new Intent(Main.BROADCAST_STATE_CANCEL);
        PendingIntent cancelService = PendingIntent.getBroadcast(context, 4,
                cancel, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent notifyIntent = new Intent(context, Main.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent notifyPendingIntent = PendingIntent.getActivity(context, 0,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int icon;

        //if(type == Detector.CAR){
        //    icon = R.drawable.ic_car_crash;
        //}else
        if (type == Detector.NONE) {
            icon = R.drawable.ic_moon;
        } else {
            icon = R.drawable.ic_fall_white;
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID_FOREGROUND_UPDATES)
                .setSmallIcon(icon)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notifyPendingIntent)
                .addAction(R.drawable.cancel, context.getString(R.string.notification_stop_detection), cancelService);

        // to make colours show in correct way
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setColor(ContextCompat.getColor(context, R.color.white));
        }

        return mBuilder.build();
    }

    /**
     * @param context any
     * @param text - content of the notification
     * @param id - notification id, if it exists
     * @param type - type of the notification
     */
    public static void updateForegroundNotification(Context context, String text, int id, int type) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, createNotificationForDetection(context, text, type));
    }

    /**
     * GPS alert if provider is off
     * @param context any
     * @return id of notification
     */
    public static int createGPSNotification(Context context) {
        createNotificationChannels(context);
        Intent notifyIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(context, 0,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return Notify.sendNotification(context, CHANNEL_ID_FOREGROUND_IMPORTANT,
                notifyPendingIntent, null, new String[]{
                        context.getString(R.string.warning_nogps_signal_title),
                        context.getString(R.string.warning_nogps_signal_description)},
                false, false, 3, null);
    }

    public static void vibrateOnce(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500L, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500L);
        }
    }

    public static void vibrateMore(Context context, long[] time, int[] amplitude, int repeat) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createWaveform(time, amplitude, repeat));
        } else {
            v.vibrate(time, repeat);
        }
    }

    /**
     * required for the app to manipulate with sound and notifications settings in order to turn on
     * alarm even when the phone is silenced
     * @param context any
     * @return boolean if it is allowed or not
     */
    public static boolean enableNotifications(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (!notificationManager.isNotificationPolicyAccessGranted()) {
                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                context.startActivity(intent);
            }
            return notificationManager.isNotificationPolicyAccessGranted();
        }
        return true;
    }


}