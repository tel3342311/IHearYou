package com.ihy.ihearyou.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ihy.ihearyou.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlarmFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TRIGGER = "Trigger";
    public static final String ADD = "Add";
    public static final String MODIFY = "Modify";
    public static final String HOUR = "Hour";
    public static final String MINUTE = "Minute";
    public static final String DATE = "Date";
    public static final String TIME = "Time";
    public static final String RE_TYPE = "Re_Type";
    public static final String RE_TITLE ="Re_Title;";
    public static final String RE_REPEAT = "Re_Repeat";

    private List<HashMap<String, Object>> data;
    private AlarmAdapter alarmAdapter;
    private int clickPosition = -1;

    public AlarmFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);
        ListView listView = (ListView)rootView.findViewById(R.id.alarm_ListView);

        data = initialData();
        alarmAdapter = new AlarmAdapter();
        listView.setAdapter(alarmAdapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clickPosition = position;
        Intent intent = new Intent(getActivity(), AddAlarmActivity.class);
        intent.putExtra(TRIGGER, MODIFY);
        startActivityForResult(intent, position);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            String trigger = data.getStringExtra(AlarmFragment.TRIGGER);
            if (resultCode == getActivity().RESULT_OK) {
                if (trigger.equals(AlarmFragment.ADD)) {
                    int hour = data.getIntExtra(HOUR, 0);
                    int min = data.getIntExtra(MINUTE, 0);
                    String type = data.getStringExtra(RE_TYPE);
                    String repeat = data.getStringExtra(RE_REPEAT);
                    String title = data.getStringExtra(RE_TITLE);
                    addAlarm(min, hour, type, repeat, title);
                } else if (trigger.equals(AlarmFragment.MODIFY)) {
                    int hour = data.getIntExtra(HOUR, 0);
                    int min = data.getIntExtra(MINUTE, 0);
                    String type = data.getStringExtra(RE_TYPE);
                    modifyAlarm(min, hour, type);
                }
            }
        }
    }

    private List<HashMap<String, Object>> initialData() {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put(DATE, "一 二 三 四 五");
        map1.put(TIME, "上午 10:30");
        map1.put(RE_TITLE, "背單字");
        list.add(map1);

        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put(DATE, "二 四 六");
        map2.put(TIME, "上午 11:30");
        map2.put(RE_TITLE, "整理房間");
        list.add(map2);

        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put(DATE, "五 六 日");
        map3.put(TIME, "下午 02:30");
        map3.put(RE_TITLE, "寫作業");
        list.add(map3);
        return list;
    }

    public void sendAddContentIntent() {
        Intent intent = new Intent(getActivity(), AddAlarmActivity.class);
        intent.putExtra(AlarmFragment.TRIGGER, AlarmFragment.ADD);
        startActivityForResult(intent, 0);
    }

    private void addAlarm(int min, int hour, String re_Type) {
        String time = "";
        if (hour > 12) {
            time = String.valueOf(hour - 12) + ":" + String.valueOf(min) + " PM";
        }
        else if (hour == 12) {
            time = String.valueOf(hour) + ":" + String.valueOf(min) + " PM";
        }
        else {
            time = String.valueOf(hour) + ":" + String.valueOf(min) + " AM";
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(DATE, re_Type.replace(">", ""));
        map.put(TIME, time);
        data.add(map);
        alarmAdapter.notifyDataSetChanged();
    }

    private void addAlarm(int min, int hour, String re_Type, String repeat, String title) {
        String time = "";
        if (hour > 12) {
            time = "下午 " + String.valueOf(hour - 12) + ":" + String.valueOf(min);
        }
        else if (hour == 12) {
            time = "下午 " + String.valueOf(hour) + ":" + String.valueOf(min);
        }
        else {
            time = "上午 " + String.valueOf(hour) + ":" + String.valueOf(min);
        }

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(DATE, repeat);
        map.put(TIME, time);
        map.put(RE_TITLE, title);
        data.add(map);
        alarmAdapter.notifyDataSetChanged();
    }

    private void modifyAlarm(int min, int hour, String re_Type) {
        String time = "";
        if (hour > 12) {
            time = "下午 " + String.valueOf(hour - 12) + ":" + String.valueOf(min);
        }
        else if (hour == 12) {
            time = "下午 " + String.valueOf(hour) + ":" + String.valueOf(min);
        }
        else {
            time = "上午 " + String.valueOf(hour) + ":" + String.valueOf(min);
        }

        HashMap<String, Object> map = data.get(clickPosition);
        map.clear();
        map.put(DATE, re_Type.replace(">", ""));
        map.put(TIME, time);

        alarmAdapter.notifyDataSetChanged();
    }

    public class AlarmAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_reminder_alarm_item, null);
            TextView date = (TextView) convertView.findViewById(R.id.dateTextView);
            TextView time = (TextView) convertView.findViewById(R.id.timeTextView);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            date.setText(data.get(position).get(DATE).toString());
            time.setText(data.get(position).get(TIME).toString());
            if (data.get(position).get(RE_TITLE) != null) {
                title.setText(data.get(position).get(RE_TITLE).toString());
            }
            return convertView;
        }
    }
}
