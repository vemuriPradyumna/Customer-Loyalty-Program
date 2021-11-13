package Codebase.POJO;

public class LoggedInBrand {
    private String brandId;
    private String brandName;
    private String lpCode;
    private String lpName;
    private int isTiered;
    public LoggedInBrand(String brandId, String brandName, String lpCode, String lpName, int isTiered) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.lpCode = lpCode;
        this.lpName = lpName;
        this.isTiered = isTiered;
    }

    public String getBrandId() {
        return brandId;
    }
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }
    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public String getLpCode() {
        return lpCode;
    }
    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }
    public String getLpName() {
        return lpName;
    }
    public void setLpName(String lpName) {
        this.lpName = lpName;
    }
    public int getIsTiered() {
        return isTiered;
    }
    public void setIsTiered(int isTiered) {
        this.isTiered = isTiered;
    }
 
    

    

}
