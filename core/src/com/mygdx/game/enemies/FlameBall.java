package com.mygdx.game.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.animations.Animation;

public class FlameBall {
    public Animation animation;

    private int positionX;
    private int positionY;
    // hitbox
    private Rectangle hitbox;
    boolean active;
    // horizontal speed
    private int dropSpeed;
    private boolean isRight;

    //constructor
    public FlameBall(int positionX, int positionY){
        Texture texture = new Texture("FlameBall/flameball.png");
        animation= new Animation(new TextureRegion(texture), 4, 1f, true);
        active = true;
        hitbox = new Rectangle(positionX, positionY, 32, 32);
        this.positionX = positionX;
        this.positionY = positionY;
        isRight = true;
        dropSpeed = 4;
    }

    // render
    public void update(float delta)
    {
        animation.update(delta);
        hitbox.x = positionX + 14;
        hitbox.y = positionY + 10;
        move();
    }

    // movement and horizontal position setting
    public void move()
    {
        if(positionY < 50)
        {
            positionY = 500;
            if(isRight)
            {
                positionX -= 700/(4 * 2);
                isRight = false;
            } else {
                positionX += 700/(4 * 2);
                isRight = true;
            }
        } else {
            positionY-=dropSpeed;
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public TextureRegion getAnimation() {
        return animation.getFrame();
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
