package com.ihy.ihearyou.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.fragment.LessonContentFragment;
import com.ihy.ihearyou.fragment.LessonIntroductionFragment;
import com.ihy.ihearyou.fragment.LessonMainFragment;

public class LessonActivity extends ActionBarActivity implements
        LessonMainFragment.OnFragmentInteractionListener,
        LessonIntroductionFragment.OnFragmentInteractionListener,
        LessonContentFragment.OnFragmentInteractionListener {

    LessonMainFragment mMainFrag;
    LessonIntroductionFragment mIntroductionFrag;
    LessonContentFragment mContentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        switchToMainView();
    }

    private void switchToMainView() {
        mMainFrag = LessonMainFragment.newInstance();
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.lesson_frame, mMainFrag, mMainFrag.getTag());
        fragmentTransaction.commit();
    }

    private void switchToIntroductionView() {
        mIntroductionFrag = LessonIntroductionFragment.newInstance("","");
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.lesson_frame, mIntroductionFrag, mIntroductionFrag.getTag());
        fragmentTransaction.commit();
    }


    private void switchToContentView() {
        mContentFragment = LessonContentFragment.newInstance("","");
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.lesson_frame, mContentFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Intent intent) {
        int id = intent.getIntExtra("TAG", 0);
        if (id == mMainFrag.getId()) {
            switchToIntroductionView();
        } else if (id == mIntroductionFrag.getId()) {
            switchToContentView();
        }
    }
}


