package com.todo.notes.di.modules

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.todo.notes.R
import com.todo.notes.data.local.data_store.PreferenceStorage
import com.todo.notes.data.local.room.UserDatabase
import dagger.Module
import dagger.Provides

/**
 * LocalDataSourceModule which is responsible for providing local database
 * such as SharedPreferences
 * such as UserDatabase
 * */
@Module
class LocalDataSourceModule {

    @Provides
    fun providePrefrences(context: Context) = context.getSharedPreferences(
        context.getString(R.string.app_name),
        AppCompatActivity.MODE_PRIVATE
    )

    @Provides
    fun provideRoomDatabase(context: Context) = Room.databaseBuilder(
        context,
        UserDatabase::class.java, context.getString(R.string.app_name)
    ).build()

    @Provides
    fun provideDao(userDatabase: UserDatabase) = userDatabase.userDao()

}