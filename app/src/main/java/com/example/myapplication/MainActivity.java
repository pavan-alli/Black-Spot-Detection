package com.example.myapplication;

import android.Manifest;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
//import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    double lat, lon;
    double near_the_black_spot = 0.05;
    double in_black_spot = 0.005;
    double diff_lat, diff_lon;


    private List<LatLangdata> latlangdata = new ArrayList<>();
    private Handler handler1=new Handler();
    private Handler handler2=new Handler();


    // private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    //private FusedLocationProviderClient mFusedLocationClient;
    //Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);


        //Toast.makeText(getApplicationContext(), "lat" + lat + "\n lon" + lon, Toast.LENGTH_LONG).show();
        readlatlang();


        runnable1.run();

    }


    private void readlatlang()
    {

        //LatLangdata ll;
        InputStream is = getResources().openRawResource(R.raw.maharashtra);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";

        try {

            reader.readLine();

            while ((line = reader.readLine()) != null) {

                Log.d("MyActivity", "Line" + line);

                //split by " , "

                String tokens[] = line.split(",");

                //read the data

                LatLangdata data = new LatLangdata();

                data.setnumber(tokens[0]);
                data.setLat(Double.parseDouble(tokens[1]));
                data.setLang(Double.parseDouble(tokens[2]));
                latlangdata.add(data);


            }


        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }

    public void taskrepeat()
    {
        GPStracker g = new GPStracker(getApplicationContext());
        Location l = g.getLocation();
        if (l != null) {
            lat = l.getLatitude();
            lon = l.getLongitude();
        }
        Iterator itr = latlangdata.iterator();
        while (itr.hasNext()) {
            LatLangdata ld = (LatLangdata) itr.next();
            //Log.d("MyActivity", "data" + " " + ld.getnumber() + " " + ld.getLat() + " " + ld.getLang());

            final double ld_lat = ld.getLat();
            final double ld_lon = ld.getLang();

            diff_lat = lat - ld_lat;
            diff_lon = lon - ld_lon;
            if (diff_lat < near_the_black_spot && diff_lon < near_the_black_spot) {


                    Toast.makeText(getApplicationContext(), "You are in the range of  blackspot", Toast.LENGTH_LONG).show();
                    final MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.rangeaudio);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.release();
                        }
                    });





                runnable2.run();
            }

        }
    }


    public void  inside_black_spot_zone_check()
        {
        GPStracker g = new GPStracker(getApplicationContext());
        Location l = g.getLocation();
        if (l != null) {
            lat = l.getLatitude();
            lon = l.getLongitude();
        }




        if( diff_lat < in_black_spot && diff_lon < in_black_spot)
        {


                    Toast.makeText(getApplicationContext(), "You are in the blackspot", Toast.LENGTH_LONG).show();



        }

        if(diff_lat >in_black_spot && diff_lon > in_black_spot &&diff_lat < near_the_black_spot && diff_lon < near_the_black_spot)
            {

                    Toast.makeText(getApplicationContext(), "You have passed the blackspot zone", Toast.LENGTH_LONG).show();
                    handler2.removeCallbacks(runnable2);

            }



    }

    private Runnable runnable1 = new Runnable()
    {
        @Override
        public void run() {
            taskrepeat();
            handler1.postDelayed(this,20000);

        }
    };

    private Runnable runnable2 = new Runnable()
    {
        @Override
        public void run() {

            inside_black_spot_zone_check();
            handler2.postDelayed(this, 10000);
        }
    };


}





















