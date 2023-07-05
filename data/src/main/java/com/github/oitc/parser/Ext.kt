package com.github.oitc.parser

import com.github.oitc.parser.data.ExpressionValue
import java.lang.IllegalArgumentException

fun getDoubleValue(input: ExpressionValue<*>): Double = when (input) {
    is ExpressionValue.ExValInt ->
        input.value.toDouble()

    is ExpressionValue.ExValDouble ->
        input.value

    else ->
        throw IllegalArgumentException("Unable to get double value")
}

fun getIntValue(input: ExpressionValue<*>): Int = when (input) {
    is ExpressionValue.ExValInt ->
        input.value

    else ->
        throw IllegalArgumentException("Unable to get int value")
}
