package com.aloptrbl.avengersapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.aloptrbl.avengersapp.BuildConfig
import com.aloptrbl.avengersapp.api.HeroAPI
import com.aloptrbl.avengersapp.db.AppDatabase
import com.aloptrbl.avengersapp.db.HeroDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("hash", BuildConfig.MARVEL_HASH)
                .addQueryParameter("apikey", BuildConfig.MARVEL_PUBLIC_KEY)
                .build()

            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideHeroAPI(retrofit: Retrofit): HeroAPI {
        return retrofit.create(HeroAPI::class.java)
    }

    @Provides
    fun provideHeroDao(database: AppDatabase): HeroDao {
        return database.heroDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "hero"
        ).build()
    }

    @Provides
    fun provideApplication(@ApplicationContext context: Context): Application {
        return context as Application
    }


}