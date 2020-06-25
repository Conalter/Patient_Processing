package patientprocessing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import uod.gla.io.File;
import uod.gla.menu.Finalisable;
import uod.gla.menu.MenuBuilder;
import uod.gla.menu.MenuItem;
import uod.gla.util.CollectionUtils;
import uod.gla.util.Reader;
import uod.gla.util.Sequence;

public class Triage {

    public static void main(String[] args) {
 
    public static triage(Patient patient) {
        String key = Reader.readLine("Enter Patient's details").toUpperCase();
        Patient patient = search(key);

        if (patient == null) {
            System.out.println("No patient found");
            return;
        }

        System.out.println(patient);

        patient.setPriorityLevel(Reader.readEnum("\nPlease assess the patient priority level:", PriorityLevel.class));
        System.out.print("\n" + patient.getFullname() + " has a priority level of " + patient.getPriorityLevel() + ". Consequently they "
                + patient.getPriorityLevel().getplString() + "\n");
        appObject.finalise();
        return Patient ;
    }    
}
