package com.example.practice

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun provideRestaurantViewModel(viewModel: RestaurantViewModel): ViewModel
}