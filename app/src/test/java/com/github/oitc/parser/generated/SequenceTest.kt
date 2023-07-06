package com.github.oitc.parser.generated

import com.github.oitc.parser.generated.ext.withEndLine
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals

class SequenceTest {

    @Test
    fun `when out correct sequence - then print it`() {
        // Given
        val input = "out {0, 5}"
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        parser.Start(outStream)

        // Then
        val outputStr = byteOutStream.toString()
        assertEquals("[0, 1, 2, 3, 4, 5]".withEndLine(), outputStr)
    }

    @Test
    fun `when out sequence with a single item - then print it`() {
        // Given
        val input = "out {7, 7}"
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        parser.Start(outStream)

        // Then
        val outputStr = byteOutStream.toString()
        assertEquals("[7]".withEndLine(), outputStr)
    }

    @Test
    fun `when out sequence sum - then print it`() {
        // Given
        val input = "out {0, 1} + {8, 9}"
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        parser.Start(outStream)

        // Then
        val outputStr = byteOutStream.toString()
        assertEquals("[0, 1, 8, 9]".withEndLine(), outputStr)
    }

    @Test
    fun `when out sequence left more than right - then error`() {
        // Given
        val input = "out {9, 2}"
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        assertThrows<IllegalArgumentException>{ parser.Start(outStream) }

    }
}