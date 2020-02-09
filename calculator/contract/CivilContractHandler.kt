package calculator.contract

import calculator.io.`interface`.CalculatorEngine
import calculator.io.`interface`.OutputPrinter
import calculator.model.TaxConfig
import calculator.model.TaxDataHolder
import calculator.operation.interfaces.ContractHandler
import calculator.res.StringResources
import java.text.Format

class CivilContractHandler(
        private val calculatorEngine: CalculatorEngine,
        private val taxConfig: TaxConfig,
        private val outputPrinter: OutputPrinter,
        private val defaultFormater: Format,
        private val preciseFormater: Format
) : ContractHandler {
    override fun executeContractProcess(taxDataHolder: TaxDataHolder) {
        sendMessage(StringResources.INFO_CIVIL_CONTRACT.string)
        sendMessage(
                StringResources.FORMAT_BASIS_TAXES,
                taxDataHolder.income.toString())

        calculateResults(taxDataHolder)

        sendMessage(
                StringResources.FORMAT_PENSION_TAX,
                preciseFormater,
                taxDataHolder.pensionTaxValue)
        sendMessage(StringResources.FORMAT_DISABILITY_TAX,
                preciseFormater,
                taxDataHolder.disabledTaxValue)
        sendMessage(StringResources.FORMAT_ILLNESS_INSURANCE,
                preciseFormater,
                taxDataHolder.illnessTaxValue)
        sendMessage(StringResources.FORMAT_HEALTH_INSURANCE,
                taxDataHolder.basis.toString())

        sendMessage(
                StringResources.FORMAT_HEALTH_INSURANCE_PERCENT,
                (taxConfig.taxHealthFirst * 100).toString(),
                preciseFormater.format(taxDataHolder.firstHealthTaxValue),
                (taxConfig.taxHealthSecond * 100).toString(),
                preciseFormater.format(taxDataHolder.secondHealthTaxValue)
        )

        sendMessage(StringResources.FORMAT_CONSTANT_TAX_COST,
                preciseFormater.format(calculatorEngine.provideIncomeCost(taxDataHolder)).toString())

        sendMessage(StringResources.FORMAT_TAX_BASIS_ROUNDED,
                taxDataHolder.reducedBasis.toString(),
                defaultFormater.format(taxDataHolder.reducedBasis))

        sendMessage(
                StringResources.FORMAT_ADVANCE_INCOME_TAX,
                (taxConfig.advancedTax * 100).toString(),
                taxDataHolder.advancedTaxValue.toString()
        )
        sendMessage(StringResources.FORMAT_EXEMPTED_TAX,
                preciseFormater.format(calculatorEngine.calculateExemptedValue(taxDataHolder)))

        sendMessage(StringResources.FORMAT_ADVANCE_TAX_OFFICE_ROUNDED,
                preciseFormater.format(taxDataHolder.advanceTaxOfficeTaxValue),
                defaultFormater.format(taxDataHolder.advanceTaxOfficeTaxValue))
        sendMessage("")
        sendMessage(StringResources.FORMAT_NET_SALARY,
                preciseFormater.format(calculatorEngine.calculateSalary(taxDataHolder)))

    }

    private fun calculateResults(taxDataHolder: TaxDataHolder){
        taxDataHolder.pensionTaxValue = calculatorEngine.calculatePensionTax(taxDataHolder)
        taxDataHolder.disabledTaxValue = calculatorEngine.calculateDisabledTax(taxDataHolder)
        taxDataHolder.illnessTaxValue = calculatorEngine.calculateIllnessTax(taxDataHolder)
        taxDataHolder.basis = calculatorEngine.calculateBase(taxDataHolder)

        taxDataHolder.firstHealthTaxValue = calculatorEngine.calculateFirstHealthInsurance(taxDataHolder)
        taxDataHolder.secondHealthTaxValue =  calculatorEngine.calculateSecondHealthInsurance(taxDataHolder)

        taxDataHolder.reducedBasis =
                preciseFormater.format(
                        calculatorEngine.calculateBasisReducedByIncomeTax(taxDataHolder)).toDouble()

        taxDataHolder.advancedTaxValue =
                preciseFormater.format(
                        calculatorEngine.calculateBasis(taxDataHolder,
                                defaultFormater.format(taxDataHolder.reducedBasis).toDouble())).toDouble()

        taxDataHolder.advanceTaxOfficeTaxValue = calculatorEngine.calculateAdvanceTaxOffice(taxDataHolder)

        taxDataHolder.advanceTaxOfficeTaxRounded = defaultFormater.format(
                taxDataHolder.advanceTaxOfficeTaxValue).toDouble()
    }

    private fun sendMessage(message: String) {
        outputPrinter.showMessage(message)
    }

    private fun sendMessage(resource: StringResources, vararg arguments: String) {
        outputPrinter.showMessage(
                String.format(resource.string, *arguments)
        )
    }

    private fun sendMessage(resource: StringResources, format: Format, argument: Double) {
        sendMessage(
                String.format(resource.string,
                        format.format(argument))
        )
    }
}