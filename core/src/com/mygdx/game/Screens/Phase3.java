package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Input;
import com.mygdx.game.enemies.FlameBall;
import com.mygdx.game.enemies.Hydra;
import com.mygdx.game.midGameUI.Story;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.Projectile;

import java.util.ArrayList;

public class Phase3 extends Phase{
    Hydra hydra;
    ArrayList<Rectangle> phasePhysicShapes;

    // constructor
    public Phase3(MedievalGame game, SpriteBatch batch, Player player) {
        this.medievalGame = game;
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, 800, 600);
        gamePort = new FitViewport(medievalGame.V_WIDTH, medievalGame.V_HEIGHT, gamecam);
        background = new Texture("Sceneries/Phase03.jpg");
        this.player = player;
        this.hydra = new Hydra(21, (float)(350), (float)(350), 20, 10, 5, 50);
        this.batch = batch;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        music = Gdx.audio.newMusic(Gdx.files.internal("SoundEffects/Musics/Music03.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
        player.setDefault();
        currentPhase = 3;
    }

    // game platforms physics
    @Override
    public void isGrounded() {
        if (player.getHitBox().y <= 55)
            player.setGrounded(true);
        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            player.setGrounded(false);
        else if (player.getHitBox().x > 220 && player.getHitBox().x < 340 && player.getHitBox().y >= 165 && player.getHitBox().y <= 175)
            player.setGrounded(true);
        else if (player.getHitBox().x > 120 && player.getHitBox().x < 215 && player.getHitBox().y >= 290 && player.getHitBox().y <= 300)
            player.setGrounded(true);
        else if (player.getHitBox().x > 545 && player.getHitBox().x < 640 && player.getHitBox().y >= 290 && player.getHitBox().y <= 300)
            player.setGrounded(true);
        else if (player.getHitBox().x > 420 && player.getHitBox().x < 540 && player.getHitBox().y >= 165 && player.getHitBox().y <= 175)
            player.setGrounded(true);
        else if (player.getHitBox().x > 220 && player.getHitBox().x < 340 && player.getHitBox().y >= 410 && player.getHitBox().y <= 420)
            player.setGrounded(true);
        else if (player.getHitBox().x > 420 && player.getHitBox().x < 540 && player.getHitBox().y >= 410 && player.getHitBox().y <= 420)
            player.setGrounded(true);
        else
            player.setGrounded(false);
    }

    // render
    @Override
    public void render(float delta) {
        super.render(delta);
        if (hydra.getLife() > 0)
        {
            batch.draw(hydra.getCurrentAnimation(), hydra.getPositionX(), hydra.getPositionY());
            if(hydra.getAction() == 1)
            {
                for (int i = 0; i < 6; i++)
                {
                    batch.draw(hydra.getListOfBalls().get(i).getAnimation(), hydra.getListOfBalls().get(i).getPositionX(), hydra.getListOfBalls().get(i).getPositionY());
                    hydra.getListOfBalls().get(i).update(delta);
                }
            }
            hydra.update(delta, player);
        }
        player.update(phasePhysicShapes, delta, batch);
        batch.end();
        nextlevel();
    }

    private void nextlevel () {
        if (hydra.getLife() <= 0 || Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            this.dispose();
            medievalGame.setScreen(new Story(medievalGame, batch, player, currentPhase));
        }
    }

    // game hit and damage system
    @Override
    public void verifyColision() {
        if (hydra.getLife() > 0)
        {
            ArrayList<Projectile> arrows = player.getProjectiles();
            if (player.getHitBox().overlaps(hydra.getHitBox()) && !hydra.getStatus())
                player.takeHit(hydra);
            for (Projectile arrow : arrows) {
                if (hydra.getHitBox().overlaps(arrow.getProjectile()) && !hydra.getStatus()) {
                    hydra.getHit(arrow.getProjectile().x);
                    arrow.hit();
                }
            }
            if (hydra.action == 0) return;
            for (FlameBall f: hydra.getListOfBalls()){
                if (player.getHitBox().overlaps(f.getHitbox()))
                {
                    player.takeHitNokb(f.getHitbox().x + f.getHitbox().width / 2);
                }
            }
        }
    }

    // dispose
    @Override
    public void dispose() {
        music.dispose();
        shapeRenderer.dispose();
    }
}
