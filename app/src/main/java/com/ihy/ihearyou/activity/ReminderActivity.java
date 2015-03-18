package com.ihy.ihearyou.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.reminder.AlarmFragment;
import com.ihy.ihearyou.reminder.CheckListFragment;
import com.ihy.ihearyou.reminder.TicketFragment;

public class ReminderActivity extends ActionBarActivity implements android.app.ActionBar.TabListener {

    private AlarmFragment mAlarmFragment;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        getSupportActionBar().setHomeButtonEnabled(true);
        switchToAlarmFragment();
    }

    private void switchToAlarmFragment() {
        mAlarmFragment = new AlarmFragment();
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.reminder_frame, mAlarmFragment, mAlarmFragment.getTag());
        fragmentTransaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.reminder_add:
                addContent();
                return true;
            case R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTabSelected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {
        //mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }

    private void addContent() {
        switch (mCurrentPosition) {
            case 0:
                mAlarmFragment.sendAddContentIntent();
                break;
            default:
                break;
        }
    }
}