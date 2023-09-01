package com.mygdx.game.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.animations.Animation;
import com.mygdx.game.player.Player;

public class Vulture extends Enemy {

    private Animation flyAnimation;

    // constructor
    public Vulture(int life, float positionX, float positionY, int strength) {
        super(life, positionX - 10, positionY - 10, strength, (float) (Math.random() * 3) + 1, (float) (Math.random() * 1.5) + 1);
        Texture texture = new Texture("Enemies/Vulture/Fly.png");
        flyAnimation = new Animation(new TextureRegion(texture), 4, 0.5f, true);
        texture = new Texture("Enemies/Vulture/Spawn.png");
        spawnAnimation = new Animation(new TextureRegion(texture), 28, 0.5f, true);
        right = false;
        hitBox = new Rectangle(positionX, positionY, 54, 27);
    }

    // flying towards player
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
                    flyAnimation.flip();
                }
            } else {
                positionX += moveSpeedX;
                if (!right) {
                    right = true;
                    flyAnimation.flip();
                }
            }
            if (player.getHitBox().y + player.getHitBox().height / 2 + moveSpeedY < hitBox.y) {
                positionY -= moveSpeedY;
            } else if (player.getHitBox().y + player.getHitBox().height / 2 - moveSpeedY > hitBox.y) {
                positionY += moveSpeedY;
            }
        }
    }

    // hitbox update
    private void hitBoxPosition() {
        hitBox.x = positionX + 10;
        hitBox.y = positionY + 10;
    }

    public TextureRegion getAnimation() {
        if (spawning)
            return spawnAnimation.getFrame();
        else
            return flyAnimation.getFrame();
    }

    // render
    public void update(float dt, Player player) {
        super.update(dt, player);
        flyAnimation.update(dt);
        spawnAnimation.update(dt);
        hitBoxPosition();
        movement(player);
    }
}
