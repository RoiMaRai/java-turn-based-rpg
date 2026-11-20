package com.roimarai.project_rpg.entity;

import java.util.List;
import com.roimarai.project_rpg.util.Debug;
import java.util.ArrayList;

public abstract class Entity {
	private static final double ARMOR_K = 50.0;
	
	private String name;
	private double maxHp;
	private double hp;
	private double armor;
	//private Weapon equippedWeapon;
	private final List<Double> bleedArray = new ArrayList<>();
	private int actionsPerTurn;
	
	public String getName() {
		return name;
	}
	public double getMaxHp() {
		return maxHp;
	}
	public double getHp() {
		return hp;
	}
	public double getArmor() {
		return armor;
	}
	//public Weapon getEquippedWeapon() {
	//	return equippedWeapon;
	//}
	public List<Double> getBleedArray() {
		return bleedArray;
	}
	public int getActionsPerTurn() {
		return actionsPerTurn;
	}
	
	public boolean isAlive() {
		return hp > 0;
	}
	
	public void takeDamage(double damageAmount) {
		takeDamage(damageAmount, 0.0);
	}
	
	public void takeDamage(double damageAmount, double armorPenetration) {
		if (damageAmount <= 0) {
			Debug.warn("Entity.takeDamage: damageAmount must be positive, got " + damageAmount);
			return;
		}
		if (armorPenetration < 0) {
			Debug.warn("Entity.takeDamage: armorPenetration cannot be negative, got " + armorPenetration);
			return;
		}
		
		double armorReductionMultiplier = ARMOR_K/(ARMOR_K + Math.max(0, armor - armorPenetration));
		double finalDamage = damageAmount * armorReductionMultiplier;
		
		hp = Math.max(0, hp - finalDamage);
	}
	
	public void heal(double healAmount) {
		if (!isAlive()) {
			Debug.warn("Entity.heal: Cannot heal dead entity, attempted to heal " + name);
			hp = 0;
			return;
		}
		
		if (healAmount <= 0) {
			Debug.warn("Entity.heal: healAmount must be positive, got " + healAmount);
			return;
		}
		
		hp = Math.min(hp + healAmount, maxHp);
	}
}
