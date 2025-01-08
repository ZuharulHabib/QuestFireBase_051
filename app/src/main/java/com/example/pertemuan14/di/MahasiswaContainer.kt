package com.example.pertemuan14.di

import com.example.pertemuan14.Repository.NetworkRepositoryMhs
import com.example.pertemuan14.Repository.RepositoryMhs
import com.google.firebase.firestore.FirebaseFirestore


interface  AppContainer{
    val repositoryMhs: RepositoryMhs
}
class MahasiswaContainer: AppContainer{
    private val firestore = FirebaseFirestore.getInstance()
    override val repositoryMhs: RepositoryMhs by lazy {
        NetworkRepositoryMhs(firestore)
    }
}