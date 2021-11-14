package src.Codebase;

public class Reward {
    private String rewardCategory;
    private String rewardName;
    private String value;
    private int instances;
    private String lpCode;
    private int rewardIdentifier;

    public Reward(String rewardCategory, String rewardName) {
        this.rewardCategory = rewardCategory;
        this.rewardName = rewardName;
    }

    public String getRewardCategory() {
        return rewardCategory;
    }

    public void setRewardCategory(String rewardCategory) {
        this.rewardCategory = rewardCategory;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getInstances() {
        return instances;
    }

    public void setInstances(int instances) {
        this.instances = instances;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public int getRewardIdentifier() {
        return rewardIdentifier;
    }

    public void setRewardIdentifier(int rewardIdentifier) {
        this.rewardIdentifier = rewardIdentifier;
    }

    
    

    

    

    
}
