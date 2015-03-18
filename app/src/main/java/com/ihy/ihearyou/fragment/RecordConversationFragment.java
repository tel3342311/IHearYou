package com.ihy.ihearyou.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.datamodel.ConversationData;
import com.ihy.ihearyou.datamodel.ConversationRepository;
import com.ihy.ihearyou.datamodel.SentenceData;
import com.ihy.ihearyou.interfaces.PressBackInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

public class RecordConversationFragment extends Fragment implements PressBackInterface{

    private boolean mRecordDetail = false;
    @Override
    public boolean back() {
        if(mRecordDetail)
        {
            mRecordDetail = false;
            mDisplayRoot.setVisibility(View.GONE);
            //stop and clear play record status
            mPlayButton.setText("Play");
            mPlayHandler.removeMessages(Play_Record);
            mPlayRecordIndex = 0;
            mPlayRecordList = null;
            return true;
        }
        else
            return false;
    }

    public interface OnRecordItemClickedListener
    {
        public void onRecordClicked(ConversationData conversation);
    }

    ConversationRepository mRepository;
    TextView mNodataText;
    ListView mRecordList;
    RelativeLayout mDisplayRoot;
    TextView mPlayRecordText;
    Button mPlayButton;

    //playing handler
    private static final int Play_Record = 11;
    private ConversationData mPlayRecordList = null;
    private int mPlayRecordIndex = 0;
    Handler mPlayHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(mPlayRecordList == null || mPlayRecordList.getSentenceDataList() == null)
                return;
            if(mPlayRecordIndex >= mPlayRecordList.getSentenceDataList().size()) {
                mPlayButton.setText("Play");
                mPlayRecordIndex = 0;
                return;
            }
            if(msg.what == Play_Record)
            {
                mPlayRecordText.setText(mPlayRecordList.getSentenceDataList().get(mPlayRecordIndex).getText());
                mPlayRecordIndex++;
                sendMessageDelayed(obtainMessage(Play_Record), 1000);
            }
        }
    };


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
        mDisplayRoot = (RelativeLayout)rootView.findViewById(R.id.record_show_root);
        mPlayRecordText = (TextView)rootView.findViewById(R.id.play_record_text);
        mPlayButton = (Button)rootView.findViewById(R.id.play_record_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayButton.getText().toString().equalsIgnoreCase("Play"))
                {
                    mPlayButton.setText("Stop");
                    mPlayHandler.sendMessage(mPlayHandler.obtainMessage(Play_Record));
                }
                else
                {
                    mPlayButton.setText("Play");
                    mPlayHandler.removeMessages(Play_Record);
                    mPlayRecordIndex = 0;
                }
            }
        });

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
            mRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mRecordDetail = true;
                    mDisplayRoot.setVisibility(View.VISIBLE);
                    mPlayRecordList = mRepository.getConversationDataList().get(position);
                }
            });
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
            ImageView avatar_icon = (ImageView) view.findViewById(R.id.avatar_icon);
            setAvatar(position, avatar_icon);
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

        private void setAvatar(int idx, ImageView avatar_icon) {
            switch (idx % 4) {
                case 0:
                    avatar_icon.setBackground(getActivity().getDrawable(R.drawable.avatar_history_1));
                    break;
                case 1:
                    avatar_icon.setBackground(getActivity().getDrawable(R.drawable.avatar_history_2));
                    break;
                case 2:
                    avatar_icon.setBackground(getActivity().getDrawable(R.drawable.avatar_history_3));
                    break;
                case 3:
                    avatar_icon.setBackground(getActivity().getDrawable(R.drawable.avatar_history_4));
                    break;
            }
        }
    }
}
