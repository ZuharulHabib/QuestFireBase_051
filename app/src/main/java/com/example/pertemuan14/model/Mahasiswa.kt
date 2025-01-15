package com.example.pertemuan14.model

data class Mahasiswa(
    val nim: String,
    val nama: String,
    val alamat: String,
    val jenisKelamin: String,
    val kelas: String,
    val angkatan: String,
    val judul_skripsi : String,
    val dsn_pembimbing1: String,
    val dsn_pembimbing2: String,
    ){
    constructor() : this("","","","","","","","","")

}