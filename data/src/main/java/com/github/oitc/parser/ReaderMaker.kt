package com.github.oitc.parser

import java.io.*

object ReaderMaker : (InputStream) -> Reader {
    override fun invoke(inputStream: InputStream): Reader {
        val inputStream: InputStream = inputStream
        val buffer = ByteArrayOutputStream()

        val data = ByteArray(4096)
        var readAmount = 0
        try {
            while (inputStream.read(data, 0, data.size).also { readAmount = it } != -1) {
                buffer.write(data, 0, readAmount)
            }
        } catch (ex: IOException) {
            println("Error while reading inputStream")
        }

        return StringReader(buffer.toString())
    }
}
