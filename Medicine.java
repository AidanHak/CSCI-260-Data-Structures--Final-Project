import java.util.Date;

public class Medicine
{
    private String name;
    private String doses;
    private int numDoses;
    private Date medTime;
    
    public Medicine()
    {
        name = "[generic medicine]";
        numDoses = 4;
        doses = "4 doses a day (once every " + 24 / 4 + " hours)";
        medTime = new Date();
    }
    
    public Medicine(String setName, int setNumDoses)
    {
        name = setName;
        numDoses = setNumDoses;
        doses = setNumDoses + " dose(s) a day (approximately once every " + (24 / setNumDoses) + " hours)";
        medTime = new Date();
        // 1. Get time on computer clock
        // 2. Convert to milliseconds
        // 3. Add a random number from 0ms to 5 hours (to separate all the patients' times
        // 4. Add a certain number of hours from the time of admission for first dosage of medicine
        medTime = new Date((long) (medTime.getTime() + (Math.random() * 18000000) + ((24 / setNumDoses) * 3600000)));
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDoses()
    {
        return doses;
    }
    
    public int getNumDoses()
    {
        return numDoses;
    }
    
    public Date getMedTime()
    {
        return medTime;
    }
    
    public void setName(String setName)
    {
        name = setName;
    }
    
    public void setDoses(int setNumDoses)
    {
        doses = setNumDoses + " dose(s) a day (approximately once every " + (24 / setNumDoses) + " hours)";
    }
    
    public void setNumDoses(int setNumDoses)
    {
        numDoses = setNumDoses;
    }
    
    public void setMedTime(long ms)
    {
        medTime.setTime(ms);
    }
    
    public String toString()
    {
        return doses + " of " + name + "\n\tstarting on " + medTime.toString();
    }
}