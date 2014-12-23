package com.ihy.ihearyou.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryBroadcastReceiver extends BroadcastReceiver{

    public interface OnBatteryStatusListener
    {
        public void onPowerStatusChanged(boolean chargePlugged, float percentage);
    }

    private OnBatteryStatusListener mBatteryStatusListener = null;
    public void setBatteryStatusListener(OnBatteryStatusListener listener)
    {
        mBatteryStatusListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;
        if(mBatteryStatusListener != null)
            mBatteryStatusListener.onPowerStatusChanged(usbCharge | acCharge, batteryPct);
    }
}
