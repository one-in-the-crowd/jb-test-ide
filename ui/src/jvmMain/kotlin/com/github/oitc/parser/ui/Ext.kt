package com.github.oitc.parser.ui

import com.github.oitc.parser.generated.ParseException
import com.github.oitc.parser.ui.model.ErrorHighlight

internal fun ParseException.toErrorHighlight(
    codeInput: String
): ErrorHighlight = with(currentToken) {
    codeInput.toErrorHighlight(beginLine - 1, beginColumn)
}

internal fun String.toErrorHighlight(lineIndex: Int, columnIndex: Int): ErrorHighlight {
    var currentLineIndex = 0
    var index = 0
    while (currentLineIndex != lineIndex) {
        index += substring(index).indexOf(System.lineSeparator()) + 1
        currentLineIndex++
    }

    val errorStartIndex = index + columnIndex
    val errorEndIndex = if (index == 0) {
        indexOf(System.lineSeparator())
            .takeIf { it != -1 }
            ?: lastIndex + 1
    } else {
        errorStartIndex + (
                substring(errorStartIndex)
                    .indexOf(System.lineSeparator())
                    .takeIf { it != -1 }
                    ?: lastIndex + 1
                )
    }

    return ErrorHighlight(errorStartIndex, errorEndIndex)
}