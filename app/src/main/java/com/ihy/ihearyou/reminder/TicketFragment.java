package com.ihy.ihearyou.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ihy.ihearyou.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TicketFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TRIGGER = "Trigger";
    public static final String ADD = "Add";
    public static final String MODIFY = "Modify";
    public static final String TIME = "Time";
    public static final String TRAIN_TYPE = "Train_Type";
    public static final String NOTIFY = "Notify";
    public static final String STATION = "Station";

    private List<HashMap<String, Object>> data;
    private TicketAdapter ticketAdapter;
    private int clickPosition = -1;

    public TicketFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ticket, container, false);
        ListView listView = (ListView)rootView.findViewById(R.id.ticket_ListView);

        data = initialData();
        ticketAdapter = new TicketAdapter();
        listView.setAdapter(ticketAdapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            String trigger = data.getStringExtra(AlarmFragment.TRIGGER);
            if (resultCode == getActivity().RESULT_OK) {
                if (trigger.equals(TicketFragment.ADD)) {
                    String time = data.getStringExtra(TIME);
                    String station = data.getStringExtra(STATION);
                    String type = data.getStringExtra(TRAIN_TYPE);
                    addTicket(time, station, type);
                } else if (trigger.equals(AlarmFragment.MODIFY)) {
                    String time = data.getStringExtra(TIME);
                    String station = data.getStringExtra(STATION);
                    String type = data.getStringExtra(TRAIN_TYPE);
                    modifyTicket(time, station, type);
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clickPosition = position;
        Intent intent = new Intent(getActivity(), AddTicketActivity.class);
        intent.putExtra(TRIGGER, MODIFY);
        startActivityForResult(intent, position);
    }

    private void addTicket(String time, String station, String type) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(TIME, time);
        map.put(STATION, station);
        map.put(TRAIN_TYPE, type);
        map.put(NOTIFY, "接送通知 - 阿姨");
        data.add(map);
        ticketAdapter.notifyDataSetChanged();
    }

    private void modifyTicket(String time, String station, String type) {
        HashMap<String, Object> map = data.get(clickPosition);
        map.clear();
        map.put(TIME, time);
        map.put(STATION, station);
        map.put(TRAIN_TYPE, type);
        map.put(NOTIFY, "接送通知 - 阿姨");
        ticketAdapter.notifyDataSetChanged();
    }

    public void sendAddContentIntent() {
        Intent intent = new Intent(getActivity(), AddTicketActivity.class);
        intent.putExtra(TicketFragment.TRIGGER, TicketFragment.ADD);
        startActivityForResult(intent, 0);
    }

    private List<HashMap<String, Object>> initialData() {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(TIME, "10:15 PM");
        map.put(STATION, "台南---台北");
        map.put(TRAIN_TYPE, "0632次 高鐵");
        map.put(NOTIFY, "接送通知 - 阿姨");
        list.add(map);
        return list;
    }

    public class TicketAdapter extends BaseAdapter {

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
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_reminder_ticket_item, null);
            CheckBox rb = (CheckBox)convertView.findViewById(R.id.ticket_Time_CB);
            TextView location = (TextView)convertView.findViewById(R.id.ticket_location_TextView);
            TextView train = (TextView)convertView.findViewById(R.id.ticket_TrainType_TextView);
            CheckBox cb = (CheckBox)convertView.findViewById(R.id.ticket_Notify_CB);
            rb.setText(data.get(position).get(TIME).toString());
            location.setText(data.get(position).get(STATION).toString());
            train.setText(data.get(position).get(TRAIN_TYPE).toString());
            cb.setText(data.get(position).get(NOTIFY).toString());
            return convertView;
        }
    }
}
