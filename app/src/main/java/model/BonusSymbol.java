package model;

public class BonusSymbol extends Symbol {
    private final String impact;
    private double rewardMultiplier;
    private int extra;

    public BonusSymbol(String name, String impact, double rewardMultiplier, int extra) {
        super(name, "bonus");
        this.impact = impact;
        this.rewardMultiplier = rewardMultiplier;
        this.extra = extra;
    }

    public String getImpact() {
        return impact;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public int getExtra() {
        return extra;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }
}