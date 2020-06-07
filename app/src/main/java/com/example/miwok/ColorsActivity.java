package com.example.miwok;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mplayer;
    private MediaPlayer.OnCompletionListener med=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releasemediaplayer();
        }
    } ;
    private AudioManager maudio;
    AudioManager.OnAudioFocusChangeListener listenerobject=new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int focusChange) {

        }
        public void OnAudioFocusChange(int focus){
            if(focus==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focus==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mplayer.pause();
                mplayer.seekTo(0);
            }
            else if(focus==AudioManager.AUDIOFOCUS_GAIN){
                mplayer.start();
            }
            else if(focus==AudioManager.AUDIOFOCUS_LOSS){
                releasemediaplayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        maudio=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<word> words=new ArrayList<word>();
        words.add(new word("red","wetetti",R.drawable.color_red,R.raw.color_red));
        words.add(new word("green","chokokki",R.drawable.color_green,R.raw.color_green));
        words.add(new word("brown","takaakki",R.drawable.color_brown,R.raw.color_brown));
        words.add(new word("gray","topoppi",R.drawable.color_gray,R.raw.color_gray));
        words.add(new word("black","kululli",R.drawable.color_black,R.raw.color_black));
        words.add(new word("white","kelelli",R.drawable.color_white,R.raw.color_white));
        words.add(new word("dusty yellow","topiis",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new word("mustard yellow","chiwitti",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));


        wordactivity adapt=new wordactivity(this,words,R.color.category_colors);
        ListView listview =(ListView)findViewById(R.id.list);
        listview.setAdapter(adapt);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word sound=words.get(position);
                releasemediaplayer();
                int result=maudio.requestAudioFocus(listenerobject,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    MediaPlayer  mplayer= MediaPlayer.create(ColorsActivity.this,sound.getmusicid());
                    mplayer.start();
                    mplayer.setOnCompletionListener(med);
                }
            }
        });
    }
    private void releasemediaplayer(){
        if(mplayer!=null){
            mplayer.release();
            mplayer=null;
            maudio.abandonAudioFocus(listenerobject);
        }


    }
    protected void onStop() {
        super.onStop();
        releasemediaplayer();
    }
}
