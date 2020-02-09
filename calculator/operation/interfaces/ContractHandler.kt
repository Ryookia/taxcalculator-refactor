package calculator.operation.interfaces

import calculator.io.`interface`.CalculatorEngine
import calculator.model.TaxDataHolder

interface ContractHandler {
    fun executeContractProcess(taxDataHolder: TaxDataHolder)
}