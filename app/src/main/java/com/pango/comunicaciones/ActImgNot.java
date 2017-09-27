package com.pango.comunicaciones;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;



public class ActImgNot extends AppCompatActivity {
    public static String URL_IMG_NOT = "";
    TouchImageView img_not;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_img_not);


        Bundle datos = this.getIntent().getExtras();
        URL_IMG_NOT=datos.getString("url_img");

        img_not = (TouchImageView) findViewById(R.id.not_img_z);

        cargarImagenExtendida();

    }

    private void cargarImagenExtendida() {
        Glide.with(img_not.getContext())
                .load(URL_IMG_NOT)
                .into(img_not);
    }
}
