package com.example.pertemuan14.insert.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan14.Repository.RepositoryMhs
import com.example.pertemuan14.model.Mahasiswa
import com.example.pertemuan14.navigation.DestinasiDetail
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val RepositoryMhs: RepositoryMhs
): ViewModel(){
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    init {
        getMahasiswaByNim()
    }

    fun getMahasiswaByNim(){
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            RepositoryMhs.getAllMhs()
                .onStart {
                    detailUiState = DetailUiState.Loading
                }
                .catch {
                    detailUiState = DetailUiState.Error(it)
                }
                .collect{
                        mahasiswa ->
                    detailUiState = DetailUiState.Success(mahasiswa)
                }
        }
    }
}

sealed class DetailUiState {
    data class Success(val Mahasiswa: List<Mahasiswa>) : DetailUiState()
    data class Error (val message : Throwable): DetailUiState()
    object Loading : DetailUiState()
}