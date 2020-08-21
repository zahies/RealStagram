package com.example.realstagram;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realstagram.data.model.LoggedInUser;
import com.example.realstagram.ui.login.LoginActivity;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    Connection con;

    private LoggedInUser loggedInUser;
    private String username;
    private CircleImageView myStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.username = getIntent().getStringExtra("username");

        Button button = findViewById(R.id.btn1);
        Button buttonLogin = findViewById(R.id.btn2);
        TextView textView = findViewById(R.id.tv1);
        myStory = (CircleImageView)findViewById(R.id.imageButton);


        String pathToMyProfilePic = null;


        boolean found = false;
        try{
            con = MainActivity.connectionclass();
            if (con == null){
                System.out.println("check internet");
            }else{
                PreparedStatement preparedStatement = con.prepareStatement("select * from users where username = ?");
                preparedStatement.setString(1,username);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next() && !found){
                    if (rs.getString("username").equals(username)){
                        found = true;
                        pathToMyProfilePic = rs.getString("profile_pic");
                    }
                }
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Picasso.with(this).load(pathToMyProfilePic).into(myStory);
        //Picasso.with(this).load(pathToMyProfilePic).transform(new CircleTransform()).into(myStory);
        Uri uri = Uri.parse(pathToMyProfilePic);
        myStory.setImageURI(uri);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = findViewById(R.id.tv1);
                if (textView.isShown()){
                    textView.setVisibility(View.GONE);
                }else {
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });


        myStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(MainActivity.this, VideoView.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        //Intent i  = new Intent(MainActivity.this, LoginActivity.class);
       // startActivity(i);


    }


    public void setDetails(String profilePic, String displayName, String date, String email){
        LoggedInUser loggedInUser = new LoggedInUser(profilePic,displayName,date,email);
        this.loggedInUser = loggedInUser;
    }

    @SuppressLint("NewApi")
    public static Connection connectionclass(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://realstagram.database.windows.net:1433;DatabaseName=realStagramDB;user=zahi@realstagram;password=semizK93;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            //connection = DriverManager.getConnection(connectionURL,"zahi","semizK93");
            connection = DriverManager.getConnection(connectionURL);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }


    public void doSomething(){

    }
}