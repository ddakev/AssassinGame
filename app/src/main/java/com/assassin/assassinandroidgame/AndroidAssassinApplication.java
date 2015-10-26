package com.assassin.assassinandroidgame;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Abhinav on 10/3/2015.
 */
public class AndroidAssassinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "j8TYn96ROaW0hxHkYihb31JAVPGUTlDYF7sGJlk0", "MXT0iefC5JUKHyS91qBM0UXj6uvjrQEx1bSCPzRl");
    }
}
