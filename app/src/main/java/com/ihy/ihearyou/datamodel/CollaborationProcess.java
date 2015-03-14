package com.ihy.ihearyou.datamodel;

public class CollaborationProcess {

    private static final String[] ProcessTitle =
        {
            "高頻測驗(左耳)",
            "高頻測驗(右耳)",
            "純音測驗(左耳)",
            "純音測驗(右耳)",
        };

    private static final String[] ProcessDescription =
        {
            "點擊播放鍵開始測試\n並回答聽見聲音與否",
            "點擊播放鍵開始測試\n並回答聽見聲音與否",
            "點擊播放鍵開始測試\n並回答聽見聲音與否",
            "點擊播放鍵開始測試\n並回答聽見聲音與否",
        };

    private static final String[] ProcessInformation =
        {
            "Info 1",
            "Info 2",
            "Info 3",
            "Info 4"
        };

    private int mCurrentProcess;

    public CollaborationProcess()
    {
        mCurrentProcess = -1;
    }

    public String getTitle()
    {
        return ProcessTitle[mCurrentProcess];
    }

    public String getDescription()
    {
        return ProcessDescription[mCurrentProcess];
    }

    public String getInformation()
    {
        return ProcessInformation[mCurrentProcess];
    }

    public int getTotalProcessCount()
    {
        return 4;
    }

    public int getCurrentProcess()
    {
        return mCurrentProcess;
    }

    public boolean stepToNextProcess()
    {
        mCurrentProcess++;
        if(mCurrentProcess >= ProcessTitle.length)
            return false;
        return true;
    }
}
