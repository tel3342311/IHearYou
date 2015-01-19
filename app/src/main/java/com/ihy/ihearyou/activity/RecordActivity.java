package com.ihy.ihearyou.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.datamodel.ConversationData;
import com.ihy.ihearyou.fragment.RecordConversationFragment;
import com.ihy.ihearyou.fragment.RecordPhoneFragment;

public class RecordActivity extends ActionBarActivity {

    private ViewPager mViewPager;
    private TextView mTextConversation, mTextPhone;

    private ContentPager mContentPager;
    private int mCurrentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mTextConversation = (TextView)findViewById(R.id.text_conversation);
        mTextPhone = (TextView)findViewById(R.id.text_phone);
        mViewPager = (ViewPager)findViewById(R.id.pager_container);
        mContentPager = new ContentPager(getSupportFragmentManager());
        mViewPager.setAdapter(mContentPager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                if(position == 0)
                {
                    mTextConversation.setTextColor(getResources().getColor(android.R.color.white));
                    mTextPhone.setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
                else
                {
                    mTextConversation.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    mTextPhone.setTextColor(getResources().getColor(android.R.color.white));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if(mContentPager.onBackPressed())
            return;
        else
            super.onBackPressed();
    }

    public class ContentPager extends FragmentPagerAdapter
    {
        RecordConversationFragment mConversationFragment;
        RecordPhoneFragment mPhoneFragment;

        public ContentPager(FragmentManager fm) {
            super(fm);
            mConversationFragment = RecordConversationFragment.newInstance();
            mPhoneFragment = RecordPhoneFragment.newInstance();
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return mConversationFragment;
            return mPhoneFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        public boolean onBackPressed()
        {
            if(mCurrentPosition == 0)
                return mConversationFragment.back();
            return false;
        }
    }
}
