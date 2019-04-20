package com.example.google.videoupload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText namez,userzname,pass,phonez,fieldz;
    Button confirm_sign;
    String insertUrl = "http://192.168.1.33/ayaan/insertData.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        namez = findViewById(R.id.users_name);
        userzname = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        phonez = findViewById(R.id.phone);
        fieldz = findViewById(R.id.field);
        confirm_sign = findViewById(R.id.confirm_signup);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        confirm_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest requests = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters = new HashMap<String,String>();
                        parameters.put("username",userzname.getText().toString());
                        parameters.put("password",pass.getText().toString());
                        parameters.put("name",namez.getText().toString());
                        parameters.put("field",fieldz.getText().toString());
                        parameters.put("phone",phonez.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(requests);
                Toast.makeText(SignupActivity.this, "You are now a member of Ayaan Community. Login to Continue", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this,SignupLogin.class);
                startActivity(intent);

            }
        });
    }
}
