package com.example.indiacastdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.AlertDialogModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class MonitoringPointsFragment extends Fragment {
    String networkId, networkName;
    DatabaseHelper db;
    String Technicians, Ground_subscribers;
    TextView txt_Tec, txt_Grd;
    EditText edt_Tec, edt_Grd;
    Button submit;

    public MonitoringPointsFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_monitoring_points, container, false);
        networkId = getArguments().getString("networkId");
        networkName = getArguments().getString("networkName");
        txt_Tec = v.findViewById(R.id.txt_Tec);
        txt_Grd = v.findViewById(R.id.txt_Grd);
        edt_Tec = v.findViewById(R.id.edt_Tec);
        edt_Grd = v.findViewById(R.id.edt_Grd);
        submit = v.findViewById(R.id.submit);
        db = new DatabaseHelper(getContext());
        Cursor res = db.getMonitoringPoint(networkId);
        if (res.getCount() == 1) {
            if (res.moveToFirst()) {
                Ground_subscribers = res.getString(res.getColumnIndex("Ground_subscribers"));
                Technicians = res.getString(res.getColumnIndex("Technicians"));
                res.moveToNext();
            }
            txt_Grd.setText(Ground_subscribers);
            txt_Tec.setText(Technicians);
        } else if (res.getCount() == 0) {
            txt_Grd.setText("000");
            txt_Tec.setText("000");
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(false);
                    builder.setMessage("Are you sure, you want to submit?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (db.setMonitoringPoint(networkId, edt_Tec.getText().toString(), edt_Grd.getText().toString())) {
                                        txt_Grd.setText(edt_Grd.getText().toString());
                                        txt_Tec.setText(edt_Tec.getText().toString());
                                        JSONObject postData = new JSONObject();
                                        try {
                                            postData.put("Ground_subscribers", edt_Grd.getText().toString());
                                            postData.put("Technicians", edt_Tec.getText().toString());
                                            postData.put("Network_ID", networkId);
                                            postRequest(postData.toString());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    void postRequest(String postBody) throws IOException {

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
//        String postUrl = "http://182.71.62.87:80/api/setlist/monitoringpoints";
            String postUrl = getString(R.string.api) + "/api/setlist/monitoringpoints";
//        String postUrl = "http://http://172.50.2.114:80/api/setlist/monitoringpoints";
//        String postUrl = "http://10.102.32.153:8975/api/setlist/monitoringpoints";

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, postBody);
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(postUrl)
                    .addHeader("content-type", "application/json")
                    .put(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        AlertDialogModel.generateAlertDialog(getContext(), "Alert", "Server connection lost!");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    String jsonData = null;
                    try {
                        jsonData = response.body().string();
                        Log.d("submitResponse: ", jsonData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                AlertDialogModel.generateAlertDialog(getContext(), "Alert", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
