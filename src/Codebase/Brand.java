package src.Codebase;

public class Brand {
    private String brandId;
    private String brandname;
    private String lpCode;
    private String lpName;
    
    public Brand(String brandId, String brandname, String lpCode, String lpName) {
        this.brandId = brandId;
        this.brandname = brandname;
        this.lpCode = lpCode;
        this.lpName = lpName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
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

    
}
