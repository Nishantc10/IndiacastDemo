package com.example.indiacastdemo;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.indiacastdemo.Database.DatabaseHelper;


public class HistoryMonitoringPointsFragment extends Fragment {
    String networkId, networkName,status;
    DatabaseHelper db;
    String Technicians, Ground_subscribers;
    TextView txt_Tec, txt_Grd;
    EditText edt_Tec, edt_Grd;
    Button submit;

    public HistoryMonitoringPointsFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_history_monitoring_points, container, false);
        networkId = getArguments().getString("networkId");
        networkName = getArguments().getString("networkName");
        status = getArguments().getString("status");
        txt_Tec = v.findViewById(R.id.txt_Tec);
        txt_Grd = v.findViewById(R.id.txt_Grd);
        edt_Tec = v.findViewById(R.id.edt_Tec);
        edt_Grd = v.findViewById(R.id.edt_Grd);
        submit = v.findViewById(R.id.submit);
        if(status.equals("Approved")){
            submit.setVisibility(View.GONE);
        }else if(status.equals("Submitted")){
            submit.setVisibility(View.GONE);
        }else if(status.equals("Completed")){
            submit.setVisibility(View.GONE);
        }else {
            submit.setVisibility(View.VISIBLE);
        }
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setMessage("Are you sure, you want to submit?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (db.setMonitoringPoint(networkId, edt_Tec.getText().toString(), edt_Grd.getText().toString())) {
                                    try {
                                        txt_Grd.setText(edt_Grd.getText().toString());
                                        txt_Tec.setText(edt_Tec.getText().toString());
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setTitle("Alert!");
                                        builder.setMessage("Monitoring Points updated Successfully");
                                        builder.setCancelable(false);
                                        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        builder.show();
                                    }catch (Exception e){
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
            }
        });
        return v;
    }
}
