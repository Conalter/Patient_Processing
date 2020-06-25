package patientprocessing;


// Blood Types =  A+, A-, B+, B-, O+, O-, AB+, AB-

public enum BloodType {
   A_PLUS ("A+"),
   A_MINUS ("A-"),
   B_PLUS ("B+"),
   B_MINUS ("B-"),
   O_PLUS ("O+"),
   O_MINUS ("O-"),
   AB_PLUS ("AB+"),
   AB_MINUS ("AB-")
   ;
   
   private final String btString;
    
    BloodType(String string){
        this.btString = string;
    }
    
    public String getbtString(){
        return this.btString;
    }
}                 