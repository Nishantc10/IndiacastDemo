package com.example.indiacastdemo;

import android.database.Cursor;

import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.indiacastdemo.Database.DatabaseHelper;

public class HomeFragment extends Fragment {
    String networkCount = "0";
    TextView txt_totalNetworksCount;
    DatabaseHelper db;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        txt_totalNetworksCount = v.findViewById(R.id.txt_totalNetworksCount);
        db = new DatabaseHelper(getContext());
        Cursor cursor = db.getHomePageNetworkCount();
        if (cursor.moveToFirst()) {
            networkCount = cursor.getString(0);
        }
        db.close();
        txt_totalNetworksCount.setText(networkCount);
        TableLayout tableLayout = (TableLayout) v.findViewById(R.id.tableLayout);
        cursor = db.getAllNetworksCount();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                View tableRow = LayoutInflater.from(getActivity()).inflate(R.layout.homepage_table_item, null, false);
                TextView networkName = (TextView) tableRow.findViewById(R.id.networkName);
                TextView waiting = (TextView) tableRow.findViewById(R.id.waiting);
                TextView approved = (TextView) tableRow.findViewById(R.id.approved);
                TextView rejected = (TextView) tableRow.findViewById(R.id.rejected);
                TextView completed = (TextView) tableRow.findViewById(R.id.completed);
                networkName.setText(cursor.getString(cursor.getColumnIndex("Network_Name")));
                waiting.setText(cursor.getString(cursor.getColumnIndex("Submitted")));
                approved.setText(cursor.getString(cursor.getColumnIndex("Approved")));
                rejected.setText(cursor.getString(cursor.getColumnIndex("Rejected")));
                completed.setText(cursor.getString(cursor.getColumnIndex("Completed")));
                tableLayout.addView(tableRow);
                cursor.moveToNext();
            }
        }
        db.close();
//        JSONObject postData = new JSONObject();
//        try {
//            db = new DatabaseHelper(getContext());
//            cursor = db.getUserDetails();
//            if (cursor.moveToFirst()) {
//                while (!cursor.isAfterLast()) {
//                    Login_ID = cursor.getString(cursor.getColumnIndex("Login_ID"));
//                    cursor.moveToNext();
//                }
//                try {
//                    postData.put("loginid", Login_ID);
//                    postRequest(postData.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        boolean IntStatus = connectionCheck.getConnectivityStatusString(getContext());
//        if(IntStatus){
////            final String User_ID = getArguments().getString("User_ID");
////            final String networkName = getArguments().getString("Login_ID");
//        }else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("Alert!");
//            builder.setMessage("No Internet Connection!");
//            builder.setCancelable(false);
//            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                }
//            });
//            builder.show();
////            Toast.makeText(getContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
//        }
        return v;
    }

//    void postRequest(String postBody) throws IOException {
//        ConnectivityManager cm =
//                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null &&
//                activeNetwork.isConnectedOrConnecting();
//        if (isConnected) {
//            String postUrl = getString(R.string.api) + "/api/list/list/networkcount";
//            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//            OkHttpClient client = new OkHttpClient();
//            RequestBody body = RequestBody.create(JSON, postBody);
//            okhttp3.Request request = new okhttp3.Request.Builder()
//                    .url(postUrl)
//                    .addHeader("content-type", "application/json")
//                    .post(body)
//                    .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    call.cancel();
//                    getActivity().runOnUiThread(new Runnable() {
//                        public void run() {
//                            try {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                                builder.setCancelable(false);
//                                builder.setTitle("Alert!");
//                                builder.setMessage("Error!");
//                                builder.setCancelable(true);
//                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                });
//                                builder.show();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//
//                @Override
//                public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                    String jsonData = response.body().string();
////                    Toast.makeText(getActivity(), jsonData, Toast.LENGTH_LONG).show();
//                }
//            });
//        } else {
//            try {
//                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
//                builder.setCancelable(false);
//                builder.setTitle("Alert!");
//                builder.setMessage("No internet connection!!!");
//                builder.setCancelable(true);
//                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//                builder.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
