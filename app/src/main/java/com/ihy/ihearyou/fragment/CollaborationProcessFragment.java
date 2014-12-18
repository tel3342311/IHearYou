package com.ihy.ihearyou.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ihy.ihearyou.R;

public class CollaborationProcessFragment extends Fragment {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_collaboration_process, container, false);

        return rootView;
    }


}
