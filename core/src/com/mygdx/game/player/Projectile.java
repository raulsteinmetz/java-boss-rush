package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;


public class Projectile {
    private Rectangle projectile;
    private TextureRegion img;
    private Sound missedSound;
    private Sound hitSound;
    private float speed;
    boolean right;
    boolean active;

    // constructor
    public Projectile (float x, float y, float speed, boolean right, Texture arrowTexture) {
        projectile = new Rectangle(x, y, arrowTexture.getWidth(), arrowTexture.getHeight());
        img = new TextureRegion(arrowTexture);
        this.speed = speed;
        this.right = right;
        if(!right) {
            img.flip(true, false);
            projectile.x -= 40;
        }
        active = true;
        missedSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/SFX/ArrowWallSound.mp3"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/SFX/ArrowEnemySound.mp3"));
    }

    public Rectangle getProjectile () {
        return projectile;
    }

    public boolean getActivity () {
        return active;
    }

    public float getX () {
        return projectile.x;
    }

    // projectile trajectory
    private void moveX() {
        if (right)
            projectile.x += speed;
        else 
            projectile.x -= speed;
    }

    // projectile collides with enemie
    public void hit () {
        active = false;
        hitSound.play(0.2f);
    }

    // projectile collides with wall
    private void verify (float lim1, float lim2) {
        if (projectile.x <= lim1 || projectile.x + projectile.width >= lim2) {
            active = false;
            missedSound.play(0.1f);
        }
    }

    // render
    public void drawProjectile (SpriteBatch batch, float lim1, float lim2) {
        verify(lim1, lim2);
        if (active) {
            moveX();
            batch.draw(img, projectile.x, projectile.y);
        }
    }
}