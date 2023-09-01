package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.enemies.Enemy;
import com.mygdx.game.enemies.Hyena;
import com.mygdx.game.MedievalGame;
import com.mygdx.game.player.Player;
import com.mygdx.game.enemies.Vulture;
import com.mygdx.game.midGameUI.GameOverMenu;
import com.mygdx.game.midGameUI.Story;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;

import java.util.ArrayList;

public class Phase2 extends Phase {
    ArrayList<Rectangle> phasePhysicShapes;
    private float waitingTime;
    private boolean spawned;

    // constructor
    public Phase2(MedievalGame game, SpriteBatch batch, Player player) {
        this.medievalGame = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, 800, 600);
        gamePort = new FitViewport(medievalGame.V_WIDTH, medievalGame.V_HEIGHT, gamecam);
        background = new Texture("Sceneries/Phase02.jpg");
        this.player = player;
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        music = Gdx.audio.newMusic(Gdx.files.internal("SoundEffects/Musics/Music02.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        player.setDefault();
        music.play();
        enemies = new ArrayList<Enemy>();
        enemiesKilled = 0;
        waitingTime = 5;
        spawned = false;
        currentPhase = 2;
        numWave = 1;
    }

    // game platforms collision physics
    @Override
    public void isGrounded() {
        if (player.getHitBox().y <= 55)
            player.setGrounded(true);
        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            player.setGrounded(false);
        else if (player.getHitBox().x > 170 && player.getHitBox().x < 340 && player.getHitBox().y >= 165 && player.getHitBox().y <= 175)
            player.setGrounded(true);
        else if (player.getHitBox().x > 70 && player.getHitBox().x < 240 && player.getHitBox().y >= 290 && player.getHitBox().y <= 300)
            player.setGrounded(true);
        else if (player.getHitBox().x > 520 && player.getHitBox().x < 690 && player.getHitBox().y >= 290 && player.getHitBox().y <= 300)
            player.setGrounded(true);
        else if (player.getHitBox().x > 420 && player.getHitBox().x < 590 && player.getHitBox().y >= 165 && player.getHitBox().y <= 175)
            player.setGrounded(true);
        else if (player.getHitBox().x > 290 && player.getHitBox().x < 465 && player.getHitBox().y >= 420 && player.getHitBox().y <= 430)
            player.setGrounded(true);
        else
            player.setGrounded(false);
    }

    // rendering enemies
    public void drawEnemies (float delta) {
        for (Enemy enemy : enemies) {
            enemy.update(delta, player);
            //shapeRenderer.rect(enemy.getHitBox().getX(), enemy.getHitBox().getY(), enemy.getHitBox().getWidth(), enemy.getHitBox().getHeight());
            if(enemy instanceof Vulture) {
                batch.draw(((Vulture)enemy).getAnimation(), enemy.getPositionX(), enemy.getPositionY());
            } else if (enemy instanceof Hyena)
                batch.draw(((Hyena)enemy).getAnimation(), enemy.getPositionX(), enemy.getPositionY());
        }
        removeEnemies();
    }

    public void spawnEnemies(float delta) {
        if(enemiesKilled == 1 && numWave == 1) {
            spawned = false;
            numWave++;
        } else if (enemiesKilled == 3 && numWave == 2) {
            spawned = false;
            numWave++;
        } else if (enemiesKilled == 6 && numWave == 3) {
            spawned = false;
            numWave++;
        }
        else if (enemiesKilled == 10 && numWave == 4) {
            spawned = false;
            numWave++;
        } else if (enemiesKilled == 15 && numWave == 5) {
            spawned = false;
            numWave++;
        }
        
        if (!spawned && enemiesKilled < 1 && numWave == 1) {
            spawned = true;
            enemies.add(new Hyena(4, 450, 55, 1));
        } else if (!spawned && enemiesKilled < 3 && numWave == 2) {
            spawned = true;
            enemies.add(new Hyena(4, 500, 55, 1));
            enemies.add(new Vulture(3, 500, 500, 1));
        } else if (!spawned && enemiesKilled < 6 && numWave == 3) {
            spawned = true;
            enemies.add(new Vulture(3, 500, 500, 1));
            enemies.add(new Vulture(3, 300, 400, 1));
            enemies.add(new Vulture(3, 100, 300, 1));
        } else if (!spawned && enemiesKilled < 10 && numWave == 4) {
            spawned = true;
            enemies.add(new Vulture(3, 500, 500, 1));
            enemies.add(new Hyena(4, 500, 55, 1));
            enemies.add(new Hyena(4, 150, 55, 1));
            enemies.add(new Hyena(4, 300, 55, 1));
        } else if (!spawned && enemiesKilled < 15 && numWave == 5) {
            spawned = true;
            enemies.add(new Vulture(3, 300, 400, 1));
            enemies.add(new Vulture(3, 100, 300, 1));
            enemies.add(new Hyena(4, 500, 55, 1));
            enemies.add(new Hyena(4, 150, 55, 1));
            enemies.add(new Hyena(4, 300, 55, 1));
        } else if (!spawned && enemiesKilled < 21 && numWave == 6) {
            spawned = true;
            enemies.add(new Vulture(3, 400, 500, 1));
            enemies.add(new Vulture(3, 300, 400, 1));
            enemies.add(new Vulture(3, 100, 300, 1));
            enemies.add(new Vulture(3, 500, 400, 1));
            enemies.add(new Vulture(3, 600, 300, 1));
            enemies.add(new Hyena(4, 400, 55, 1));
        }
    }

    // changing level
    private void nextlevel () {
        if (enemiesKilled >= 21 || Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            this.dispose();
            medievalGame.setScreen(new Story(medievalGame, batch, player, currentPhase));
        }
    }

    // render
    @Override
    public void render(float delta) {
        super.render(delta);
        spawnEnemies(delta);
        player.update(phasePhysicShapes, delta, batch);
        drawEnemies(delta);
        batch.end();
        //shapeRenderer.rect(player.getHitBox().getX(), player.getHitBox().getY(), player.getHitBox().getWidth(), player.getHitBox().getHeight());
        shapeRenderer.end();
        nextlevel();
    }

    // dispose
    @Override
    public void dispose() {
        music.dispose();
        shapeRenderer.dispose();
    }
}
