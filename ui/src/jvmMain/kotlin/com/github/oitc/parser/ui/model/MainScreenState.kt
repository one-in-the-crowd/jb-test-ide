package com.github.oitc.parser.ui.model

data class MainScreenState(
    val codeExecutionOutput: String,
    val errorHighlight: ErrorHighlight? = null
)

data class ErrorHighlight(
    val start: Int = -1,
    val end: Int = -1
)
