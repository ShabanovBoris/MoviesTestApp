package com.bosha.data.di

import com.bosha.data.BuildConfig
import com.bosha.data.remote.MovieNetworkApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideTheMovieApi(retrofit: Retrofit): MovieNetworkApi =
        retrofit.create(MovieNetworkApi::class.java)

    @Provides
    fun provideOkHttpClient(
        interceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient().newBuilder().apply {
            addInterceptor(loggingInterceptor)
            addNetworkInterceptor(interceptor)
        }.build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder().apply {
            client(okHttpClient)
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        }.build()

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideApiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .url(chain.request().url.toString().plus(apiKey))
            .build()
        chain.proceed(request)
    }

    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    //TODO - switch to access token
    //The better way is to create access token and add it to header, but so
    companion object {
        private const val apiKey = "&api_key=${BuildConfig.TMDB_APIKEY}"
    }
}