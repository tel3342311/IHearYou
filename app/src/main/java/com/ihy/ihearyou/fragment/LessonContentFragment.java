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
    private Button mBtnRecord;
    private TextView mQuestionTitle;
    private TextView mQuestionDes;
    private TextView mTitleState;
    private int mCurrentAudioRes;
    private TextView mOption1;
    private TextView mOption2;
    private TextView mOption3;
    private TextView mOption4;
    private View mOptionView1;
    private View mOptionView2;
    private View mOptionView3;
    private View mOptionView4;

    class LessonInfo {
        int idx;
        String Title;
        int aduioRes;
        //for dialogue
        String option1;
        String option2;
        String option3;
        String option4;
    }
    private List<LessonInfo> mLessInfo = new ArrayList<>();
    private List<LessonInfo> mLessInfo_pronounce = new ArrayList<>();
    private List<LessonInfo> mLessInfo_dialogue = new ArrayList<>();
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
        View v;
        int nLessonType = ((LessonActivity)getActivity()).getLessonType();
        if (nLessonType == LessonActivity.LESSON_ORAL) {
            v = inflater.inflate(R.layout.fragment_lesson_content, container, false);
            return v;
        } else if (nLessonType == LessonActivity.LESSON_PRONOUNCE) {
            v = inflater.inflate(R.layout.fragment_lesson_content_pronounce, container, false);
            return v;
        } else if (nLessonType == LessonActivity.LESSON_DIALOGUE) {
            v = inflater.inflate(R.layout.fragment_lesson_content_dialogue, container, false);
            return v;
        }

        return null;

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
        setupLessonInfo_pronounce();
        setupLessonInfo_dialogue();
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
        mTitleState = (TextView) getActivity().findViewById(R.id.title_state);
        mQuestionTitle = (TextView) getActivity().findViewById(R.id.questionTitle);
        mQuestionDes = (TextView) getActivity().findViewById(R.id.questionDesc);
        mOption1 = (TextView) getActivity().findViewById(R.id.option_text1);
        mOption2 = (TextView) getActivity().findViewById(R.id.option_text2);
        mOption3 = (TextView) getActivity().findViewById(R.id.option_text3);
        mOption4 = (TextView) getActivity().findViewById(R.id.option_text4);
        mOptionView1 = getActivity().findViewById(R.id.option1);
        mOptionView2 = getActivity().findViewById(R.id.option2);
        mOptionView3 = getActivity().findViewById(R.id.option3);
        mOptionView4 = getActivity().findViewById(R.id.option4);
        int nLessonType = ((LessonActivity)getActivity()).getLessonType();
        int nQuestionNum = ((LessonActivity)getActivity()).getQuestionNum();
        if (nLessonType == LessonActivity.LESSON_ORAL) {
            LessonInfo item = mLessInfo.get(nQuestionNum);
            mQuestionTitle.setText("題目 " + item.idx);
            mQuestionDes.setText(item.Title);
            mCurrentAudioRes = item.aduioRes;
            mBtnRecord = (Button) getActivity().findViewById(R.id.record);
            mBtnRecord.setOnClickListener(mOnClickListener);
        } else if (nLessonType == LessonActivity.LESSON_PRONOUNCE) {
            LessonInfo item = mLessInfo_pronounce.get(nQuestionNum);
            mQuestionTitle.setText("題目 " + item.idx);
            mQuestionDes.setText(item.Title);
            mCurrentAudioRes = item.aduioRes;
            mBtnRecord = (Button) getActivity().findViewById(R.id.record);
            mBtnRecord.setOnClickListener(mOnClickListener);
        } else if (nLessonType == LessonActivity.LESSON_DIALOGUE) {
            LessonInfo item = mLessInfo_dialogue.get(nQuestionNum);
            mCurrentAudioRes = item.aduioRes;
            mOption1.setText(item.option1);
            mOption2.setText(item.option2);
            mOption3.setText(item.option3);
            mOption4.setText(item.option4);
            mOptionView1.setOnClickListener(mOnClickListener);
            mOptionView2.setOnClickListener(mOnClickListener);
            mOptionView3.setOnClickListener(mOnClickListener);
            mOptionView4.setOnClickListener(mOnClickListener);
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.play) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), mCurrentAudioRes);
                mediaPlayer.setOnCompletionListener(mOnCompleteListener);
                mediaPlayer.start();
                int nLessonType = ((LessonActivity)getActivity()).getLessonType();
                if (nLessonType != LessonActivity.LESSON_DIALOGUE) {
                    showRecording();
                }
            } else {
                if (v.getId() == R.id.record) {
                    showPlaying();
                }
                Intent intent = new Intent();
                intent.putExtra("TAG", LessonContentFragment.this.getId());
                onButtonPressed(intent);
            }

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

    public void setupLessonInfo_pronounce() {
        LessonInfo item = new LessonInfo();
        item.idx = 1;
        item.Title = "ㄅ\nㄢ";
        item.aduioRes = R.raw.pronounce_ban;
        mLessInfo_pronounce.add(item);
        //2
        item = new LessonInfo();
        item.idx = 2;
        item.Title = "ㄔ\nㄨ";
        item.aduioRes = R.raw.pronounce_chu;
        mLessInfo_pronounce.add(item);
        //3
        item = new LessonInfo();
        item.idx = 3;
        item.Title = "ㄕ\nㄨ";
        item.aduioRes = R.raw.pronounce_shu;
        mLessInfo_pronounce.add(item);
        //4
        item = new LessonInfo();
        item.idx = 4;
        item.Title = "ㄘ\nㄨ";
        item.aduioRes = R.raw.pronounce_tsu;
        mLessInfo_pronounce.add(item);
        //5
        item = new LessonInfo();
        item.idx = 5;
        item.Title = "ㄙㄨ";
        item.aduioRes = R.raw.pronounce_su;
        mLessInfo_pronounce.add(item);
    }

    public void setupLessonInfo_dialogue() {
        LessonInfo item = new LessonInfo();
        item.idx = 1;
        item.aduioRes = R.raw.dialogue_running;
        item.option1 = "早上要去上班";
        item.option2 = "早上要去跑步";
        item.option3 = "中午要去上班";
        item.option4 = "中午要去跑步";
        mLessInfo_dialogue.add(item);
        //2
        item = new LessonInfo();
        item.idx = 2;
        item.aduioRes = R.raw.dialogue_book_looking;
        item.option1 = "他在休息";
        item.option2 = "他在看書";
        item.option3 = "我在休息";
        item.option4 = "我在看書";
        mLessInfo_dialogue.add(item);
        //3
        item = new LessonInfo();
        item.idx = 3;
        item.aduioRes = R.raw.dialogue_exam;
        item.option1 = "明天要考試";
        item.option2 = "明天要看書";
        item.option3 = "今天要考試";
        item.option4 = "今天要看書";
        mLessInfo_dialogue.add(item);
        //4
        item = new LessonInfo();
        item.idx = 4;
        item.aduioRes = R.raw.dialogue_disscussion;
        item.option1 = "明天一起去討論";
        item.option2 = "明天一起去唱歌";
        item.option3 = "後天一起去討論";
        item.option4 = "後天一起去唱歌";
        mLessInfo_dialogue.add(item);
        //5
        item = new LessonInfo();
        item.idx = 5;
        item.aduioRes = R.raw.dialogue_bookstore;
        item.option1 = "我們一起到公園運動";
        item.option2 = "我們一起到書店看書";
        item.option3 = "他們一起到公園運動";
        item.option4 = "他們一起到書店看書";
        mLessInfo_dialogue.add(item);
    }
    MediaPlayer.OnCompletionListener mOnCompleteListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            //Intent intent = new Intent();
            //intent.putExtra("TAG", LessonContentFragment.this.getId());
            //onButtonPressed(intent);
        }
    };

    private void showRecording() {
        mBtnRecord.setVisibility(View.VISIBLE);
        mBtnPlay.setVisibility(View.INVISIBLE);
        mTitleState.setText(getString(R.string.questionDetailRecord));
    }

    private void showPlaying() {
        mBtnPlay.setVisibility(View.VISIBLE);
        mBtnRecord.setVisibility(View.INVISIBLE);
        mTitleState.setText(getString(R.string.questionDetail));
    }
}
