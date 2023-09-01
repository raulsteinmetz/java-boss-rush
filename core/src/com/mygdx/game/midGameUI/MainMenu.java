package com.mygdx.game.midGameUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MedievalGame;
import com.mygdx.game.Screens.Phase1;

public class MainMenu implements Screen{

    private MedievalGame medievalGame;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Music music;
    private SpriteBatch batch;
    private Texture backGround;
    private Texture playButtonIdle;
    private Texture playButtonSelected;
    private Texture exitButtonIdle;
    private Texture exitButtonSelected;
    private float waitingTime;

    // constructor
    public MainMenu(MedievalGame medievalGame, SpriteBatch batch) {
        this.medievalGame = medievalGame;
        this.batch = batch;
        backGround = new Texture("MainMenu/MainMenuImage.png");
        playButtonIdle = new Texture("MainMenu/playButtonIdle.png");
        playButtonSelected = new Texture("MainMenu/playButtonClicked.png");
        exitButtonIdle = new Texture("MainMenu/exitButtonIdle.png");
        exitButtonSelected = new Texture("MainMenu/exitButtonClicked.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("SoundEffects/Musics/MusicMainMenu.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, 800, 600);
        gamePort = new FitViewport(medievalGame.V_WIDTH, medievalGame.V_HEIGHT, gamecam);
        waitingTime = 0;
    }

    @Override
    public void show() {
        
    }

    // render
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backGround, 0, 0);
        verifyClicks();
        wait(delta);
        batch.end();
    }

    // button system
    private void verifyClicks() {
        if (Gdx.input.getX() > 340 && Gdx.input.getX() < 340 + playButtonIdle.getWidth() && Gdx.input.getY() < 420 && Gdx.input.getY() > 418 - playButtonIdle.getHeight()) {
            batch.draw(playButtonSelected, 340, 180);
            if(Gdx.input.isTouched()) {
                this.dispose();
                medievalGame.setScreen(new Story(medievalGame, batch, medievalGame.getPlayer(), medievalGame.getCurrentPhase()));
            }
        } else {
            batch.draw(playButtonIdle, 340, 180);
        }
        if (Gdx.input.getX() > 338 && Gdx.input.getX() < 338 + exitButtonIdle.getWidth() && Gdx.input.getY() < 520 && Gdx.input.getY() > 520 - exitButtonIdle.getHeight()) {
            batch.draw(exitButtonSelected, 338, 80);
            if(Gdx.input.isTouched() && waitingTime >= 1) {
                Gdx.app.exit();
            }
        } else {
            batch.draw(exitButtonIdle, 338, 80);
        }
    }

    private void wait (float dt) {
        if (waitingTime <= 1)
            waitingTime += dt;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
        music.dispose();
    }
}
