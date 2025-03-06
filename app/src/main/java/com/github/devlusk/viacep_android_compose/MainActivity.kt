package com.github.devlusk.viacep_android_compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.devlusk.viacep_android_compose.compose.CepInputField
import com.github.devlusk.viacep_android_compose.ui.theme.ViacepandroidcomposeTheme
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

    var cepInput by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    fun getAddress() {
        println("Antes da thread")
        Thread {
            println("Inicio da thread")
            val url = URL("https://viacep.com.br/ws/$cepInput/json/")
            var json: String = ""

            cepInput = ""
            isLoading = true

            with(url.openConnection() as HttpURLConnection) {
                requestMethod = "GET"  // optional default is GET

                println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")

                json = inputStream.bufferedReader().readText()

                println(json)
            }

            isLoading = false
            println("Fim da thread")
        }.start()
        println("Depois da thread")
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
                    if (cepInput.length < 8) {
                        Toast.makeText(context, "Error: Invalid CEP", Toast.LENGTH_SHORT).show()
                    } else {
                        getAddress()
                    }
                }
            ) {
                Text("SEARCH CEP")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (isLoading == true) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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