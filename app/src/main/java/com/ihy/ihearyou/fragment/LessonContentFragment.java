package com.ihy.ihearyou.fragment;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.activity.LessonContentActivity;

public class LessonContentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button mBtnPlay;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static LessonContentFragment newInstance(String param1, String param2) {
        LessonContentFragment fragment = new LessonContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LessonContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson_content, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Intent intent) {
        if (mListener != null) {
            mListener.onFragmentInteraction(intent);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void findViews() {
        mBtnPlay = (Button) getActivity().findViewById(R.id.play);
        mBtnPlay.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), R.raw.goat);
            mediaPlayer.start();
        }
    };

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Intent intent);
    }

}
