package patientprocessing;

import java.text.DecimalFormat;
import java.util.Objects;

public class RegisteredPatient extends Patient {

    double bmi;
    Enum bmicategory;

    public RegisteredPatient(
            String idnumber,
            String firstname,
            String lastname,
            Gender gender,
            int age,
            double height,
            double weight,
            BloodType bloodtype,
            double bmi,
            BMICategory bmicategory,
            PriorityLevel prioritylevel
            )
    {
        super(idnumber, firstname, lastname, gender, age, height, weight, bloodtype, prioritylevel);
        this.bmi = bmi;
        this.bmicategory = bmicategory;
    }
    
    public double getBMI(){
        return bmi;
    }

    public void setbloodType(BloodType bloodtype) {
        this.bloodtype = bloodtype;
    }

    public static double calcBMI(double height, double weight) {
        return Math.round(weight / (height * height) * 703) / 10;
    }

    public static BMICategory calcBMICategory(double BMI) {
        if (BMI >= 30) {
            return BMICategory.OBESE;
        } else if (BMI >= 25) {
            return BMICategory.OVERWEIGHT;
        } else if (BMI >= 18.5) {
            return BMICategory.NORMAL;
        } else {
            return BMICategory.UNDERWEIGHT;
        }
    }

    public void setBmicategory(BMICategory bmicategory) {
        this.bmicategory = bmicategory;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\nBMI: " + bmi + "\nBMI Category: " + bmicategory;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 83 * hash + Objects.hashCode(this.bmi);
        return hash;
    }
}
