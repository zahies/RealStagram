package com.example.realstagram.data.model;

import java.sql.Date;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String profilePic;
    private String displayName;
    private String dateSignUp;
    private String email;


    public LoggedInUser(String profilePic, String displayName, String date, String email) {
        this.profilePic = profilePic;
        this.dateSignUp = date;
        this.displayName = displayName;
        this.email = email;
    }

//    public String getUserId() {
//        return userId;
//    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getDateSignUp() {
        return dateSignUp;
    }

    public String getEmail() {
        return email;
    }


    public String getDisplayName() {
        return displayName;
    }
}