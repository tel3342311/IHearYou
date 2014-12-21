package com.ihy.ihearyou.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ihy.ihearyou.R;

public class CollaborationFinalizeFragment extends Fragment {

    private static final int Collaborating_Completed = 11;

    public static CollaborationFinalizeFragment newInstance() {
        CollaborationFinalizeFragment fragment = new CollaborationFinalizeFragment();
        return fragment;
    }

    public CollaborationFinalizeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button mBtnFinish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_collaboration_finalize, container, false);
        mBtnFinish = (Button)rootView.findViewById(R.id.button_finish);
        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Handler collaboratingHandler = new Handler() {
            @Override
            public void handleMessage(Message msg)
            {
                if(msg.what == Collaborating_Completed)
                {
                    mBtnFinish.setEnabled(true);
                    mBtnFinish.setText("Collaboration Completed.");
                }
            }
        };

        Message msg = collaboratingHandler.obtainMessage(Collaborating_Completed);
        collaboratingHandler.sendMessageDelayed(msg, 3000);
    }
}
