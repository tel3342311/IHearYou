package com.ihy.ihearyou.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ihy.ihearyou.R;

import java.util.ArrayList;

public class InterpreterFragment extends Fragment implements RecognitionListener {

    private OnFragmentInteractionListener mListener;
    private View mRootView;
    private ListView mListView;
    private Button mBtnRecord;
    private SpeechRecognizer mSpeechRecognizer;
    private Boolean mIsListening = false;
    private ArrayList<String> mSpeechList = new ArrayList<String>();
    private SpeechBubbleAdapter mSpeechBubbleAdapter;

    public static InterpreterFragment newInstance() {
        InterpreterFragment fragment = new InterpreterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public InterpreterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_interpreter, container, false);
        findViews();
        return mRootView;
    }

    private void findViews() {
        mBtnRecord = (Button) mRootView.findViewById(R.id.record_btn);
        mListView = (ListView) mRootView.findViewById(R.id.speech_list);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d("Speech", "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("Speech", "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.d("Speech", "onRmsChanged");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.d("Speech", "onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("Speech", "onEndOfSpeech");
    }

    @Override
    public void onError(int error) {
        Log.d("Speech", "onError :" + error);
    }

    @Override
    public void onResults(Bundle results) {

        Log.d("Speech", "onResults :" + results);
        ArrayList<String> ret = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        //Confidence values close to 1.0 indicate high confidence
        float[] scores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
        StringBuffer buf = new StringBuffer();
        int idx = 0;
        float max = 0.f;
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] > max) {
                idx = i;
            }
        }
        mSpeechList.add(ret.get(idx));
        mIsListening = false;
        mSpeechBubbleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.d("Speech", "onPartialResults");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d("Speech", "onEvent");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        boolean isSupport = SpeechRecognizer.isRecognitionAvailable(getActivity());
        if (isSupport) {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity().getApplicationContext());
            mSpeechRecognizer.setRecognitionListener(this);
        }
        setupListView();
        mBtnRecord.setEnabled(isSupport);
        mBtnRecord.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSpeechRecognizer != null && !mIsListening) {
                Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "cmn-Hant-TW");
                // accept partial results if they come
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.ihy.ihearyou");
                mSpeechRecognizer.startListening(recognizerIntent);
                mIsListening = true;
            } else if (mIsListening == true) {
                mSpeechRecognizer.stopListening();
                mIsListening = false;
            }
        }
    };

    private void startListen() {
        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "cmn-Hant-TW");
        // accept partial results if they come
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.ihy.ihearyou");
        mSpeechRecognizer.startListening(recognizerIntent);
        mIsListening = true;
    }

    private void stopListening() {
        mSpeechRecognizer.stopListening();
        mIsListening = false;
    }
    private void setupListView() {
        mSpeechBubbleAdapter = new SpeechBubbleAdapter(getActivity(), mSpeechList);
        mListView.setAdapter(mSpeechBubbleAdapter);
    }

    private class SpeechBubbleAdapter extends BaseAdapter {

        private Context mContext = null;
        private LayoutInflater mInflater = null;
        private ArrayList<String> mAdapterList;

        public class ViewHolder {
            public TextView mTitleView;
        }

        public SpeechBubbleAdapter(Context context, ArrayList<String> list) {
            mContext = context;
            mAdapterList = list;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return mAdapterList.size();
        }

        @Override
        public Object getItem(int position) {
            return mAdapterList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listview_interperter_item, null);
                holder = new ViewHolder();
                holder.mTitleView = (TextView) convertView.findViewById(R.id.speech_data);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.mTitleView.setText(mAdapterList.get(position));
            convertView.setTag(holder);
            return convertView;
        }
    }
}
