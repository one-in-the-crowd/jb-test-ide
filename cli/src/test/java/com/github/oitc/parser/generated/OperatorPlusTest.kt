package com.github.oitc.parser.generated

import com.github.oitc.parser.generated.ext.withEndLine
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

internal class OperatorPlusTest {

    @Test
    fun `when correct arguments - then calculate result`() {
        // Given
        val input = "out 22 + 11"
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        parser.Start(outStream)

        // Then
        val outputStr = byteOutStream.toString()
        assertEquals("33".withEndLine(), outputStr)
    }

    @Test
    fun `when first arg non num - then error`() {
        // Given
        val input = "out qwe + 11"
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

    @Test
    fun `when second arg non num - then error`() {
        // Given
        val input = "out 12 + q"
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

    @Test
    fun `when first arg absent - then parse exception`() {
        // Given
        val input = "out + 12"
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
        val input = "out 12 + "
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
