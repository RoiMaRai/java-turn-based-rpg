package com.roimarai.project_rpg.weapon.onehanded;

import com.roimarai.project_rpg.weapon.Weapon;

public class Sword extends Weapon {
    public Sword(String name, boolean showName, double damageLower, double damageUpper, double armorPenetration, double critChance, double critMultiplier, double accuracy) {
        super(name, showName, damageLower, damageUpper, armorPenetration, critChance, critMultiplier, accuracy);
    }

    public Sword(String name, boolean showName, double damageLower, double damageUpper, double armorPenetration, double critChance, double critMultiplier, double accuracy, double bleedChance, double bleedAmount, int bleedDuration, boolean isTrueDamage) {
        super(name, showName, damageLower, damageUpper, armorPenetration, critChance, critMultiplier, accuracy, bleedChance, bleedAmount, bleedDuration, isTrueDamage);
    }

    public static Sword armingSword() {
        return new Sword(
                "Arming Sword",true,
                4.0,8.0,
                0.0,0.2,
                1.5, 0.9
        );
    }
}
