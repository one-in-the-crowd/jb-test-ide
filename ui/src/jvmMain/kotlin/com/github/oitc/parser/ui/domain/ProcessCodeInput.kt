package com.github.oitc.parser.ui.domain

import com.github.oitc.parser.generated.Parser
import java.io.*


object ProcessCodeInput :  (String) -> String {

    override  fun invoke(codeInput: String): String {
        val parser = Parser(StringReader(codeInput))

        val byteOutStream = ByteArrayOutputStream()
        val outStream = PrintStream(byteOutStream)
        parser.Start(outStream)

        return byteOutStream.toString()
    }
}
