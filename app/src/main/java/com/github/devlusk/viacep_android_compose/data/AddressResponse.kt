package com.github.devlusk.viacep_android_compose.data

data class AddressResponse(
    val cep: String? = null,
    val logradouro: String? = null,
    val complemento: String? = null,
    val unidade: String? = null,
    val bairro: String? = null,
    val localidade: String? = null,
    val uf: String? = null,
    val estado: String? = null,
    val regiao: String? = null,
    val ibge: String? = null,
    val gia: String? = null,
    val ddd: String? = null,
    val siafi: String? = null
)