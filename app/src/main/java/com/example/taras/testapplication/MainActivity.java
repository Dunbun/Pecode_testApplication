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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.pager);
        adapter = new AdapterCollection(getSupportFragmentManager());
        adapter.initializeCollection();
        viewPager.setAdapter(adapter);
    }

    public void plus(View view) {
        adapter.addFragment();
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getCount() - 1);
    }

    public void minus(View view) {
        int position = viewPager.getCurrentItem();
        if (manager != null) {
            manager.cancel(adapter.getPageNumber(position));
        }
        adapter.deleteFragment(position);
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position-1);
    }

    public void makeNotification(View view) {
        int current = adapter.getPageNumber(viewPager.getCurrentItem());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("You create a notification")
                .setContentText("Notification " + current);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.putExtra("id", current);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if(current == 6) {
            PendingIntent contentIntent = PendingIntent.getActivity(this,  666, notificationIntent, 0);
            builder.setContentIntent(contentIntent);
        }
        else{
            PendingIntent contentIntent = PendingIntent.getActivity(this, current, notificationIntent, 0);
            builder.setContentIntent(contentIntent);
        }
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(current, builder.build());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extra = intent.getExtras();
        int notificationId = extra == null
                ? 0
                : extra.getInt("id");
        viewPager.setCurrentItem(adapter.getPagePosition(notificationId));
    }

}
