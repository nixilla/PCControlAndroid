package com.nixilla.pccontroller.app

import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import groovy.transform.CompileStatic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.ImageView
import org.json.JSONObject

@CompileStatic
class HostAdapter extends ArrayAdapter<TargetHost> {

    AsyncHttpClient client

    HostAdapter(Context context, List<TargetHost> hosts, AsyncHttpClient client) {
        super(context, R.layout.host_row, hosts)
        this.client = client
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

        Button button = hostView.findViewById(R.id.shutdownButton) as Button

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            void onClick(View v) {

                Log.i("com.nixilla", "I would shutdown host at IP ${host?.ipAddress}")

                String url = "http://${host?.ipAddress}:8081/"

                RequestParams params = new RequestParams()

                params.add("token", host.token)

                client.post(url, params, new JsonHttpResponseHandler() {

                    @Override
                    void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("com.nixilla", "Shutdown request success")
                        Toast.makeText(v.getContext(), "Shutdown request has been sent", Toast.LENGTH_SHORT).show()
                    }

                    @Override
                    void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.i("com.nixilla", "Shutdown request failed: ${throwable.message}")
                        Toast.makeText(v.getContext(), "Unable to send shutdown request, try again ...", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })

        hostIpTextView.setText(host.ipAddress)
        hostNameTextView.setText(host.hostname)
        hostBoottimeTextView.setText(host.boottime)

        icon.setImageResource(R.drawable.ubuntu)

        hostView
    }
}
