package com.mayur.quizzy.di

import com.mayur.quizzy.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @GeminiApiKey
    fun provideGeminiApiKey(): String = BuildConfig.MY_API_KEY

    @Provides
    @Singleton
    fun provideKtorHttpClient(): HttpClient {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
        }

        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(json)
            }
        }
    }
}
