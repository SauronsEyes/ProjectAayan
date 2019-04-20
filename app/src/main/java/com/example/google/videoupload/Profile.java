package com.example.google.videoupload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
 Button buysub;
 Button pay;
 ImageView b1,b2,b3;
 TextView hyper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pay = findViewById(R.id.pay);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        hyper = findViewById(R.id.hyper);
        hyper.setClickable(true);
        hyper.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='https://www.facebook.com/groups/424550245037825/'> Get Started </a>";
        hyper.setText(Html.fromHtml(text));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,Badge.class);
                i.putExtra("bid","a");
                startActivity(i);
            }
        });

        buysub = findViewById(R.id.buysub);
        buysub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,esewa.class);
                startActivity(i);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,ezsewa.class);
                startActivity(i);

            }
        });
    }
}
