package com.ihy.ihearyou.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConversationData implements Serializable{
    private long mDate;
    private List<SentenceData> mSentenceDataList = null;

    public ConversationData(long date)
    {
        mDate = date;
        mSentenceDataList = new ArrayList<SentenceData>();
    }

    public long getDate()
    {
        return mDate;
    }

    public void addSentence(SentenceData sentence)
    {
        mSentenceDataList.add(sentence);
    }

    public List<SentenceData> getSentenceDataList()
    {
        return mSentenceDataList;
    }
}
