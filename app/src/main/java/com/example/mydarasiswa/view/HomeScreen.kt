package com.example.mydarasiswa.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.MyDaraSiswa.R

// Import dari package kamu sendiri

import com.example.mydarasiswa.modeldata.DataSiswa
import com.example.mydarasiswa.uicontroller.route.DestinasiHome
import com.example.mydarasiswa.viewmodel.HomeViewModel
import com.example.mydarasiswa.viewmodel.StatusUiSiswa
import com.example.mydarasiswa.viewmodel.provider.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiHome.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(
                        R.string.entry_siswa)
                )
            }
        },
    ) { innerPadding ->
        HomeStatus(
            statusUiSiswa = viewModel.listSiswa,
            retryAction = { viewModel.loadSiswa() },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun HomeStatus(
    statusUiSiswa: StatusUiSiswa,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (statusUiSiswa) {
        // PERBAIKAN: Menggunakan CircularProgressIndicator (Loading bawaan Android)
        // Jadi tidak perlu gambar loading_img.png lagi
        is StatusUiSiswa.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

        is StatusUiSiswa.Success -> HomeBody(
            listSiswa = statusUiSiswa.siswa,
            modifier = modifier.fillMaxWidth()
        )

        is StatusUiSiswa.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator() // Loading putar-putar bawaan
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // PERBAIKAN: Menggunakan Icon Warning bawaan
        // Jadi tidak perlu gambar ic_connection_error.png lagi
        Icon(
            imageVector = Icons.Default.Delete, // Ikon sementara tanda error
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun HomeBody(
    listSiswa: List<DataSiswa>,
    modifier: Modifier = Modifier
) {
    if (listSiswa.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Tidak ada data mahasiswa")
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = listSiswa, key = { it.id }) { siswa ->
                MhsCard(
                    siswa = siswa,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                )
            }
        }
    }
}

@Composable
fun MhsCard(
    siswa: DataSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = siswa.nama,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
            Text(
                text = siswa.alamat,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = siswa.telpon,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}