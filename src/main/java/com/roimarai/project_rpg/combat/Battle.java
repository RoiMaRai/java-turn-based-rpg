package com.roimarai.project_rpg.combat;

import com.roimarai.project_rpg.entity.Entity;
import com.roimarai.project_rpg.util.Debug;

import java.util.*;

public class Battle {
    private final List<Entity> playerSide = new ArrayList<>();
    private final List<Entity> enemySide = new ArrayList<>();
    private final Queue<Entity> turnQueue = new ArrayDeque<>();

    private int turns = 1;
    private Entity currentTurnOf;

    public int getTurns() {
        return turns;
    }

    public Entity getCurrentTurnOf() {
        return currentTurnOf;
    }

    public List<Entity> getPlayerSide() {
        return Collections.unmodifiableList(playerSide);
    }

    public List<Entity> getEnemySide() {
        return Collections.unmodifiableList(enemySide);
    }

    public List<Entity> getLivingPlayerSide() {
        return playerSide.stream().filter(Entity::isAlive).toList();
    }

    public List<Entity> getLivingEnemySide() {
        return enemySide.stream().filter(Entity::isAlive).toList();
    }

    public boolean isOver() {
        return !getLivingPlayerSide().isEmpty() && !getLivingEnemySide().isEmpty();
    }

    public boolean addEntityToSide(Entity entity, List<Entity> targetSide, String sideName){
        if (entity == null) {
            Debug.crucial("Battle.addTo" + sideName + ": Entity expected, got null");
            return false;
        }
        if (!entity.isAlive()) {
            Debug.warn("Battle.addTo" + sideName + ": cannot add dead Entity (" + entity.getName() + ")");
            return false;
        }
        if (playerSide.contains(entity) || enemySide.contains(entity)) {
            Debug.warn("Battle.addTo" + sideName + ": Entity is already is battle (" + entity.getName() + ")");
            return false;
        }
        targetSide.add(entity);
        return true;
    }

    public final void addToPlayerSide(Entity entity) {
        addEntityToSide(entity, playerSide, "PlayerSide");
    }

    public final void addToEnemySide(Entity entity) {
        addEntityToSide(entity, enemySide, "EnemySide");
    }

    public Battle(List<Entity> playerSide, List<Entity> enemySide) {
        if (playerSide == null || playerSide.isEmpty()) {
            Debug.crucial("Battle: playerSide requires at least one entity");
            throw new IllegalArgumentException("playerSide cannot be empty");
        }
        if (enemySide == null || enemySide.isEmpty()) {
            Debug.crucial("Battle: enemySide requires at least one entity");
            throw new IllegalArgumentException("enemySide cannot be empty");
        }
        playerSide.forEach(this::addToPlayerSide);
        enemySide.forEach(this::addToEnemySide);
        startNewRound();
    }

    public final void startNewRound() {
        turnQueue.clear();
        turnQueue.addAll(playerSide);
        turnQueue.addAll(enemySide);
        currentTurnOf = turnQueue.poll();
    }
}
