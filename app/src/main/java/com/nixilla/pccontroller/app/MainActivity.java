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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

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

        AsyncHttpClient client = new AsyncHttpClient();
        PersistentCookieStore cookieStore = new PersistentCookieStore(this);
        client.setCookieStore(cookieStore);

        String url = "http://10.0.1.6:8081/";

        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


                try {
                    TargetHost host = new TargetHost();
                    host.setHostname(response.getString("hostname"));
                    host.setIpAddress(1);
                    host.setToken(response.getString("token"));
                    host.setBoottime(response.getString("boottime"));
                    host.setStatus(response.getString("status"));
//                    host.setCookieName("PHPSSID");
//                    host.setCookieValue("sdkjfhalkjdfhalskjdf");

                    ((HostAdapter)((ListView) findViewById(R.id.hostsListView)).getAdapter()).add(host);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
