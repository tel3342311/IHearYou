package com.ihy.ihearyou.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.datamodel.ConversationData;
import com.ihy.ihearyou.datamodel.ConversationRepository;
import com.ihy.ihearyou.datamodel.SentenceData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

public class RecordConversationFragment extends Fragment {

    ConversationRepository mRepository;
    TextView mNodataText;
    ListView mRecordList;

    public static RecordConversationFragment newInstance() {
        RecordConversationFragment fragment = new RecordConversationFragment();

        return fragment;
    }

    public RecordConversationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_record_conversation, container, false);
        mNodataText = (TextView)rootView.findViewById(R.id.nodata_text);
        mRecordList = (ListView)rootView.findViewById(R.id.record_list);

        mRepository = ConversationRepository.getInstance(getActivity());
        mRepository.load();
        //create fake data
        /*
        SentenceData s1 = new SentenceData("I hear you test sentence1","");
        SentenceData s2 = new SentenceData("I hear you test sentence2","");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        long date = sdf.getCalendar().getTimeInMillis();
        mRepository.addSentence(s1, date);
        mRepository.addSentence(s2, date);*/
        if(mRepository.getConversationDataList() == null || mRepository.getConversationDataList().size() == 0)
        {
            mNodataText.setVisibility(View.VISIBLE);
        }
        else
        {
            ConversationRecordAdapter recordAdapter = new ConversationRecordAdapter(getActivity());
            mRecordList.setAdapter(recordAdapter);
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mRepository.save();
    }

    class ConversationRecordAdapter extends BaseAdapter
    {
        Context mContext;
        ConversationRepository mRepository = null;

        public ConversationRecordAdapter(Context context)
        {
            mContext = context;
            mRepository = ConversationRepository.getInstance(context);
        }

        @Override
        public int getCount() {
            if(mRepository.getConversationDataList() == null)
                return 0;

            return mRepository.getConversationDataList().size();
        }

        @Override
        public Object getItem(int position) {
            return mRepository.getConversationDataList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            LayoutInflater inflater = LayoutInflater.from(mContext);
            if(view == null)
            {
                view = inflater.inflate(R.layout.listview_conversation, null);
            }

            ConversationData data = mRepository.getConversationDataList().get(position);
            TextView dateText = (TextView)view.findViewById(R.id.record_date);
            TextView conversationText = (TextView)view.findViewById(R.id.conversation_text);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            sdf.getCalendar().setTimeInMillis(data.getDate());
            dateText.setText(sdf.format(new Date()));
            if(data.getSentenceDataList() != null && data.getSentenceDataList().size() != 0)
            {
                conversationText.setText(data.getSentenceDataList().get(0).getText());
            }
            else
            {
                conversationText.setText("");
            }

            return view;
        }
    }
}
