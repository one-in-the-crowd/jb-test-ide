package com.github.oitc.parser.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.oitc.parser.ui.model.ErrorHighlight
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
            CodeInput(
                screenState.value.errorHighlight
            ) { newInput ->
                viewModel.onCodeInputUpdated(newInput)
            }
            ExecutionOutput(screenState.value.codeExecutionOutput)
        }
    }
}

@Composable
fun CodeInput(
    errorHighlight: ErrorHighlight?,
    onEval: (String) -> Unit
) {
    var inputValue by remember { mutableStateOf("") }

    var visualTransformation by remember { mutableStateOf(VisualTransformation.None) }
    visualTransformation = errorHighlight?.run {
        ErrorHighlightTransformation(
            color = MaterialTheme.colors.error,
            errorPosition = start to end
        )
    } ?: VisualTransformation.None

    TextField(
        value = inputValue,
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight(fraction = 0.6f),
        onValueChange = {
            inputValue = it
            onEval(inputValue)
        },
        visualTransformation = visualTransformation
    )
}


@Composable
fun ExecutionOutput(value: String) {
    Text(text = value)
}


private class ErrorHighlightTransformation(
    val color: Color,
    val errorPosition: Pair<Int, Int>? = null
) : VisualTransformation {
    override fun filter(text: AnnotatedString) = TransformedText(
        text = errorPosition?.let { (start, end) ->
            createAnnotatedString(text, color, start, end)
        } ?: text,
        offsetMapping = OffsetMapping.Identity
    )

    private fun createAnnotatedString(
        string: AnnotatedString,
        color: Color,
        start: Int,
        end: Int
    ): AnnotatedString = buildAnnotatedString {
        append(string)
        addStyle(
            style = SpanStyle(
                color = color,
                textDecoration = TextDecoration.Underline
            ),
            start = start,
            end = end
        )
    }
}