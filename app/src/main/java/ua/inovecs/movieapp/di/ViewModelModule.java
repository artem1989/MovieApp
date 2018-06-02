package ua.inovecs.movieapp.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

import ua.inovecs.movieapp.viewmodel.MovieViewModel;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel.class)
    abstract ViewModel bindsMovie(MovieViewModel model);


    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(VMFactory factory);

}
