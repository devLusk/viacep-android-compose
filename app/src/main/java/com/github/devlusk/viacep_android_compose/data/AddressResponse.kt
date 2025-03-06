package com.github.devlusk.viacep_android_compose.data

data class AddressResponse(
    val cep: String?,
    val logradouro: String?,
    val complemento: String?,
    val unidade: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?,
    val estado: String?,
    val regiao: String?,
    val ibge: String?,
    val gia: String?,
    val ddd: String?,
    val siafi: String?
)