package com.ihy.ihearyou.reminder;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class CheckListFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TITLE = "Title";
    public static final String CONTENT = "Content";
    public static final String TRIGGER = "Trigger";
    public static final String ADD = "Add";
    public static final String MODIFY = "Modify";

    private List<HashMap<String, Object>> data;
    private CheckListAdapter checkListAdapter;
    private int clickPosition = -1;

    public CheckListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_list, container, false);
        ListView listView = (ListView)rootView.findViewById(R.id.checkList_ListView);
        data = initialData();
        checkListAdapter = new CheckListAdapter();
        listView.setAdapter(checkListAdapter);
        listView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            String trigger = data.getStringExtra(CheckListFragment.TRIGGER);
            if (resultCode == getActivity().RESULT_OK) {
                if (trigger.equals(CheckListFragment.ADD)) {
                    String title = data.getStringExtra(TITLE);
                    String content = data.getStringExtra(CONTENT);
                    AddCheckList(title, content);
                } else if (trigger.equals(CheckListFragment.MODIFY)) {
                    String title = data.getStringExtra(TITLE);
                    String content = data.getStringExtra(CONTENT);
                    modifyAlarm(title, content);
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        clickPosition = position;
        HashMap<String, Object> map = (HashMap<String, Object>)checkListAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), AddCheckActivity.class);
        intent.putExtra(TRIGGER, MODIFY);
        intent.putExtra(TITLE, (String)map.get(TITLE));
        intent.putExtra(CONTENT, (String)map.get(CONTENT));
        startActivityForResult(intent, position);
    }

    private void AddCheckList(String title, String content) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(TITLE, title);
        map.put(CONTENT, content);
        data.add(map);
        checkListAdapter.notifyDataSetChanged();
    }

    private void modifyAlarm(String title, String content) {
        HashMap<String, Object> map = data.get(clickPosition);
        map.clear();
        map.put(TITLE, title);
        map.put(CONTENT, content);
        checkListAdapter.notifyDataSetChanged();
    }

    private List<HashMap<String, Object>> initialData() {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put(TITLE, "勇安生日計畫");
        map1.put(CONTENT, "刮鬍泡(1打)\n雞蛋(3顆)\n乳酪蛋糕\n驚喜大禮物");
        list.add(map1);
        return list;
    }

    public void sendAddContentIntent() {
        Intent intent = new Intent(getActivity(), AddCheckActivity.class);
        intent.putExtra(CheckListFragment.TRIGGER, CheckListFragment.ADD);
        startActivityForResult(intent, 0);
    }

    public class CheckListAdapter extends BaseAdapter {

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
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_reminder_checklist_item, null);
            TextView title = (TextView) convertView.findViewById(R.id.checkTitleTextView);
            TextView item = (TextView) convertView.findViewById(R.id.checkItemTextView);
            title.setText(data.get(position).get(TITLE).toString());
            item.setText(data.get(position).get(CONTENT).toString());
            return convertView;
        }
    }
}
