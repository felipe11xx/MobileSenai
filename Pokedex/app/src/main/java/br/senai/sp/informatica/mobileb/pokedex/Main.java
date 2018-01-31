package br.senai.sp.informatica.mobileb.pokedex;

import android.app.Application;
import android.content.Context;

/**
 * Created by WEB on 30/01/2018.
 */

public class Main extends Application {

    private static Main app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }


    public static Context getContext() {

        return app.getBaseContext();
    }
}
