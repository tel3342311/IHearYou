package com.ihy.ihearyou.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ihy.ihearyou.R;

import javax.xml.datatype.Duration;

public class CollaborationMainFragment extends Fragment {

    public interface OnCollaborationStartListener
    {
        public void onStart();
    }

    private OnCollaborationStartListener mOnCollaborationStartListener = null;
    public void setOnCollaborationStartListener(OnCollaborationStartListener listener)
    {
        mOnCollaborationStartListener = listener;
    }

    private Button mStartButton;

    public static CollaborationMainFragment newInstance() {
        CollaborationMainFragment fragment = new CollaborationMainFragment();
        return fragment;
    }

    public CollaborationMainFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_collaboration_main, container, false);

        mStartButton = (Button)rootView.findViewById(R.id.button_start);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnCollaborationStartListener != null)
                    mOnCollaborationStartListener.onStart();
            }
        });

        Spinner spinnerHeadset = (Spinner)rootView.findViewById(R.id.spinner_headset);
        Spinner spinnerHelp = (Spinner)rootView.findViewById(R.id.spinner_help);
        ArrayAdapter<String> headsetAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item);
        headsetAdapter.add("助聽器");
        spinnerHeadset.setAdapter(headsetAdapter);

        ArrayAdapter<String> helpAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item);
        helpAdapter.add("耳目譯新");
        spinnerHelp.setAdapter(helpAdapter);

        return rootView;
    }
}
