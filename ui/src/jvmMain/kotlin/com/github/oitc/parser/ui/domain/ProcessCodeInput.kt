package com.github.oitc.parser.ui.domain

import com.github.oitc.parser.generated.Parser
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.io.StringReader


object ProcessCodeInput : suspend (String) -> String {

    override suspend fun invoke(codeInput: String): String {
        val parser = Parser(StringReader(codeInput))

        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        parser.Start(outStream)
        return byteOutStream.toString()
    }
}
