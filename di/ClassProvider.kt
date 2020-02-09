package di

import calculator.TaxCalculator
import calculator.contract.CivilContractHandler
import calculator.contract.OrdinaryContractHandler
import calculator.io.ConsoleReader
import calculator.io.ConsolePrinter
import calculator.io.`interface`.CalculatorEngine
import calculator.io.`interface`.InputReader
import calculator.io.`interface`.OutputPrinter
import calculator.model.ContractType
import calculator.model.TaxConfig
import calculator.operation.interfaces.ContractHandler
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.text.DecimalFormat
import java.text.Format

class ClassProvider {

    val inputStreamReader: InputStreamReader by lazy { provideInputStreamReader() }
    val inputReader: InputReader by lazy { provideInputReader(inputStreamReader) }
    val outputPrinter: OutputPrinter by lazy { provideOutputPrinter() }
    val defaultFormater: Format by lazy { provideDefaultFormater() }
    val preciseFormater: Format by lazy { providePreciseFormater() }

    private fun provideInputReader(inputStreamReader: InputStreamReader): InputReader {
        return ConsoleReader(
                BufferedReader(inputStreamReader))
    }

    private fun provideInputStreamReader(): InputStreamReader {
        return InputStreamReader(System.`in`)
    }

    private fun provideCalculatorEngine(taxConfig: TaxConfig): CalculatorEngine {
        return TaxCalculator(taxConfig)
    }

    private fun provideOutputPrinter(): OutputPrinter {
        return ConsolePrinter()
    }

    private fun provideTaxConfig(contractType: ContractType): TaxConfig? {
        return when (contractType) {
            ContractType.ORDINARY -> TaxConfig()
            ContractType.CIVIL -> TaxConfig(exemptedValue = 0.0)
            else -> null
        }
    }

    fun provideContractHandler(type: ContractType): ContractHandler? {
        val config = provideTaxConfig(type) ?: return null
        val calculatorEngine = provideCalculatorEngine(config)
        return when (type) {
            ContractType.ORDINARY -> {
                OrdinaryContractHandler(
                        calculatorEngine,
                        config,
                        outputPrinter,
                        defaultFormater,
                        preciseFormater)
            }
            ContractType.CIVIL -> {
                CivilContractHandler(calculatorEngine,
                        config,
                        outputPrinter,
                        defaultFormater,
                        preciseFormater)
            }
            ContractType.UNKNOWN -> null
        }
    }

    private fun provideDefaultFormater(): Format {
        return DecimalFormat("#")
    }

    private fun providePreciseFormater(): Format {
        return DecimalFormat("#.00")
    }
}