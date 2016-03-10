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

    AsyncHttpClient client;
    NetworkHelper helper = new NetworkHelper();
    String ipAddress;
    List<TargetHost> hosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new AsyncHttpClient();
        PersistentCookieStore cookieStore = new PersistentCookieStore(this);
        client.setCookieStore(cookieStore);
        client.setTimeout(1000);
        client.setMaxRetriesAndTimeout(0,0);


        TextView ipTextView = (TextView) findViewById(R.id.ipTextView);

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {

            WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
            ipAddress = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

            ipTextView.setText(ipAddress);
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

        final HostAdapter adapter = (HostAdapter) ((ListView) findViewById(R.id.hostsListView)).getAdapter();

        adapter.clear();

        List<String> ipRange = helper.getClassC(ipAddress);

        for(final String ip : ipRange) {

            String url = "http://" + ip + ":8081/";

            client.get(url, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {

                        TargetHost host = new TargetHost();
                        host.setHostname(response.getString("hostname"));
                        host.setIpAddress(ip);
                        host.setToken(response.getString("token"));
                        host.setBoottime(response.getString("boottime"));
                        host.setStatus(response.getString("status"));

                        adapter.add(host);

                        Log.i("com.nixilla", "IP " + ip + " responds, added ...");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.i("com.nixilla", "IP " + ip + " not responding. " + throwable.getMessage());
                }
            });
        }
    }
}
