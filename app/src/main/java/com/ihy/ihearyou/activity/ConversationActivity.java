package com.ihy.ihearyou.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.fragment.InterperterFragment;
import com.ihy.ihearyou.fragment.PhoneCallFragment;
import com.ihy.ihearyou.fragment.VideoRecordFragment;

public class ConversationActivity extends ActionBarActivity implements
        android.app.ActionBar.TabListener,
        ViewPager.OnPageChangeListener,
        VideoRecordFragment.OnFragmentInteractionListener,
        InterperterFragment.OnFragmentInteractionListener,
        PhoneCallFragment.OnFragmentInteractionListener{
    private ViewPager mViewPager;
    private int mCurrentPosition;
    private final static int FRAGMENT_COUNT = 3;
    private InterperterFragment mInterperterFragment;
    private VideoRecordFragment mVideoRecordFragment;
    private PhoneCallFragment mPhoneCallFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        findViews();
    }

    private void findViews() {

        mInterperterFragment = InterperterFragment.newInstance("","");
        mVideoRecordFragment = VideoRecordFragment.newInstance("","");
        mPhoneCallFragment = PhoneCallFragment.newInstance("","");
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(this);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        mCurrentPosition = position;
        TextView interpreter = (TextView)findViewById(R.id.text_interpreter);
        TextView video_record = (TextView)findViewById(R.id.text_video_record);
        TextView phone_call = (TextView)findViewById(R.id.text_phone);

        switch (position) {
            case 0:
                interpreter.setTextColor(getResources().getColor(android.R.color.white));
                video_record.setTextColor(getResources().getColor(android.R.color.darker_gray));
                phone_call.setTextColor(getResources().getColor(android.R.color.darker_gray));
                break;
            case 1:
                interpreter.setTextColor(getResources().getColor(android.R.color.darker_gray));
                video_record.setTextColor(getResources().getColor(android.R.color.white));
                phone_call.setTextColor(getResources().getColor(android.R.color.darker_gray));
                break;
            case 2:
                interpreter.setTextColor(getResources().getColor(android.R.color.darker_gray));
                video_record.setTextColor(getResources().getColor(android.R.color.darker_gray));
                phone_call.setTextColor(getResources().getColor(android.R.color.white));
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public class HomeViewPagerAdapter extends FragmentPagerAdapter {

        public HomeViewPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return mInterperterFragment;
                case 1:
                    return mVideoRecordFragment;
                case 2:
                    return mPhoneCallFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }
    }
}
