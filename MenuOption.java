public class MenuOption
{
    private String optTxt;
    private int optNum;

    public MenuOption(String setTxt, int setNum)
    {
        optTxt = setTxt;
        optNum = setNum;
    }

    public String getTxt()
    {
        return optTxt;
    }

    public int getNum()
    {
        return optNum;   
    }
    
    public String toString()
    {
        return getNum() + " - " + getTxt();
    }
    
    public static MenuOption create(String setTxt, int setNum)
    {
        return new MenuOption(setTxt, setNum);
    }
}