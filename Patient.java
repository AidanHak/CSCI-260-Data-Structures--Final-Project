public class Patient extends Person implements Comparable<Patient>
{
    private Medicine med;
    public BT bt = new BT();

    public Patient()
    {
        super();
        med = new Medicine();
    }

    public Patient(String setName, int setAge, String medName, int medDoses)
    {
        super(setName, setAge);
        med = new Medicine(medName, medDoses);
    }
    
    public Medicine getMed()
    {
        return med;
    }
    
    public void setMed(Medicine newMed)
    {
        med = newMed;
    }
    
    public String toString()
    {
        return getName() + "\n\tis " + getAge() + " years old\n\tand takes " + getMed() + ".";
    }
    
    @Override
    public int compareTo(Patient p2)
    {
        if (this.getMed().getMedTime().after(p2.getMed().getMedTime()))
        {
            return 1;
        } else if(this.getMed().getMedTime().before(p2.getMed().getMedTime()))
        {
            return -1;
        } else
        {
            return 0;
        }
    }
}