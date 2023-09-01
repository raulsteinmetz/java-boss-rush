package com.mygdx.game.enemies;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.animations.Animation;
import com.mygdx.game.player.Player;

public class Enemy {
    protected int life;
    protected float positionX;
    protected float positionY;
    protected int strength;
    protected float moveSpeedX;
    protected float moveSpeedY;
    protected boolean right;
    protected Rectangle hitBox;
    protected float timeSpawning;
    protected boolean spawning;
    protected Animation spawnAnimation;

    public Enemy(int life, float positionX, float positionY, int strength, float moveSpeedX, float moveSpeedY) {
        this.life = life;
        this.positionX = positionX;
        this.positionY = positionY;
        this.strength = strength;
        this.moveSpeedX = moveSpeedX;
        this.moveSpeedY = moveSpeedY;
        spawning = true;
        timeSpawning = 0;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getPositionX() {
        return positionX;
    }

    public int getLife() {
        return life;
    }

    public boolean getStatus() {
        return spawning;
    }

    public int getStrength() {
        return strength;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    // gets damaged by player
    public void getHit(float arrowX) {
        if (!spawning) {
            life--;
            if (arrowX < hitBox.getX()) {
                positionX += 50;
            } else {
                positionX -= 50;
            }
            positionY += 30;
        }
    }

    // damages player
    public void hit(float playerX) {
        if (!spawning) {
            if (playerX < hitBox.getX()) {
                positionX += 50;
            } else {
                positionX -= 50;
            }
            positionY += 30;
        }
    }

    public void spawn (float dt) {
        if (spawning && timeSpawning <= 3)
            timeSpawning += dt;
        else
            spawning = false;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void update(float dt, Player player) {
        spawn(dt);
    }
}