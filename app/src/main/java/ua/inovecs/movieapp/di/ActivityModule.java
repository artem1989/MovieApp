package ua.inovecs.movieapp.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ua.inovecs.movieapp.ui.MainActivity;


@SuppressWarnings("unused")
@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();

}
