package Codebase;

public class LoggedInUser {
    private String user_Id;
    private String user_Name;
    private String user_Type;

    public LoggedInUser(String user_Id, String user_Name, String user_Type) {
        this.user_Id = user_Id;
        this.user_Name = user_Name;
        this.user_Type = user_Type;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUser_Type() {
        return user_Type;
    }

    public void setUser_Type(String user_Type) {
        this.user_Type = user_Type;
    }

    



    
}
