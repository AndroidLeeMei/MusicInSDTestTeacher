package com.julia.musicinsdtest;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnprev, btnpause, btnstop, btnplay, btnnext;
    ListView lv;
//    String[] songname = new String[]{"AAA", "BBB", "CCC", "DDD"};
//    String[] songfile = new String[]{"a.mp3", "b.mp3", "c.mp3", "d.mp3"};
String[] songname=new String[]{"001","0941","0942","0943"};
    String[] songfile=new String[]{"001.mp3","0941.mp3","0942.mp3","0943.mp3"};
    private int cListItem = 0;
    private boolean flag = false;
    private final String songpath = new String("/sdcard/");
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findviews();
        lv.setOnItemClickListener(lstListener);
        btnprev.setOnClickListener(btnListener);
        btnnext.setOnClickListener(btnListener);
        btnstop.setOnClickListener(btnListener);
        btnplay.setOnClickListener(btnListener);
        btnpause.setOnClickListener(btnListener);

        mediaPlayer = new MediaPlayer();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songname);
        lv.setAdapter(adapter);

    }

    private void findviews() {
        btnprev = (Button) findViewById(R.id.btn_prev);
        btnnext = (Button) findViewById(R.id.btn_next);
        btnpause = (Button) findViewById(R.id.btn_pause);
        btnplay = (Button) findViewById(R.id.btn_play);
        btnstop = (Button) findViewById(R.id.btn_stop);
        lv = (ListView) findViewById(R.id.listview1);
    }

    // ==========================================
    private ListView.OnItemClickListener lstListener = new ListView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            cListItem = position;
            playsong(songpath + songfile[cListItem]);
        }
    };
    // ============================================
    private Button.OnClickListener btnListener = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_stop:
                    mediaPlayer.release();
                    break;
                case R.id.btn_play:
                    if(flag){
                        mediaPlayer.start();
                        flag=false;
                    }else{
                        playsong(songpath + songfile[cListItem]);
                    }
                    break;
                case R.id.btn_prev:
                    prevsong();
                    break;
                case R.id.btn_next:
                    nextsong();
                    break;
                case R.id.btn_pause:
                    mediaPlayer.pause();
                    flag=true;
                    break;

            }
        }
    };

    // ===================================
    private void playsong(String path) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    nextsong();
                }
            });


        } catch (IOException e) {
        }


    }

    // =============================
    private void nextsong() {
        cListItem++;
        if (cListItem >= lv.getCount()) {
            cListItem = 0;
        }
        playsong(songpath + songfile[cListItem]);
    }

    // ===================================
    private void prevsong() {
        cListItem--;
        if (cListItem < 0) {
            cListItem = lv.getCount() - 1;
        }
        playsong(songpath + songfile[cListItem]);
    }

}
