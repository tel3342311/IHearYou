package com.ihy.ihearyou.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.reminder.AlarmFragment;
import com.ihy.ihearyou.reminder.CheckListFragment;
import com.ihy.ihearyou.reminder.TicketFragment;

public class ReminderActivity extends ActionBarActivity implements android.app.ActionBar.TabListener, OnPageChangeListener {

    private ViewPager mViewPager;
    private AlarmFragment mAlarmFragment;
    private CheckListFragment mCheckListFragment;
    private TicketFragment mTicketFragment;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        mCurrentPosition = 0;
        getSupportActionBar().setHomeButtonEnabled(true);
        mAlarmFragment = new AlarmFragment();
        mCheckListFragment = new CheckListFragment();
        mTicketFragment = new TicketFragment();

        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(this);
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
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(android.app.ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
        TextView alarm_text = (TextView)findViewById(R.id.text_alarm);
        TextView check_text = (TextView)findViewById(R.id.text_checklist);
        TextView ticket_text = (TextView)findViewById(R.id.text_ticket);

        switch (position) {
            case 0:
                alarm_text.setTextColor(getResources().getColor(android.R.color.white));
                check_text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                ticket_text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                break;
            case 1:
                alarm_text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                check_text.setTextColor(getResources().getColor(android.R.color.white));
                ticket_text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                break;
            case 2:
                alarm_text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                check_text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                ticket_text.setTextColor(getResources().getColor(android.R.color.white));
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void addContent() {
        switch (mCurrentPosition) {
            case 0:
                mAlarmFragment.sendAddContentIntent();
                break;
            case 1:
                mCheckListFragment.sendAddContentIntent();
                break;
            case 2:
                mTicketFragment.sendAddContentIntent();
                break;
            default:
                break;
        }
    }

    public class HomeViewPagerAdapter extends FragmentPagerAdapter {

        public HomeViewPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int position) {
            // TODO Auto-generated method stub
            switch (position) {
                case 0:
                    return mAlarmFragment;
                case 1:
                    return mCheckListFragment;
                case 2:
                    return mTicketFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 3;
        }
    }
}