package com.example.indiacastdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.indiacastdemo.Model.AlertDialogModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class TaskFragment extends Fragment implements View.OnClickListener {
    EditText task_comment;
    String comment, task;
    Button task_submit;
    CheckBox checkBox, checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10, checkBox11, checkBox12, checkBox13, checkBox14, checkBox15, checkBox16, checkBox17, checkBox18;
    final ArrayList<String> selchkboxlist = new ArrayList<String>();
    String User_ID;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        try {
            User_ID = getArguments().getString("User_ID");
            checkBox = v.findViewById(R.id.checkBox);
            checkBox1 = v.findViewById(R.id.checkBox1);
            checkBox2 = v.findViewById(R.id.checkBox2);
            checkBox3 = v.findViewById(R.id.checkBox3);
            checkBox4 = v.findViewById(R.id.checkBox4);
            checkBox5 = v.findViewById(R.id.checkBox5);
            checkBox6 = v.findViewById(R.id.checkBox6);
            checkBox7 = v.findViewById(R.id.checkBox7);
            checkBox8 = v.findViewById(R.id.checkBox8);
            checkBox9 = v.findViewById(R.id.checkBox9);
            checkBox10 = v.findViewById(R.id.checkBox10);
            checkBox11 = v.findViewById(R.id.checkBox11);
            checkBox12 = v.findViewById(R.id.checkBox12);
            checkBox13 = v.findViewById(R.id.checkBox13);
            checkBox14 = v.findViewById(R.id.checkBox14);
            checkBox15 = v.findViewById(R.id.checkBox15);
            checkBox16 = v.findViewById(R.id.checkBox16);
            checkBox17 = v.findViewById(R.id.checkBox17);
            checkBox18 = v.findViewById(R.id.checkBox18);
            task_submit = v.findViewById(R.id.task_submit);
            task_comment = v.findViewById(R.id.task_comment);
            checkBox.setOnClickListener(this);
            checkBox1.setOnClickListener(this);
            checkBox2.setOnClickListener(this);
            checkBox3.setOnClickListener(this);
            checkBox4.setOnClickListener(this);
            checkBox5.setOnClickListener(this);
            checkBox6.setOnClickListener(this);
            checkBox7.setOnClickListener(this);
            checkBox8.setOnClickListener(this);
            checkBox9.setOnClickListener(this);
            checkBox10.setOnClickListener(this);
            checkBox11.setOnClickListener(this);
            checkBox12.setOnClickListener(this);
            checkBox13.setOnClickListener(this);
            checkBox14.setOnClickListener(this);
            checkBox15.setOnClickListener(this);
            checkBox16.setOnClickListener(this);
            checkBox17.setOnClickListener(this);
            checkBox18.setOnClickListener(this);
            task_submit.setOnClickListener(this);
            task_comment.setOnClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }
//        boolean IntStatus = connectionCheck.getConnectivityStatusString(getContext());
//        if (IntStatus) {
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("Alert!");
//            builder.setMessage("No Internet Connection!");
//            builder.setCancelable(true);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        if (((CheckBox) view).isChecked()) {
            String checkbox = (String) ((CheckBox) view).getText();
            selchkboxlist.add(checkbox);
        } else {
            String checkbox = (String) ((CheckBox) view).getText();
            selchkboxlist.remove(checkbox);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkBox:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox1:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox2:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox3:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox4:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox5:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox6:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox7:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox8:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox9:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox10:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox11:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox12:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox13:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox14:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox15:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox16:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox17:
                onCheckboxClicked(v);
                break;
            case R.id.checkBox18:
                onCheckboxClicked(v);
                break;
            case R.id.task_comment:
                comment = task_comment.getText().toString();
                break;
            case R.id.task_submit:
                comment = task_comment.getText().toString();
                task = selchkboxlist + "," + comment;
                selchkboxlist.add("&" + comment);
                JSONObject postData = new JSONObject();
                try {
                    postData.put("Tasks", selchkboxlist);
                    postData.put("User_ID", User_ID);
                    postData.put("Created_Date", "");
                    postRequest(postData.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    void postRequest(String postBody) throws IOException {

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
//        String postUrl = "http://182.71.62.87:80/api/setlist/placement";
            String postUrl = getString(R.string.api) + "/api/setlist/tasks";
//        String postUrl = "http://http://172.50.2.114:80/api/setlist/placement";

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
                                        AlertDialogModel.generateAlertDialog(getActivity(),"Alert!","Server connection lost!");
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
                        JSONArray jsonArray = new JSONArray(jsonData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject taskObject = jsonArray.getJSONObject(i);
                            String taskresponse = taskObject.getString("Name");
                            if (taskresponse.equals("Data Updated Successfully")) {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        try {
                                            AlertDialogModel.generateAlertDialog(getActivity(),"Alert!","Task submitted Successfully");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                AlertDialogModel.generateAlertDialog(getActivity(),"Alert!","No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
