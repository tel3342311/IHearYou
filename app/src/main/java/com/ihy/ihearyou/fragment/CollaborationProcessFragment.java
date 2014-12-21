package com.ihy.ihearyou.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.datamodel.CollaborationProcess;

public class CollaborationProcessFragment extends Fragment {

    public interface OnCollaborationProcessFinishListener
    {
        public void onFinish();
    }
    private OnCollaborationProcessFinishListener mListener;
    public void setOnCollaborationProcessFinishListener(OnCollaborationProcessFinishListener listener)
    {
        mListener = listener;
    }

    public static CollaborationProcessFragment newInstance() {
        CollaborationProcessFragment fragment = new CollaborationProcessFragment();
        return fragment;
    }

    public CollaborationProcessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button mPlayButton, mNoButton, mYesButton;
    TextView mTitle, mDescription;
    ProgressBar mProgress;

    View.OnClickListener mButtonClickListener;
    CollaborationProcess mProcessInstance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_collaboration_process, container, false);

        mProcessInstance = new CollaborationProcess();

        mPlayButton = (Button)rootView.findViewById(R.id.button_play);
        mNoButton = (Button)rootView.findViewById(R.id.button_no);
        mYesButton = (Button)rootView.findViewById(R.id.button_yes);
        mTitle = (TextView)rootView.findViewById(R.id.text_title);
        mDescription = (TextView)rootView.findViewById(R.id.text_description);
        mProgress = (ProgressBar)rootView.findViewById(R.id.progress_bar);

        mButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == mPlayButton)
                {

                }
                else if(v == mYesButton)
                {
                    setupTestingUI();
                }
                else if(v == mNoButton)
                {
                    setupTestingUI();
                }
            }
        };

        mPlayButton.setOnClickListener(mButtonClickListener);
        mYesButton.setOnClickListener(mButtonClickListener);
        mNoButton.setOnClickListener(mButtonClickListener);

        setupTestingUI();

        return rootView;
    }

    private void setupTestingUI()
    {
        if(mProcessInstance.stepToNextProcess())
        {
            mTitle.setText(mProcessInstance.getTitle());
            mDescription.setText(mProcessInstance.getDescription());
            int percentage = (int)(((float)(mProcessInstance.getCurrentProcess() + 1) / (float)mProcessInstance.getTotalProcessCount()) * 100.f);
            mProgress.setProgress(percentage);
        }
        else
        {
            if(mListener != null)
                mListener.onFinish();
        }
    }
}
