package com.github.oitc.parser.data

sealed class ExpressionValue<T>(val value: T) {

    abstract operator fun plus(inc: ExpressionValue<*>): ExpressionValue<*>
    abstract operator fun minus(inc: ExpressionValue<*>): ExpressionValue<*>
    abstract operator fun times(inc: ExpressionValue<*>): ExpressionValue<*>
    abstract operator fun div(inc: ExpressionValue<*>): ExpressionValue<*>

    class ExValInt(value: Int) : ExpressionValue<Int>(value) {
        override fun plus(inc: ExpressionValue<*>): ExValInt = when (inc) {
            is ExValInt ->
                ExValInt(value + inc.value)

            is ExValDouble ->
                ExValInt(value + inc.value.toInt())

            else ->
                throw UnsupportedOperationException("Has different type")
        }

        override fun minus(inc: ExpressionValue<*>): ExValInt = when (inc) {
            is ExValInt ->
                ExValInt(value - inc.value)

            is ExValDouble ->
                ExValInt(value - inc.value.toInt())

            else ->
                throw UnsupportedOperationException("Has different type")
        }

        override fun times(inc: ExpressionValue<*>): ExValInt = when (inc) {
            is ExValInt ->
                ExValInt(value * inc.value)

            is ExValDouble ->
                ExValInt(value * inc.value.toInt())

            else ->
                throw UnsupportedOperationException("Has different type")
        }

        override fun div(inc: ExpressionValue<*>): ExValInt = when (inc) {
            is ExValInt ->
                ExValInt(value / inc.value)

            is ExValDouble ->
                ExValInt(value / inc.value.toInt())

            else ->
                throw UnsupportedOperationException("Has different type")
        }
    }

    class ExValDouble(value: Double) : ExpressionValue<Double>(value) {

        override fun plus(inc: ExpressionValue<*>): ExValDouble = when (inc) {
            is ExValDouble ->
                ExValDouble(value + inc.value)

            is ExValInt ->
                ExValDouble(value + inc.value.toDouble())

            else ->
                throw UnsupportedOperationException("Has different type")
        }

        override fun minus(inc: ExpressionValue<*>): ExValDouble = when (inc) {
            is ExValDouble ->
                ExValDouble(value - inc.value)

            is ExValInt ->
                ExValDouble(value - inc.value.toDouble())

            else ->
                throw UnsupportedOperationException("Has different type")
        }

        override fun times(inc: ExpressionValue<*>): ExValDouble = when (inc) {
            is ExValDouble ->
                ExValDouble(value * inc.value)

            is ExValInt ->
                ExValDouble(value * inc.value.toDouble())

            else ->
                throw UnsupportedOperationException("Has different type")
        }

        override fun div(inc: ExpressionValue<*>): ExValDouble = when (inc) {
            is ExValDouble ->
                ExValDouble(value / inc.value)

            is ExValInt ->
                ExValDouble(value / inc.value.toDouble())

            else ->
                throw UnsupportedOperationException("Has different type")
        }
    }

    class ExValSequence(value: List<Int>) : ExpressionValue<List<Int>>(value) {

        override fun plus(inc: ExpressionValue<*>): ExValSequence {
            if (inc is ExValSequence) {
                val newList = value.toMutableList().apply { addAll(inc.value) }
                return ExValSequence(newList)
            } else {
                throw UnsupportedOperationException("Has different type")
            }
        }

        override fun minus(inc: ExpressionValue<*>): ExpressionValue<*> {
            throw UnsupportedOperationException("ExValSequence minus")
        }

        override fun times(inc: ExpressionValue<*>): ExpressionValue<*> {
            throw UnsupportedOperationException("ExValSequence times")
        }

        override fun div(inc: ExpressionValue<*>): ExpressionValue<*> {
            throw UnsupportedOperationException("ExValSequence div")
        }
    }
}
