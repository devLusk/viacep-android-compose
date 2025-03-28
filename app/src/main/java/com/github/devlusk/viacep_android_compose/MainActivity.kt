package com.github.devlusk.viacep_android_compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.devlusk.viacep_android_compose.compose.CepInputField
import com.github.devlusk.viacep_android_compose.data.AddressResponse
import com.github.devlusk.viacep_android_compose.ui.theme.ViacepandroidcomposeTheme
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViacepandroidcomposeTheme {
                ViacepSearch()
            }
        }
    }
}

@Composable
fun ViacepSearch(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var address by remember { mutableStateOf<AddressResponse?>(null) }
    var cepInput by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    fun getAddress() {
        Thread {
            val url = URL("https://viacep.com.br/ws/$cepInput/json/")

            cepInput = ""
            isLoading = true

            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"

                println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")

                val json = inputStream.bufferedReader().readText()

                address = Gson().fromJson(json, AddressResponse::class.java)
            }

            isLoading = false
        }.start()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            CepInputField(
                value = cepInput,
                onValueChange = { cepInput = it },
                label = "CEP",
                placeHolder = "00000-000"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if (cepInput.length < 8 || cepInput.length > 8) {
                        Toast.makeText(context, "Error: Invalid CEP", Toast.LENGTH_SHORT).show()
                    } else {
                        getAddress()
                    }
                }
            ) {
                Text("SEARCH CEP")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .background(Color.LightGray.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                if (isLoading == true) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                if (address !== null) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(text = "CEP: ${address?.cep ?: ""}")
                        Text(text = "Logradouro: ${address?.logradouro ?: ""}")
                        Text(text = "Bairro: ${address?.bairro ?: ""}")
                        Text(text = "Localidade: ${address?.localidade ?: ""}")
                        Text(text = "UF: ${address?.uf ?: ""}")
                        Text(text = "Estado: ${address?.estado ?: ""}")
                    }
                } else {
                    Text(
                        "Insert a CEP...",
                        Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ViacepSearchPreview() {
    ViacepandroidcomposeTheme {
        ViacepSearch()
    }
}