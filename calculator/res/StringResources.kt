package calculator.res

enum class StringResources(val string: String) {
    PROMPT_PROVIDE_AMOUNT("Provide income: "),
    PROMPT_PROVIDE_CONTRACT_TYPE("Contract type: (O)rdinary, (C)ivil: "),

    WARNING_INCORRECT_VALUE("Incorrect value"),
    WARNING_UNKNOWN_CONTRACT("Unknown contract!"),

    INFO_ORDINARY_CONTRACT("Ordinary contract"),
    INFO_CIVIL_CONTRACT("CIVIL CONTRACT"),

    FORMAT_BASIS_TAXES("Basis for taxes %s"),
    FORMAT_PENSION_TAX("Pension tax basis %s"),
    FORMAT_DISABILITY_TAX("Disability tax basis %s"),
    FORMAT_ILLNESS_INSURANCE("Illness insurance basis %s"),
    FORMAT_HEALTH_INSURANCE("Health insurance basis: %s"),
    FORMAT_HEALTH_INSURANCE_PERCENT("Health insurance: %s%% = %s %s%% = %s"),
    FORMAT_CONSTANT_TAX_COST("Constant income tax cost %s"),
    FORMAT_TAX_BASIS_ROUNDED("Tax basis %s rounded %s"),
    FORMAT_ADVANCE_INCOME_TAX("Advance for income tax %s%% = %s"),
    FORMAT_EXEMPTED_VALUE("Exempted value = %s"),
    FORMAT_EXEMPTED_TAX("Exempted tax = %s"),
    FORMAT_ADVANCE_TAX_OFFICE_ROUNDED("Advance for the tax office = %s rounded = %s"),
    FORMAT_NET_SALARY("Net salary = %s"),

}