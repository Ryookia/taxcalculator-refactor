package calculator.model

enum class ContractType(val symbol: String, val contractName: String) {
    ORDINARY("O", "Ordinary"),
    CIVIL("C", "Civil"),
    UNKNOWN("", "");


    companion object{
        fun convertContractType(symbol: String): ContractType {
            return when(symbol){
                ORDINARY.symbol -> ORDINARY
                CIVIL.symbol -> CIVIL
                else -> UNKNOWN
            }
        }
    }

}