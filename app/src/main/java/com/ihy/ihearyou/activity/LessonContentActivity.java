package com.ihy.ihearyou.activity;

import android.app.ActionBar;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.ihy.ihearyou.R;

import java.io.File;

/**
 * Created by sunweihung on 14/12/15.
 */
public class LessonContentActivity extends ActionBarActivity {
    private Button mBtnPlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_content);
        findViews();
    }

    private void findViews() {
        mBtnPlay = (Button) this.findViewById(R.id.play);
        mBtnPlay.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MediaPlayer mediaPlayer = MediaPlayer.create(LessonContentActivity.this, R.raw.goat);
            mediaPlayer.start();
        }
    };
}
