package com.roimarai.project_rpg.entity;

import com.roimarai.project_rpg.weapon.innate.Unarmed;
import com.roimarai.project_rpg.weapon.Weapon;

import static com.roimarai.project_rpg.config.GameConfig.DEFAULT_BASE_DAMAGE_DEVIATION;

public class Player extends Entity {
    private static final int DEFAULT_LEVEL = 1;
    private static final double DEFAULT_BASE_HP = 100.0;
    private static final double DEFAULT_HP_PER_LEVEL = 10.0;
    private static final double DEFAULT_ARMOR = 0.0;
    private static final double DEFAULT_BASE_DAMAGE = 5.0;

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public Weapon getInnateWeapon() {
        return Unarmed.INSTANCE;
    }

    public Player(String name) {
        this(name, DEFAULT_LEVEL, DEFAULT_BASE_HP, DEFAULT_HP_PER_LEVEL,
                DEFAULT_ARMOR, DEFAULT_BASE_DAMAGE, DEFAULT_BASE_DAMAGE_DEVIATION,
                Unarmed.INSTANCE, 1);
    }

    public Player(String name, int level, double baseHp, double hpPerLevel, double armor, double baseDamage, double baseDamageDeviation, Weapon weapon, int actionsPerTurn) {
        super(name, level, baseHp, hpPerLevel, armor, baseDamage, baseDamageDeviation, weapon, actionsPerTurn);
    }

    public void printData() {
        System.out.println("Name: " + getName());
        System.out.println("Health: " + getHp() + "/" + getMaxHp());
        System.out.println("Level: " + getLevel());
        System.out.println("Armor: " + getArmor());
        System.out.println("Base damage: " + getBaseDamage());
        System.out.println("Weapon: " + getEquippedWeapon().getName());
    }
}
