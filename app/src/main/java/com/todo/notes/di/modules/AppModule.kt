package com.todo.notes.di.modules

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.todo.notes.R
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: Application): Context

}