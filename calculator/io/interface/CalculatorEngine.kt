package calculator.io.`interface`

import calculator.model.TaxDataHolder

interface CalculatorEngine {
    fun calculateAdvanceTaxOffice(holder: TaxDataHolder): Double
    fun calculateBase(holder: TaxDataHolder): Double
    fun calculatePensionTax(holder: TaxDataHolder): Double
    fun calculateDisabledTax(holder: TaxDataHolder): Double
    fun calculateIllnessTax(holder: TaxDataHolder): Double
    fun calculateFirstHealthInsurance(holder: TaxDataHolder): Double
    fun calculateSecondHealthInsurance(holder: TaxDataHolder): Double
    fun calculateBasis(holder: TaxDataHolder, secondBasis: Double): Double
    fun calculateBasisReducedByIncomeTax(holder: TaxDataHolder): Double
    fun calculateExemptedValue(holder: TaxDataHolder): Double
    fun calculateSalary(holder: TaxDataHolder): Double
    fun provideIncomeCost(holder: TaxDataHolder): Double
}