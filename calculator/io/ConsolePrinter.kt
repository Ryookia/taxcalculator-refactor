package calculator.io

import calculator.io.`interface`.OutputPrinter

class ConsolePrinter: OutputPrinter {

    override fun showMessage(message: String) {
        println(message)
    }
}