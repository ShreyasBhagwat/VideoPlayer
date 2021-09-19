package com.example.videoplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    VideoView videoView;
    ArrayList<Integer> videolist = new ArrayList<>();
    int currvideo=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView)findViewById(R.id.videoView);
        videoView.setMediaController(new MediaController(this));
        videoView.setOnCompletionListener(this);

        videolist.add(R.raw.rickroll1);
        videolist.add(R.raw.rickroll);
        videolist.add(R.raw.demovid);

        setVideo(videolist.get(0));
    }

    private void setVideo(Integer id) {
        String uriPath = "andriod.resource://"+getPackageName()+"/"+ id;
        Uri uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);
        videoView.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("Playback Finished");
        obj.setIcon(R.mipmap.ic_launcher);
        MyListener m = new  MyListener();
        obj.setPositiveButton("Replay",m);
        obj.setNegativeButton("Next",m);
        obj.setMessage("Want to replay or play next video?");
        obj.show();
    }
    class MyListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which ==-1){
                videoView.seekTo(0);
                videoView.start();
            }
            else {
                ++currvideo;
                if(currvideo == videolist.size()){
                    currvideo = 0;
                }
                setVideo(videolist.get(currvideo));
            }
        }
    }

}
