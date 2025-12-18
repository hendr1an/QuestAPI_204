package com.example.mydarasiswa.apiservice

import com.example.mydarasiswa.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ServiceApiSiswa {
    @Headers(
        "Accept: application/json"
    )
    @GET("bacateman.php")
    suspend fun getSiswa(): List<DataSiswa>

    @POST("insertTM.php")
    suspend fun insertSiswa(@Body siswa: DataSiswa): Response<Void>
}