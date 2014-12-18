package com.ihy.ihearyou.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.component.BatteryBroadcastReceiver;

public class BatteryActivity extends ActionBarActivity {

    private BatteryBroadcastReceiver mPowerReceiver = null;

    private TextView mTotalBatteryText, mRemainTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        mTotalBatteryText = (TextView)findViewById(R.id.text_total_battery);
        mRemainTimeText = (TextView)findViewById(R.id.text_remain_time);
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
                if(chargePlugged)
                    mTotalBatteryText.setText("Charging " + percentage * 100.f + "%");
                else
                    mTotalBatteryText.setText("Battery left " + percentage * 100.f + "%");
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
}
