package com.github.oitc.parser

import com.github.oitc.parser.ext.withEndLine
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

internal class CommandPrintTest {

    @Test
    fun `when argument correct - then print argument`() {
        // Given
        val input = "print \"foo bar\""
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        parser.Start(outStream)

        // Then
        val outputStr = byteOutStream.toString()
        assertEquals("foo bar".withEndLine(), outputStr)
    }

    @Test
    fun `when argument is empty string - then print argument`() {
        // Given
        val input = "print \"\""
            .withEndLine()
            .byteInputStream()

        // When
        val parser = Parser(input)
        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        parser.Start(outStream)

        // Then
        val outputStr = byteOutStream.toString()
        assertEquals("".withEndLine(), outputStr)
    }

    @Test
    fun `when arg is not string - then token mgr error`() {
        // Given
        val input = "print qwe123"
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
    fun `when arg absent - then parse exception`() {
        // Given
        val input = "print"
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
