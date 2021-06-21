package com.todo.notes.di.modules

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.todo.notes.R
import com.todo.notes.data.api.ApiService
import com.todo.notes.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * ActivityBuildersModule which is responsible for injecting activities
 * and creating subcomponents for them.
 * */
@Module
class RemoteServiceModule {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory) =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor) =
        OkHttpClient().newBuilder()
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(getInterceptor()).build()

    @Singleton
    @Provides
    fun provideGSON(): GsonConverterFactory {
        return GsonConverterFactory.create()

    }

    @Singleton
    @Provides
    fun getInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    fun providePrefrences(context: Context) = context.getSharedPreferences(
        context.getString(R.string.app_name),
        AppCompatActivity.MODE_PRIVATE
    )
}