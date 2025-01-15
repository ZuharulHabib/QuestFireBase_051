package com.example.pertemuan14.insert.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan14.Repository.RepositoryMhs
import com.example.pertemuan14.model.Mahasiswa
import kotlinx.coroutines.launch

class InsertViewModel(
    private val mhs: RepositoryMhs
): ViewModel(){

    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set
    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    // Memperbarui state berdasarkan input pengguna
    fun updateState(mahasiswaEvent: MahasiswaEvent){
        uiEvent = uiEvent.copy(
            insertUiEvent = mahasiswaEvent,
        )
    }

    // validasi data input pengguna
    fun validateFields(): Boolean{
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "Nim tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            jenis_kelamin = if (event.jenis_kelamin.isNotEmpty()) null else "Jenis_Kelamin tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "angkatan tidak boleh kosong",
            judul_skripsi = if (event.judul_skripsi.isNotEmpty()) null else "Judul Skripsi tidak boleh kosong",
            dsn_pembimbing1 = if (event.dsn_pembimbing1.isNotEmpty()) null else "Dosen Pembimbing1 tidak boleh kosong",
            dsn_pembimbing2 = if (event.dsn_pembimbing2.isNotEmpty()) null else "Dosen Pembimbing2 tidak boleh kosong",
        )

        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertMhs(){
        if (validateFields()) {
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    mhs.insertMhs(uiEvent.insertUiEvent.toMhsModel())
                    uiState = FormState.Success("Data berhasil disimpan")
                } catch (e: Exception){
                    uiState = FormState.Error("Data gagal disimpan")
                }
            }
        }else {
            uiState = FormState.Error("Data tidak valid")
        }
    }

    fun resetForm(){
        uiEvent = InsertUiState()
        uiState = FormState.Idle
    }

    fun resetSnackBarMessage(){
        uiState = FormState.Idle
    }
}
sealed class FormState {
    object Idle : FormState()
    object Loading : FormState()
    data class Success (val message: String) : FormState()
    data class Error (val message: String) : FormState()
}

data class InsertUiState (
    val insertUiEvent: MahasiswaEvent = MahasiswaEvent (),
    val isEntryValid: FormErrorState = FormErrorState(),
)

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val alamat: String? = null,
    val jenis_kelamin: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null,
    val judul_skripsi: String? = null,
    val dsn_pembimbing1: String? = null,
    val dsn_pembimbing2: String? = null
) {
    fun isValid() : Boolean {
        return nim == null && nama == null && alamat == null && jenis_kelamin == null
                && kelas == null && angkatan == null && judul_skripsi == null && dsn_pembimbing1 == null && dsn_pembimbing2 == null
    }
}

// data class Variable yang menyimpan data input form
data class MahasiswaEvent(
    val nim: String="",
    val nama: String="",
    val alamat: String="",
    val jenis_kelamin: String="",
    val kelas: String="",
    val angkatan: String="",
    val judul_skripsi: String="",
    val dsn_pembimbing1: String="",
    val dsn_pembimbing2: String=""
)

// Menyimpan input form ke dalam entity
fun MahasiswaEvent.toMhsModel() : Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenis_kelamin = jenis_kelamin,
    kelas = kelas,
    angkatan = angkatan,
    judul_skripsi = judul_skripsi,
    dsn_pembimbing1 = dsn_pembimbing1,
    dsn_pembimbing2 = dsn_pembimbing2
)

