package com.roimarai.project_rpg.weapon;

public abstract class Weapon {
    private String name;
    private boolean showName;
    private double damageLower;
    private double damageUpper;
    private double armorPenetration; // 50 = half, refer to the formula
    private double critChance; // 0 - 1
    private double critMultiplier; // 1+
    private double accuracy; // 0 - 1
    private double bleedChance; // 0 - 1
    private double bleedAmount;
    private int bleedDuration; // in turns
    private boolean isTrueDamage;

    public String getName() {
        return name;
    }

    public boolean isShowName() {
        return showName;
    }

    public double getDamageLower() {
        return damageLower;
    }

    public double getDamageUpper() {
        return damageUpper;
    }

    public double getArmorPenetration() {
        return armorPenetration;
    }

    public double getCritChance() {
        return critChance;
    }

    public double getCritMultiplier() {
        return critMultiplier;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getBleedChance() {
        return bleedChance;
    }

    public double getBleedAmount() {
        return bleedAmount;
    }

    public int getBleedDuration() {
        return bleedDuration;
    }

    public boolean getIsTrueDamage() {
        return isTrueDamage;
    }

    public boolean isInnate() {
        return false;
    }

    protected Weapon(
            String name, boolean showName, double damageLower, double damageUpper, double armorPenetration,
            double critChance, double critMultiplier, double accuracy
    ) {
        this(name, showName, damageLower, damageUpper, armorPenetration, critChance, critMultiplier, accuracy, 0.0, 0.0, 0, false);
    }

    protected Weapon(
            String name, boolean showName, double damageLower, double damageUpper, double armorPenetration,
            double critChance, double critMultiplier, double accuracy, boolean isTrueDamage
    ) {
        this(name, showName, damageLower, damageUpper, armorPenetration, critChance, critMultiplier, accuracy, 0.0, 0.0, 0, isTrueDamage);
    }

    protected Weapon(
            String name, boolean showName, double damageLower, double damageUpper, double armorPenetration,
            double critChance, double critMultiplier, double accuracy,
            double bleedChance, double bleedAmount, int bleedDuration, boolean isTrueDamage
    ) {
        this.name = name;
        this.showName = showName;
        this.damageLower = damageLower;
        this.damageUpper = damageUpper;
        this.armorPenetration = armorPenetration;
        this.critChance = critChance;
        this.critMultiplier = critMultiplier;
        this.accuracy = accuracy;
        this.bleedChance = bleedChance;
        this.bleedAmount = bleedAmount;
        this.bleedDuration = bleedDuration;
        this.isTrueDamage = isTrueDamage;
    }


}
