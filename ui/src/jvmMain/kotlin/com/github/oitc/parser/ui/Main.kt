package com.github.oitc.parser.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


fun main() = application {

    val viewModel = ViewModel(CoroutineScope(Dispatchers.Default))
    Window(
        title = "Test IDE",
        onCloseRequest = ::exitApplication
    ) {
        App(viewModel)
    }
}

@Composable
@Preview
fun App(viewModel: ViewModel) {

    val screenState = viewModel.screenState.collectAsState()
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CodeInput { newInput ->
                viewModel.onCodeInputUpdated(newInput)
            }
            ExecutionOutput(screenState.value.codeExecutionOutput)
        }
    }
}

@Composable
fun CodeInput(onEval: (String) -> Unit) {
    var inputValue by remember { mutableStateOf("") }
    TextField(
        value = inputValue,
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(fraction = 0.6f),
        onValueChange = {
            inputValue = it
            onEval(inputValue)
        }
    )
}

@Composable
fun ExecutionOutput(value: String) {
    Text(
        text = value
    )
}
