package com.example.indiacastdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.indiacastdemo.Database.DatabaseHelper;
import com.example.indiacastdemo.Validation.ConditionCheckClass;

public class RegisterActivity extends AppCompatActivity {
    EditText et_username, et_email, et_password, et_rpassword;
    Button bt_signUp;
    DatabaseHelper db;
    ConditionCheckClass conditionCheckClass = new ConditionCheckClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new DatabaseHelper(this);
        et_email = (EditText) findViewById(R.id.et_email);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_rpassword = (EditText) findViewById(R.id.et_rpassword);
        bt_signUp = (Button) findViewById(R.id.bt_signUp);
        bt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String rpassword = et_rpassword.getText().toString().trim();
                if (conditionCheckClass.isNotNull(username) && conditionCheckClass.isNotNull(password) && conditionCheckClass.isNotNull(email) && conditionCheckClass.isNotNull(rpassword) && password.equals(rpassword)) {
//                    db.insertData(username, password, email);
//                    Toast.makeText(RegisterActivity.this, "Registration successfully", Toast.LENGTH_SHORT).show();
//                    et_email.setText("");
//                    et_username.setText("");
//                    et_password.setText("");
//                    et_rpassword.setText("");
//                    Intent intent = new Intent(RegisterActivity.this, LoginScreen.class);
//                    startActivity(intent);
//                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
