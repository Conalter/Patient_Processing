package patientprocessing;

public enum BMICategory {
    OBESE (3),
    OVERWEIGHT (2),
    NORMAL (1),
    UNDERWEIGHT (0)
    ;
    
    private final double bmiCode;
    
    BMICategory(double code){
        this.bmiCode = code;
    }
    
    public double getbmiCode(){
        return this.bmiCode;
    }
}
