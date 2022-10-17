package com.curiosityrover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.curiosityrover.model.CustomImageCard;
import com.curiosityrover.network.CustomVolleyRequest;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CardDescription extends AppCompatActivity {

    MaterialTextView solText, cameraName, imageIDText, dateText;
    ImageLoader imgLoader;
    String imageUrl, sol, camera, image_id, earth_date;
    NetworkImageView imgView;
    ImageButton backBtn;
    Gson gson;
    SharedPreferences sp;

    ArrayList<CustomImageCard> savedCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_description);
        imgView = findViewById(R.id.rover_image_description_view);
        solText = findViewById(R.id.description_image_sol);
        cameraName = findViewById(R.id.description_image_camera);
        imageIDText = findViewById(R.id.description_image_id);
        dateText = findViewById(R.id.description_image_date);
        backBtn = findViewById(R.id.back_button_activity2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent i = getIntent();

        imageUrl = i.getStringExtra("url");
        sol = i.getStringExtra("sol");
        camera = i.getStringExtra("camera");
        image_id = i.getStringExtra("img_id");
        earth_date = i.getStringExtra("earth_date");

        imgLoader = CustomVolleyRequest.getInstance(this)
                .getImageLoader();
        imgLoader.get(imageUrl, ImageLoader.getImageListener(imgView,
                com.google.android.material.R.drawable.ic_clock_black_24dp, android.R.drawable
                        .ic_dialog_alert));
        imgView.setImageUrl(imageUrl, imgLoader);
        solText.setText(sol);
        cameraName.setText(camera);
        imageIDText.setText(image_id);
        dateText.setText(earth_date);

        sp = getApplicationContext().getSharedPreferences("ROVER_IMAGE_DATA", MODE_PRIVATE);
        gson = new Gson();
        String savedJson = sp.getString("image_data", null);

        Type type = new TypeToken<ArrayList<CustomImageCard>>(){}.getType();
        savedCards = gson.fromJson(savedJson, type);
        if(savedCards == null){
            savedCards = new ArrayList<CustomImageCard>();
        }
    }

    public void handleData(View view){

        SharedPreferences.Editor editor = sp.edit();

        savedCards.add(new CustomImageCard(imageUrl, image_id, sol, camera, earth_date));
        String json = gson.toJson(savedCards);
        editor.putString("image_data", json);
        editor.apply();
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
    }
}