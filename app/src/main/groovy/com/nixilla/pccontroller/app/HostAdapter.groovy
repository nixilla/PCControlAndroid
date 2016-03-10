package com.nixilla.pccontroller.app

import groovy.transform.CompileStatic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.ImageView

@CompileStatic
class HostAdapter extends ArrayAdapter<TargetHost> {

    HostAdapter(Context context, List<TargetHost> hosts) {
        super(context, R.layout.host_row, hosts)
    }

    @Override
    View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext())

        View hostView = inflater.inflate(R.layout.host_row, parent, false)

        TargetHost host = getItem(position) as TargetHost
        TextView hostIpTextView = hostView.findViewById(R.id.hostIpTextView) as TextView
        TextView hostNameTextView = hostView.findViewById(R.id.hostNameTextView) as TextView
        TextView hostBoottimeTextView = hostView.findViewById(R.id.hostBoottimeTextView) as TextView
        ImageView icon = hostView.findViewById(R.id.iconImageView) as ImageView

        hostIpTextView.setText(host.ipAddress)
        hostNameTextView.setText(host.hostname)
        hostBoottimeTextView.setText(host.boottime)

        icon.setImageResource(R.drawable.ubuntu)

        hostView
    }
}
