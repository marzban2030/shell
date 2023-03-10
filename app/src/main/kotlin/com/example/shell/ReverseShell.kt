package com.example.shell

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.InputStream
import java.util.concurrent.TimeUnit

suspend fun shell(cmd: String): String {
    val process = Runtime.getRuntime().exec(cmd)
    val result =  process.read()
    return result
}

@RequiresApi(Build.VERSION_CODES.O)
private fun Process.read(): String {
    return StringBuffer().let { buf ->
        buf.readStream(errorStream)
        buf.readStream(inputStream)
        waitFor()
        buf.appendln("")
    }.toString()
}

private fun StringBuffer.readStream(stream: InputStream) = stream.bufferedReader().useLines { it.forEach { ln -> this.appendln(ln) } }
