package com.roimarai.project_rpg.weapon.innate;

import com.roimarai.project_rpg.weapon.Weapon;

import static com.roimarai.project_rpg.config.GameConfig.NATURAL_DEFAULT_ACCURACY;

public final class NaturalAttack extends Weapon {
    public static final NaturalAttack INSTANCE = new NaturalAttack();

    @Override
    public boolean isInnate() {
        return true;
    }

    public NaturalAttack() {
        super("Natural Attack", false, 0, 0, 0,
                0.05, 1.5, NATURAL_DEFAULT_ACCURACY);
    }
}
