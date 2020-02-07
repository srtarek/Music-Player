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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Player extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {

//    Ativity id
    ImageView backPlayer,more;
    Button playBtn,pauseBtn,nxtBtn,prvBtn,allSong;
    SeekBar seekBar;
    TextView starTextTime,totalTextTime,songTitle;

    String[] items;

    static MediaPlayer mediaPlayer;
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
        prvBtn = findViewById(R.id.previous);
        allSong = findViewById(R.id.allsong);
//        Song Title
        songTitle = findViewById(R.id.songTitle);

        starTextTime = findViewById(R.id.startText);
        totalTextTime = findViewById(R.id.totalText);

        playBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        nxtBtn.setOnClickListener(this);
        prvBtn.setOnClickListener(this);

        playBtn.setVisibility(View.GONE);
        pauseBtn.setVisibility(View.VISIBLE);

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
//        SeekBar Code Start Here
        seekBar = findViewById(R.id.seekBar);
        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                seekBar.setMax(totalDuration);
                while (currentPosition<totalDuration){
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
//                super.run();
            }
        };
//        SeekBar Code End Here
//        Release MediaPlayer if mediaPlayer Not null
        if (mediaPlayer !=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
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
        //Start Music Here
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(this);
        if (status){
            status=false;
            updateMediaPlayerDuration();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();

//        updateMediaPlayerDuration();
    }
//    Updatetime Duration from mediaPlayer use SeekBar and Text View code Start Here
private void updateMediaPlayerDuration(){

    try {
        if (mediaPlayer !=null) {
            //Text set Start Time
            finalTime = mediaPlayer.getDuration();
            startTime = mediaPlayer.getCurrentPosition();
            starTextTime.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    startTime)))
            );
            //Total Song Time
            totalTextTime.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                    finalTime)))
            );
            seekBar.setMax(mediaPlayer.getDuration());
            updateSeekBar.start();
            myHandler.postDelayed(UpdateSongTime, 100);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//    Updatetime Duration from mediaPlayer use seekBar and Text View code End Here
//    UpdateSong start time and total time code Start Here
private Runnable UpdateSongTime = new Runnable() {
    public void run() {
        startTime = mediaPlayer.getCurrentPosition();
        starTextTime.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                toMinutes((long) startTime)))
        );
        totalTextTime.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        myHandler.postDelayed(this, 100);
    }
};
//    UpdateSong start time and total time code End Here


//    Text Print Toast
    public void toast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    All Button Action Code Start Here
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.play:
                mediaPlayer.start();
                playBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.pause:
                mediaPlayer.pause();
                pauseBtn.setVisibility(View.GONE);
                playBtn.setVisibility(View.VISIBLE);
                break;
//                Next Putton
            case R.id.next:
                mediaPlayer.stop();
                mediaPlayer.release();
//                position = (position+1)%mySongs.size();
                if (++position<mySongs.size()) {
                    u = Uri.parse(mySongs.get(position).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                    mediaPlayer.setOnCompletionListener(this);
                    mediaPlayer.start();
                }
                break;
            case R.id.previous:
                mediaPlayer.stop();
                mediaPlayer.release();
//                position = (position-1<0)?mySongs.size()-1:position-1;
//                if (position-1<0){
//                    position = mySongs.size()-1;
//                }else {
//                    position = position-1;
//                }
                if (--position>=0) {
                    u = Uri.parse(mySongs.get((position)).toString());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                    mediaPlayer.setOnCompletionListener(this);
                    mediaPlayer.start();
                }
                break;

        }
//    All Button Action Code End Here
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        if (position>mySongs.size())
            return;
        else
        {
            u = Uri.parse(mySongs.get(++position).toString());
            mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(this);
                 /*myHandler.removeCallbacks(UpdateSongTime);
                 updateMediaPlayerDuration();*/

        }

    }
}
