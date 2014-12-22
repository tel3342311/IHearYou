package com.ihy.ihearyou.reminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ihy.ihearyou.R;

import java.util.Calendar;

public class AddTicketActivity extends ActionBarActivity {

    private static final String[] TRAINS = new String[] {"台灣鐵路局", "台灣高鐵"};
    private static final String[] STATIONS = new String[] {"台北", "桃園", "新竹", "苗栗", "台中", "彰化", "雲林", "嘉義", "台南"};
    private static final String[] TRAIN_TYPE = new String[] {"對號車", "區間車", "全部"};

    private Spinner trainSpinner, startSpinner, endSpinner, trainTypeSpinner;
    private Button timeButton, dateButton;
    private String trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent intent = getIntent();
        trigger = intent.getStringExtra(CheckListFragment.TRIGGER);

        trainSpinner = (Spinner)findViewById(R.id.ticket_TrainType_Spinner);
        trainSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.abc_simple_dropdown_hint, TRAINS));
        startSpinner = (Spinner)findViewById(R.id.ticket_Start_Spinner);
        startSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.abc_simple_dropdown_hint, STATIONS));
        endSpinner = (Spinner)findViewById(R.id.ticket_End_Spinner);
        endSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.abc_simple_dropdown_hint, STATIONS));
        trainTypeSpinner = (Spinner)findViewById(R.id.ticket_Train_Spinner);
        trainTypeSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.abc_simple_dropdown_hint, TRAIN_TYPE));
        timeButton = (Button)findViewById(R.id.ticket_Time_Btn);
        timeButton.setOnClickListener(onClickListener);
        dateButton = (Button)findViewById(R.id.ticket_Date_Btn);
        dateButton.setOnClickListener(onClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.ticket_select_complete:
                String type = (String) trainSpinner.getSelectedItem();
                String start = (String) startSpinner.getSelectedItem();
                String end = (String) endSpinner.getSelectedItem();
                String time = timeButton.getText().toString();
                String station = start + "---" + end;
                Intent intent = new Intent();
                intent.putExtra(TicketFragment.TIME, time);
                intent.putExtra(TicketFragment.STATION, station);
                intent.putExtra(TicketFragment.TRAIN_TYPE, type);
                intent.putExtra(AlarmFragment.TRIGGER, trigger);
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
            int id = v.getId();
            Calendar calendar = Calendar.getInstance();
            switch (id) {
                case R.id.ticket_Time_Btn:
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddTicketActivity.this, onTimeSetListener,
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true);
                    timePickerDialog.show();
                    break;
                case R.id.ticket_Date_Btn:
                        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTicketActivity.this, onDateSetListener,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.show();
                    break;
                default:
                    break;

            }
        }
    };

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateButton.setText(String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth));
        }
    };

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timeButton.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
        }
    };

}
