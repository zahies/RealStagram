package com.example.realstagram.data;

import com.example.realstagram.MainActivity;
import com.example.realstagram.data.model.LoggedInUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    Connection con;

    public Result<LoggedInUser> login(String username, String password) {
        boolean found = false;
        try {
            // TODO: handle loggedInUser authentication


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
                            if(rs.getString("password").equals(password)){
                                LoggedInUser user =
                                        new LoggedInUser(rs.getString("profile_pic")
                                                ,rs.getString("username"),
                                                rs.getString("start_date"),rs.getString("email"));
                                return new Result.Success<>(user);
                            }
                        }
                    }
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }



        return new Result.Error(new IOException("Error logging in"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}