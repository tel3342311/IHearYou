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
import com.ihy.ihearyou.fragment.LessonIntroductionFragment;
import com.ihy.ihearyou.fragment.LessonMainFragment;
import com.ihy.ihearyou.fragment.LessonSearchResult;

public class LessonActivity extends ActionBarActivity implements
        LessonMainFragment.OnFragmentInteractionListener,
        LessonIntroductionFragment.OnFragmentInteractionListener,
        LessonContentFragment.OnFragmentInteractionListener,
        LessonSearchResult.OnFragmentInteractionListener {

    LessonMainFragment mMainFrag;
    LessonIntroductionFragment mIntroductionFrag;
    LessonContentFragment mContentFragment;
    LessonSearchResult mSearchResult;

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


