package com.github.oitc.parser

import com.github.oitc.parser.ext.withEndLine
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

internal class OperatorPowTest {

    @Test
    fun `when correct arguments - then calculate result`() {
        // Given
        val input = "2 ^ 3"
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        parser.Start(outStream)

        // Then
        val outputStr = byteOutStream.toString()
        assertEquals("8.0".withEndLine(), outputStr)
    }

    @Test
    fun `when first arg non num - then token mgr error`() {
        // Given
        val input = "qwe ^ 11"
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)

        assertThrows<TokenMgrError> {
            parser.Start(outStream)
        }
    }

    @Test
    fun `when second arg non num - then token mgr error`() {
        // Given
        val input = "12 ^ q"
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)

        assertThrows<TokenMgrError> {
            parser.Start(outStream)
        }
    }

    @Test
    fun `when first arg absent - then parse exception`() {
        // Given
        val input = " ^ 12"
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)

        assertThrows<ParseException> {
            parser.Start(outStream)
        }
    }

    @Test
    fun `when second arg absent - then parse exception`() {
        // Given
        val input = "12 ^ "
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)

        assertThrows<ParseException> {
            parser.Start(outStream)
        }
    }
}
