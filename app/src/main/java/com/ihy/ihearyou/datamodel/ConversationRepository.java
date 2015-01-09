package com.ihy.ihearyou.datamodel;

import android.content.Context;

import com.ihy.ihearyou.utility.SharedPreferencesUtility;

import java.util.ArrayList;
import java.util.List;

public class ConversationRepository {

    public static final String ConversationRecord = "conversation_data";

    private static ConversationRepository mInstance = null;
    private Context mContext = null;

    //data to record
    List<ConversationData> mConversationDataList = null;

    private ConversationRepository(Context context){
        mContext = context;
        load();
    }

    static public ConversationRepository getInstance(Context context)
    {
        if(mInstance == null)
        {
            mInstance = new ConversationRepository(context);
        }
        return mInstance;
    }

    public void addSentence(SentenceData sentence, long date)
    {
        boolean added = false;
        for(ConversationData data : mConversationDataList)
        {
            if(data.getDate() == date) {
                data.addSentence(sentence);
                added = true;
                break;
            }
        }

        if(!added)
        {
            ConversationData newConversation = new ConversationData(date);
            newConversation.addSentence(sentence);
        }
    }

    public void load()
    {
        //by record type: sentence or video...
        Object conversationCache = SharedPreferencesUtility.readSharedPreference(mContext, ConversationRecord);
        if(conversationCache != null)
            mConversationDataList = (List<ConversationData>)conversationCache;
        else
            mConversationDataList = new ArrayList<ConversationData>();
    }

    public void save()
    {
        //by record type: sentence or video...
        if(mConversationDataList != null)
            SharedPreferencesUtility.writeSharedPreference(mContext, ConversationRecord, mConversationDataList);
    }
}
