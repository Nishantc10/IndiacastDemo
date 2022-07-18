package com.example.indiacastdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.AlertDialogModel;
import com.example.indiacastdemo.Model.StatusMaster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class StatusFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<StatusMaster> lst_status;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    String networkId, networkName, createdDate, ABCD;
    private ProgressDialog progress;
    DatabaseHelper db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_status, container, false);
        networkId = getArguments().getString("networkId");
        networkName = getArguments().getString("networkName");
        createdDate = getArguments().getString("createdDate");
        ABCD = getArguments().getString("ABCD");
        lst_status = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.status_fragment_recy);
        adapter = new StatusViewAdapter(getContext(), lst_status);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        db = new DatabaseHelper(getContext());
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            getStatusResponse();
        } else {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Alert!");
                builder.setMessage("No internet connection...!");
                builder.setCancelable(true);
                builder.setNeutralButton("Try again!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConnectivityManager cm =
                                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        boolean isConnected = activeNetwork != null &&
                                activeNetwork.isConnectedOrConnecting();
                        if (isConnected) {
                            getStatusResponse();
                        } else {
                            try {
                                AlertDialogModel.generateAlertDialog(getActivity(), "Alert!", "No internet connection!!!");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                builder.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return v;
    }

    private void getStatusResponse() {
        progress = new ProgressDialog(getContext());
        progress.setCancelable(false);
        progress.setTitle("Please wait....");
        progress.setMessage("Fetching details");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            JSONObject postData = new JSONObject();
            try {
                postData.put("networkId", networkId);
                postData.put("createdDate", createdDate);
                postRequest(postData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            progress.dismiss();
        }
    }

    void postRequest(String postBody) throws IOException {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            String postUrl = getString(R.string.api) + "/api/list/status/placement/";

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, postBody);
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(postUrl)
                    .addHeader("content-type", "application/json")
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        try {
                                            progress.dismiss();
                                            AlertDialogModel.generateAlertDialog(getActivity(), "Alert!", "Server connection lost!");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    } catch (Exception a) {
                        a.printStackTrace();
                        progress.dismiss();
                    }
                    call.cancel();
                }

                public JSONArray sortJsonArray(JSONArray array) {
                    List<JSONObject> jsons = new ArrayList<JSONObject>();
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            jsons.add(array.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    Collections.sort(jsons, new Comparator<JSONObject>() {
                        @Override
                        public int compare(JSONObject lhs, JSONObject rhs) {
                            String lid = null;
                            String rid = null;
                            try {
                                lid = lhs.getString("Updated_Date");
                                rid = rhs.getString("Updated_Date");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Here you could parse string id to integer and then compare.
                            return lid.compareTo(rid);
                        }
                    });
                    return new JSONArray(jsons);
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    String jsonData = response.body().string();
                    lst_status.clear();
                    String Last_Status = null;
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        JSONArray statusArray = jsonArray.getJSONArray(0);
                        statusArray = sortJsonArray(statusArray);
                        JSONObject statusObject = null;

                        Drawable statusDrawable = null;
                        for (int i = 0; i < statusArray.length(); i++) {
                            StringTokenizer tk = null;
                            statusObject = statusArray.getJSONObject(i);
                            SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-d H:m:S");
                            String Created_Date = statusObject.getString("Created_Date").replace('T', ' ');
                            String Updated_Date = statusObject.getString("Updated_Date").replace('T', ' ');
                            String Approved_At = statusObject.getString("Approved_At");
                            String Approved_By = statusObject.getString("Approved_By");
                            String Rejected_At = statusObject.getString("Rejected_At");
                            String Rejected_By = statusObject.getString("Rejected_By");
                            String Status_ID = statusObject.getString("Status_ID");
                            String Network_ID = statusObject.getString("Network_ID");
                            String Network_Name = statusObject.getString("Network_Name");
                            String Entered_By_Username = statusObject.getString("Entered_By_Username");
                            if (Status_ID.equals("STS0003")) {
                                tk = new StringTokenizer(Updated_Date);
                            } else {
                                tk = new StringTokenizer(Updated_Date);
                            }
                            String date = tk.nextToken();
                            String time = tk.nextToken();

                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                            SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
                            Date tm = null;
                            try {
                                tm = sdf.parse(time);
                                time = sdfs.format(tm);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
//                            Activity activity = getActivity();
                            if (Status_ID.equals("STS0003")) {
                                statusDrawable = getResources().getDrawable(R.drawable.ic_status_yellow);
                                Status_ID = "Submitted";
                                lst_status.add(new StatusMaster(Network_ID, networkName, "By " + Entered_By_Username, "", statusDrawable, date + " " + time, Status_ID));
                            }
                            if (Status_ID.equals("STS0004")) {
                                statusDrawable = getResources().getDrawable(R.drawable.ic_status_green);
                                Status_ID = "Approved";
                                lst_status.add(new StatusMaster(Network_ID, networkName, "By " + Approved_By, "(" + Approved_At + ")", statusDrawable, date + " " + time, Status_ID));
                            }
                            if (Status_ID.equals("STS0005")) {
                                statusDrawable = getResources().getDrawable(R.drawable.ic_status_red);
                                Status_ID = "Rejected";
                                lst_status.add(new StatusMaster(Network_ID, networkName, "By " + Rejected_By, "(" + Rejected_At + ")", statusDrawable, date + " " + time, Status_ID));
                            }
                            if (Status_ID.equals("STS0006")) {
                                statusDrawable = getResources().getDrawable(R.drawable.ic_status_orange);
                                Status_ID = "Completed";
                                lst_status.add(new StatusMaster(Network_ID, networkName, "By " + Approved_By, "(" + Approved_At + ")", statusDrawable, date + " " + time, Status_ID));
                            }
                            if (i == (statusArray.length() - 1)) {
                                Last_Status = statusObject.getString("Status_ID");
                                db.updatePlacementStatus(networkId, createdDate, Last_Status);
                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = new StatusViewAdapter(getContext(), lst_status);
                                    recyclerView.setAdapter(adapter);
                                }
                            });
                        }
                        progress.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                        progress.dismiss();
                    }
                }
            });
        } else {
            try {
                progress.dismiss();
                AlertDialogModel.generateAlertDialog(getContext(), "Alert!", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
