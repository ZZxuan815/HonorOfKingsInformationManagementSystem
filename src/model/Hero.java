package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Hero implements Serializable, Searchable {
    private static final long serialVersionUID = 1L;

    private String name;
    private HeroType type;
    private Map<String, Integer> baseStats;
    private List<String> compatibleEquipment;

    public Hero() {
        this.type = HeroType.WARRIOR;
        this.baseStats = new HashMap<>();
        this.compatibleEquipment = new ArrayList<>();
    }

    public Hero(String name, HeroType type) {
        this.name = name;
        this.type = type;
        this.baseStats = new HashMap<>();
        this.compatibleEquipment = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroType getType() {
        return type;
    }

    public void setType(HeroType type) {
        this.type = type;
    }

    public Map<String, Integer> getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(Map<String, Integer> baseStats) {
        this.baseStats = baseStats;
    }

    public List<String> getCompatibleEquipment() {
        return compatibleEquipment;
    }

    public void setCompatibleEquipment(List<String> compatibleEquipment) {
        this.compatibleEquipment = (compatibleEquipment != null) ? new ArrayList<>(compatibleEquipment) : new ArrayList<>();
    }

    @Override
    public boolean matches(String keyword) {
        String kw = keyword.toLowerCase();
        if (name != null && name.toLowerCase().contains(kw)) return true;
        if (type != null && type.name().toLowerCase().contains(kw)) return true;
        if (compatibleEquipment != null) {
            for (String eq : compatibleEquipment) {
                if (eq != null && eq.toLowerCase().contains(kw)) return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Hero{name='" + name + "', type=" + type + "}";
    }
}
