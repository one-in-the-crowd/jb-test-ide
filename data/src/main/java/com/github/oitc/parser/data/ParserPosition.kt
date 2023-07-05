package com.github.oitc.parser.data

data class ParserPosition(
    val bufPos: Int,
    val line: Int,
    val column: Int,
)
