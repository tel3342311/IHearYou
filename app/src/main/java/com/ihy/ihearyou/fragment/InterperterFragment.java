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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ihy.ihearyou.R;

import java.util.ArrayList;

public class InterperterFragment extends Fragment implements RecognitionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View mRootView;
    private ListView mListView;
    private Button mBtnRecord;
    private SpeechRecognizer mSpeechReconizer;
    private Boolean mIsListening = false;
    private ArrayList<String> mSpeechList = new ArrayList<String>();
    private SpeechBubbleAdapter mSpeechBubbleAdapter;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InterperterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InterperterFragment newInstance(String param1, String param2) {
        InterperterFragment fragment = new InterperterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public InterperterFragment() {
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
        mRootView = inflater.inflate(R.layout.fragment_interperter, container, false);
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
            mSpeechReconizer = SpeechRecognizer.createSpeechRecognizer(getActivity().getApplicationContext());
            mSpeechReconizer.setRecognitionListener(this);
        }
        setupListView();
        mBtnRecord.setEnabled(isSupport);
        mBtnRecord.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSpeechReconizer != null && !mIsListening) {
                Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "cmn-Hant-TW");
                // accept partial results if they come
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.ihy.ihearyou");
                mSpeechReconizer.startListening(recognizerIntent);
                mIsListening = true;
            } else if (mIsListening == true) {
                mSpeechReconizer.stopListening();
                mIsListening = false;
            }
        }
    };

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
