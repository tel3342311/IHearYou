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
import android.widget.TextView;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.activity.LessonActivity;

import java.util.ArrayList;
import java.util.List;

public class LessonContentFragment extends Fragment {

    private Button mBtnPlay;
    private TextView mQuestionTitle;
    private TextView mQuestionDes;
    private int mCurrentAudioRes;
    class LessonInfo {
        int idx;
        String Title;
        int aduioRes;
    }
    private List<LessonInfo> mLessInfo = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static LessonContentFragment newInstance() {
        LessonContentFragment fragment = new LessonContentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public LessonContentFragment() {
        // Required empty public constructor
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
        setupLessonInfo();
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
        mQuestionTitle = (TextView) getActivity().findViewById(R.id.questionTitle);
        mQuestionDes = (TextView) getActivity().findViewById(R.id.questionDesc);
        int nLessonType = ((LessonActivity)getActivity()).getLessonType();
        int nQuestionNum = ((LessonActivity)getActivity()).getQuestionNum();
        if (nLessonType == LessonActivity.LESSON_ORAL) {
            LessonInfo item = mLessInfo.get(nQuestionNum);
            mQuestionTitle.setText("題目 " + item.idx);
            mQuestionDes.setText(item.Title);
            mCurrentAudioRes = item.aduioRes;
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), mCurrentAudioRes);
            mediaPlayer.setOnCompletionListener(mOnCompleteListener);
            mediaPlayer.start();

        }
    };

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Intent intent);
    }

    private void setupLessonInfo() {
        LessonInfo item = new LessonInfo();
        item.idx = 1;
        item.Title = "歸納";
        item.aduioRes = R.raw.oral_sumup;
        mLessInfo.add(item);
        //2
        item = new LessonInfo();
        item.idx = 2;
        item.Title = "補助";
        item.aduioRes = R.raw.oral_subsidy;
        mLessInfo.add(item);
        //3
        item = new LessonInfo();
        item.idx = 3;
        item.Title = "辨別";
        item.aduioRes = R.raw.oral_discern;
        mLessInfo.add(item);
        //4
        item = new LessonInfo();
        item.idx = 4;
        item.Title = "討論";
        item.aduioRes = R.raw.oral_disscuss;
        mLessInfo.add(item);
        //5
        item = new LessonInfo();
        item.idx = 5;
        item.Title = "精簡";
        item.aduioRes = R.raw.oral_simplify;
        mLessInfo.add(item);
    }

    MediaPlayer.OnCompletionListener mOnCompleteListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Intent intent = new Intent();
            intent.putExtra("TAG", LessonContentFragment.this.getId());
            onButtonPressed(intent);
        }
    };
}
