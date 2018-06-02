package ua.inovecs.movieapp.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ua.inovecs.movieapp.ui.DetailsFragment;
import ua.inovecs.movieapp.ui.GridFragment;

@Module
@SuppressWarnings("unused")
public abstract class MainActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract GridFragment providesGridFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract DetailsFragment providesDetailsFragment();
}
