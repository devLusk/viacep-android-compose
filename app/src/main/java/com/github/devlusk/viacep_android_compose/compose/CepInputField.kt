package com.github.devlusk.viacep_android_compose.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.devlusk.viacep_android_compose.ui.theme.ViacepandroidcomposeTheme

@Composable
fun CepInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeHolder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        label = { Text(label) },
        placeholder = { Text(placeHolder) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showSystemUi = true)
@Composable
private fun CepInputFieldPreview() {
    ViacepandroidcomposeTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CepInputField(
                value = "",
                onValueChange = {},
                label = "CEP",
                placeHolder = "00000-000"
            )
        }
    }
}