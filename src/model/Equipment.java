package model;

import java.io.Serializable;

public class Equipment implements Serializable, Searchable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String type;
    private String statBonus;
    private int usageCount;
    private double winRateContribution;

    public Equipment() {
    }

    public Equipment(String name, String type, String statBonus) {
        this.name = name;
        this.type = type;
        this.statBonus = statBonus;
        this.usageCount = 0;
        this.winRateContribution = 0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatBonus() {
        return statBonus;
    }

    public void setStatBonus(String statBonus) {
        this.statBonus = statBonus;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public double getWinRateContribution() {
        return winRateContribution;
    }

    public void setWinRateContribution(double winRateContribution) {
        this.winRateContribution = winRateContribution;
    }

    @Override
    public boolean matches(String keyword) {
        String kw = keyword.toLowerCase();
        if (name.toLowerCase().contains(kw)) return true;
        if (type.toLowerCase().contains(kw)) return true;
        if (statBonus.toLowerCase().contains(kw)) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Equipment{name='" + name + "', type='" + type + "', usageCount=" + usageCount + "}";
    }
}
