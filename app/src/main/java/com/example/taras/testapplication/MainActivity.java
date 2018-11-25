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
    private int notificationId = 0;

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

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null)
            notificationId = intent.getExtras().getInt("id");
        viewPager.setCurrentItem(1);
        // put your code here...

    }

    public void plus(View view) {
        adapter.addFragment();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getCount() - 1);
    }

    public void minus(View view) {
        //position = viewPager.getCurrentItem() - 1;
        position = adapter.getCount();
        if (manager != null) {
            manager.cancel(position - 1);
        }
        adapter.deleteFragment();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    public void makeNotification(View view) {
        addNotification();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extra = intent.getExtras();
        if(extra != null)
        //notificationId = intent.getExtras().getInt("id");
        viewPager.setCurrentItem(1);
    }

    public void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("You create a notification")
                .setContentText("Notification " + (viewPager.getCurrentItem() + 1));

        Intent notificationIntent = new Intent(this, MainActivity.class);
        // notificationIntent.putExtra("id",(int)viewPager.getCurrentItem());
        notificationIntent.putExtra("id", 0);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, Intent.FILL_IN_ACTION);
        builder.setContentIntent(contentIntent);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(viewPager.getCurrentItem(), builder.build());
    }
}
