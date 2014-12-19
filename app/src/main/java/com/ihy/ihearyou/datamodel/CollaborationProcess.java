package com.ihy.ihearyou.datamodel;

public class CollaborationProcess {

    private static final String[] ProcessTitle =
        {
            "Test 1",
            "Test 2",
            "Test 3",
            "Test 4"
        };

    private static final String[] ProcessDescription =
        {
            "Description 1",
            "Description 2",
            "Description 3",
            "Description 4"
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
