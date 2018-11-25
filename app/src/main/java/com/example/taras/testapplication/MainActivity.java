package com.example.taras.testapplication;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private AdapterCollection adapter;
    private NotificationManager manager = null;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.pager);
        adapter = new AdapterCollection(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        manager.cancelAll();
        super.onDestroy();
    }

    public void plus(View view) {
        adapter.addFragment();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getCount() - 1);
    }

    public void minus(View view) {
        position = adapter.getCount();
        if (manager != null) {
            manager.cancel(position);
        }
        adapter.deleteFragment();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position-1);
    }

    public void makeNotification(View view) {
        addNotification();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extra = intent.getExtras();

        int notificationId = extra == null
                ? 0
                : extra.getInt("id");

        viewPager.setCurrentItem(notificationId-1);
    }

    public void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("You create a notification")
                .setContentText("Notification " + (viewPager.getCurrentItem() + 1));

        Intent notificationIntent = new Intent(this, MainActivity.class);

        int current = viewPager.getCurrentItem()+1;
        notificationIntent.putExtra("id", current);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, current, notificationIntent, 0);

        builder.setContentIntent(contentIntent);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(current, builder.build());
    }
}
