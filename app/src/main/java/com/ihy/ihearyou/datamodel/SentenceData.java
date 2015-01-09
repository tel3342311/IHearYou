package com.ihy.ihearyou.datamodel;

import java.io.Serializable;

public class SentenceData implements Serializable{
    private String mText;
    private String mVoiceFile;

    public SentenceData(String text, String voiceFile)
    {
        mText = text;
        mVoiceFile = voiceFile;
    }

    public String getText()
    {
        return mText;
    }

    public String getVoiceFile()
    {
        return mVoiceFile;
    }
}
