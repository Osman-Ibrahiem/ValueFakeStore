package com.value.fakestore.di.remote

import android.content.Context
import com.data.cache.source.UserModule
import com.domain.core.di.annotations.qualifiers.AppContext
import com.value.fakestore.di.ApplicationModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module(includes = [ApplicationModule::class])
class OkHttpClientModule {

    @Provides
    fun cache(@AppContext context: Context): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return Cache(context.cacheDir, cacheSize)
    }

    @Provides
    fun okHttpClientAuth(
        @AppContext context: Context,
        responseInterceptor: ResponseInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        userModule: UserModule,
    ): OkHttpClient {
//        val httpCacheDirectory = File(context.cacheDir, "responses")
//        val cacheSize = 10 * 1024 * 1024 // 10 MiB
//        val cache = Cache(httpCacheDirectory, cacheSize.toLong())
        return OkHttpClient()
            .newBuilder()
//            .cache(cache)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
//                .addNetworkInterceptor (REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()


                val token = userModule.token
                if (!token.isNullOrEmpty()) {
                    request.header("Authorization", "Bearer $token")
                }

                request.header("Content-Type", "application/json")
                    .method(original.method, original.body)
//                    if (!NetworkUtils.isConnected()) {
//                        val cacheControl = CacheControl.Builder()
//                                .maxStale(7, TimeUnit.DAYS)
//                                .build()
//
//                        request.cacheControl(cacheControl)
//
//                    }
                chain.proceed(request.build())
            }
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(responseInterceptor)
//                .addNetworkInterceptor { chain ->
//                    val response = chain.proceed(chain.request())
//
//                    if (NetworkUtils.isConnected()) {
//                        // re-write response header to force use of cache
//                        val cacheControl = CacheControl.Builder()
//                                .maxAge(1, TimeUnit.SECONDS)
//                                .build()
//
//
//                        response.newBuilder()
//                                .header("Cache-Control", cacheControl.toString())
//                                .build()
//                    }else{
//                        // re-write response header to force use of cache
//                        val cacheControl = CacheControl.Builder()
//                                .maxAge(2, TimeUnit.MINUTES)
//                                .build()
//
//
//                        response.newBuilder()
//                                .header("Cache-Control", cacheControl.toString())
//                                .build()
//                    }
//                }
            .build()
    }


    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


}