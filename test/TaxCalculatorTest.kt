package test

import calculator.TaxCalculator
import calculator.model.ContractType
import calculator.model.TaxConfig
import calculator.model.TaxDataHolder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxCalculatorTest{

    private var holder: TaxDataHolder = mock()
    private var taxConfig: TaxConfig = mock()


    lateinit var calculator: TaxCalculator

    @BeforeAll
    fun init(){
        calculator = TaxCalculator(taxConfig)
    }

    @Test
    fun `should calculate correct advance tax office from advancedTaxValue`(){
        whenever(taxConfig.exemptedValue).thenReturn(46.33)
        whenever(holder.advancedTaxValue).thenReturn(135.36)
        whenever(holder.secondHealthTaxValue).thenReturn(66.87)

        Assertions.assertEquals(22.16, calculator.calculateAdvanceTaxOffice(holder), 0.01)
    }

    @Test
    fun `should calculate correct pension tax from 1000 income`(){
        whenever(holder.income).thenReturn(1000.0)
        whenever(taxConfig.taxPension).thenReturn(0.0976)

        Assertions.assertEquals(97.60, calculator.calculatePensionTax(holder), 0.01)
    }

    @Test
    fun `should calculate disability tax basis from 1000 income`(){
        whenever(holder.income).thenReturn(1000.0)
        whenever(taxConfig.taxDisabled).thenReturn(0.015)

        Assertions.assertEquals(15.00, calculator.calculateDisabledTax(holder), 0.01)
    }

    @Test
    fun `should calculate illness tax basis from 1000 income`(){
        whenever(holder.income).thenReturn(1000.0)
        whenever(taxConfig.taxDisabled).thenReturn(0.0245)

        Assertions.assertEquals(24.50, calculator.calculateDisabledTax(holder), 0.01)
    }

    @Test
    fun `should calculate health insurance basis from 1000 income`(){
        whenever(holder.income).thenReturn(1000.0)
        whenever(holder.pensionTaxValue).thenReturn(97.6)
        whenever(holder.disabledTaxValue).thenReturn(15.0)
        whenever(holder.illnessTaxValue).thenReturn(24.5)

        Assertions.assertEquals(862.9, calculator.calculateBase(holder), 0.01)
    }

    @Test
    fun `should calculate first health tax from 862,9 basis`(){
        whenever(holder.basis).thenReturn(862.9)
        whenever(taxConfig.taxHealthFirst).thenReturn(0.09)

        Assertions.assertEquals(77.66, calculator.calculateFirstHealthInsurance(holder), 0.01)
    }

    @Test
    fun `should calculate second health tax from 862,9 basis`(){
        whenever(holder.basis).thenReturn(862.9)
        whenever(taxConfig.taxHealthSecond).thenReturn(0.0775)

        Assertions.assertEquals(66.87, calculator.calculateSecondHealthInsurance(holder), 0.01)
    }

    @Test
    fun `should calculate second basis from 690 basis`(){
        whenever(taxConfig.advancedTax).thenReturn(0.18)

        Assertions.assertEquals(124.2, calculator.calculateBasis(holder, 690.0), 0.01)
    }

    @Test
    fun `should calculate basis reduced by income tax from 862,9 basis in civil contract`(){
        whenever(holder.basis).thenReturn(862.9)
        whenever(holder.contractType).thenReturn(ContractType.CIVIL)
        whenever(taxConfig.civilIncomeValue).thenReturn(0.2)

        Assertions.assertEquals(690.32, calculator.calculateBasisReducedByIncomeTax(holder), 0.01)
    }

    @Test
    fun `should calculate basis reduced by income tax from 862,9 basis in ordinary contract`(){
        whenever(holder.basis).thenReturn(862.9)
        whenever(holder.contractType).thenReturn(ContractType.ORDINARY)
        whenever(taxConfig.incomeCost).thenReturn(111.25)

        Assertions.assertEquals(751.65, calculator.calculateBasisReducedByIncomeTax(holder), 0.01)
    }

    @Test
    fun `should calculate basis reduced by income tax from 862,9 basis in unknown contract`(){
        whenever(holder.basis).thenReturn(862.9)
        whenever(holder.contractType).thenReturn(ContractType.UNKNOWN)

        Assertions.assertEquals(862.9, calculator.calculateBasisReducedByIncomeTax(holder), 0.01)
    }

    @Test
    fun `should calculate exempted value from 124,2 advanced tax for civil contract`(){
        whenever(holder.advancedTaxValue).thenReturn(124.2)
        whenever(taxConfig.exemptedValue).thenReturn(0.0)
        whenever(holder.contractType).thenReturn(ContractType.CIVIL)

        Assertions.assertEquals(124.20, calculator.calculateExemptedValue(holder), 0.01)
    }

    @Test
    fun `should calculate exempted value from 124,2 advanced tax for civil ordinal`(){
        whenever(holder.advancedTaxValue).thenReturn(124.2)
        whenever(taxConfig.exemptedValue).thenReturn(46.33)
        whenever(holder.contractType).thenReturn(ContractType.ORDINARY)

        Assertions.assertEquals(77.87, calculator.calculateExemptedValue(holder), 0.01)
    }

    @Test
    fun `should calculate salary`(){
        whenever(holder.income).thenReturn(1000.0)
        whenever(holder.advanceTaxOfficeTaxRounded).thenReturn(22.0)
        whenever(holder.pensionTaxValue).thenReturn(97.6)
        whenever(holder.disabledTaxValue).thenReturn(15.0)
        whenever(holder.illnessTaxValue).thenReturn(24.5)
        whenever(holder.firstHealthTaxValue).thenReturn(77.66)

        Assertions.assertEquals(763.24, calculator.calculateSalary(holder))
    }
}