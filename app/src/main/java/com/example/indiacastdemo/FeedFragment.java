//package com.example.indiacastdemo;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.indiacastdemo.Database.DatabaseHelper;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class FeedFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private LinearLayoutManager linearLayoutManager;
//    private ArrayList<Movie> movieList;
//    private DividerItemDecoration dividerItemDecoration;
//    private RecyclerView.Adapter adapter;
//    //    String URL = "http://122.179.136.81:4000/network";
//    String URL = "http://10.102.32.153:4000/network";
//    DatabaseHelper db;
//    String moviename;
//    Cursor c;
//
//    public FeedFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_feed, container, false);
//        recyclerView = (RecyclerView) v.findViewById(R.id.feed_fragment_recy);
//        movieList = new ArrayList<>();
//        adapter = new MovieAdapter(getContext(), movieList);
//        linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(dividerItemDecoration);
//        recyclerView.setAdapter(adapter);
//        ConnectivityManager cm =
//                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null &&
//                activeNetwork.isConnectedOrConnecting();
////        getData();
//        if (isConnected) {
//            getDataFromDatabase();
//        } else {
//            Toast.makeText(getContext(), "No Internet connection!", Toast.LENGTH_SHORT).show();
//            getStaticData();
//        }
//        return v;
//    }
//
//    public void getDataFromDatabase() {
//        db = new DatabaseHelper(getContext());
//        c = db.getAllHeNetworks("TABLE_FEED_NETWORK");
//        if (c.getCount() > 0) {
//            if (c.moveToFirst()) {
//                do {
//                    moviename = c.getString(1);
//                    Movie movie = new Movie(moviename);
//                    movieList.add(movie);
//                } while (c.moveToNext());
//            }
//        }
//
//    }
////database inserti  on remaining.......
//
//    public void getData() {
//        JsonObjectRequest objectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                URL,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        JSONArray popup_value = null;
//                        try {
//                            popup_value = response.getJSONArray("Result");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        for (int i = 0; i < popup_value.length(); i++) {
////                                arralist1 = new JSONObject(popup_value.getString(i));
//                            try {
//                                JSONObject arralist1 = popup_value.getJSONObject(i);
////                                    for (int j = 0; j < arralist1.length(); j++) {
//                                Movie movie = new Movie();
//                                String valueTosee = arralist1.getString("Network");
//                                movie.setMoviename(arralist1.getString("Network"));
////                                db.insertHeNetworkData(valueTosee,"COLUMN_FEED_NETWORK_NAME","TABLE_FEED_NETWORK");
////                                    }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("onResponse: ", error.toString());
//
//                    }
//                }
//        );
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(objectRequest);
//    }
//
//    public void getStaticData() {
//        movieList.add(new Movie("feed network 1"));
//        movieList.add(new Movie("feed network 2"));
//        movieList.add(new Movie("feed network 3"));
//        movieList.add(new Movie("feed network 4"));
//        movieList.add(new Movie("feed network 5"));
//        movieList.add(new Movie("feed network 6"));
//        movieList.add(new Movie("feed network 7"));
//        movieList.add(new Movie("feed network 8"));
//        movieList.add(new Movie("feed network 9"));
//        movieList.add(new Movie("feed network 10"));
//        movieList.add(new Movie("feed network 11"));
//        movieList.add(new Movie("feed network 12"));
//        movieList.add(new Movie("feed network 13"));
//        movieList.add(new Movie("feed network 14"));
//        movieList.add(new Movie("feed network 15"));
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//}