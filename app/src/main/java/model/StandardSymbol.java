package model;

public class StandardSymbol extends Symbol {

    private double rewardMultiplier;

    public StandardSymbol(String name, double rewardMultiplier) {
        super(name, "standard");
        this.rewardMultiplier = rewardMultiplier;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }    
}
