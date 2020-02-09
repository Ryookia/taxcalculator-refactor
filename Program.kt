import calculator.CalculatorService
import calculator.model.TaxDataHolder
import di.ClassProvider
import legacy.TaxCalculator

fun main() {
    test()
}

fun test(){
    CalculatorService(
            ClassProvider()).executeCalculationProcess(
            TaxDataHolder()
    )
    TaxCalculator.main(arrayOf())
}