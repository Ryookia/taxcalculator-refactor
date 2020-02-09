package calculator.model

data class TaxConfig(
        var taxPension: Double = 0.0976,
        var taxDisabled: Double = 0.015,
        var taxIllness: Double = 0.0245,
        var incomeCost: Double = 111.25,
        var taxHealthFirst: Double = 0.09,
        var taxHealthSecond: Double = 0.0775,
        var advancedTax: Double = 0.18,
        var exemptedValue: Double = 46.33,
        var civilIncomeValue: Double = 0.2
)