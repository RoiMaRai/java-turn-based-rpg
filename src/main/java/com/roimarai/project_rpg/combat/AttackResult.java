package com.roimarai.project_rpg.combat;

public record AttackResult(double outgoingDamage, double actualDamage, boolean isCrit, boolean isMiss) {
    public static AttackResult miss() {
        return new AttackResult(0.0,0.0,false,true);
    }
}
