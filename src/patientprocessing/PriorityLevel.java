
package patientprocessing;


public enum PriorityLevel {
    
   RESUSCITATION (" must be revived!"),
   EMERGENCY (" will be seen immediately."),
   URGENT (" will be seen as soon as possible."),
   STANDARD (" will be seen very soon."),
   TRIVIAL (" will have to wait to be seen."),
   FINE (" must leave as they are taking up space.")
   ;
   
   private final String plString;
    
    PriorityLevel(String string){
        this.plString = string;
    }
    
    public String getplString(){
        return this.plString;
    }
    
}
