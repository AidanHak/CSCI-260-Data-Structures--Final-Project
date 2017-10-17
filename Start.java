import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Date;
import java.util.Queue;
import java.util.Stack;

public class Start
{
    private static int exitCode;
    private static Queue<MenuOption> options = new LinkedList<MenuOption>();
    private static Queue<MenuOption> patientProps = new LinkedList<MenuOption>();
    private static Scanner scan = new Scanner(System.in);
    private static List<Patient> patientList = new ArrayList<Patient>();
    private static Stack<Patient> st = new Stack<Patient>();
    private static BT bt = new BT();

    public static void main(String[] args)
    {
        int numPatients = -1;
        System.out.println("How many patients are already in the hospital?");
        while (numPatients < 0)
        {
            try
            {
                numPatients = scan.nextInt();
                if (numPatients < 0)
                {
                    System.out.println("You must enter an integer greater than -1!");
                }
            } catch(InputMismatchException e)
            {
                notIntError(e);
            }
            scan.nextLine();
        }

        if (numPatients > 0)
        {
            for (int i = 0; i < numPatients; i++)
            {
                patientList.add(new Patient(generateName(), generateAge(), generateMed(), generateNumDoses()));
            }

            System.out.println("\nWould you like to see the details of the existing " + numPatients + " patients?");
            System.out.println("Yes - 'y'");
            System.out.println("No - 'n'");
            char yesNo = 'a';
            while (yesNo == 'a')
            {
                yesNo = scan.next().charAt(0);
                if (yesNo == 'y')
                {
                    printPatientList(patientList);
                } else if (yesNo != 'n') // not yes (failed if) and not no (passed else if), so they did something wrong
                {
                    yesNo = 'a';
                    System.out.println("You must enter 'y' for Yes or 'n' for No!");
                }
                scan.nextLine();
            }
        }

        int choice = readOption();
        while (choice != exitCode)
        {
            numPatients = patientList.size();
            switch (choice)
            {
                case 1: // Add a patient
                System.out.println("\nWho would you like to to add?");
                String name = scan.nextLine();
                System.out.println("How old is " + name + "?");
                int age = -1;
                while (age < 0)
                {
                    try
                    {
                        age = scan.nextInt();
                        if (age < 0)
                        {
                            System.out.println("You must enter an integer greater than -1!");
                        }
                    } catch (InputMismatchException e)
                    {
                        notIntError(e);
                    }
                    scan.nextLine();
                }
                System.out.println("What medicine does " + name + " need?");
                String medName = scan.nextLine();
                System.out.println("How many doses a day of " + medName + " does he/she need?");
                int numDoses = 0;
                while (numDoses < 1)
                {
                    try
                    {
                        numDoses = scan.nextInt();
                        if (numDoses < 1)
                        {
                            System.out.println("You must enter an integer greater than 0");
                        }
                    } catch (InputMismatchException e)
                    {
                        notIntError(e);
                    }
                    scan.nextLine();
                }
                patientList.add(new Patient(name, age, medName, numDoses));
                System.out.println("Patient added!");
                break;
                case 2: // Remove a patient
                if (numPatients > 0)
                {
                    System.out.println("Which patient would you like to remove?");
                    printPatientList(patientList);

                    int patientNum = getPatientNum();
                    patientList.remove(patientNum);
                    System.out.println("Patient removed!");
                } else
                {
                    System.out.println("There are no patients left to remove.");
                }
                break;
                case 3: // Modify a patient's record
                if (numPatients > 0)
                {
                    System.out.println("\nWhich patient's record would you like to change?");
                    printPatientList(patientList);

                    int patientNum = getPatientNum();
                    System.out.println("What would you like to change about this patient?");
                    genProps();
                    for(MenuOption pProp : patientProps)
                    {
                        System.out.println(pProp);
                        bt.insert(pProp.getNum());
                    }

                    int propNum = 0;
                    while (!bt.search(propNum))
                    //while (propNum < 1 || propNum > patientProps.size())
                    {
                        try
                        {
                            propNum = scan.nextInt();
                            if (propNum < 1 || propNum > patientProps.size())
                            {
                                System.out.println("You must choose an integer between 1 and " + patientProps.size());
                            }
                        } catch (InputMismatchException e)
                        {
                            notIntError(e);
                        }
                        scan.nextLine();
                    }

                    Patient p = patientList.get(patientNum);
                    switch (propNum)
                    {
                        case 1: // Change name
                        System.out.println("What would you like to make " + p.getName() + "'s name?");
                        p.setName(scan.nextLine());
                        break;
                        case 2: // Change age
                        System.out.println("How old is " + p.getName() + "?");
                        int newAge = -1;
                        while (newAge < 0)
                        {
                            try
                            {
                                newAge = scan.nextInt();
                                if (newAge < 0)
                                {
                                    System.out.println("You must enter an integer greater than 0!");
                                }
                            } catch (InputMismatchException e)
                            {
                                notIntError(e);
                            }
                            scan.nextLine();
                        }
                        p.setAge(newAge);
                        break;
                        case 3: // Change medicine name
                        System.out.println("What medicine does " + p.getName() + " need?");
                        p.setMed(new Medicine(scan.nextLine(), p.getMed().getNumDoses()));
                        break;
                        case 4: // Change number of doses of medicine
                        System.out.println("How many doses of " + p.getMed().getName() + " does " + p.getName() + " need per day?");
                        int newNumDoses = 0;
                        while (newNumDoses < 1)
                        {
                            try
                            {
                                newNumDoses = scan.nextInt();
                                if (newNumDoses < 1)
                                {
                                    System.out.println("You must choose an integer greater than 0!");
                                }
                            } catch (InputMismatchException e)
                            {
                                notIntError(e);
                            }
                            scan.nextLine();
                        }
                        p.setMed(new Medicine(p.getMed().getName(), newNumDoses));
                        break;
                    }
                    System.out.println(p.getName() + "'s record has been modified!");
                } else
                {
                    System.out.println("There are no patients left to change.");
                }
                break;
                case 4: // Sort patients by time of medication due
                // Collections.sort calls compareTo overriden method in Patient class
                Collections.sort(patientList);
                System.out.println("Sorted!");
                break;
                case 5: // View all patients' records (first to last)
                System.out.println("\nThere are currently " + numPatients + " patients.");
                printPatientList(patientList);
                break;
                case 6: // View all patients' records (last to first)
                System.out.println("\nThere are currently " + numPatients + " patients.");
                while (!st.empty())
                {
                    st.pop();
                }
                
                for (Patient p : patientList)
                {
                    st.push(p);
                }
                
                for (int i = 1; i <= numPatients; i++)
                {
                    System.out.println(i + " - " + st.pop());
                }
                break;
            }
            choice = readOption();
        }

        //System.out.println("Thank you for being the best professor ever! (Thank you for the A!)");
    }

    public static String generateName()
    {
        Random randName = new Random();
        String[] firstName = {"James", "John", "Robert", "Michael", "William", "David", "Richard", "Joseph", 
                "Thomas", "Charles", "Christopher", "Daniel", "Matthew", "Anthony", "Donald", "Marcus", "Paul", 
                "Steven", "George", "Kenneth", "Andrew", "Joshua", "Edward", "Brian", "Kevin", "Ronald", "Timothy", 
                "Jason", "Jeffrey", "Ryan", "Gary", "Jacob", "Nicholas", "Eric", "Stephen", "Jonathan", "Larry", 
                "Scott", "Frank", "Justin", "Brandon", "Raymond", "Gregory", "Samuel", "Benjamin", "Patrick", 
                "Jack", "Alexander", "Dennis", "Jerry", "Tyler", "Aaron", "Henry", "Mary", "Patricia", 
                "Jennifer", "Elizabeth", "Linda", "Barbara", "Susan", "Jessica", "Margaret", "Sarah", "Karen", 
                "Nancy", "Betty", "Dorothy", "Lisa", "Sandra", "Ashley", "Kimberly", "Donna", "Carol", "Michelle", 
                "Emily", "Helen", "Amanda", "Melissa", "Deborah", "Stephanie", "Laura", "Rebecca", "Sharon", 
                "Cynthia", "Kathleen", "Shirley", "Amy", "Anna", "Angela", "Ruth", "Brenda", "Pamela", "Virgina", 
                "Katherine", "Nicole", "Catherine", "Christine", "Samantha", "Debra", "Janet", "Carolyn", 
                "Rachel", "Heather", "Maria", "Diane", "Emma"};
        String[] lastName = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", 
                "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", 
                "Garcia", "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", 
                "Young", "Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", 
                "Gonzalez", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", 
                "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez", "Morris", "Rogers", "Reed", "Cook", 
                "Morgan", "Bell", "Murphy", "Bailey", "Rivera", "Cooper", "Richardson", "Cox", "Howard", "Ward", 
                "Torres", "Peterson", "Gray", "Ramirez", "James", "Watson", "Brooks", "Kelly", "Sanders"};

        return firstName[randName.nextInt(firstName.length)] + " " + lastName[randName.nextInt(lastName.length)];
    }

    public static int generateAge()
    {
        return new Random().nextInt(100) + 1;
    }

    public static String generateMed()
    {
        Random randMed = new Random();
        String[] medName = {"Sodium Valproate", "Actos", "Amiodarone", "Atenolol", "Betapace", "Cardizem", "Cipro", "Coreg", "Coumadin", 
            "Crestor", "Heparin", "Humalog", "Insulin", "Isosobride", "KCL", "Lasix", "Levaquin", "Lexapro", "Liptor", "Crestor", "Zocor", 
            "Lovastatin", "Lisinopril", "Lovenox", "Metformin", "Morphine", "Nitropaste", "Norvasc", "Potassium", "Protonix", "Saline Flush", 
            "Solu-medrol", "Tagamet", "Toprol", "Tricor", "Vacomycin", "Vasotec", "Venofer", "Wellbutrin", "Xanax", "Zithromax", "Zithromycin", "Zosyn"};
        return medName[randMed.nextInt(medName.length)];
    }

    public static int generateNumDoses()
    {
        // chooses a number from [0, 4) - 0, 1, 2, 3
        // then add one to get [1, 4] - 1, 2, 3, 4
        return new Random().nextInt(4) + 1;
    }

    public static void printPatientList(List<Patient> patientList)
    {
        int numPatients = patientList.size();
        for (int i = 0; i < numPatients; i++)
        {
            System.out.println(i + 1 + " - " + patientList.get(i));
        }
    }

    public static int getPatientNum()
    {
        int patientNum = 0;
        int numPatients = patientList.size();
        while (patientNum < 1 || patientNum > numPatients)
        {
            try
            {
                patientNum = scan.nextInt();
                if (patientNum < 1 || patientNum > numPatients)
                {
                    System.out.println("You must choose a number between 1 and " + numPatients);
                }
            } catch (InputMismatchException e)
            {
                notIntError(e);
            }
            scan.nextLine();
        }
        return --patientNum;
    }

    public static int readOption()
    {
        System.out.println("\nWhat would you like to do?");
        System.out.println("The time is now: " + new Date());
        genOpts();
        for(MenuOption opt : options)
        {
            System.out.println(opt);
        }

        int choice = 0;
        while (choice == 0)
        {
            try
            {
                choice = scan.nextInt();
                if (choice < 1 || choice > options.size())
                {
                    System.out.println("Please choose another option!");
                }
            } catch (InputMismatchException e)
            {
                notIntError(e);
            }
            scan.nextLine();
        }
        return choice;
    }

    public static void notIntError(InputMismatchException e)
    {
        System.out.println("You must enter an integer, first and only! (" + e + ")");
    }

    public static void genOpts()
    {
        int num = 1;
        options.clear();
        options.add(MenuOption.create("Add patient", num++));
        options.add(MenuOption.create("Remove a patient", num++));
        options.add(MenuOption.create("Modify a patient's record", num++));
        options.add(MenuOption.create("Sort patients by time of medication due", num++));
        options.add(MenuOption.create("View all patients' records (first to last)", num++));
        options.add(MenuOption.create("View all patients' records (last to first)", num++));
        options.add(MenuOption.create("Exit", num));
        exitCode = num;
    }

    public static void genProps()
    {
        int num = 1;
        patientProps.clear();
        patientProps.add(MenuOption.create("Patient name", num++));
        patientProps.add(MenuOption.create("Patient age", num++));
        patientProps.add(MenuOption.create("Medication name", num++));
        patientProps.add(MenuOption.create("Number of doses of medication", num++));
    }
}