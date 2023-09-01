package com.mygdx.game.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.animations.Animation;
import com.mygdx.game.player.Player;

import java.util.ArrayList;

public class Hydra extends Enemy {

    private float bodyRadius;

    private Animation facingSideAnimation;
    private Animation specialAttackAnimation;
    private Animation currentAnimation;

    // constrols where hydra is looking at
    private boolean isPlayerLeft;

    // flamebols in special attack
    private ArrayList<FlameBall> listOfBalls;

    private boolean flameBallsIniciated;

    public int action; // controla estado do boss

    private int isFacingRight; // controla movimento e animação horizontal
    private int isGoingUp; // controla movimento horizontal

    // bools and ints used to control special attack
    private int xAxeBiggerThenCenter;
    private int yAxeBiggerThenCenter;
    private boolean transitionStarted;

    // constructor
    public Hydra(int life, float positionX, float positionY, int strength, float moveSpeedX, float moveSpeedY,
            float bodyRadius) {
        super(life, positionX, positionY, strength, moveSpeedX, moveSpeedY);
        this.bodyRadius = bodyRadius;
        action = 0;
        Texture side = new Texture("Enemies/Hydra/tile000.png");
        Texture special = new Texture("Enemies/Hydra/tile002.png");
        facingSideAnimation = new Animation(new TextureRegion(side), 8, 0.5f, true);
        specialAttackAnimation = new Animation(new TextureRegion(special), 4, 1f, true);
        Texture spawnTexture = new Texture("Enemies/Hydra/Spawn.png");
        spawnAnimation = new Animation(new TextureRegion(spawnTexture), 31, 0.5f, true);
        isFacingRight = 1;
        isGoingUp = 1;
        hitBox = new Rectangle(positionX, positionY, 100, 100);
        currentAnimation = facingSideAnimation;
        right = true;
        listOfBalls = new ArrayList<FlameBall>();
        flameBallsIniciated = false;
        life = 10;
    }

    // moving around
    public void move(Player player) {
        if (!spawning) {
            if (action == 0) // moving
            {
                if (player.getHitBox().x < hitBox.x) {
                    isPlayerLeft = true;
                } else {
                    isPlayerLeft = false;
                }
                if (isPlayerLeft && right) {
                    currentAnimation.flip();
                    right = false;
                } else if (!isPlayerLeft && !right) {
                    currentAnimation.flip();
                    right = true;
                }

                currentAnimation = facingSideAnimation;
                if (hitBox.x + hitBox.width > 750) {
                    isFacingRight = -1;
                } else if (hitBox.x < 50) {
                    isFacingRight = 1;
                }
                if (hitBox.y + hitBox.height > 550) {
                    isGoingUp = -1;
                } else if (hitBox.y < 50) {
                    isGoingUp = 1;
                }

                positionX = positionX + moveSpeedX * isFacingRight;
                positionY = positionY + moveSpeedY * isGoingUp;

            } else if (action == 1) // flame ball
            {
                currentAnimation = specialAttackAnimation;
                transitionToCenter();
                if (!flameBallsIniciated) {
                    iniciateFlameBalls();
                }

            }
        }
    }

    // spawn flame balls
    private void iniciateFlameBalls() {
        for (int i = 0; i < 6; i++) {
            listOfBalls.add(new FlameBall(130 + i * 800 / 7, 500));
        }
        flameBallsIniciated = true;
    }

    // transiting to center screen when special attack initiates
    private void transitionToCenter() {
        if (positionX > 315) {
            positionX -= 1;
        } else if (positionX < 314) {
            positionX += 1;
        }
        if (positionY > 150) {
            positionY -= 1;
        } else if (positionY < 149) {
            positionY += 1;
        }
    }

    // updates hitbox to match with where the sprite is at
    private void hitBoxPosition() {
        hitBox.x = positionX + 35;
        hitBox.y = positionY + 120;
    }

    public TextureRegion getCurrentAnimation() {
        if (spawning)
            return spawnAnimation.getFrame();
        else
            return currentAnimation.getFrame();
    }

    // render
    public void update(float dt, Player player) {
        super.update(dt, player);
        currentAnimation.update(dt);
        spawnAnimation.update(dt);
        hitBoxPosition();
        action = (life + 1) % 2;
        move(player);
    }

    @Override
    public void hit(float playerX) {

    }

    public ArrayList<FlameBall> getListOfBalls() {
        return listOfBalls;
    }

    public int getAction() {
        return action;
    }
}