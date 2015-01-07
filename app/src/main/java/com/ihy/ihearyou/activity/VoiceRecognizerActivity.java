package com.ihy.ihearyou.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ihy.ihearyou.R;

import java.util.ArrayList;
import java.util.List;

public class VoiceRecognizerActivity extends ActionBarActivity implements RecognitionListener{

    Button mBtnReconizer;
    TextView mTextOutput;
    SpeechRecognizer mSpeechReconizer;
    Boolean mIsListening = false;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSpeechReconizer != null && !mIsListening) {
                //Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //mSpeechReconizer.startListening(intent);

                Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "cmn-Hant-TW");
                // accept partial results if they come
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
                recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.ihy.ihearyou");
                mSpeechReconizer.startListening(recognizerIntent);
                //mSpeechReconizer.startListening(RecognizerIntent.getVoiceDetailsIntent(getApplicationContext()));
                mIsListening = true;
            } else if (mIsListening == true) {
                mSpeechReconizer.stopListening();
                mIsListening = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recognizer);
        findViews();


    }

    private void findViews() {
        mBtnReconizer = (Button) this.findViewById(R.id.btn_recongize);
        mTextOutput = (TextView) this.findViewById(R.id.text_output);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_voice_recognizer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isSupport = SpeechRecognizer.isRecognitionAvailable(this);
        if (isSupport) {

            mSpeechReconizer = SpeechRecognizer.createSpeechRecognizer(this);
            mSpeechReconizer.setRecognitionListener(this);

            //query support languages
            Intent detailIntent = new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
            sendOrderedBroadcast(detailIntent, null, new LanguageChecker(), null, Activity.RESULT_OK, null, null);
        }
        mTextOutput.setEnabled(isSupport);
        mBtnReconizer.setEnabled(isSupport);
        mBtnReconizer.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSpeechReconizer != null) {
            mSpeechReconizer.destroy();
        }
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
        int i = 0;
        for (String s : ret) {
            buf.append(s + "  --score:"+scores[i++]);
            buf.append("\n");
        }
        mTextOutput.setText(buf.toString());
        mIsListening = false;
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.d("Speech", "onPartialResults");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d("Speech", "onEvent");
    }

    class LanguageChecker extends BroadcastReceiver
    {
          private List<String> supportedLanguages;

        private String languagePreference;

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle results = getResultExtras(true);
            if (results.containsKey(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE))
            {
                languagePreference = results.getString(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE);
                Log.d("speech", "languagePreference :"+languagePreference);
            }
            if (results.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES))
            {
                supportedLanguages = results.getStringArrayList(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES);
                for ( String tmp : supportedLanguages) {
                    Log.d("speech", "supportedLanguages :" + tmp);
                }
            }
        }
    }
}