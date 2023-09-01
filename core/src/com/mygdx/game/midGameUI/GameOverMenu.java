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
import com.mygdx.game.Screens.Phase2;
import com.mygdx.game.Screens.Phase3;

public class GameOverMenu implements Screen{

    private MedievalGame medievalGame;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Music music;
    private SpriteBatch batch;
    private Texture backGround;
    private Texture retryButtonIdle;
    private Texture retryButtonSelected;
    private Texture menuButtonIdle;
    private Texture menuButtonSelected;
    private Texture exitButtonIdle;
    private Texture exitButtonSelected;
    private int currentPhase;

    // constructor
    public GameOverMenu(MedievalGame medievalGame, SpriteBatch batch, int phase) {
        this.medievalGame = medievalGame;
        this.batch = batch;
        backGround = new Texture("GameOverMenu/GameOverMenuImage.png");
        retryButtonIdle = new Texture("GameOverMenu/retryButtonIdle.png");
        retryButtonSelected = new Texture("GameOverMenu/retryButtonClicked.png");
        menuButtonIdle = new Texture("GameOverMenu/mainButtonIdle.png");
        menuButtonSelected = new Texture("GameOverMenu/mainButtonClicked.png");
        exitButtonIdle = new Texture("MainMenu/exitButtonIdle.png");
        exitButtonSelected = new Texture("MainMenu/exitButtonClicked.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("SoundEffects/Musics/MusicGameOver.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
        gamecam = new OrthographicCamera();
        gamecam.setToOrtho(false, 800, 600);
        gamePort = new FitViewport(medievalGame.V_WIDTH, medievalGame.V_HEIGHT, gamecam);
        currentPhase = phase;
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
        batch.end();
    }

    // button system
    private void verifyClicks() {
        if (Gdx.input.getX() > 315 && Gdx.input.getX() < 315 + retryButtonIdle.getWidth() && Gdx.input.getY() < 445 && Gdx.input.getY() > 443 - retryButtonIdle.getHeight()) {
            batch.draw(retryButtonSelected, 315, 155);
            if(Gdx.input.isTouched()) {
                this.dispose();
                if (currentPhase == 1)
                    medievalGame.setScreen(new Phase1(medievalGame, batch, medievalGame.getPlayer()));
                else if (currentPhase == 2)
                    medievalGame.setScreen(new Phase2(medievalGame, batch, medievalGame.getPlayer()));
                else
                    medievalGame.setScreen(new Phase3(medievalGame, batch, medievalGame.getPlayer()));
            }
        } else {
            batch.draw(retryButtonIdle, 315, 155);
        }

        if (Gdx.input.getX() > 290 && Gdx.input.getX() < 290 + menuButtonIdle.getWidth() && Gdx.input.getY() < 520 && Gdx.input.getY() > 520 - menuButtonIdle.getHeight()) {
            batch.draw(menuButtonSelected, 290, 80);
            if(Gdx.input.isTouched()) {
                this.dispose();
                medievalGame.setScreen(new MainMenu(medievalGame, batch));
            }
        } else {
            batch.draw(menuButtonIdle, 290, 80);
        }

        if (Gdx.input.getX() > 338 && Gdx.input.getX() < 338 + exitButtonIdle.getWidth() && Gdx.input.getY() < 570 && Gdx.input.getY() > 570 - exitButtonIdle.getHeight()) {
            batch.draw(exitButtonSelected, 338, 30);
            if(Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            batch.draw(exitButtonIdle, 338, 30);
        }
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
