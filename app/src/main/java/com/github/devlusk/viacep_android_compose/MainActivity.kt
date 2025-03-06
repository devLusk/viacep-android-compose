package com.github.devlusk.viacep_android_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.devlusk.viacep_android_compose.compose.CepInputField
import com.github.devlusk.viacep_android_compose.ui.theme.ViacepandroidcomposeTheme

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
    var cep by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            CepInputField(
                value = cep,
                onValueChange = { cep = it },
                label = "CEP",
                placeHolder = "00000-000"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = {}
            ) {
                Text("SEARCH CEP")
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