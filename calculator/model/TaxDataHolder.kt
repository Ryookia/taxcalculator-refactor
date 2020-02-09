package calculator.model

class TaxDataHolder {
    var income = 0.0
    var basis = 0.0
    var reducedBasis = 0.0
    lateinit var contractType: ContractType

    // social taxes
    var pensionTaxValue = 0.0 // 9,76% of the income
    var disabledTaxValue = 0.0 // 1,5% of the income
    var illnessTaxValue = 0.0 // 2,45% of the income

    // health taxes
    var firstHealthTaxValue = 0.0 // 9% of the incomeCost
    var secondHealthTaxValue = 0.0 // 7,75 % of the incomeCost

    var advancedTaxValue = 0.0 // income tax (18%) advance
    var advanceTaxOfficeTaxValue = 0.0
    var advanceTaxOfficeTaxRounded = 0.0
}