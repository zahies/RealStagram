package com.example.realstagram;

import android.net.Uri;
import android.os.Bundle;

import com.example.realstagram.data.Result;
import com.example.realstagram.data.model.LoggedInUser;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VideoView extends AppCompatActivity {

    private String username;
    private Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        this.username = getIntent().getStringExtra("username");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        String pathToMyStory = null;


        boolean found = false;
        try{
            con = MainActivity.connectionclass();
            if (con == null){
                System.out.println("check internet");
            }else{
                PreparedStatement preparedStatement = con.prepareStatement("select * from media where username = ?");
                preparedStatement.setString(1,username);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next() && !found){
                    if (rs.getString("username").equals(username)){
                        found = true;
                        pathToMyStory = rs.getString("story_path");
                    }
                }
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        android.widget.VideoView myStory = findViewById(R.id.videoView1);
        if(pathToMyStory == null){
            Toast.makeText(getApplicationContext(), "ERROR - not found", Toast.LENGTH_SHORT).show();
        }
        Uri uri = Uri.parse(pathToMyStory);
        myStory.setVideoURI(uri);
        myStory.start();




    }
}