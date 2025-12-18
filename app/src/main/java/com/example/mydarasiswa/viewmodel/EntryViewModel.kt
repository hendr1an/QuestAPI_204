package com.example.mydarasiswa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mydarasiswa.modeldata.DetailSiswa
import com.example.mydarasiswa.modeldata.UIStateSiswa
import com.example.mydarasiswa.modeldata.toDataSiswa
import com.example.mydarasiswa.repositori.RepositoryDataSiswa

class EntryViewModel(private val repositoryDataSiswa: RepositoryDataSiswa) : ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    // Fungsi untuk menangani saat ada perubahan pada text input
    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    /* Fungsi untuk menyimpan data yang di-entry */
    suspend fun addSiswa() {
        if (validasiInput()) {
            try {
                repositoryDataSiswa.postDataSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}