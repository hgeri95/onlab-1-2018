package bme.cateringmobilapp;

import android.app.Application;

import bme.cateringmobilapp.ui.UIModule;

public class CateringApplication extends Application {

    public static CateringApplicationComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = DaggerCateringApplicationComponent.builder()
                .uIModule(new UIModule(this))
                .build();
    }
}
