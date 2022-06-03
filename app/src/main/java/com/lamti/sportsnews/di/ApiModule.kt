package com.lamti.sportsnews.di

import android.content.Context
import android.content.pm.PackageManager
import com.lamti.sportsnews.data.local.FootballDatabase
import com.lamti.sportsnews.data.networkResult.NetworkResultCallAdapterFactory
import com.lamti.sportsnews.data.remoteApi.FootballApi
import com.lamti.sportsnews.data.remoteApi.PlayersRemoteDataSource
import com.lamti.sportsnews.data.remoteApi.TeamsRemoteDataSource
import com.lamti.sportsnews.data.repository.FootballRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://v3.football.api-sports.io/"
    private const val X_RAPIDAPI_KEY = "x-rapidapi-key"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        @ApplicationContext applicationContext: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                val applicationInfo = applicationContext.packageManager.getApplicationInfo(
                    applicationContext.packageName,
                    PackageManager.GET_META_DATA
                )
                val key = applicationInfo.metaData[X_RAPIDAPI_KEY].toString()
                val request: Request = chain.request().newBuilder().addHeader(X_RAPIDAPI_KEY, key).build()
                chain.proceed(request)
            })
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): FootballApi = retrofit.create(FootballApi::class.java)

    @Singleton
    @Provides
    fun provideRemotePlayersDataSource(apiService: FootballApi) = PlayersRemoteDataSource(apiService)

    @Singleton
    @Provides
    fun provideRemoteTeamsDataSource(apiService: FootballApi) = TeamsRemoteDataSource(apiService)

    @Singleton
    @Provides
    fun provideRepository(
        teamsRemoteDataSource: TeamsRemoteDataSource,
        footballApi: FootballApi,
        footballDatabase: FootballDatabase
    ) =
        FootballRepository(teamsRemoteDataSource, footballApi, footballDatabase)
}
