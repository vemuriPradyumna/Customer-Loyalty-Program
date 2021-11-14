package src.Codebase;

public class ReRule {
    private String reCode;
    private String brandId;
    private String activityCode;
    private String activityName;
    private String ruleVersion;
    private int points;

    public ReRule(String reCode, String brandId, String activityCode, String activityName, String ruleVersion,
            int points) {
        this.reCode = reCode;
        this.brandId = brandId;
        this.activityCode = activityCode;
        this.activityName = activityName;
        this.ruleVersion = ruleVersion;
        this.points = points;
    }

    public String getReCode() {
        return reCode;
    }

    public void setReCode(String reCode) {
        this.reCode = reCode;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getRuleVersion() {
        return ruleVersion;
    }

    public void setRuleVersion(String ruleVersion) {
        this.ruleVersion = ruleVersion;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    
    


}
