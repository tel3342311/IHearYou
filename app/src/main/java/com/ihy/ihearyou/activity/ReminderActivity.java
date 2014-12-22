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

    private static final int WHITE = 0xffffffff;
    private static final int GRAY = 0xff666666;

    private ViewPager viewPager;
    private AlarmFragment alarmFragment;
    private CheckListFragment checkListFragment;
    private TicketFragment ticketFragment;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        currentPosition = 0;
        getSupportActionBar().setHomeButtonEnabled(true);
        alarmFragment = new AlarmFragment();
        checkListFragment = new CheckListFragment();
        ticketFragment = new TicketFragment();

        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(this);
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
        viewPager.setCurrentItem(tab.getPosition());
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
        currentPosition = position;
        TextView alarm_text = (TextView)findViewById(R.id.text_alarm);
        TextView check_text = (TextView)findViewById(R.id.text_checklist);
        TextView ticket_text = (TextView)findViewById(R.id.text_ticket);

        switch (position) {
            case 0:
                alarm_text.setTextColor(WHITE);
                check_text.setTextColor(GRAY);
                ticket_text.setTextColor(GRAY);
                break;
            case 1:
                alarm_text.setTextColor(GRAY);
                check_text.setTextColor(WHITE);
                ticket_text.setTextColor(GRAY);
                break;
            case 2:
                alarm_text.setTextColor(GRAY);
                check_text.setTextColor(GRAY);
                ticket_text.setTextColor(WHITE);
                break;
            default:
                alarm_text.setTextColor(WHITE);
                check_text.setTextColor(GRAY);
                ticket_text.setTextColor(GRAY);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void addContent() {
        switch (currentPosition) {
            case 0:
                alarmFragment.sendAddContentIntent();
                break;
            case 1:
                checkListFragment.sendAddContentIntent();
                break;
            case 2:
                ticketFragment.sendAddContentIntent();
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
        public Fragment getItem(int posiotn) {
            // TODO Auto-generated method stub
            switch (posiotn) {
                case 0:
                    return alarmFragment;
                case 1:
                    return checkListFragment;
                case 2:
                    return ticketFragment;
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