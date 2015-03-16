package com.ihy.ihearyou.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ihy.ihearyou.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonFinishFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private ImageView mBtnRepeat;
    private ImageView mBtnNext;

    public LessonFinishFragment() {
        // Required empty public constructor
    }
    public static LessonFinishFragment newInstance() {
        LessonFinishFragment fragment = new LessonFinishFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onButtonPressed(Intent intent) {
        if (mListener != null) {
            mListener.onFragmentInteraction(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson_finish, container, false);
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

    private void findViews() {
        mBtnRepeat = (ImageView) getActivity().findViewById(R.id.repeat);
        mBtnNext = (ImageView) getActivity().findViewById(R.id.next);
        mBtnRepeat.setOnClickListener(mOnClickListener);
        mBtnNext.setOnClickListener(mOnClickListener);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Intent intent);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.next) {
                Intent intent = new Intent();
                intent.putExtra("TAG", LessonFinishFragment.this.getId());
                intent.putExtra(LessonFinishFragment.this.toString(), 1);
                onButtonPressed(intent);
            } else {
                Intent intent = new Intent();
                intent.putExtra("TAG", LessonFinishFragment.this.getId());
                intent.putExtra(LessonFinishFragment.this.toString(), 0);
                onButtonPressed(intent);
            }
        }
    };
}
