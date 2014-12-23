package com.ihy.ihearyou.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.ihy.ihearyou.R;
import com.ihy.ihearyou.fragment.CollaborationFinalizeFragment;
import com.ihy.ihearyou.fragment.CollaborationMainFragment;
import com.ihy.ihearyou.fragment.CollaborationProcessFragment;

public class CollaborationActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaboration);

        CollaborationMainFragment cmf = CollaborationMainFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.root_frame, cmf).commit();
        cmf.setOnCollaborationStartListener(new CollaborationMainFragment.OnCollaborationStartListener() {
            @Override
            public void onStart() {
                CollaborationProcessFragment cpf = CollaborationProcessFragment.newInstance();
                cpf.setOnCollaborationProcessFinishListener(new CollaborationProcessFragment.OnCollaborationProcessFinishListener() {
                    @Override
                    public void onFinish() {
                        CollaborationFinalizeFragment cff = CollaborationFinalizeFragment.newInstance();
                        getFragmentManager().beginTransaction().replace(R.id.root_frame, cff).commit();
                    }
                });
                getFragmentManager().beginTransaction().replace(R.id.root_frame, cpf).commit();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }
}
