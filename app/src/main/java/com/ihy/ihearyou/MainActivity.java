package com.ihy.ihearyou;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ihy.ihearyou.activity.BatteryActivity;
import com.ihy.ihearyou.activity.CollaborationActivity;
import com.ihy.ihearyou.activity.ConversationActivity;
import com.ihy.ihearyou.activity.LessonActivity;
import com.ihy.ihearyou.activity.RecordActivity;
import com.ihy.ihearyou.activity.ReminderActivity;
import com.ihy.ihearyou.activity.VoiceReconizerActivity;
import com.ihy.ihearyou.component.BatteryBroadcastReceiver;


public class MainActivity extends ActionBarActivity {

    Button mBtnConversation, mBtnLesson, mBtnReminder, mBtnRecord, mBtnCollaboration, mBtnBattery;

    View.OnClickListener mOnButtonClickListener = null;
    BatteryBroadcastReceiver mPowerReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnConversation = (Button)findViewById(R.id.button_conversation);
        mBtnLesson = (Button)findViewById(R.id.button_lesson);
        mBtnReminder = (Button)findViewById(R.id.button_reminder);
        mBtnRecord = (Button)findViewById(R.id.button_record);
        mBtnCollaboration = (Button)findViewById(R.id.button_collaboration);
        mBtnBattery = (Button)findViewById(R.id.button_battery);



        mOnButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == mBtnConversation)
                {
                    Intent intent = new Intent(MainActivity.this, ConversationActivity.class);
                    startActivity(intent);
                }
                else if(v == mBtnLesson)
                {
                    Intent intent = new Intent(MainActivity.this, LessonActivity.class);
                    startActivity(intent);
                }
                else if(v == mBtnReminder)
                {
                    Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
                    startActivity(intent);
                }
                else if(v == mBtnRecord)
                {
                    Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                    startActivity(intent);
                }
                else if(v == mBtnCollaboration)
                {
                    Intent intent = new Intent(MainActivity.this, CollaborationActivity.class);
                    startActivity(intent);
                }
                else if(v == mBtnBattery)
                {
                    Intent intent = new Intent(MainActivity.this, BatteryActivity.class);
                    startActivity(intent);
                }
            }
        };

        mBtnConversation.setOnClickListener(mOnButtonClickListener);
        mBtnLesson.setOnClickListener(mOnButtonClickListener);
        mBtnRecord.setOnClickListener(mOnButtonClickListener);
        mBtnReminder.setOnClickListener(mOnButtonClickListener);
        mBtnCollaboration.setOnClickListener(mOnButtonClickListener);
        mBtnBattery.setOnClickListener(mOnButtonClickListener);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mPowerReceiver = new BatteryBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mPowerReceiver, intentFilter);

        mPowerReceiver.setBatteryStatusListener(new BatteryBroadcastReceiver.OnBatteryStatusListener() {
            @Override
            public void onPowerStatusChanged(boolean chargePlugged, float percentage) {
                mBtnBattery.setText("Battery-" + percentage * 100.f + "%");
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(mPowerReceiver);
        mPowerReceiver = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_speech_reconizer:
                Intent intent = new Intent(MainActivity.this, VoiceReconizerActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
