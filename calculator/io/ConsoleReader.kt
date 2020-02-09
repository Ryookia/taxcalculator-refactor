package calculator.io

import calculator.io.`interface`.InputReader
import java.io.BufferedReader
import java.io.InputStreamReader

class ConsoleReader(val bufferReader: BufferedReader): InputReader{

    override fun readline(): String {
        return bufferReader.readLine()
    }
}