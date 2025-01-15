package com.example.pertemuan14.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object DestinasiHome: DestinasiNavigasi{
    override val route: String = "home"
    override val titleRes: String = "Home Mahasiswa"
}

object DestinasiInsert: DestinasiNavigasi{
    override val route: String = "insert"
    override val titleRes: String = "Insert Mahasiswa"
}

object DestinasiDetail : DestinasiNavigasi {
    override val route= "detail"
    override val titleRes = "Detail Mahasiswa"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}