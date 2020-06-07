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

public class PhrasesActivity extends AppCompatActivity {

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
        final ArrayList<word> words=new ArrayList<word>();
        maudio=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        words.add(new word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        words.add(new word("My name is..","oyaaset...",R.raw.phrase_my_name_is));
        words.add(new word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        words.add(new word("I’m feeling good","kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        words.add(new word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        words.add(new word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        words.add(new word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        words.add(new word("Come here.","әnni'nem",R.raw.phrase_come_here));

        wordactivity adapt=new wordactivity(this,words,R.color.category_phrases);
        ListView listview =(ListView)findViewById(R.id.list);
        listview.setAdapter(adapt);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word sound=words.get(position);
                releasemediaplayer();

                int result=maudio.requestAudioFocus(listenerobject,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mplayer= MediaPlayer.create(PhrasesActivity.this,sound.getmusicid());
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
    protected void onStop() {
        super.onStop();
        releasemediaplayer();
    }

}
