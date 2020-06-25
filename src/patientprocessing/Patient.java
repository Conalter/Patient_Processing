package patientprocessing;

import java.io.Serializable;
import java.util.Objects;

public abstract class Patient
        implements Comparable<Patient>, Serializable {

    final String idnumber;
    String firstname;
    String lastname;
    Gender gender;
    int age;
    double height;
    double weight;
    BloodType bloodtype;
    PriorityLevel prioritylevel;

    public Patient(String idnumber, String firstname, String lastname, Gender gender, int age,
            double height, double weight, BloodType bloodtype, PriorityLevel prioritylevel) {
        this.idnumber = idnumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bloodtype = bloodtype;
        this.prioritylevel = prioritylevel;
    }

    public String getFullname() {
        return firstname + " " + lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int newAge) {
        age = newAge;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public BloodType getbloodtype() {
        return bloodtype;
    }

    public String getidnumber() {
        return idnumber;
    }

    public PriorityLevel getPriorityLevel() {
        return prioritylevel;
    }

    public void setPriorityLevel(PriorityLevel prioritylevel) {
        this.prioritylevel = prioritylevel;
    }

    @Override
    public String toString() {
        return "PatentID: " + idnumber + "\nName: " + firstname + " " + lastname + "\nGender: " + gender.getGenSymb()
                + "\nAge: " + age + "\nHeight: " + height + "\nWeight: " + weight + "\nBlood Group: " + bloodtype.getbtString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.idnumber);
        hash = 83 * hash + Objects.hashCode(this.firstname);
        hash = 83 * hash + Objects.hashCode(this.lastname);
        hash = 83 * hash + Objects.hashCode(this.gender);
        hash = 83 * hash + Objects.hashCode(this.height);
        hash = 83 * hash + Objects.hashCode(this.weight);

        return hash;
    }

    @Override
    public int compareTo(Patient o) {
        if (!this.firstname.equals(o.firstname)) {
            return this.firstname.compareTo(o.firstname);
        }
        if (!this.lastname.equals(o.lastname)) {
            return this.lastname.compareTo(o.lastname);
        } else {
            return this.gender.compareTo(o.gender);
        }
    }

}
