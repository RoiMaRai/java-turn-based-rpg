package com.roimarai.project_rpg.entity.enemy;

import static com.roimarai.project_rpg.config.GameConfig.DEFAULT_BASE_DAMAGE_DEVIATION;

import com.roimarai.project_rpg.entity.Entity;
import com.roimarai.project_rpg.weapon.innate.NaturalAttack;
import com.roimarai.project_rpg.weapon.Weapon;

public class Slime extends Entity {
    private static final double DEFAULT_BASE_HP = 20.0;
    private static final double DEFAULT_HP_PER_LEVEL = 10.0;
    private static final double DEFAULT_ARMOR = 0.0;
    private static final double DEFAULT_BASE_DAMAGE = 15.0;

    public Slime(int level) {
        this(level, DEFAULT_BASE_HP, DEFAULT_HP_PER_LEVEL, DEFAULT_ARMOR, DEFAULT_BASE_DAMAGE, DEFAULT_BASE_DAMAGE_DEVIATION);
    }

    public Slime(int level, double baseHp, double hpPerLevel, double armor, double baseDamage, double baseDamageDeviation) {
        this("Slime", level, baseHp, hpPerLevel, armor, baseDamage, baseDamageDeviation, NaturalAttack.INSTANCE, 1);
    }

    public Slime(String name, int level, double baseHp, double hpPerLevel, double armor, double baseDamage, double baseDamageDeviation, Weapon weapon, int actionsPerTurn) {
        super(name, level, baseHp, hpPerLevel, armor, baseDamage, baseDamageDeviation, weapon, actionsPerTurn);
    }
}
