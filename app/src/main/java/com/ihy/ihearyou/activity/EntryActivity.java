package com.ihy.ihearyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.ihy.ihearyou.MainActivity;
import com.ihy.ihearyou.R;
import com.ihy.ihearyou.fragment.EntryConnectionFragment;
import com.ihy.ihearyou.fragment.EntrySearchDeviceFragment;
import com.ihy.ihearyou.fragment.EntryWelcomeFragment;

public class EntryActivity extends Activity {

    private static final String Connected_Device = "connected_device";

    private static final int LAUNCH_MAIN = 10;
    private static final int WELCOME_FINISH = 11;
    private static final int DEVICE_SELECTED = 12;
    private static final int DEVICE_CONNECTED = 13;

    private String mConnectedDeviceName;

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == LAUNCH_MAIN || msg.what == DEVICE_CONNECTED)
            {
                startActivity(new Intent(EntryActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                finish();
            }
            else if(msg.what == WELCOME_FINISH)
            {
                EntrySearchDeviceFragment esd = EntrySearchDeviceFragment.newInstance();
                esd.setOnFragmentInteractionListener(new EntrySearchDeviceFragment.OnFragmentInteractionListener() {
                    @Override
                    public void onDeviceSelected(String deviceName) {
                        mConnectedDeviceName = deviceName;
                        mHandler.sendMessage(mHandler.obtainMessage(DEVICE_SELECTED));
                    }
                });
                getFragmentManager().beginTransaction().replace(R.id.root_frame, esd).commit();
            }
            else if(msg.what == DEVICE_SELECTED)
            {
                EntryConnectionFragment ecf = EntryConnectionFragment.newInstance(mConnectedDeviceName);
                ecf.setOnFragmentInteractionListener(new EntryConnectionFragment.OnFragmentInteractionListener() {
                    @Override
                    public void onDeviceConnected() {
                        //write connected device name to shared preference
                        SharedPreferences sp = getPreferences(MODE_PRIVATE);
                        sp.edit().putString(Connected_Device,mConnectedDeviceName).commit();
                        startActivity(new Intent(EntryActivity.this, MainActivity.class));
                        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                        finish();
                    }
                });
                getFragmentManager().beginTransaction().replace(R.id.root_frame, ecf).commit();
            }
        }
    };

    ImageView mSplashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        mSplashImage = (ImageView)findViewById(R.id.splash_image);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        if(sp.contains(Connected_Device))
        {
            mConnectedDeviceName = sp.getString(Connected_Device, "");
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LAUNCH_MAIN), 3000);
        }
        else
        {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    EntryWelcomeFragment ewf = EntryWelcomeFragment.newInstance();
                    ewf.setOnFragmentInteractionListener(new EntryWelcomeFragment.OnFragmentInteractionListener() {
                        @Override
                        public void onStart() {
                            mHandler.sendMessage(mHandler.obtainMessage(WELCOME_FINISH));
                        }
                    });
                    getFragmentManager().beginTransaction().replace(R.id.root_frame, ewf).commit();
                    mSplashImage.setVisibility(View.INVISIBLE);
                }
            }, 3000);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }
}
