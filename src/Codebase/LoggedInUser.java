package src.Codebase;

public class LoggedInUser {
    private String user_Id;
    private String user_Type;
    private String walletId;

    public LoggedInUser(String user_Id, String user_Type,String walletId) {
        this.user_Id = user_Id;
        this.user_Type = user_Type;
        this.walletId = walletId;
    }

    

    public String getWalletId() {
        return walletId;
    }



    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }



    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getUser_Type() {
        return user_Type;
    }

    public void setUser_Type(String user_Type) {
        this.user_Type = user_Type;
    }

}
