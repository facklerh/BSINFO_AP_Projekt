package com.example.pixelgame;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collection;
import java.util.HashSet;

import static com.example.pixelgame.Bluetooth.isMobileDevice;

public class MobileDeviceListAdapter extends ArrayAdapter<BluetoothDevice> {

    private static final int RSC_VIEW_ID = R.layout.item_mobile_device;
    private PlayerData statistics;

    HashSet<BluetoothDevice> devices = new HashSet<>();

    public MobileDeviceListAdapter(@NonNull Context context) {
        super(context, RSC_VIEW_ID);
        statistics = AppData.getPlayerData();
    }

    @Override
    public void add(@Nullable BluetoothDevice object) {
        if (isMobileDevice(object) && isNotContained(object)) {
            devices.add(object);
            super.add(object);
        }
    }

    private boolean isNotContained(BluetoothDevice object) {
        return !devices.contains(object);
    }

    @Override
    public void addAll(BluetoothDevice... items) {
        for (BluetoothDevice device : items) {
            add(device);
        }
    }

    @Override
    public void addAll(@NonNull Collection<? extends BluetoothDevice> collection) {
        for (BluetoothDevice device : collection) {
            add(device);
        }
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(RSC_VIEW_ID, parent, false);

        BluetoothDevice device = getItem(position);

        if (device != null) {
            TextView tvName = convertView.findViewById(R.id.tvName);
            GameStatistic statistic = statistics.get(device.getAddress());
            tvName.setText(statistic == null ? device.getName() : statistic.name);
        }

        return convertView;
    }
}
