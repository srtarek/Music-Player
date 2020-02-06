package com.srtarek00055.mp3player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity {
    ImageView backPlayer,more;

    Button playBtn,pauseBtn,nxtBtn;

    static MediaPlayer mediaPlayer
            ;
    ArrayList<File> mySongs;
    static int position;
    Uri u;
    Thread updateSeekBar;
    private static boolean status=true;

    private double startTime = 0;
    private double finalTime = 0;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        this.getSupportActionBar().hide();
//        Find All Button
        playBtn = findViewById(R.id.play);
        pauseBtn = findViewById(R.id.pause);
        nxtBtn = findViewById(R.id.next);

        btnPlay();
        nextBtn();
//        Pause Button Code Start here
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                toast("Pause");
                playBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.GONE);
            }
        });
//        Pause Button Code End here
//        Back Button Start
        backPlayer = findViewById(R.id.backPlayer);
        backPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Player.this,MainActivity.class);
                startActivity(intent);
                toast("Back to Music List");
                Player.super.finish();
            }
        });
//        Back Button End Code
//        More Button Start Code
        more = findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("More Option");
            }
        });
//        More Button End Code
//        Play Song Here
        Intent i = getIntent();
        Bundle b = i.getExtras();
        mySongs = (ArrayList) b.getParcelableArrayList("songlist");
        position = b.getInt("pos");
        u = Uri.parse(mySongs.get(position).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
//       Start Song First time

        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }else{
        //Start Music Here
         mediaPlayer.start();
        }

    }
    //        Play Button Code Start here
    public void nextBtn(){
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toast("Next Button");
            }
        });
    }
    public void btnPlay(){
        playBtn.setVisibility(View.GONE);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                toast("Play Button");
                playBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.VISIBLE);
            }
        });
    }
    //        Play Button Code End here

//    Text Print Toast
    public void toast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
