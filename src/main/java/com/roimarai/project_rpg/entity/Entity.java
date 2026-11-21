package com.roimarai.project_rpg.entity;

import java.util.List;

import com.roimarai.project_rpg.combat.AttackResult;
import com.roimarai.project_rpg.util.Debug;
import com.roimarai.project_rpg.weapon.innate.NaturalAttack;
import com.roimarai.project_rpg.weapon.Weapon;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static com.roimarai.project_rpg.config.GameConfig.*;

public abstract class Entity {
	private String name;
	private int level;
	private double baseHp;
	private double hpPerLevel;
	private double maxHp;
	private double hp;
	private double armor;
	private double baseDamage;
	private double baseDamageDeviation;
	private Weapon equippedWeapon;
	private final List<Double> bleedArray = new ArrayList<>();
	private int actionsPerTurn;

	/////////////////////////////////////////////////////////

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
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

	public double getBaseDamage() {
		return baseDamage;
	}

	public Weapon getEquippedWeapon() {
		return equippedWeapon;
	}
	public List<Double> getBleedArray() {
		return bleedArray;
	}
	public int getActionsPerTurn() {
		return actionsPerTurn;
	}
	/////////////////////////////////////////////////////////
	public void setActionsPerTurn(int actionsPerTurn){
		this.actionsPerTurn = actionsPerTurn;
	}
	/////////////////////////////////////////////////////////
	protected Entity(String name, int level, double baseHp, double hpPerLevel, double armor, double baseDamage) {
		this(name, level, baseHp, hpPerLevel, armor, baseDamage, DEFAULT_BASE_DAMAGE_DEVIATION, NaturalAttack.INSTANCE, 1);
	}

	protected Entity(String name, int level, double baseHp, double hpPerLevel, double armor, double baseDamage, int actionsPerTurn) {
		this(name, level, baseHp, hpPerLevel, armor, baseDamage, DEFAULT_BASE_DAMAGE_DEVIATION, NaturalAttack.INSTANCE, actionsPerTurn);
	}

	protected Entity(String name, int level, double baseHp, double hpPerLevel, double armor, double baseDamage, double baseDamageDeviation, Weapon weapon, int actionsPerTurn) {
		this.name = name;
		this.level = level;
		this.baseHp = baseHp;
		this.hpPerLevel = hpPerLevel;
		this.maxHp = calculateMaxHp();
		changeHpTo(this.maxHp);
		this.armor = armor;
		this.baseDamage = baseDamage;
		this.baseDamageDeviation = baseDamageDeviation;
		this.equippedWeapon = weapon;
		this.actionsPerTurn = actionsPerTurn;
	}

	/////////////////////////////////////////////////////////

	public boolean isPlayer() {
		return false;
	}

	public Weapon getInnateWeapon() {
		return NaturalAttack.INSTANCE;
	}

	public final boolean isAlive() {
		return hp > 0;
	}

	protected final double calculateMaxHp() {
		return baseHp + hpPerLevel * (level - 1);
	}

	public final void changeHpTo(double newHp) {
		hp = Math.clamp(newHp, 0, maxHp);
	}

	public final void changeLevelTo(int newLevel) {
		level = Math.max(1,newLevel);

		double oldMaxHp = maxHp;
		maxHp = calculateMaxHp();
		if (isAlive()) {
			double adjustedHp = hp + (maxHp - oldMaxHp);
			changeHpTo(Math.max(1.0, adjustedHp));
		}
	}

	public final void levelUp() {
		changeLevelTo(level+1);
	}

	private static double rollBetween(double min, double max) {
		if (min == max) {
			return min;
		}
		return ThreadLocalRandom.current().nextDouble(min,max);
	}

	public final AttackResult attack(Entity target) {
        return attack(target, false);
    }

	public final AttackResult attack(Entity target, boolean isTrueDamage) {
		if (target == null) {
			Debug.crucial("Entity.attack: Entity expected, got null");
			return AttackResult.miss();
		}
		if (!this.isAlive()) {
			Debug.warn("Entity.attack: attacker must be alive (" + this + ")");
		}
		if (!target.isAlive()) {
			Debug.warn("Entity.attack: cannot attack dead Entity (" + target.getName() + ")");
			return AttackResult.miss();
		}
		// Roll for hit chance;
		if (ThreadLocalRandom.current().nextDouble() > equippedWeapon.getAccuracy()) {
			return AttackResult.miss();
		}

		boolean attackTrueDamage = isTrueDamage || equippedWeapon.getIsTrueDamage();
		boolean isCrit = ThreadLocalRandom.current().nextDouble() < equippedWeapon.getCritChance();

		double variedBaseDamage = baseDamage * rollBetween(1 - baseDamageDeviation, 1 + baseDamageDeviation);
		double weaponDamage = rollBetween(equippedWeapon.getDamageLower(), equippedWeapon.getDamageUpper());
		double criticalMultiplier = isCrit ? equippedWeapon.getCritMultiplier() : 1.0;
		double levelMultiplier = 1.0 + DAMAGE_SCALE_PER_LEVEL * (level - 1);

		double outgoingDamage = (variedBaseDamage + weaponDamage) * criticalMultiplier * levelMultiplier;
		double actualDamage = target.takeDamage(outgoingDamage, equippedWeapon.getArmorPenetration(), attackTrueDamage);
		return new AttackResult(outgoingDamage, actualDamage, isCrit, false);
	}

	public final void takeDamage(double damageAmount) {
		takeDamage(damageAmount, 0.0, false);
	}

	public final double takeDamage(double incomingDamage, double armorPenetration) {
		return takeDamage(incomingDamage, armorPenetration, false);
	}

	public final double takeDamage(double incomingDamage, boolean isTrueDamage) {
		return takeDamage(incomingDamage, 0.0, isTrueDamage);
	}
	
	public final double takeDamage(double incomingDamage, double armorPenetration, boolean isTrueDamage) {
		if (!isAlive()) {
			return 0.0;
		}

		if (incomingDamage <= 0) {
			Debug.warn("Entity.takeDamage: incomingDamage must be positive, got " + incomingDamage);
			return 0.0;
		}
		if (armorPenetration < 0) {
			Debug.warn("Entity.takeDamage: armorPenetration cannot be negative, got " + armorPenetration);
			return 0.0;
		}

		double effectiveArmor = Math.max(0.0, this.armor - armorPenetration);
		double armorReductionMultiplier = isTrueDamage ? 1.0 : ARMOR_K / (ARMOR_K + effectiveArmor);
		double finalDamage = incomingDamage * armorReductionMultiplier;

		changeHpTo(this.hp - finalDamage);
		return finalDamage;
	}
	
	public final void heal(double healAmount) {
		if (!isAlive()) {
			Debug.warn("Entity.heal: cannot heal dead entity, attempted to heal " + name);
			hp = 0;
			return;
		}
		
		if (healAmount <= 0) {
			Debug.warn("Entity.heal: healAmount must be positive, got " + healAmount);
			return;
		}
		
		hp = Math.min(hp + healAmount, maxHp);
	}

	public final Weapon equip(Weapon weapon) {
		Weapon oldWeapon = this.equippedWeapon;
		if (weapon == null) {
			Debug.crucial("Entity.equip: Weapon expected, got null");
			return oldWeapon;
		}
		if (oldWeapon == weapon) {
			Debug.warn("Entity.equip: cannot equip the same item");
			return oldWeapon;
		}
		this.equippedWeapon = weapon;
		return  oldWeapon;
	}

	public final Weapon unequip() {
		Weapon oldWeapon = this.equippedWeapon;
		if (oldWeapon.isInnate()) {
			Debug.warn("Entity.uneqiup: cannot unequip innate weapon (" + oldWeapon.getName() + ")");
			return oldWeapon;
		}
		return equip(getInnateWeapon());
	}
}
