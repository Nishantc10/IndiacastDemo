package com.example.indiacastdemo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.indiacastdemo.Database.DatabaseHelper;
import com.google.firebase.FirebaseApp;

import java.io.FileInputStream;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    TextView edt_empid, edt_empname, edt_email, edt_address;
    ImageView img_profile;
    DatabaseHelper db;
    TextDrawable drawable;
    String a, b;
    String Token, Login_ID, User_ID;
    private static final int SELECT_PICTURE = 100;
    private static int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImageUri;
    Bundle bundle;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        try {
            db = new DatabaseHelper(getContext());
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

        edt_empid = v.findViewById(R.id.edt_empid);
        edt_empname = v.findViewById(R.id.edt_empname);
        edt_address = v.findViewById(R.id.edt_address);
        edt_email = v.findViewById(R.id.edt_email);
        img_profile = v.findViewById(R.id.img_profile);
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
//                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                i.setType("image/*");
                // ******** code for crop image
//                i.putExtra("crop", "true");
//                i.putExtra("aspectX", 100);
//                i.putExtra("aspectY", 100);
//                i.putExtra("outputX", 256);
//                i.putExtra("outputY", 356);

//                try {
//                    i.putExtra("return-data", true);
//                    startActivityForResult(
//                            Intent.createChooser(i, "Select Picture"), 0);
//                }catch (ActivityNotFoundException ex){
//                    ex.printStackTrace();
//                }
            }
        });
        db = new DatabaseHelper(getContext());
        Cursor cursor = db.getUserDetails();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String EMP_ID = cursor.getString(cursor.getColumnIndex("EMP_ID"));
                String Firstname = cursor.getString(cursor.getColumnIndex("Firstname"));
                String Lastname = cursor.getString(cursor.getColumnIndex("Lastname"));
                String Address = cursor.getString(cursor.getColumnIndex("Address"));
                String EmailID = cursor.getString(cursor.getColumnIndex("EmailID"));
                a = Firstname.substring(0, 1);
                if (Firstname.equals("")) {
                    b = Lastname.substring(0, 1);
                    drawable = TextDrawable.builder()
                            .buildRoundRect(b, Color.RED, 60);
                } else if (Lastname.equals("")) {
                    a = Firstname.substring(0, 1);
                    drawable = TextDrawable.builder()
                            .buildRoundRect(a, Color.RED, 60);
                } else {
                    a = Firstname.substring(0, 1);
                    b = Lastname.substring(0, 1);
                    drawable = TextDrawable.builder()
                            .buildRoundRect(a + "" + b, Color.RED, 60);
                }
                img_profile.setImageDrawable(drawable);
                edt_empid.setText(EMP_ID);
                edt_empname.setText(Firstname + " " + Lastname);
                edt_address.setText(Address);
                edt_email.setText(EmailID);
                cursor.moveToNext();
            }
        }
        db.close();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setCancelable(false);
                    builder.setMessage("Are you sure, you want to Logout?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    db.deleteAllTablesOnLogout();
//                                    FirebaseMessaging.getInstance().unsubscribeFromTopic(Login_ID);
                                    Intent intent = new Intent(getActivity(), LoginScreen.class);
                                    startActivity(intent);
                                    getActivity().finish();
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    img_profile.setImageURI(selectedImageUri);
                    try {
                        FileInputStream fis = new FileInputStream(data.getDataString());
                        byte[] image = new byte[fis.available()];
                        fis.read(image);
                        ContentValues values = new ContentValues();
                        values.put("image", image);
                        db.insertImage(image);
                        fis.close();
                        Toast.makeText(getContext(), "Image Fetched", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
