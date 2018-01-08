package com.example.iskandar.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String imageURL = "http://www.androidbegin.com/wp-content/uploads/2013/07/HD-Logo.gif";
    /*
        Tidak semua gambar dapat ditampilkan
        berikut adalah contoh gambar yang tidak dapat ditampilkan
        https://theheran.com/wp-content/uploads/2016/10/angry.png
    */
    ImageView imgHasil;
    ProgressDialog pDialog;
    Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgHasil = findViewById(R.id.imageView);
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setIndeterminate(true); //lama proses tidak dapat dipastikan (true)
        pDialog.setCancelable(false);//dapat ditutup atau tidak
        pDialog.setMessage("Loading Boss...");

        btnDownload = findViewById(R.id.button);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadImage().execute(imageURL);
            }
        });

    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //menampilkan progress dialog
            pDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String imageURL = params[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(imageURL);
                //Download Image dari URL
                InputStream inputStream = new java.net.URL(imageURL).openStream();
                //Decode Bitmap
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            //Menampilkan gambar ke ImageView
            imgHasil.setImageBitmap(result);
            //Menutup ProgresDialog
            pDialog.dismiss();
        }
    }

}
