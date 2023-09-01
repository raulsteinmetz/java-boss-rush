package com.mygdx.game.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.animations.Animation;
import com.mygdx.game.player.Player;

public class Hyena extends Enemy {

    private Animation runAnimation;
    private float timeInAir;

    // constructor
    public Hyena(int life, float positionX, float positionY, int strength) {
        super(life, positionX - 20, positionY - 10, strength, (float) (Math.random() * 3) + 2, 0);
        Texture texture = new Texture("Enemies/Hyena/Run.png");
        runAnimation = new Animation(new TextureRegion(texture), 6, 0.5f, true);
        texture = new Texture("Enemies/Hyena/Spawn.png");
        spawnAnimation = new Animation(new TextureRegion(texture), 28, 0.5f, true);
        right = false;
        hitBox = new Rectangle(positionX, positionY, 70, 35);
    }

    // movement
    private void movement(Player player) {
        if (!spawning) {
            if (right && player.getHitBox().x > hitBox.x && player.getHitBox().x < hitBox.x + hitBox.width)
                positionX += 0;
            else if (!right && player.getHitBox().x < hitBox.x
                    && player.getHitBox().x + player.getHitBox().width > hitBox.x)
                positionX += 0;
            else if (player.getHitBox().x + player.getHitBox().width / 2 < hitBox.x) {
                positionX -= moveSpeedX;
                if (right) {
                    right = false;
                    runAnimation.flip();
                }
            } else {
                positionX += moveSpeedX;
                if (!right) {
                    right = true;
                    runAnimation.flip();
                }
            }
        }
    }

    // gravity system
    public void gravityEffect() {
        if (!spawning) {
            float newPositionY = positionY - 2 * timeInAir;
            if (hitBox.y > 55) {
                positionY = newPositionY;
                timeInAir += 0.1;
            } else {
                timeInAir = 0;
            }
        }
    }

    // hit box update
    private void hitBoxPosition() {
        hitBox.x = positionX + 20;
        hitBox.y = positionY + 10;
    }

    public TextureRegion getAnimation() {
        if (spawning)
            return spawnAnimation.getFrame();
        else
            return runAnimation.getFrame();
    }

    // render
    public void update(float dt, Player player) {
        super.update(dt, player);
        runAnimation.update(dt);
        spawnAnimation.update(dt);
        hitBoxPosition();
        gravityEffect();
        movement(player);
    }
}
