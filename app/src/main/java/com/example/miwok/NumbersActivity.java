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

public class NumbersActivity extends AppCompatActivity {

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
        words.add(new word("one","lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new word("two","otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new word("three","tolookosu",R.drawable.number_three,R.raw.number_three));
        words.add(new word("four","oyissa",R.drawable.number_four,R.raw.number_four));
        words.add(new word("five","massokka",R.drawable.number_five,R.raw.number_five));
        words.add(new word("six","temmokka",R.drawable.number_six,R.raw.number_six));
        words.add(new word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new word("eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new word("nine","woe",R.drawable.number_nine,R.raw.number_nine));
        words.add(new word("ten","naaacha",R.drawable.number_ten,R.raw.number_ten));

        wordactivity adapt=new wordactivity(this,words,R.color.category_numbers);
        ListView listview =(ListView)findViewById(R.id.list);
        listview.setAdapter(adapt);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word sound=words.get(position);
                releasemediaplayer();
                int result=maudio.requestAudioFocus(listenerobject,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    MediaPlayer  mplayer= MediaPlayer.create(NumbersActivity.this,sound.getmusicid());
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
        }
        maudio.abandonAudioFocus(listenerobject);
    }


    @Override
    protected void onStop() {
        super.onStop();
        releasemediaplayer();
    }
}
