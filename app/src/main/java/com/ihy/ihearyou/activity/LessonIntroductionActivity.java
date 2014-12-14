package com.ihy.ihearyou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.ihy.ihearyou.R;

/**
 * Created by sunweihung on 14/12/14.
 */
public class LessonIntroductionActivity extends ActionBarActivity {

    private Button mStartLesson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_introduction);
        findViews();
    }
    private void findViews() {
        mStartLesson = (Button) this.findViewById(R.id.btnStartLesson);
        mStartLesson.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LessonIntroductionActivity.this, LessonContentActivity.class);
                startActivity(intent);
            }
        });
    }
}
