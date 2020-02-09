package legacy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

//Original code in a single class
public class TaxCalculator {
	
	public static double income = 0;
	public static char contractType = ' ';
	// social taxes
	public static double t_pension = 0; // 9,76% of the income
	public static double t_disabled = 0; // 1,5% of the income
	public static double s_illness = 0; // 2,45% of the income
	// health taxes
	public static double incomeCost = 111.25; 
	public static double t_health1 = 0; // 9% of the incomeCost
	public static double t_health2 = 0; // 7,75 % of the incomeCost
	public static double advanceTax = 0; // income tax (18%) advance
	public static double exemptedValue = 46.33; // reduced value 46,33 PLN
	public static double advanceTaxOffice = 0;
	public static double advanceTaxOffice0 = 0;

	public static void main(String[] args) {
		//Very long try == bad
		try {
			//Short and ambiguous variable name
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);

			//Logging can be done by separate class instead of hard codded system print
			System.out.print("Provide income: ");	
			income = Double.parseDouble(br.readLine());
			
			System.out.print("Contract type: (O)rdinary, (C)ivil: ");
			contractType = br.readLine().charAt(0);
			
		} catch (Exception ex) {
			System.out.println("Incorrect value");
			System.err.println(ex);
			return;
		}

		//Again variable naming
		DecimalFormat df00 = new DecimalFormat("#.00");
		DecimalFormat df = new DecimalFormat("#");

		//Types via hardcoded string. Should be enum or any kind of static variable
		//Extremely long conditions
		if (contractType == 'O') {
			System.out.println("Ordinary contract");
			System.out.println("basis for taxes " + income);
			double cBasis = colculatedBasis(income);
			System.out.println("Pension tax basis "
					+ df00.format(t_pension));
			System.out.println("Disability tax basis "
					+ df00.format(t_disabled));
			System.out.println("Illness insurance basis  "
					+ df00.format(s_illness));
			System.out
					.println("Health insurance basis: "
							+ cBasis);
			calculateInsurance(cBasis);
			System.out.println("Health insutance: 9% = "
					+ df00.format(t_health1) + " 7,75% = " + df00.format(t_health2));
			System.out.println("Constant income tax cost "
					+ incomeCost);
			double taxBasis = cBasis - incomeCost;
			double taxBasis0 = Double
					.parseDouble(df.format(taxBasis));
			System.out.println("Tax basis " + taxBasis
					+ " rounded " + df.format(taxBasis0));
			calculateBasis(taxBasis0);
			System.out.println("Advance for income tax 18 % = "
					+ advanceTax);
			System.out.println("Exempted value = " + exemptedValue);
			double exemtedTax = advanceTax - exemptedValue;
			System.out.println("Exempted tax = "
					+ df00.format(exemtedTax));
			calculateAdvance();
			advanceTaxOffice0 = Double.parseDouble(df.format(advanceTaxOffice));
			System.out.println("Advance for the tax office = "
					+ df00.format(advanceTaxOffice) + " rounded = "
					+ df.format(advanceTaxOffice0));
			double salary = income
					- ((t_pension + t_disabled + s_illness) + t_health1 + advanceTaxOffice0);
			System.out.println();
			System.out
					.println("Net salary = "
							+ df00.format(salary));
		} else if (contractType == 'C') {
			System.out.println("CIVIL CONTRACT");
			System.out.println("Basis for taxes " + income);
			double oBasis = colculatedBasis(income);
			System.out.println("Pension tax "
					+ df00.format(t_pension));
			System.out.println("Disability tax "
					+ df00.format(t_disabled));
			System.out.println("Illness insurance tax  "
					+ df00.format(s_illness));
			System.out
					.println("Basis for the health tax: "
							+ oBasis);
			calculateInsurance(oBasis);
			System.out.println("Healt tax: 9% = "
					+ df00.format(t_health1) + " 7,75% = " + df00.format(t_health2));
			exemptedValue = 0;
			incomeCost = (oBasis * 20) / 100;
			System.out.println("Income tax cost (constant) "
					+ incomeCost);
			double basisTax = oBasis - incomeCost;
			double basisTax0 = Double.parseDouble(df.format(basisTax));
			System.out.println("Basis tax " + basisTax
					+ " rouded " + df.format(basisTax0));
			calculateBasis(basisTax0);
			System.out.println("Advance for income tax 18 % = "
					+ advanceTax);
			double taxTaken = advanceTax;
			System.out.println("Tax taken = "
					+ df00.format(taxTaken));
			calculateAdvance();
			advanceTaxOffice0 = Double.parseDouble(df.format(advanceTaxOffice));
			System.out.println("Advance for tax office = "
					+ df00.format(advanceTaxOffice) + " rounded = "
					+ df.format(advanceTaxOffice0));
			//Long calculation in a single line
			double salary = income
					- ((t_pension + t_disabled + s_illness) + t_health1 + advanceTaxOffice0);
			System.out.println();
			System.out
					.println("Net salary = "
							+ df00.format(salary));
		} else {
			System.out.println("Unknown contract!");
		}
	}
	//No empty lines inside long code

	//All taxes calculations could be provided by separated class
	public static void calculateAdvance() {
		advanceTaxOffice = advanceTax - t_health2 - exemptedValue;
	}

	//Magic numbers
	public static void calculateBasis(double basis) {
		advanceTax = (basis * 18) / 100;
	}

	//Magic numbers
	public static double colculatedBasis(double basis) {
		t_pension = (basis * 9.76) / 100;
		t_disabled = (basis * 1.5) / 100;
		s_illness = (basis * 2.45) / 100;
		return (basis - t_pension - t_disabled - s_illness);
	}

	//Magic numbers
	public static void calculateInsurance(double basis) {
		t_health1 = (basis * 9) / 100;
		t_health2 = (basis * 7.75) / 100;
	}
}
