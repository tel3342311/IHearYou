package com.ihy.ihearyou.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.fragment.LessonContentFragment;
import com.ihy.ihearyou.fragment.LessonCorrectFragment;
import com.ihy.ihearyou.fragment.LessonFinishFragment;
import com.ihy.ihearyou.fragment.LessonIntroductionFragment;
import com.ihy.ihearyou.fragment.LessonMainFragment;
import com.ihy.ihearyou.fragment.LessonSearchResult;

public class LessonActivity extends ActionBarActivity implements
        LessonMainFragment.OnFragmentInteractionListener,
        LessonIntroductionFragment.OnFragmentInteractionListener,
        LessonContentFragment.OnFragmentInteractionListener,
        LessonSearchResult.OnFragmentInteractionListener,
        LessonFinishFragment.OnFragmentInteractionListener,
        LessonCorrectFragment.OnFragmentInteractionListener{

    LessonMainFragment mMainFrag;
    LessonIntroductionFragment mIntroductionFrag;
    LessonContentFragment mContentFragment;
    LessonSearchResult mSearchResult;
    LessonFinishFragment mFinishFrag;
    LessonCorrectFragment mCorrectFrag;
    public static final int LESSON_ORAL = 0;
    public static final int LESSON_PRONOUNCE = 1;
    public static final int LESSON_DIALOGUE = 2;
    private int mLessonType = LESSON_ORAL;
    private int mQuestion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        switchToMainView();
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchKeyWord(query);
        }
    }

    private void searchKeyWord(String query) {
        if (query.equals("goat")) {
            switchToSearchView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lesson, menu);

        //setup search
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_search:
                openSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openSearch() {

    }

    private void switchToMainView() {
        mMainFrag = LessonMainFragment.newInstance();
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.lesson_frame, mMainFrag, mMainFrag.getTag());
        fragmentTransaction.commit();
    }

    private void switchToIntroductionView() {
        mIntroductionFrag = LessonIntroductionFragment.newInstance();
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.lesson_frame, mIntroductionFrag, mIntroductionFrag.getTag());
        fragmentTransaction.commit();
    }

    private void switchToContentView() {
        mContentFragment = LessonContentFragment.newInstance();
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.lesson_frame, mContentFragment);
        fragmentTransaction.commit();
    }

    private void switchToSearchView() {
        mSearchResult = LessonSearchResult.newInstance();
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.lesson_frame, mSearchResult);
        fragmentTransaction.commit();
    }

    private void switchToFinishView() {
        mFinishFrag = LessonFinishFragment.newInstance();
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.lesson_frame, mFinishFrag);
        fragmentTransaction.commit();
    }

    private void switchToCorrectView() {
        mCorrectFrag = LessonCorrectFragment.newInstance();
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.lesson_frame, mCorrectFrag);
        fragmentTransaction.commit();
    }
    @Override
    public void onFragmentInteraction(Intent intent) {
        int id = intent.getIntExtra("TAG", 0);
        int type = intent.getIntExtra(mMainFrag.toString(), -1);
        if (id == mMainFrag.getId()) {
            if (type == 0) {
                setLessonType(LESSON_ORAL);
            } else if (type == 1) {
                setLessonType(LESSON_PRONOUNCE);
            } else if (type == 2) {
                setLessonType(LESSON_DIALOGUE);
            }
            switchToContentView();
        } else if (id == mContentFragment.getId()) {
            if (mLessonType == LESSON_DIALOGUE) {
                if (mQuestion == 4) {
                    switchToFinishView();
                    return;
                }
                mQuestion = (mQuestion + 1) % 5;
                switchToContentView();
            } else if (mLessonType == LESSON_ORAL) {
                switchToCorrectView();
            } else if (mLessonType == LESSON_PRONOUNCE) {
                switchToCorrectView();
            }
        } else if (mCorrectFrag != null && id == mCorrectFrag.getId()) {
            int next = intent.getIntExtra(mCorrectFrag.toString(), 0);
            if (next == 0) {
                switchToContentView();
            } else if (next == 1) {
                if (mQuestion == 4) {
                    switchToFinishView();
                    return;
                }
                mQuestion = (mQuestion + 1) % 5;
                switchToContentView();
            }
        } else if (mFinishFrag != null && id == mFinishFrag.getId()) {
            int next = intent.getIntExtra(mFinishFrag.toString(), 0);
            if (next == 0) {
                switchToContentView();
            } else if (next == 1) {
                mQuestion = 0;
                switchToMainView();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mMainFrag != null && mMainFrag.isVisible()) {
            super.onBackPressed();
        } else {
            switchToMainView();
        }
    }

    public void setLessonType(int nType) {
        mLessonType = nType;
    }

    public int getLessonType() {
        return mLessonType;
    }

    public void setQuestionNum(int QuestionNum) {
        mQuestion = QuestionNum;
    }

    public int getQuestionNum() {
        return mQuestion;
    }
}


