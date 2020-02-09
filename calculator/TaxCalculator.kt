package calculator

import calculator.io.`interface`.CalculatorEngine
import calculator.model.ContractType
import calculator.model.TaxConfig
import calculator.model.TaxDataHolder

class TaxCalculator(
        private val config: TaxConfig
): CalculatorEngine {

    override fun calculateAdvanceTaxOffice(holder: TaxDataHolder): Double {
        return holder.advancedTaxValue - holder.secondHealthTaxValue - config.exemptedValue
    }

    override fun calculatePensionTax(holder: TaxDataHolder): Double {
        return holder.income * config.taxPension
    }

    override fun calculateDisabledTax(holder: TaxDataHolder): Double {
        return holder.income * config.taxDisabled
    }

    override fun calculateIllnessTax(holder: TaxDataHolder): Double {
        return holder.income * config.taxIllness
    }

    override fun calculateBase(holder: TaxDataHolder): Double {
        return holder.income - holder.pensionTaxValue - holder.disabledTaxValue - holder.illnessTaxValue
    }

    override fun calculateFirstHealthInsurance(holder: TaxDataHolder): Double {
        return holder.basis * config.taxHealthFirst
    }

    override fun calculateSecondHealthInsurance(holder: TaxDataHolder): Double {
        return holder.basis * config.taxHealthSecond
    }

    override fun calculateBasis(holder: TaxDataHolder, secondBasis: Double): Double {
        return secondBasis * config.advancedTax
    }

    override fun calculateBasisReducedByIncomeTax(holder: TaxDataHolder): Double {
        return holder.basis - provideIncomeCost(holder)
    }

    override fun calculateExemptedValue(holder: TaxDataHolder): Double {
        return holder.advancedTaxValue - config.exemptedValue
    }

    override fun calculateSalary(holder: TaxDataHolder): Double {
        return holder.income - (holder.pensionTaxValue +
                holder.disabledTaxValue +
                holder.illnessTaxValue +
                holder.firstHealthTaxValue +
                holder.advanceTaxOfficeTaxRounded)
    }

    override fun provideIncomeCost(holder: TaxDataHolder): Double {
        return when (holder.contractType) {
            ContractType.ORDINARY -> config.incomeCost
            ContractType.CIVIL -> (holder.basis * config.civilIncomeValue)
            else -> 0.0
        }
    }
}