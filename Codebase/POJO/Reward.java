package Codebase.POJO;

public class Reward {
    private String rewardCategory;
    private String rewardName;

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

    

    
}
