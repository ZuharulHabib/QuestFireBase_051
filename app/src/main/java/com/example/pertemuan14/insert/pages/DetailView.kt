package com.example.pertemuan14.insert.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan14.insert.viewmodel.DetailUiState
import com.example.pertemuan14.insert.viewmodel.DetailViewModel
import com.example.pertemuan14.insert.viewmodel.PenyediaViewModel
import com.example.pertemuan14.model.Mahasiswa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("Detail Mahasiswa") },
                navigationIcon = {
                    Button(onClick = onBack) {
                        Text("Back")
                    }
                }

            )
        },

        ) { innerPadding ->
        BodyDetailMhs(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.detailUiState,
            retryAction = { viewModel.getMahasiswaByNim() }
        )
    }
}


@Composable
fun BodyDetailMhs(
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    detailUiState: DetailUiState,

    ) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailUiState.Success -> {
            if (detailUiState.mahasiswa.nim.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailMhs(
                    mahasiswa = detailUiState.mahasiswa,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailUiState.Error -> OnError(message = detailUiState.message.message?:"Error",
            retryAction,
            modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailMhs(
    modifier: Modifier = Modifier,
    mahasiswa: Mahasiswa,
){
    Card(
        modifier = modifier.fillMaxWidth().padding(top = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            DetailMhs(judul = "NIM", isinya = mahasiswa.nim)
            Spacer(modifier = Modifier.padding(4.dp))
            DetailMhs(judul = "Nama", isinya = mahasiswa.nama)
            Spacer(modifier = Modifier.padding(4.dp))
            DetailMhs(judul = "Jenis Kelamin", isinya = mahasiswa.jenis_kelamin)
            Spacer(modifier = Modifier.padding(4.dp))
            DetailMhs(judul = "Alamat", isinya = mahasiswa.alamat)
            Spacer(modifier = Modifier.padding(4.dp))
            DetailMhs(judul = "Kelas", isinya = mahasiswa.kelas)
            Spacer(modifier = Modifier.padding(4.dp))
            DetailMhs(judul = "Angkatan", isinya = mahasiswa.angkatan)
            Spacer(modifier = Modifier.padding(4.dp))
            DetailMhs(judul = "Judul Skripsi", isinya = mahasiswa.judul_skripsi)
            Spacer(modifier = Modifier.padding(4.dp))
            DetailMhs(judul = "Dosen Pembimbing1", isinya = mahasiswa.dsn_pembimbing1)
            Spacer(modifier = Modifier.padding(4.dp))
            DetailMhs(judul = "Dosen Pembimbing2", isinya = mahasiswa.dsn_pembimbing2)
        }
    }
}

@Composable
fun DetailMhs(
    modifier: Modifier =Modifier,
    judul: String,
    isinya: String,
){
    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start)
    {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}