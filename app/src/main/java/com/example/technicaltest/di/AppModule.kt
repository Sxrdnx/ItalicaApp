package com.example.technicaltest.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.data.repository.PokemonRepository
import com.example.data.source.PokemonLocalDataSource
import com.example.data.source.PokemonRemoteDataSource
import com.example.technicaltest.data.local.PokemonDao
import com.example.technicaltest.data.local.PokemonDatabase
import com.example.technicaltest.data.local.RoomPokemonDatasource
import com.example.technicaltest.data.remote.PokemonApi
import com.example.technicaltest.data.remote.RetrofitPokemonDataSource
import com.example.technicaltest.utils.Constants
import com.example.technicaltest.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePokemonDatabase(
        @ApplicationContext contex: Context
    ) = Room.databaseBuilder(contex, PokemonDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun providePokemonDao(db: PokemonDatabase) = db.pokemonDao()

    @Singleton
    @Provides
    fun provideLocalDatasource(pokemonDao: PokemonDao): PokemonLocalDataSource {
        return RoomPokemonDatasource(pokemonDao)
    }

    @Singleton
    @Provides
    fun provideNoteApi(client: OkHttpClient): PokemonApi {
        return Retrofit.Builder()
            .baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PokemonApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRemoteDatasource(pokemonApi: PokemonApi): PokemonRemoteDataSource {
        return RetrofitPokemonDataSource(pokemonApi)
    }

    @Singleton
    @Provides
    fun providePokemonRepository(
        pokemonRemoteDataSource: PokemonRemoteDataSource,
        pokemonLocalDataSource: PokemonLocalDataSource
    ): PokemonRepository {
        return PokemonRepository(pokemonRemoteDataSource, pokemonLocalDataSource)
    }

    @Provides
    fun provideGlideInstance(@ApplicationContext contex: Context): RequestManager {
        return Glide.with(contex)
    }


}