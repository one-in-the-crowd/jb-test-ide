package com.github.oitc.parser

import com.github.oitc.parser.ext.withEndLine
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

internal class IdentTest {

    @Test
    fun `when input is expression - execute expression`() {
        // Given
        val input =
            """
                var num1 = 11
                var num2 = 22
                out num1 + num2
            """.trimIndent()
                .withEndLine()
                .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        parser.Start(outStream)

        // Then
        val outputStr = byteOutStream.toString()
        assertEquals("33.0".withEndLine(), outputStr)
    }

    @Test
    fun `when input is known ident - execute expression`() {
        // Given
        val input =
            """
                var num1 = 11
                out num1
            """.trimIndent()
                .withEndLine()
                .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        parser.Start(outStream)

        // Then
        val outputStr = byteOutStream.toString()
        assertEquals("11.0".withEndLine(), outputStr)
    }

    @Test
    fun `when input has unknown var - then error`() {
        // Given
        val input =
            """
            out num1
            """.trimIndent()
                .withEndLine()
                .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        assertThrows<NullPointerException> {
            parser.Start(outStream)
        }
    }

}