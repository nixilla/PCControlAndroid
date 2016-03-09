package com.nixilla.pccontroller.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    List<TargetHost> hosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView ipTextView = (TextView) findViewById(R.id.ipTextView);

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {

            WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

            Log.i("com.nixilla", wm.getConnectionInfo().toString());

            ipTextView.setText(ip);
        }
        else {
            ipTextView.setText("Wifi not connected");
            Log.i("com.nixilla", "Wifi not connected");
        }

        ListAdapter adapter = new HostAdapter(this, hosts);
        ListView hostsListView = (ListView) findViewById(R.id.hostsListView);
        hostsListView.setAdapter(adapter);
    }

    public void findHosts(View view) {
        Log.i("com.nixilla", "clicked Find hosts");

        // for testing

        for (int i = 1; i < 19; i++) {

            TargetHost host = new TargetHost();
            host.setHostname("ubuntu-desktop");
            host.setIpAddress(i);
            host.setToken("123123");
            host.setCookieName("PHPSSID");
            host.setCookieValue("sdkjfhalkjdfhalskjdf");
            hosts.add(host);
        }



        ListView hostsListView = (ListView) findViewById(R.id.hostsListView);
        ((BaseAdapter) hostsListView.getAdapter()).notifyDataSetChanged();
    }
}
