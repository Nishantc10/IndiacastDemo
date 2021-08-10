package com.example.indiacastdemo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.AlertDialogModel;
import com.example.indiacastdemo.Model.ConnectionCheck;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.iammert.library.readablebottombar.ReadableBottomBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Main2Activity extends AppCompatActivity {
    TextView customtitle;
    Toolbar toolbar;
    ReadableBottomBar bottomnav;
    DatabaseHelper db;
    ConnectionCheck connectionCheck;
    private ProgressDialog progress;
    Bundle bundle;
    String Login_ID, User_ID, Token, ABCD = null;
    //Firebase notification
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAtWF-790:APA91bFURgz3Xb4WoYGSWlzbolradwXQrSDbe2M3mI3PacC0HH-c92Ea2sUiQCgGVSE2MVEABLDQCJOSFWdEmrR7DW3XuFDaBfqAn96xIMV0-WjJI_SI7SS58cloLwcrow9eOvXK788s";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    String networkCount;
    Fragment fragment;


    //------------------------
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHelper(this);
        try {
            try {
                db = new DatabaseHelper(this);
                Cursor cursor = db.getUserDetails();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Login_ID = cursor.getString(cursor.getColumnIndex("Login_ID"));
                        Token = cursor.getString(cursor.getColumnIndex("Token"));
                        User_ID = cursor.getString(cursor.getColumnIndex("User_ID"));
                        cursor.moveToNext();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            FirebaseMessaging.getInstance().subscribeToTopic(Login_ID.toLowerCase().trim())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "abcd";
                            if (!task.isSuccessful()) {
                                msg = "xyz";
                            }
                            Log.d(TAG, msg);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cursor cursor = db.getHomePageNetworkCount();
        if (cursor.moveToFirst()) {
            networkCount = cursor.getString(0);
        }
        db.close();
        getIndiaCastChannelsStatus();
        getIndiaCastChannels();
        bundle = new Bundle();
        bundle.putString("User_ID", User_ID);
        bundle.putString("Login_ID", Login_ID);
        bundle.putString("Token", Token);

        JSONObject postData = new JSONObject();
        try {
            Cursor res = db.getAllNetworks();
            if (res.getCount() == 0) {
                try {
                    postData.put("loginid", Login_ID);
                    postData.put("token", Token);
                    postRequest(postData.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        customtitle = toolbar.findViewById(R.id.customtitletoolbar);
        customtitle.setText(R.string.home);
        bottomnav = findViewById(R.id.navigation);
        fragment = new Fragment();
        // region bottom navigation bar
        bottomnav.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case 0:
                        fragment = new HomeFragment();
                        fragment.setArguments(bundle);
                        customtitle.setText(R.string.home);

                        break;
                    case 1:
                        try {
                            bundle.putString("ABCD", "asdfghjk");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        fragment = new HEFragment();
                        fragment.setArguments(bundle);
                        customtitle.setText(R.string.mapping);

                        break;
                    case 2:
                        fragment = new TaskFragment();
                        fragment.setArguments(bundle);
                        customtitle.setText(R.string.task);

                        break;
                    case 3:
                        fragment = new ProfileFragment();
                        fragment.setArguments(bundle);

                        //firebase notification
//                        try {
//                            Cursor cursor = db.getUserDetails();
//                            if (cursor.moveToFirst()) {
//                                Login_ID = cursor.getString(cursor.getColumnIndex("Login_ID"));
//                                Token = cursor.getString(cursor.getColumnIndex("Token"));
//                                User_ID = cursor.getString(cursor.getColumnIndex("User_ID"));
////
//                                ABCD = bundle.getString("ABCD");
//                            }
//                            TOPIC = "/topics/" + Login_ID.toLowerCase(); //topic has to match what the receiver subscribed to
//                            NOTIFICATION_TITLE = "IndiaCast";
//                            NOTIFICATION_MESSAGE = "Firebase Notification";
//
//                            JSONObject notification = new JSONObject();
//                            JSONObject notifcationBody = new JSONObject();
//                            notifcationBody.put("title", NOTIFICATION_TITLE);
//                            notifcationBody.put("message", NOTIFICATION_MESSAGE);
//
//                            notification.put("to", TOPIC);
//                            notification.put("data", notifcationBody);
//                            sendNotification(notification);
//                        } catch (JSONException e) {
//                            Log.e(TAG, "onCreate: " + e.getMessage());
//                        }
//                        //------------------------------------------
                        customtitle.setText(R.string.profile);
                        break;
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
            }
        });
        //endregion
        //default fragment is HomeFragment
        fragment = new HomeFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        try {
            bundle = new Bundle();
            bundle = getIntent().getExtras();
            ABCD = bundle.getString("ABCD");
            String createdDate = bundle.getString("createdDate");
            String networkId = bundle.getString("networkId");
            String networkName = bundle.getString("networkName");
            String status = bundle.getString("status");

            if (ABCD.equals("abcd")) {
                bundle.putString("networkId", networkId);
                bundle.putString("createdDate", createdDate);
                bundle.putString("networkName", networkName);
                bundle.putString("status", status);
                HEFragment heFragment = new HEFragment();
                heFragment.setArguments(bundle);
                Fragment fragment = heFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // region Firebase notification
    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    //endregion
    //-------------------------------------
    @Override
    public void onBackPressed() {



        if (getFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    //region get indiacast channel status master
    private void getIndiaCastChannelsStatus() {
        JSONObject postData = new JSONObject();
        try {
            postData.put("loginid", Login_ID);
            indiacastChannelsStatusPostRequest(postData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void indiacastChannelsStatusPostRequest(String postBody) throws IOException {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            String postUrl = getString(R.string.api) + "/api/list/list/icchannelstatus";
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
                    Main2Activity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Main2Activity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        progress.dismiss();
                                        AlertDialogModel.generateAlertDialog(getApplicationContext(), "Alert!", "Server connection lost!");
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
                    try {
                        if (db.setIndiaCastChannelStatusResponse(response.body().string())) {
//                            progress.dismiss();
                        } else {
                            progress.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                AlertDialogModel.generateAlertDialog(getApplicationContext(), "Alert!", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //endregion
    //region get indiacast channel master
    private void getIndiaCastChannels() {
        JSONObject postData = new JSONObject();
        try {
            postData.put("loginid", Login_ID);
            indiacastChannelsPostRequest(postData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void indiacastChannelsPostRequest(String postBody) throws IOException {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            String postUrl = getString(R.string.api) + "/api/list/list/icchannellist";
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
                    Main2Activity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Main2Activity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        progress.dismiss();
                                        AlertDialogModel.generateAlertDialog(getApplicationContext(), "Alert!", "Server connection lost!");
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
                    try {
                        if (db.setIndiaCastChannelsResponse(response.body().string())) {
//                            progress.dismiss();
                        } else {
//                            progress.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                AlertDialogModel.generateAlertDialog(getApplicationContext(), "Alert!", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //endregion
    // region Token Authentication
    void postRequest(String postBody) throws IOException {
        progress = new ProgressDialog(Main2Activity.this);
        progress.setCancelable(false);
        progress.setTitle("Please wait....");
        progress.setMessage("Fetching details");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            String postUrl = getString(R.string.api) + "/api/list/user/tokenauthentication/";
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
                    call.cancel();
                    progress.dismiss();
                    Main2Activity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                AlertDialogModel.generateAlertDialog(Main2Activity.this, "Alert!", "Error!");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    String jsonData = response.body().string();
                    try {
                        if (db.getResponse(jsonData)) {
                            refreshManu();
                            Cursor netcount = db.getNetworkcount();
                            JSONObject postData = new JSONObject();
                            try {
                                Cursor res = db.getAllNetworks();
                                if (res.getCount() < netcount.getCount()) {
                                    Log.d("res.getCount(): ", netcount.getCount() + "");
                                    Log.d("res.getCount(): ", res.getCount() + "");
                                    ArrayList<JSONObject> networkList = new ArrayList<>();
                                    netcount.moveToFirst();
                                    while (!netcount.isAfterLast()) {
                                        JSONObject Data = new JSONObject();
                                        Data.put("Network_ID", netcount.getString(netcount.getColumnIndex("Network_ID")));
                                        networkList.add(Data);
                                        netcount.moveToNext();
                                    }
                                    try {
                                        postData.put("loginid", Login_ID);
                                        postData.put("token", Token);
                                        postData.put("networkid_list", networkList);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String postUrl = getString(R.string.api) + "/api/list/list/network_mapped/";
                            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                            OkHttpClient client = new OkHttpClient();
                            RequestBody body = RequestBody.create(JSON, postData.toString());
                            okhttp3.Request request = new okhttp3.Request.Builder()
                                    .url(postUrl)
                                    .addHeader("content-type", "application/json")
                                    .post(body)
                                    .build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    call.cancel();
                                    Main2Activity.this.runOnUiThread(new Runnable() {
                                        public void run() {
                                            try {
                                                AlertDialogModel.generateAlertDialog(Main2Activity.this, "Alert!", "Error!");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                                    String jsonData = response.body().string();
                                    try {
                                        if (db.getMappedResponse(jsonData)) {
                                        } else {
//                                            progress.dismiss();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                progress.dismiss();
                AlertDialogModel.generateAlertDialog(Main2Activity.this, "Alert!", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //endregion
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //call super
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void refreshManu() {
        ConnectivityManager cm =
                (ConnectivityManager) Main2Activity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            JSONObject postData = new JSONObject();
            try {
                postData.put("loginid", Login_ID);
                postData.put("token", Token);
                networkCountPostRequest(postData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    void networkCountPostRequest(String postBody) throws IOException {
        ConnectivityManager cm =
                (ConnectivityManager) Main2Activity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            String postUrl = getString(R.string.api) + "/api/list/history/placement";
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
                    progress.dismiss();
                    Main2Activity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Main2Activity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        AlertDialogModel.generateAlertDialog(Main2Activity.this, "Alert!", "Server connection lost!");
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
                    try {
                        if (db.getPlacement(response.body().string())) {
                            progress.dismiss();
                            Fragment fragment = null;
                            HomeFragment homeFragment = new HomeFragment();
                            fragment = homeFragment;
                            fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                        } else {
                            progress.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                AlertDialogModel.generateAlertDialog(Main2Activity.this, "Alert!", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
