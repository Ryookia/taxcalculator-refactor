package calculator

import calculator.io.`interface`.InputReader
import calculator.io.`interface`.OutputPrinter
import calculator.model.ContractType
import calculator.model.TaxDataHolder
import calculator.operation.interfaces.ContractHandler
import calculator.res.StringResources
import di.ClassProvider

class CalculatorService(
        private val classProvider: ClassProvider
) {

    private val outputPrinter = classProvider.outputPrinter
    private val inputReader = classProvider.inputReader
    private lateinit var contractHandler: ContractHandler

    fun executeCalculationProcess(taxDataHolder: TaxDataHolder){
        try {
            fetchDataFromUser(taxDataHolder)
        } catch (exception: Exception){
            sendOutput(StringResources.WARNING_INCORRECT_VALUE.string)
            System.err.println(exception)
            return
        }

        classProvider.provideContractHandler(taxDataHolder.contractType)?.let {
            contractHandler = it
            contractHandler.executeContractProcess(taxDataHolder)
        } ?: kotlin.run {
            outputPrinter.showMessage(StringResources.WARNING_UNKNOWN_CONTRACT.string)
        }
    }

    private fun fetchDataFromUser(taxDataHolder: TaxDataHolder){
        outputPrinter.showMessage(
                StringResources.PROMPT_PROVIDE_AMOUNT.string)
        taxDataHolder.income = inputReader.readline().toDouble()
        outputPrinter.showMessage(
                StringResources.PROMPT_PROVIDE_CONTRACT_TYPE.string)
        taxDataHolder.contractType = ContractType.convertContractType(
                inputReader.readline()[0].toString()
        )
    }

    private fun sendOutput(message: String){
        outputPrinter.showMessage(message)
    }
}