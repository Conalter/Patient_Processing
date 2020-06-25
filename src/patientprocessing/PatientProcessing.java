package patientprocessing;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import uod.gla.io.File;
import uod.gla.menu.Finalisable;
import uod.gla.menu.MenuBuilder;
import uod.gla.menu.MenuItem;
import uod.gla.util.CollectionUtils;
import uod.gla.util.Reader;
import uod.gla.util.Sequence;

public class PatientProcessing implements Finalisable {

    private static List<Patient> patientList = new ArrayList<>();
    private static Sequence sequence = new Sequence(10, 1000000000, "c");

    private static File patientListFile = new File("storage", "patientList");
    private static File sequenceFile = new File("storage", "sequence");
    
    //establishing Deques: first=RESUSCITATION/second=EMERGENCY/third=URGENT/forth=STANDARD/fifth=TRIVIAL/sixth=FINE       
        public static Deque<Patient> first = new LinkedList<>();
        public static Deque<Patient> second = new LinkedList<>();
        public static Deque<Patient> third = new LinkedList<>();
        public static Deque<Patient> fourth = new LinkedList<>();
        public static Deque<Patient> fifth = new LinkedList<>();
        public static Deque<Patient> sixth = new LinkedList<>();

    private static PatientProcessing appObject = new PatientProcessing();

    public static void main(String[] args) {
        System.out.println("Patient Processing");

        List<Patient> retrievedPatientList = patientListFile.<List<Patient>>retrieve(true);
        Sequence retrievedSequence = sequenceFile.<Sequence>retrieve(true);
        if (retrievedPatientList != null) {
            patientList = retrievedPatientList;
        }
        if (retrievedSequence != null) {
            sequence = retrievedSequence;
        }

        MenuItem register = new MenuItem("REG", "Patient Register", appObject, "register");
        MenuItem triage = new MenuItem("TRI", "Triage", appObject, "triage");
        MenuItem treat = new MenuItem("TRT", "Treatment", appObject, "treat");
        MenuItem stats = new MenuItem("STA", "Statistics", appObject, "stats");
        MenuBuilder.displayMenu(appObject, register, triage, treat, stats);

        appObject.finalise();
    }

    @Override
    public void finalise() {
        patientListFile.save((Serializable) patientList);
        sequenceFile.save((Serializable) sequence);
    }
    
    public static void stats(){
        MenuItem queue = new MenuItem("Q", "Display Triage Queues", appObject, "queue");
    }
    
    public static void treat(){
        MenuItem treat = new MenuItem("NXT", "Treat Next Patient", appObject, "treatnext");
        MenuBuilder.displayMenu(appObject, treat);
        
        
    }
    
    public static void treatnext(){
        first.pop();
    }

    public static void triage() {
        
        
        String key = Reader.readLine("Enter Patient's details").toUpperCase();
        Patient patient = search(key);

        if (patient == null) {
            System.out.println("No patient found");
            return;
        }
        
        System.out.println(patient);
        //boolean edited = false;
        
        //PriorityLevel prioritylevel = Reader.readEnum("\nPlease assess the patient priority level:", PriorityLevel.class);
        patient.setPriorityLevel(Reader.readEnum("\nPlease assess the patient priority level:", PriorityLevel.class));
        System.out.print("\n" + patient.getFullname() + " has a priority level of " + patient.getPriorityLevel()
                + ".\nConsequently, they" + patient.getPriorityLevel().getplString() + "\n" );
        appObject.finalise();
        
        if(null == patient.prioritylevel){
            sixth.addLast(patient);
        } else switch (patient.prioritylevel) {
            case RESUSCITATION:
                first.addLast(patient);
                break;
            case EMERGENCY:
                second.addLast(patient);
                break;
            case URGENT:
                third.addLast(patient);
                break;
            case STANDARD:
                fourth.addLast(patient);
                break;
            case TRIVIAL:
                fifth.addLast(patient);
                break;
            default:
                sixth.addLast(patient);
                break;
        }   
        System.out.print("\n"+first);
        System.out.print("\n"+second);
        System.out.print("\n"+third);
        System.out.print("\n"+fourth);
        System.out.print("\n"+fifth);
    }

    public static void register() {
        MenuItem create = new MenuItem("N", "New Patient", appObject, "create");
        MenuItem display = new MenuItem("L", "List Patients", appObject, "display");
        MenuItem edit = new MenuItem("U", "Update Patients", appObject, "edit");
        MenuItem delete = new MenuItem("D", "Delete Patients", appObject, "remove");
        MenuBuilder.displayMenu(appObject, create, display, edit, delete);

        appObject.finalise();
    }

    public static void create() {
        Patient patient = createPatient();
        patientList.add(patient);
    }

    public static RegisteredPatient createPatient() {
        String firstname = Reader.readLine("First name: ").toUpperCase();
        String lastname = Reader.readLine("Surname: ").toUpperCase();
        Gender gender = Reader.readEnum("Patient gender: ", Gender.class);
        int age = Reader.readInt("Patient age: ", 0, 145);
        double height = Reader.readDouble("Patient Height in feet: ", 0, 10);
        double weight = Reader.readDouble("Patient Weight in stone: ", 0, 50);
        BloodType bloodtype = Reader.readEnum("Patient Blood Group: ", BloodType.class);

        PriorityLevel prioritylevel = PriorityLevel.FINE;

        double BMI = RegisteredPatient.calcBMI(height, weight);
        BMICategory bmicategory = RegisteredPatient.calcBMICategory(BMI);

        String displayNew;

        displayNew = RegisteredPatient.class.getTypeName();
        System.out.print("--------------------------------------------"
                + "\nNew Patient details: "
                + "\nPatient Identification Number: " + sequence.next()
                + "\nFull Name: " + firstname + " " + lastname
                + "\nGender: " + gender.getGenSymb() + "\nAge: " + age + "\nHeight: " + height
                + "\nWeight: " + weight + "\nBlood Group: " + bloodtype.getbtString()
                + "\nBMI: " + BMI + "\nBMI Category: " + bmicategory
                + "\n--------------------------------------------" + "\n");

        return new RegisteredPatient(sequence.next(), firstname, lastname, gender, age,
                height, weight, bloodtype, BMI, bmicategory, prioritylevel);
    }

    public static void display() {
        Collections.sort(patientList, Collections.reverseOrder());
        patientList.forEach((V) -> {
            System.out.println(V + "\n");
        });
        //System.out.println(patientList.size() + " Registered Patients displayed\n");
        System.out.println("Registered Patients = " + patientList.size() + " \n");
    }

    private static Patient search(String key) {
        Collection<Patient> results = CollectionUtils.search(key, patientList);
        if (results == null || results.isEmpty()) {
            return null;
        }
        return Reader.readObject("Please select a Patient from the list", results);
    }

    public static void edit() {
        String key = Reader.readLine("Enter Patient's details").toUpperCase();
        Patient patient = search(key);
        double BMI = RegisteredPatient.calcBMI(patient.height, patient.weight);
        if (patient == null) {
            System.out.println("No patient found");
            return;
        }
        System.out.println(patient);
        boolean edited = false;
        // hwedited boolean for height/weight change
        boolean hwedited = false;
        if (Reader.readBoolean("Do you want to change the patient's first name?")) {
            patient.setFirstname(Reader.readName("Enter first Name: ").toUpperCase());
            edited = true;
        }
        if (Reader.readBoolean("Do you want to change the patient's surname name?")) {
            patient.setLastname(Reader.readName("Enter last Name: ").toUpperCase());
            edited = true;
        }
        if (Reader.readBoolean("Do you want to change the patient's height?")) {
            patient.setHeight(Reader.readDouble("Enter height: "));
            hwedited = true;
        }
        if (Reader.readBoolean("Do you want to change the patient's weight?")) {
            patient.setWeight(Reader.readDouble("Enter weight: "));
            hwedited = true;
        }
        if (hwedited = true){
            RegisteredPatient.calcBMI(patient.height,patient.weight);
        }         
        if (edited || hwedited) {
            System.out.println("--------------------------------------------"
                    + "\nNew Patient details: "
                    + "\nPatient Identification Number: " + sequence.next()
                    + "\nFull Name: " + patient.firstname + " " + patient.lastname
                    + "\nGender: " + patient.gender.getGenSymb() + "\nAge: " + patient.age + "\nHeight: " + patient.height
                    + "\nWeight: " + patient.weight + "\nBlood Group: " + patient.bloodtype.getbtString()
                    + "\nBMI: " + BMI + "\nBMI Category: " + RegisteredPatient.calcBMICategory(BMI)
                    + "\n--------------------------------------------" + "\n");;
        } else {
            System.out.println("No details were changed");
        }
    }

    public static void remove() {
        String key = Reader.readLine("Enter the Patient details: ").toUpperCase();
        Patient patient = search(key);
        if (patient == null) {
            System.out.println("Patient Not found!");
            return;
        }
        System.out.println(patient);
        if (Reader.readBoolean("Do you want to removed this patient?")) {
            patientList.remove(patient);
            System.out.println("Patient has been removed from register");
        } else {
            System.out.println("Deletion aborted!");
        }
    }
}