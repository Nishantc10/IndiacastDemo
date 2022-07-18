package com.example.indiacastdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.AlertDialogModel;
import com.example.indiacastdemo.Model.ConnectionCheck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    Context context = LoginScreen.this;
    EditText et_username, et_password;
    Button btn_login;
    public static String username, password, getLogID, getToken, getUserID;
    //api strings
    String Name, Result, User_ID, EMP_ID, Firstname, Lastname, DOB, EmailID, PhoneNo, Address, USER_TYPE,
            Assigned_Town, Created_Date, Updated_Date, Token, Login_ID, MAC_Address;
    DatabaseHelper db;
    ScrollView sv;
    ConnectionCheck connectionCheck;
    ProgressBar login_progressBar;
    private ProgressDialog progress;
    String macAddress = "12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);
        db = new DatabaseHelper(this);
        sv = findViewById(R.id.sv);
        btn_login = findViewById(R.id.btn_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login.setOnClickListener(this);
        login_progressBar = findViewById(R.id.login_progressBar);
        progress = new ProgressDialog(context);
        progress.setCancelable(false);
        progress.setTitle("Login");
        progress.setMessage("Wait for authentication...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        boolean IntStatus = connectionCheck.getConnectivityStatusString(this);

        try {
            macAddress = android.provider.Settings.Secure.getString(this.getApplicationContext().getContentResolver(), "android_id");
        } catch (Exception e) {
            macAddress = "1234";
        }

        if (IntStatus) {
        } else {
            LoginScreen.this.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        AlertDialogModel.generateAlertDialog(context, "Alert!", "No Internet Connection!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        try {
            Cursor res = db.getAllData();
            if (res.getCount() == 0) {
                return;
            } else {
                res.moveToFirst();
                while (!res.isAfterLast()) {
                    getLogID = res.getString(0);
                    getToken = res.getString(1);
                    getUserID = res.getString(2);
                    res.moveToNext();
                }
                db.close();
                Intent intent = new Intent(context, Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Login_ID", getLogID);
                bundle.putString("Token", getToken);
                bundle.putString("User_ID", getUserID);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(final View v) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                switch (v.getId()) {
                    case R.id.btn_login:
                        boolean IntStatus = connectionCheck.getConnectivityStatusString(getApplicationContext());
                        username = et_username.getText().toString();
                        password = et_password.getText().toString();
                        if (IntStatus) {
                            JSONObject postData = new JSONObject();
                            try {
                                postData.put("username", username);
                                postData.put("password", password);
                                postData.put("authtype", "db");
                                postData.put("macaddress", macAddress);
                                postData.put("usertype", "UTY0001");
                                postRequest(postData.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (username.equals("") || (password.equals(""))) {
                            LoginScreen.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        AlertDialogModel.generateAlertDialog(context, "Alert!", "Username or Password is incorrect!");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            LoginScreen.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        AlertDialogModel.generateAlertDialog(context, "Alert!", "No Internet Connection!");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                }
            }
        };
        thread.start();
    }

    void postRequest(String postBody) throws IOException {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            String postUrl = getString(R.string.api) + "/api/list/user/loginauthentication/";

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
                    runOnUiThread(new Runnable() {
                        public void run() {
                            login_progressBar.setVisibility(View.GONE);
                            progress.dismiss();
                            LoginScreen.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        AlertDialogModel.generateAlertDialog(context, "Alert!", "Error while connecting server please try again!");
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
                    JSONArray jsonArray1 = null;
                    JSONObject jsonObject = null;
                    try {
                        jsonData = response.body().string();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray jsonArray = new JSONArray(jsonData);
                        jsonArray1 = jsonArray.getJSONArray(0);
                        jsonObject = jsonArray1.getJSONObject(0);
                        try {
                            Name = jsonObject.getString("Name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (Name.equals("Login Successful")) {
                            try {
                                Result = jsonObject.getString("Result");
                                User_ID = jsonObject.getString("User_ID");
                                EMP_ID = jsonObject.getString("EMP_ID");
                                Firstname = jsonObject.getString("Firstname");
                                Lastname = jsonObject.getString("Lastname");
                                DOB = jsonObject.getString("DOB");
                                EmailID = jsonObject.getString("EmailID");
                                PhoneNo = jsonObject.getString("PhoneNo");
                                Address = jsonObject.getString("Address");
                                USER_TYPE = jsonObject.getString("USER_TYPE");
                                Assigned_Town = jsonObject.getString("Assigned_Town");
                                Created_Date = jsonObject.getString("Created_Date");
                                Updated_Date = jsonObject.getString("Updated_Date");
                                Token = jsonObject.getString("Token");
                                Login_ID = jsonObject.getString("Login_ID");
                                MAC_Address = jsonObject.getString("MAC_Address");
                                insert_tbl_user_details();
                                Intent intent = new Intent(context, Main2Activity.class);
                                startActivity(intent);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            LoginScreen.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        AlertDialogModel.generateAlertDialog(context, "Alert!", "Username or Password is incorrect!");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
//                                builder.setCancelable(true);
////                                builder.setTitle("Alert!");
////                                final View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog, null);
////                                builder.setView(customLayout);
//                                builder.setMessage("Username or Password is incorrect!");
//                                builder.setCancelable(true);
//                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        // send data from the AlertDialog to the Activity
//                                    }
//                                });
//                                builder.show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    login_progressBar.setVisibility(View.GONE);
                    progress.dismiss();
                }
            });
        } else {
            try {
                login_progressBar.setVisibility(View.GONE);

                progress.dismiss();
                AlertDialogModel.generateAlertDialog(context, "Alert!", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void insert_tbl_user_details() {
        db.insert_tbl_user_details(MAC_Address, Login_ID, Token, Updated_Date, Created_Date, Assigned_Town, "1",
                USER_TYPE, Address, PhoneNo, EmailID, DOB, Lastname, Firstname, EMP_ID, User_ID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

