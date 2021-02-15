package com.example.indiacastdemo;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Model.AlertDialogModel;
import com.example.indiacastdemo.Model.ConnectionCheck;

import java.util.ArrayList;


public class LandingPageFragment extends Fragment {
    ConnectionCheck connectionCheck;
    Switch landswitch;
    EditText edt_lcn;
    TextView edt_channelName, edt_genreName, edt_position, txt_channelName, txt_genreName, txt_position, txt_lcn, txt_msg;
    Button submit;
    SpinnerDialog chanels;
    ArrayList<IdentifiableObject> channelist = new ArrayList<>();
    DatabaseHelper db;
    String networkId, networkName;
    String Channel_Name, Network_ID, LCN_No, Genre, Position;


    public LandingPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_landing_page, container, false);
        networkId = getArguments().getString("networkId");
        networkName = getArguments().getString("networkName");
        db = new DatabaseHelper(getContext());
        landswitch = v.findViewById(R.id.landswitch);
        edt_genreName = v.findViewById(R.id.edt_genreName);
        txt_channelName = v.findViewById(R.id.txt_channelName);
        txt_genreName = v.findViewById(R.id.txt_genreName);
        txt_msg = v.findViewById(R.id.txt_msg);
        txt_lcn = v.findViewById(R.id.txt_lcn);
        txt_position = v.findViewById(R.id.txt_position);
        edt_lcn = v.findViewById(R.id.edt_lcn);
        edt_position = v.findViewById(R.id.edt_position);
        edt_channelName = v.findViewById(R.id.edt_channelName);
        submit = v.findViewById(R.id.submit);

        Cursor res = db.getLandingPage(networkId);
        if (res.getCount() == 1) {
            if (res.moveToFirst()) {
                landswitch.setChecked(true);
                Channel_Name = res.getString(res.getColumnIndex("Channel_Name"));
                Network_ID = res.getString(res.getColumnIndex("Network_ID"));
                LCN_No = res.getString(res.getColumnIndex("LCN_No"));
                Genre = res.getString(res.getColumnIndex("Genre"));
                Position = res.getString(res.getColumnIndex("Position"));
                res.moveToNext();
            }
            edt_channelName.setText(Channel_Name);
            edt_lcn.setText(LCN_No);
            edt_genreName.setText(Genre);
            edt_position.setText(Position);
        } else if (res.getCount() == 0) {
            landswitch.setChecked(false);
            edt_genreName.setVisibility(View.GONE);
            edt_channelName.setVisibility(View.GONE);
            edt_lcn.setVisibility(View.GONE);
            edt_position.setVisibility(View.GONE);
            txt_channelName.setVisibility(View.GONE);
            txt_genreName.setVisibility(View.GONE);
            txt_lcn.setVisibility(View.GONE);
            txt_position.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            txt_msg.setVisibility(View.VISIBLE);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edt_lcn.getText().toString()).equals("")) {
                    try {
                        AlertDialogModel.generateAlertDialog(getContext(), "Alert", "Please select LCN!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if ((edt_channelName.getText().toString()).equals(" ") || (edt_genreName.getText().toString()).equals(" ")) {
                    try {
                        String CHANNELNAME = edt_channelName.getText().toString();
                        AlertDialogModel.generateAlertDialog(getContext(), "Alert", "Please check LCN!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        db.setLandingPage(networkId, edt_lcn.getText().toString());
                        AlertDialogModel.generateAlertDialog(getContext(), "Alert", "Landing Page Updated Successfully");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        edt_lcn.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Cursor res = db.getDataByLandingPage(networkId, edt_lcn.getText().toString());
                if (res.getCount() == 1) {
                    if (res.moveToFirst()) {
                        landswitch.setChecked(true);
                        Channel_Name = res.getString(res.getColumnIndex("Channel_Name"));
                        Network_ID = res.getString(res.getColumnIndex("Network_ID"));
                        LCN_No = res.getString(res.getColumnIndex("LCN_No"));
                        Genre = res.getString(res.getColumnIndex("Genre"));
                        Position = res.getString(res.getColumnIndex("Position"));
                        res.moveToNext();
                    }
                    edt_channelName.setText(Channel_Name);
//                    edt_lcn.setText(LCN_No);
                    edt_genreName.setText(Genre);
                    edt_position.setText(Position);
                } else {
                    try {
                        AlertDialogModel.generateAlertDialog(getContext(), "Alert", "No LCN Found!");
                        edt_channelName.setText(" ");
//                    edt_lcn.setText(LCN_No);
                        edt_genreName.setText(" ");
                        edt_position.setText(" ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });

        landswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // do something when check is selected
                    edt_genreName.setVisibility(View.VISIBLE);
                    edt_channelName.setVisibility(View.VISIBLE);
                    edt_lcn.setVisibility(View.VISIBLE);
                    edt_position.setVisibility(View.VISIBLE);
                    txt_channelName.setVisibility(View.VISIBLE);
                    txt_genreName.setVisibility(View.VISIBLE);
                    txt_lcn.setVisibility(View.VISIBLE);
                    txt_position.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.VISIBLE);
                    txt_msg.setVisibility(View.GONE);
                } else {
                    //do something when unchecked
                    landswitch.setChecked(false);
                    edt_genreName.setVisibility(View.GONE);
                    edt_channelName.setVisibility(View.GONE);
                    edt_lcn.setVisibility(View.GONE);
                    edt_position.setVisibility(View.GONE);
                    txt_channelName.setVisibility(View.GONE);
                    txt_genreName.setVisibility(View.GONE);
                    txt_lcn.setVisibility(View.GONE);
                    txt_position.setVisibility(View.GONE);
                    submit.setVisibility(View.GONE);
                    txt_msg.setVisibility(View.VISIBLE);
                    db.setLandingPage(networkId, "");
                }
            }
        });
//        edt_channelName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cursor = db.getAllChannels();
//                channelist.clear();
//                if (cursor.moveToFirst()) {
//                    while (!cursor.isAfterLast()) {
//                        String name = cursor.getString(cursor.getColumnIndex("Channel_Name"));
//                        channelist.add(new IdentifiableObjectImpl(name, null, 0, R.drawable.ic_checkbox_marked_circle_outline_white_18dp));
//                        cursor.moveToNext();
//                    }
//                }
//                db.close();
//                chanels = new SpinnerDialog((Activity) getContext(), channelist, "Select or Search City", R.drawable.ic_search_black_24dp, R.drawable.ic_search_black_24dp);
//                chanels.showSpinerDialog();
//                chanels.bindOnSpinerListener(new OnSpinerItemClick() {
//                    @Override
//                    public void onClick(IdentifiableObject item, int position) {
//                        String getchannel = item.getTitle();
//                        edt_channelName.setText(getchannel);
//                    }
//                });
//            }
//        });
//        edt_genreName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cursor = db.getAllGenre();
//                channelist.clear();
//                if (cursor.moveToFirst()) {
//                    while (!cursor.isAfterLast()) {
//                        String name = cursor.getString(cursor.getColumnIndex("Genre"));
//                        channelist.add(new IdentifiableObjectImpl(name, null, 0, R.drawable.ic_checkbox_marked_circle_outline_white_18dp));
//                        cursor.moveToNext();
//                    }
//                }
//                db.close();
//                chanels = new SpinnerDialog((Activity) getContext(), channelist, "Select or Search City", R.drawable.ic_search_black_24dp, R.drawable.ic_search_black_24dp);
//                chanels.showSpinerDialog();
//                chanels.bindOnSpinerListener(new OnSpinerItemClick() {
//                    @Override
//                    public void onClick(IdentifiableObject item, int position) {
//                        String getchannel = item.getTitle();
//                        edt_genreName.setText(getchannel);
//                    }
//                });
//
//
//            }
//        });
//        boolean IntStatus = connectionCheck.getConnectivityStatusString(getContext());
//        if (IntStatus) {
//        } else {
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

}
