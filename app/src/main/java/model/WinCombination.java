package model;

import java.util.List;

public class WinCombination {
    private final String name;
    private final String when;
    private final double rewardMultiplier;
    private final String group;
    private final int count;
    private final List<List<String>> coveredAreas;

    public WinCombination(String name, String when, double rewardMultiplier, String group, int count, List<List<String>> coveredAreas) {
        this.name = name;
        this.when = when;
        this.rewardMultiplier = rewardMultiplier;
        this.group = group;
        this.count = count;
        this.coveredAreas = coveredAreas;
    }

    public String getName() {
        return name;
    }

    public String getWhen() {
        return when;
    }

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public String getGroup() {
        return group;
    }

    public int getCount() {
        return count;
    }

    public List<List<String>> getCoveredAreas() {
        return coveredAreas;
    }
}
