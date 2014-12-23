package com.ihy.ihearyou.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ihy.ihearyou.R;

public class EntryConnectionFragment extends Fragment {

    private enum ConnectStatus
    {
        UnConnect, Connecting, Connected;
    };

    private ConnectStatus mConnectStatus = ConnectStatus.UnConnect;

    private static final String Device_Name = "device_name";

    private static final int CONNECT_FINISH = 11;

    public interface OnFragmentInteractionListener {
        public void onDeviceConnected();
    }

    private OnFragmentInteractionListener mListener;
    public void setOnFragmentInteractionListener(OnFragmentInteractionListener listener)
    {
        mListener = listener;
    }
    
    private String mDeviceName;

    public static EntryConnectionFragment newInstance(String deviceName) {
        EntryConnectionFragment fragment = new EntryConnectionFragment();
        Bundle args = new Bundle();
        args.putString(Device_Name, deviceName);
        fragment.setArguments(args);
        return fragment;
    }

    public EntryConnectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDeviceName = getArguments().getString(Device_Name);
        }
    }

    Button mBtnConnect;
    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == CONNECT_FINISH)
            {
                mBtnConnect.setText("Go to main page");
                mBtnConnect.setEnabled(true);
                mConnectStatus = ConnectStatus.Connected;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_entry_connection, container, false);

        mBtnConnect = (Button)rootView.findViewById(R.id.button_connection);
        mBtnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mConnectStatus)
                {
                    case UnConnect:
                    {
                        mBtnConnect.setEnabled(false);
                        mBtnConnect.setText("Connecting...");
                        mConnectStatus = ConnectStatus.Connecting;
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(CONNECT_FINISH), 3000);
                    }
                    break;
                    case Connecting:
                    {
                        //might not happen
                    }
                    break;
                    case Connected:
                    {
                        if(mListener != null)
                            mListener.onDeviceConnected();
                    }
                    break;
                    default:break;
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
