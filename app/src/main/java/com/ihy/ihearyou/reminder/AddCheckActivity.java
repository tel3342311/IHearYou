package com.ihy.ihearyou.reminder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihy.ihearyou.R;

public class AddCheckActivity extends ActionBarActivity {

    private TextView itemsTextView;
    private EditText titleEditText;
    private String trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        trigger = intent.getStringExtra(CheckListFragment.TRIGGER);
        itemsTextView = (TextView)findViewById(R.id.check_ItemsTextView);
        titleEditText = (EditText)findViewById(R.id.check_Title_EditText);
        final EditText item = (EditText)findViewById(R.id.check_Item_EditText);
        Button addItem = (Button)findViewById(R.id.check_Item_Btn);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemStr = itemsTextView.getText().toString();
                itemStr += item.getText().toString() + "\n";
                item.setText("");
                itemsTextView.setText(itemStr);
            }
        });

        RelativeLayout alarmRL = (RelativeLayout)findViewById(R.id.check_Alarm_RL);
        alarmRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (trigger.equals(CheckListFragment.MODIFY)) {
            String title = intent.getStringExtra(CheckListFragment.TITLE);
            String content = intent.getStringExtra(CheckListFragment.CONTENT);
            titleEditText.setText(title);
            itemsTextView.setText(content + "\n");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.check_select_complete:
                String title = titleEditText.getText().toString();
                String items = itemsTextView.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(CheckListFragment.TITLE, title);
                intent.putExtra(CheckListFragment.CONTENT, items);
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
}
