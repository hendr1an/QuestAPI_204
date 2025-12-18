package com.example.mydarasiswa.repositori

import com.example.mydarasiswa.apiservice.ServiceApiSiswa
import com.example.mydarasiswa.modeldata.DataSiswa
import retrofit2.Response

interface RepositoryDataSiswa {
    suspend fun getDataSiswa(): List<DataSiswa>
    suspend fun postDataSiswa(siswa: DataSiswa): Response<Void>
}


class NetworkRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
) : RepositoryDataSiswa {
    override suspend fun getDataSiswa(): List<DataSiswa> = serviceApiSiswa.getSiswa()

    override suspend fun postDataSiswa(siswa: DataSiswa): Response<Void> = serviceApiSiswa.insertSiswa(siswa)
}