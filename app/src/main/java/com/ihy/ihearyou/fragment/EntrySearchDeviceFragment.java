package com.ihy.ihearyou.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ihy.ihearyou.R;

public class EntrySearchDeviceFragment extends Fragment {
    public interface OnFragmentInteractionListener {
        public void onDeviceSelected(String deviceName);
    }

    private OnFragmentInteractionListener mListener;
    public void setOnFragmentInteractionListener(OnFragmentInteractionListener listener)
    {
        mListener = listener;
    }

    public static EntrySearchDeviceFragment newInstance() {
        EntrySearchDeviceFragment fragment = new EntrySearchDeviceFragment();
        return fragment;
    }

    public EntrySearchDeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ListView mDeviceList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_entry_search_device, container, false);

        mDeviceList = (ListView)rootView.findViewById(R.id.list_device);
        mDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String deviceName = (String)mDeviceList.getAdapter().getItem(position);
                if(mListener != null)
                    mListener.onDeviceSelected(deviceName);
            }
        });
        Button btnSearch = (Button)rootView.findViewById(R.id.button_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDeviceList.getAdapter() != null)
                    return;
                DeviceListAdapter adapter = new DeviceListAdapter(getActivity());
                mDeviceList.setAdapter(adapter);
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

    class DeviceListAdapter extends BaseAdapter
    {
        private Context mContext;
        DeviceListAdapter(Context context)
        {
            mContext = context;
        }

        private final String[] DeviceNames =
            {
                "SL-2104",
                "AT-7170",
                "GU-3324"
            };

        @Override
        public int getCount() {
            return DeviceNames.length;
        }

        @Override
        public Object getItem(int position) {
            return DeviceNames[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            LayoutInflater inflater = LayoutInflater.from(mContext);

            if(v == null)
                v = inflater.inflate(R.layout.device_item, null);

            TextView deviceText = (TextView)v.findViewById(R.id.device_id);
            deviceText.setText(DeviceNames[position]);

            return v;
        }
    }
}
