package com.roimarai.project_rpg.weapon.innate;

import com.roimarai.project_rpg.weapon.Weapon;

import static com.roimarai.project_rpg.config.GameConfig.HUMANOID_DEFAULT_ACCURACY;

public final class Unarmed extends Weapon {
    public static final Unarmed INSTANCE = new Unarmed();

    @Override
    public boolean isInnate() {
        return true;
    }

    public Unarmed() {
        super("Unarmed", false, 0, 0, 0,
                0.1, 1.5, HUMANOID_DEFAULT_ACCURACY);
    }
}
