package test

import calculator.CalculatorService
import calculator.io.`interface`.InputReader
import calculator.io.`interface`.OutputPrinter
import calculator.model.ContractType
import calculator.model.TaxDataHolder
import calculator.operation.interfaces.ContractHandler
import com.nhaarman.mockitokotlin2.*
import di.ClassProvider
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculatorServiceTest{
    private lateinit var classProvider: ClassProvider
    private lateinit var outputPrinter: OutputPrinter
    private lateinit var inputReader: InputReader
    private lateinit var contractHandler: ContractHandler
    private var taxDataHolder = TaxDataHolder()

    private lateinit var service: CalculatorService

    @BeforeEach
    fun init(){
        classProvider = mock()
        outputPrinter = mock()
        inputReader = mock()
        contractHandler = mock()
        whenever(classProvider.outputPrinter).thenReturn(outputPrinter)
        whenever(classProvider.inputReader).thenReturn(inputReader)

        service = CalculatorService(classProvider)
    }

    @Test
    fun `should not proceed when invalid contract`(){
        whenever(inputReader.readline()).thenReturn("0", "X")
        whenever(classProvider.provideContractHandler(ContractType.UNKNOWN)).thenReturn(null)

        service.executeCalculationProcess(taxDataHolder)

        verify(contractHandler, never()).executeContractProcess(taxDataHolder)
    }

    @Test
    fun `should not proceed when civil contract`(){
        whenever(inputReader.readline()).thenReturn("0", "C")
        whenever(classProvider.provideContractHandler(ContractType.CIVIL)).thenReturn(contractHandler)

        service.executeCalculationProcess(taxDataHolder)

        verify(classProvider).provideContractHandler(ContractType.CIVIL)
        verify(contractHandler).executeContractProcess(taxDataHolder)
    }


    @Test
    fun `should not proceed when ordinal contract`(){
        whenever(inputReader.readline()).thenReturn("0", "O")
        whenever(classProvider.provideContractHandler(ContractType.ORDINARY)).thenReturn(contractHandler)

        service.executeCalculationProcess(taxDataHolder)

        verify(classProvider).provideContractHandler(ContractType.ORDINARY)
        verify(contractHandler).executeContractProcess(taxDataHolder)
    }
}