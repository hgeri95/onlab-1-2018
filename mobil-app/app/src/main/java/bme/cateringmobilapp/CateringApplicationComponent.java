package bme.cateringmobilapp;

import javax.inject.Singleton;

import bme.cateringmobilapp.network.NetworkModule;
import bme.cateringmobilapp.ui.UIModule;
import bme.cateringmobilapp.ui.main.MainActivity;
import dagger.Component;

@Singleton
@Component(modules = {UIModule.class, NetworkModule.class})
public interface CateringApplicationComponent {

    void inject(MainActivity mainActivity);
}
