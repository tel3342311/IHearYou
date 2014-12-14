package com.ihy.ihearyou.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ihy.ihearyou.MainActivity;
import com.ihy.ihearyou.R;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends ActionBarActivity {

    ListView mListView;
    LessonListAdapter mLessonListAdapter;
    final static int basicCount = 3;
    final static int interCount = 3;
    final static int advanceConut = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        findViews();
        setupListView();
    }

    private void findViews() {
        mListView = (ListView) this.findViewById(R.id.lesson_list);
    }

    private void setupListView() {

        ArrayList<String> titleList = new ArrayList<>();
        addStringToList(titleList, R.string.basic, basicCount);
        addStringToList(titleList, R.string.intermediate, interCount);
        addStringToList(titleList, R.string.advance, advanceConut);

        mLessonListAdapter = new LessonListAdapter(this, titleList);
        mListView.setAdapter(mLessonListAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    private void addStringToList(ArrayList list,int resID, int count) {
        for (int i = 0; i < count; i++) {
            list.add(String.format(this.getString(resID), i));
        }
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("lesson", "onItemClick pos" +position);
            Intent intent = new Intent(LessonActivity.this, LessonIntroductionActivity.class);
            startActivity(intent);
        }
    };

    public class LessonListAdapter extends BaseAdapter {
        private Context mContext = null;
        private LayoutInflater mInflater = null;
        private ArrayList<String> mAdapterList;

        public class ViewHolder {
            public TextView mTitleView;
        }

        public LessonListAdapter(Context context, ArrayList<String> list) {
            mContext = context;
            mAdapterList = list;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mAdapterList.size();
        }

        @Override
        public Object getItem(int position) {
            return mAdapterList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listview_lesson_item, null);
                holder = new ViewHolder();
                holder.mTitleView = (TextView) convertView.findViewById(R.id.lessonTitle);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.mTitleView.setText(mAdapterList.get(position));
            convertView.setTag(holder);
            return convertView;
        }
    }

}


