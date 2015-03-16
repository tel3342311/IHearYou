package com.ihy.ihearyou.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.activity.LessonActivity;

import java.util.ArrayList;

/**
 * Created by sunweihung on 14/12/21.
 */
public class LessonMainFragment extends Fragment {

    private View mOralView;
    private View mDialogueView;
    private View mPronounceView;

    // Container Activity must implement this interface
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Intent intent);
    }
    private OnFragmentInteractionListener mLessonSelectedListener;

    private View mRootView;
    private ListView mListView;
    private LessonListAdapter mLessonListAdapter;
    final static int basicCount = 3;
    final static int interCount = 3;
    final static int advanceConut = 3;

    public static LessonMainFragment newInstance() {
        LessonMainFragment fragment = new LessonMainFragment();
        Bundle args= new Bundle();
        //TODO set argument
        fragment.setArguments(args);
        return fragment;
    }

    public LessonMainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //TODO get bundle
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int resID = R.layout.fragment_lesson_main;
        return inflater.inflate(resID, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mLessonSelectedListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        setupListView();
    }

    private void findViews() {
        mRootView = getActivity().findViewById(R.id.lesson_frame);
        mOralView = getActivity().findViewById(R.id.oral_view);
        mDialogueView = getActivity().findViewById(R.id.dialogue_view);
        mPronounceView = getActivity().findViewById(R.id.pronounce_view);
        mOralView.setOnClickListener(mOnClickListener);
        mListView = (ListView) mRootView.findViewById(R.id.lesson_list);
    }

    private void addStringToList(ArrayList list,int resID, int count) {
        for (int i = 0; i < count; i++) {
            list.add(String.format(this.getString(resID), i));
        }
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("lesson", "onItemClick pos" + position);
            Intent intent = new Intent();
            intent.putExtra("TAG", LessonMainFragment.this.getId());
            intent.putExtra(LessonMainFragment.this.toString(), position);
            mLessonSelectedListener.onFragmentInteraction(intent);
        }
    };

    private void setupListView() {

        ArrayList<String> titleList = new ArrayList<>();
        addStringToList(titleList, R.string.basic, basicCount);
        addStringToList(titleList, R.string.intermediate, interCount);
        addStringToList(titleList, R.string.advance, advanceConut);

        mLessonListAdapter = new LessonListAdapter(getActivity(), titleList);
        mListView.setAdapter(mLessonListAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mLessonSelectedListener = null;
    }

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

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.oral_view) {
                Intent intent = new Intent();
                intent.putExtra("TAG", LessonMainFragment.this.getId());
                intent.putExtra(LessonMainFragment.this.toString(), 0);
                mLessonSelectedListener.onFragmentInteraction(intent);
            } else if (v.getId() == R.id.dialogue_view) {

                Intent intent = new Intent();
                intent.putExtra("TAG", LessonMainFragment.this.getId());
                intent.putExtra(LessonMainFragment.this.toString(), 4);
                mLessonSelectedListener.onFragmentInteraction(intent);
            } else if (v.getId() == R.id.pronounce_view) {
                Intent intent = new Intent();
                intent.putExtra("TAG", LessonMainFragment.this.getId());
                intent.putExtra(LessonMainFragment.this.toString(), 7);
                mLessonSelectedListener.onFragmentInteraction(intent);

            }
        }
    };
}
