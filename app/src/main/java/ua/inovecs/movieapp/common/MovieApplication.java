package ua.inovecs.movieapp.common;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import ua.inovecs.movieapp.di.DaggerAppComponent;

public class MovieApplication extends DaggerApplication {


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector(){
        return DaggerAppComponent.builder().application(this).build();
    }

}
