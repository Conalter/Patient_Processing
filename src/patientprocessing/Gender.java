package patientprocessing;

public enum Gender {
    Male ("♂"),
    Female ("♀");
    
    private final String GenSymb;
    
    Gender(String code){
        this.GenSymb = code;
    }
    
    public String getGenSymb(){
        return this.GenSymb;
    }
}
