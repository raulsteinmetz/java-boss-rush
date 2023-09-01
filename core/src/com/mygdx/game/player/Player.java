package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.animations.Animation;
import com.mygdx.game.enemies.Enemy;

import java.util.*;

public class Player {
    // ESTADO E CARACTERISTICAS DO PLAYER
    private int life;
    private float invincibleTime;
    private float currentInvincibleTime;
    private float positionX;
    private float positionY;
    private float moveSpeedX; // velocidade horizontal
    private float jumpSpeed; // velocidade do pulo
    private float timeInAir; // tempo no ar - usado para gravidade
    private float startJump; // posicao do comeco do pulo (y)
    private float rollSpeed; // velocidade de rolamento
    private boolean jumping; // player esta pulando
    private boolean isFacingRight; // player virado para a direita
    private boolean running; // player esta se movendo horizontalmente
    private boolean attacking; // player esta atacando
    private boolean rolling; // player esta em rolamento
    private boolean shooted; // player atirou
    private boolean grounded; // player esta no chao
    private boolean gotHited;
    private boolean dead;

    private Rectangle hitBox; // fisica (corpo) do jogador

    // TEXTURAS, ANIMACOES, E SONS
    private Texture arrowTexture;
    private Texture lifeTexture;
    private Texture avatarTexture;
    private Sound jumpSound;
    private Sound shootSound;
    private Sound rollSound;
    private Sound damageSound;
    private Sound deathSound;
    private Animation runAnimation;
    private Animation idleAnimation;
    private Animation jumpAnimation;
    private Animation fallAnimation;
    private Animation attackAnimation;
    private Animation rollAnimation;
    private Animation dieAnimation;

    // Players arrows
    private ArrayList<Projectile> projectiles;

    // Defines gravity logic
    public float gravity; // forca da gravidade


    // Constructor
    public Player(float positionX, float positionY, float moveSpeedX, float jumpSpeed, float gravity) {
        lifeTexture = new Texture("Character/Archer/Life.png");
        avatarTexture = new Texture("Character/Archer/Avatar.png");
        Texture texture = new Texture("Character/Archer/SpriteSheets/Run.png");
        runAnimation = new Animation(new TextureRegion(texture), 8, 0.5f, true);
        texture = new Texture("Character/Archer/SpriteSheets/Idle.png");
        idleAnimation = new Animation(new TextureRegion(texture), 4, 0.5f, true);
        texture = new Texture("Character/Archer/SpriteSheets/Jump.png");
        jumpAnimation = new Animation(new TextureRegion(texture), 4, 0.5f, false);
        texture = new Texture("Character/Archer/SpriteSheets/Fall.png");
        fallAnimation = new Animation(new TextureRegion(texture), 2, 0.5f, false);
        texture = new Texture("Character/Archer/SpriteSheets/Attack.png");
        attackAnimation = new Animation(new TextureRegion(texture), 7, 0.5f, true);
        texture = new Texture("Character/Archer/SpriteSheets/Rolling.png");
        rollAnimation = new Animation(new TextureRegion(texture), 7, 0.5f, true);
        texture = new Texture("Character/Archer/SpriteSheets/Die.png");
        dieAnimation = new Animation(new TextureRegion(texture), 8, 1f, false);
        hitBox = new Rectangle(positionX, positionY, 42, 55);
        this.positionX = positionX - 45;
        this.positionY = positionY - 42;
        this.moveSpeedX = moveSpeedX;
        this.jumpSpeed = jumpSpeed;
        this.gravity = gravity;
        isFacingRight = true;
        running = false;
        attacking = false;
        timeInAir = 0;
        shooted = false;
        rolling = false;
        grounded = true;
        gotHited = false;
        dead = false;
        invincibleTime = 1;
        currentInvincibleTime = 0;
        rollSpeed = 10;
        projectiles = new ArrayList<Projectile>();
        shootSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/SFX/ShootSound.mp3"));
        arrowTexture = new Texture("Projectiles/Arrow.png");
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/SFX/JumpSound.mp3"));
        rollSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/SFX/RollSound.mp3"));
        damageSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/SFX/Damage.wav"));
        deathSound = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/SFX/DeathSound.mp3"));
    }

    // returns what animation is currently playing
    public TextureRegion getAnimation() {
        if (dead)
            return dieAnimation.getFrame();
        else if (rolling)
            return rollAnimation.getFrame();
        else if (running && grounded)
            return runAnimation.getFrame();
        else if (jumping)
            return jumpAnimation.getFrame();
        else if (!grounded)
            return fallAnimation.getFrame();
        else if (attacking)
            return attackAnimation.getFrame();
        else
            return idleAnimation.getFrame();
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    // player gets damaged by enemy
    public void takeHit (Enemy enemy) {
        float enemyPosX = enemy.getHitBox().x;
        float xMove = 50;
        if (hitBox.x <= 100 && hitBox.x - 50 >= 0)
            xMove = hitBox.x - 50;
        else if (hitBox.x + hitBox.width >= 700 && - (hitBox.x + hitBox.width - 750) >= 0)
            xMove = -(hitBox.x + hitBox.width - 750);
        
        if (!gotHited && !rolling && !dead) {
            gotHited = true;
            currentInvincibleTime = 0;
            if (enemyPosX < hitBox.x)
                positionX += xMove;
            else 
                positionX -= xMove;
            positionY += 30;
            life--;
            damageSound.play(0.3f);
            enemy.hit(hitBox.x);
        }
    }

    public boolean getStatus () {
        return dead;
    }

    public void setDefault() {
        life = 3;
        positionX = 10;
        positionY = 13;
        dead = false;
    }

    // hitted by hydra (phase 3 boss)
    public void takeHitNokb(float posX)
    {

        if (!gotHited && !rolling && !dead) {
            gotHited = true;
            currentInvincibleTime = 0;
            positionY += 30;
            life--;
            damageSound.play(0.3f);
        }
    }

    // player gets invencible once its hit
    private void verifyInvencibility (float dt) {
        if (gotHited && currentInvincibleTime < invincibleTime)
            currentInvincibleTime += dt;
        else if (gotHited)
            gotHited = false;
    }

    public ArrayList<Projectile> getProjectiles () {
        return projectiles;
    }

    public float getPositionX() {
        return positionX;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public void setGrounded (boolean grounded) {
        this.grounded = grounded;
    }

    // horizontal movement
    public void moveX(ArrayList<Rectangle> objects) {
        if (!dead) {
            if (Gdx.input.isKeyPressed(Input.Keys.D) && hitBox.x + hitBox.width < 750) {
                float newPositionX;
                if (rolling)
                    newPositionX = positionX + rollSpeed;
                else
                    newPositionX = positionX + moveSpeedX;
                if (!isFacingRight) {
                    dieAnimation.flip();
                    runAnimation.flip();
                    idleAnimation.flip();
                    jumpAnimation.flip();
                    attackAnimation.flip();
                    rollAnimation.flip();
                    fallAnimation.flip();
                }
                isFacingRight = true;
                positionX = newPositionX;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && hitBox.x > 50) {
                float newPositionX;
                if (rolling)
                    newPositionX = positionX - rollSpeed;
                else
                    newPositionX = positionX - moveSpeedX;
                if (isFacingRight) {
                    dieAnimation.flip();
                    runAnimation.flip();
                    idleAnimation.flip();
                    jumpAnimation.flip();
                    attackAnimation.flip();
                    rollAnimation.flip();
                    fallAnimation.flip();
                }
                isFacingRight = false;
                positionX = newPositionX;
            }
        }
    }

    // jumping logic
    public void jump(ArrayList<Rectangle> objects) {
        if (!dead) {
            if (hitBox.y + hitBox.height > 550) {
                jumping = false;
            } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && grounded) {
                jumpSound.play();
                jumpAnimation.reset();
                startJump = positionY;
                jumping = true;
            } else if (!Gdx.input.isKeyPressed(Input.Keys.SPACE) || positionY > startJump + 150) {
                jumping = false;
            }

            if (jumping)
                positionY += jumpSpeed;
        }
    }

    // gravity system's function
    public void gravityEffect(ArrayList<Rectangle> objects) {
        float newPositionY = positionY - gravity * timeInAir;
        if (hitBox.y > 55 && (!jumping || dead) && !grounded) {
            positionY = newPositionY;
            timeInAir += 0.1;
        } else {
            timeInAir = 0;
        }
    }

    // projectile spawner
    private void shoot() {
        projectiles.add(new Projectile(hitBox.x + 15, hitBox.y + 30, 10, isFacingRight, arrowTexture));
        shootSound.play(0.3f);
    }

    // attack (shoots arrow)
    private void attack() {
        if (!dead) {
            if (Gdx.input.isKeyPressed(Input.Keys.P) && grounded && !running) {
                if (!attacking)
                    attackAnimation.reset();
                attacking = true;
                if (attackAnimation.getFrameIdx() == 6 && !shooted) {
                    shoot();
                    shooted = true;
                } else if (attackAnimation.getFrameIdx() != 6) {
                    shooted = false;
                }
            } else
                attacking = false;
        }
    }

    // rolling system
    private void roll() {
        if (!dead) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && grounded) {
                if (!rolling)
                    rollAnimation.reset();
                rolling = true;
                rollSound.play();
            }
            if (rollAnimation.getFrameIdx() == 6) {
                rolling = false;
            }
        }
    }

    // moving inputs
    private void isRunning(ArrayList<Rectangle> objects) {
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            running = true;
        } else {
            running = false;
        }
    }

    // players hitbox position update
    private void hitBoxPosition(ArrayList<Rectangle> objects) {
        hitBox.x = positionX + 45;
        hitBox.y = positionY + 42;
    }

    // removes projectiles
    private void removeShoots() {
        for (int i = 0; i < projectiles.size(); i++) {
            if (!projectiles.get(i).getActivity()) {
                projectiles.remove(i);
            }
        }
    }

    // drawing health on UI function
    private void drawLife(SpriteBatch batch) {
        batch.draw(avatarTexture, 5, 555);
        int posX = 55;
        for (int i = 0; i < life; i++) {
            batch.draw(lifeTexture, posX, 560);
            posX += 35;
        }
        if (life <= 0 && !dead) {
            dieAnimation.reset();
            dead = true;
            deathSound.play(0.6f);
        }
    }

    // render
    public void update(ArrayList<Rectangle> objects, float dt, SpriteBatch batch) {
        runAnimation.update(dt);
        idleAnimation.update(dt);
        jumpAnimation.update(dt);
        attackAnimation.update(dt);
        rollAnimation.update(dt);
        fallAnimation.update(dt);
        dieAnimation.update(dt);
        verifyInvencibility(dt);
        for (Projectile projectile : projectiles) {
            projectile.drawProjectile(batch, 50, 750);
        }
        removeShoots();
        drawLife(batch);
        roll();
        attack();
        moveX(objects);
        jump(objects);
        gravityEffect(objects);
        hitBoxPosition(objects);
        isRunning(objects);
    }

}
