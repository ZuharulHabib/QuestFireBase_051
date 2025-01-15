package com.example.pertemuan14.insert.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan14.MahasiswaApplications

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiMhs().container.repositoryMhs)
        }
        initializer {
            InsertViewModel(aplikasiMhs().container.repositoryMhs)
        }
        initializer {
            DetailViewModel(createSavedStateHandle(),aplikasiMhs().container.repositoryMhs)
        }
    }
}
fun CreationExtras.aplikasiMhs(): MahasiswaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)