package com.ihy.ihearyou.reminder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ihy.ihearyou.R;

import java.util.ArrayList;
import java.util.List;

public class AddAlarmActivity extends ActionBarActivity {

    private TextView reTextView, vibrateTextView;
    private EditText editTextTitle;
    private String trigger;
    private List<CheckBox> checkBoxList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        trigger = intent.getStringExtra(AlarmFragment.TRIGGER);

        RelativeLayout reRL = (RelativeLayout)findViewById(R.id.reRL);
        reRL.setOnClickListener(onClickListener);
        RelativeLayout vibrateRL = (RelativeLayout)findViewById(R.id.vibrateRL);
        vibrateRL.setOnClickListener(onClickListener);

        reTextView = (TextView)findViewById(R.id.reTextView);
        vibrateTextView = (TextView)findViewById(R.id.vibrateTextView);
        editTextTitle = (EditText) findViewById(R.id.content_input);

        checkBoxList.add((CheckBox)findViewById(R.id.all_check));
        checkBoxList.add((CheckBox)findViewById(R.id.mon_check));
        checkBoxList.add((CheckBox)findViewById(R.id.tue_check));
        checkBoxList.add((CheckBox)findViewById(R.id.wed_check));
        checkBoxList.add((CheckBox)findViewById(R.id.thu_check));
        checkBoxList.add((CheckBox)findViewById(R.id.fri_check));
        checkBoxList.add((CheckBox)findViewById(R.id.sat_check));
        checkBoxList.add((CheckBox)findViewById(R.id.sun_check));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.alarm_select_complete:
                TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);
                int hour = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();
                Intent intent = new Intent();
                intent.putExtra(AlarmFragment.HOUR, hour);
                intent.putExtra(AlarmFragment.MINUTE, min);
                intent.putExtra(AlarmFragment.TRIGGER, trigger);
                intent.putExtra(AlarmFragment.RE_TYPE, reTextView.getText().toString());
                intent.putExtra(AlarmFragment.RE_REPEAT, getRepeatString());
                intent.putExtra(AlarmFragment.RE_TITLE, editTextTitle.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                return true;
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.reRL) {
                if (reTextView.getText().toString().equals("從不>")) {
                    reTextView.setText("週一、週二、週三、週四、週五>");
                }
                else {
                    reTextView.setText("從不>");
                }
            }
            else if (v.getId() == R.id.vibrateRL) {
                if (vibrateTextView.getText().toString().equals("適中>")) {
                    vibrateTextView.setText("強勁>");
                }
                else {
                    vibrateTextView.setText("適中>");
                }
            }
        }
    };

    private String getRepeatString() {
        String repeat[] = {
                "一 二 三 四 五 六 日",
                "一", "二", "三", "四", "五", "六", "日",
        };
        StringBuffer strBuffer = new StringBuffer();
        if (checkBoxList.size() > 0) {
            for( int i = 0; i < checkBoxList.size(); i++) {
                CheckBox cb = checkBoxList.get(i);
                if (cb.isChecked() == true) {

                    if (i == 0)
                        return repeat[0];
                    else
                        strBuffer.append(repeat[i] + " ");
                }
            }
        }
        return strBuffer.toString();
    }
}
