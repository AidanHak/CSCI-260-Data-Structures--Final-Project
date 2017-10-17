public class Person
{
    private String name;
    public int age;
    
    public Person()
    {
        name = "Bob Smith"; // Randomly generate
        age = 21;
    }
    
    public Person(String setName, int setAge)
    {
        name = setName;
        age = setAge;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getAge()
    {
        return age;
    }
    
    public void setName(String newName)
    {
        name = newName;
    }
    
    public void setAge(int newAge)
    {
        age = newAge;
    }
}