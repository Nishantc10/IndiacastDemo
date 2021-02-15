package com.example.indiacastdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.AlertDialogModel;
import com.example.indiacastdemo.Model.ConnectionCheck;
import com.example.indiacastdemo.Model.History_Network;
import com.trendyol.bubblescrollbarlib.BubbleScrollBar;
import com.trendyol.bubblescrollbarlib.BubbleTextProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<History_Network> lst_History_Network = null;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private HistoryNetworkAdapter adapter;
    Bundle bundle;
    DatabaseHelper db;
    Cursor cursor;
    ConnectionCheck connectionCheck;
    BubbleScrollBar scrollBar;
    EditText edt_search;
    String Token, Login_ID, User_ID;
    private ProgressDialog progress;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.filter_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.all:
//                lst_History_Network.clear();
//                getStaticData();
//                adapter.notifyDataSetChanged();
//                return true;
//            case R.id.submited:
//                cursor = db.getAllNetworksByStatus("STS0003");
//                lst_History_Network.clear();
//                if (cursor.moveToFirst()) {
//                    while (!cursor.isAfterLast()) {
//                        String network_id = cursor.getString(cursor.getColumnIndex("Network_ID"));
//                        String network_name = cursor.getString(cursor.getColumnIndex("Network_Name"));
//                        String status = cursor.getString(cursor.getColumnIndex("Status"));
//                        String createdDate = cursor.getString(cursor.getColumnIndex("Created_Date"));
//                        String number_of_channels = cursor.getString(cursor.getColumnIndex("Number_of_channels"));
//                        String a = network_name.substring(0,1);
//                        TextDrawable drawable = TextDrawable.builder()
//                                .buildRoundRect(a, Color.BLACK,60);
//                        lst_History_Network.add(new History_Network(network_id, network_name, createdDate, drawable, status));
//                    }
//                }
//                db.close();
//                adapter.notifyDataSetChanged();
//                return true;
//            case R.id.rejected:
//                cursor = db.getAllNetworksByStatus("STS0005");
//                lst_History_Network.clear();
//                if (cursor.moveToFirst()) {
//                    while (!cursor.isAfterLast()) {
//                        String network_id = cursor.getString(cursor.getColumnIndex("Network_ID"));
//                        String network_name = cursor.getString(cursor.getColumnIndex("Network_Name"));
//                        String status = cursor.getString(cursor.getColumnIndex("Status"));
//                        String createdDate = cursor.getString(cursor.getColumnIndex("Created_Date"));
//
//                        String number_of_channels = cursor.getString(cursor.getColumnIndex("Number_of_channels"));
//                        String a = network_name.substring(0,1);
//                        TextDrawable drawable = TextDrawable.builder()
//                                .buildRoundRect(a, Color.BLACK,60);
//                        lst_History_Network.add(new History_Network(network_id, network_name, createdDate, drawable, status));
//                    }
//                }
//                db.close();
//                adapter.notifyDataSetChanged();
//                return true;
//            case R.id.updated:
//                cursor = db.getAllNetworksByStatus("STS0002");
//                lst_History_Network.clear();
//                if (cursor.moveToFirst()) {
//                    while (!cursor.isAfterLast()) {
//                        String network_id = cursor.getString(cursor.getColumnIndex("Network_ID"));
//                        String network_name = cursor.getString(cursor.getColumnIndex("Network_Name"));
//                        String status = cursor.getString(cursor.getColumnIndex("Status"));
//                        String createdDate = cursor.getString(cursor.getColumnIndex("Created_Date"));
//
//                        String number_of_channels = cursor.getString(cursor.getColumnIndex("Number_of_channels"));
//                        String a = network_name.substring(0,1);
//                        TextDrawable drawable = TextDrawable.builder()
//                                .buildRoundRect(a, Color.BLACK,60);
//                        lst_History_Network.add(new History_Network(network_id, network_name, createdDate, drawable, status));
//                    }
//                }
//                db.close();
//                adapter.notifyDataSetChanged();
//                return true;
//            case R.id.notUpdated:
//                cursor = db.getAllNetworksByStatus("STS0001");
//                lst_History_Network.clear();
//                if (cursor.moveToFirst()) {
//                    while (!cursor.isAfterLast()) {
//                        String network_id = cursor.getString(cursor.getColumnIndex("Network_ID"));
//                        String network_name = cursor.getString(cursor.getColumnIndex("Network_Name"));
//                        String status = cursor.getString(cursor.getColumnIndex("Status"));
//                        String createdDate = cursor.getString(cursor.getColumnIndex("Created_Date"));
//
//                        String number_of_channels = cursor.getString(cursor.getColumnIndex("Number_of_channels"));
//                        String a = network_name.substring(0,1);
//                        TextDrawable drawable = TextDrawable.builder()
//                                .buildRoundRect(a, Color.BLACK,60);
//                        lst_History_Network.add(new History_Network(network_id, network_name, createdDate, drawable, status));
//                    }
//                }
//                db.close();
//                adapter.notifyDataSetChanged();
//                return true;
//            case R.id.approved:
//                cursor = db.getAllNetworksByStatus("STS0004");
//                lst_History_Network.clear();
//                if (cursor.moveToFirst()) {
//                    while (!cursor.isAfterLast()) {
//                        String network_id = cursor.getString(cursor.getColumnIndex("Network_ID"));
//                        String network_name = cursor.getString(cursor.getColumnIndex("Network_Name"));
//                        String status = cursor.getString(cursor.getColumnIndex("Status"));
//                        String createdDate = cursor.getString(cursor.getColumnIndex("Created_Date"));
//
//                        String number_of_channels = cursor.getString(cursor.getColumnIndex("Number_of_channels"));
//                        String a = network_name.substring(0,1);
//                        TextDrawable drawable = TextDrawable.builder()
//                                .buildRoundRect(a, Color.BLACK,60);
//                        lst_History_Network.add(new History_Network(network_id, network_name, createdDate, drawable, status));
//                        cursor.moveToNext();
//                    }
//                }
//                db.close();
//                adapter.notifyDataSetChanged();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_history, container, false);
//        boolean IntStatus = connectionCheck.getConnectivityStatusString(getContext());
        db = new DatabaseHelper(getContext());
        try {
//            String ABCD = getArguments().getString("ABCD");
//            if (ABCD.equals("abcd")) {
//                //asasasas
//                String createdDate = getArguments().getString("createdDate");
//                String networkId = getArguments().getString("networkId");
//                String networkName = getArguments().getString("networkName");
//                bundle = new Bundle();
//                bundle.putString("networkId", networkId);
//                bundle.putString("networkName", networkName);
//                bundle.putString("createdDate", createdDate);
//                refreshManu();
//                HistoryMappingFragment historyMappingFragment = new HistoryMappingFragment();
//                historyMappingFragment.setArguments(bundle);
//                Fragment fragment = historyMappingFragment;
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
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

        ((Main2Activity) getActivity()).customtitle.setText(R.string.placementHistory);
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        db = new DatabaseHelper(getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.history_fragment_recy);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                dividerItemDecoration.setOrientation(0);
            }
        });
        lst_History_Network = new ArrayList<>();
        adapter = new HistoryNetworkAdapter(getContext(), lst_History_Network);
        if (isConnected) {
            getStaticData();
        } else {
            getStaticData();
        }
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(adapter);
        scrollBar = v.findViewById(R.id.bubbleScrollBar);
        scrollBar.attachToRecyclerView(recyclerView);
        scrollBar.setBubbleTextProvider(new BubbleTextProvider() {
            @Override
            public String provideBubbleText(int i) {
                return new StringBuilder(lst_History_Network.get(i).getNetwork_Name().substring(0, 1)).toString();
            }
        });
        edt_search = v.findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String text = s.toString();
                cursor = db.getNetworksFromPlacementBySearch(text);
                lst_History_Network.clear();
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        String network_id = cursor.getString(cursor.getColumnIndex("Network_ID"));
                        String network_name = cursor.getString(cursor.getColumnIndex("Network_Name"));
                        String status = cursor.getString(cursor.getColumnIndex("Status"));
                        String createdDate = cursor.getString(cursor.getColumnIndex("Created_Date"));
//                        String number_of_channels = cursor.getString(cursor.getColumnIndex("Number_of_channels"));
                        String a = network_name.substring(0, 1);
                        TextDrawable drawable = TextDrawable.builder()
                                .buildRoundRect(a, Color.BLACK, 60);
                        lst_History_Network.add(new History_Network(network_id, network_name, createdDate, drawable, status));
                        cursor.moveToNext();
                    }
                }
                db.close();
                adapter.notifyDataSetChanged();
            }
        });
        return v;
    }

    public void getStaticData() {
        lst_History_Network.clear();
        cursor = db.getAllNetworksFromPlacement();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String network_id = cursor.getString(cursor.getColumnIndex("Network_ID"));
                String network_name = cursor.getString(cursor.getColumnIndex("Network_Name"));
                String status = cursor.getString(cursor.getColumnIndex("Status"));
                String created_date = cursor.getString(cursor.getColumnIndex("Created_Date"));
//                String number_of_channels = cursor.getString(cursor.getColumnIndex("Number_of_channels"));
                String a = network_name.substring(0, 1);
                TextDrawable drawable = TextDrawable.builder()
                        .buildRoundRect(a, Color.BLACK, 60);
                lst_History_Network.add(new History_Network(network_id, network_name, created_date, drawable, status));
                cursor.moveToNext();
            }
        }
        db.close();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.historyrefresh_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_historyrefresh:
                refreshManu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void postRequest(String postBody) throws IOException {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        AlertDialogModel.generateAlertDialog(getActivity(), "Alert", "Server connection lost!");
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
//                Log.d("onResponse: ", response.body().string());
                    try {
                        if (db.getPlacement(response.body().string())) {
//                        Log.d( "Into If: ","Into If");
                            Fragment frg = null;
                            frg = getFragmentManager().findFragmentByTag("HistoryFragment");
                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(frg);
                            ft.attach(frg);
                            ft.commit();
                            progress.dismiss();
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        AlertDialogModel.generateAlertDialog(getActivity(), "Please Relogin!!!", "You have already logged in to another device.");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            progress.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        progress.dismiss();
                    }
                }
            });
        } else {
            try {
                progress.dismiss();
                AlertDialogModel.generateAlertDialog(getActivity(), "Alert", "No internet connection!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshManu() {
        progress = new ProgressDialog(getContext());
        progress.setCancelable(false);
        progress.setTitle("Please wait....");
        progress.setMessage("Fetching details");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            JSONObject postData = new JSONObject();
            try {
                postData.put("loginid", Login_ID);
                postData.put("token", Token);
                postRequest(postData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        } else {
            try {
                progress.dismiss();
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
                            progress.show();
                            JSONObject postData = new JSONObject();
                            try {
                                postData.put("loginid", Login_ID);
                                postData.put("token", Token);
                                postRequest(postData.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            try {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Alert!");
                                builder.setMessage("Internet connection not found...!");
                                builder.setCancelable(true);
                                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progress.dismiss();
                                    }
                                });
                                builder.show();//            setupRecyclerView();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                builder.show();//            setupRecyclerView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}