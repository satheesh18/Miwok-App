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

public class FamilyActivity extends AppCompatActivity {

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
        words.add(new word("father","әpә",R.drawable.family_father,R.raw.family_father));
        words.add(new word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        words.add(new word("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new word("older sister","tete",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        wordactivity adapt=new wordactivity(this,words,R.color.category_family);
        ListView listview =(ListView)findViewById(R.id.list);
        listview.setAdapter(adapt);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word sound=words.get(position);
                releasemediaplayer();

                int result=maudio.requestAudioFocus(listenerobject,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    MediaPlayer mplayer= MediaPlayer.create(FamilyActivity.this,sound.getmusicid());
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
