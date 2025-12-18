package com.example.mydarasiswa.repositori

import android.app.Application
import com.example.mydarasiswa.apiservice.ServiceApiSiswa
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val repositoryDataSiswa: RepositoryDataSiswa
}

class DefaultAppContainer : AppContainer {
    // Pastikan IP dan nama folder sudah benar sesuai htdocs kamu
    private val baseUrl = "http://10.0.2.2/Pertemuan%2012/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // Baris ini dulu merah karena kurang import ServiceApiSiswa
    private val retrofitService: ServiceApiSiswa by lazy {
        retrofit.create(ServiceApiSiswa::class.java)
    }

    // Baris ini merah jika file RepositoryData.kt kamu belum diisi code yang benar
    override val repositoryDataSiswa: RepositoryDataSiswa by lazy {
        NetworkRepositoryDataSiswa(retrofitService)
    }
}

class AplikasiDataSiswa : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}