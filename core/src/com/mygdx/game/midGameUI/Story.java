package com.mygdx.game.midGameUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MedievalGame;
import com.mygdx.game.Screens.Phase1;
import com.mygdx.game.Screens.Phase2;
import com.mygdx.game.Screens.Phase3;
import com.mygdx.game.player.Player;

import java.util.ArrayList;

public class Story implements Screen {
    private int current;
    private int currentPhase;
    private int last;
    private Music music;
    private SpriteBatch batch;
    private MedievalGame medievalGame;
    private float time;
    ArrayList<Texture> images;
    public Story(MedievalGame game, SpriteBatch batch, Player player, int currentPhase)
    {   
        music = Gdx.audio.newMusic(Gdx.files.internal("SoundEffects/Musics/MusicStory.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
        images = new ArrayList<>();
        images.add(new Texture("Story/0.png"));
        images.add(new Texture("Story/1.jpg"));
        images.add(new Texture("Story/2.jpg"));
        images.add(new Texture("Story/3.jpg"));
        images.add(new Texture("Story/4.png"));
        images.add(new Texture("Story/5.jpeg"));
        images.add(new Texture("Story/6.jpg"));
        images.add(new Texture("Story/7.png"));
        images.add(new Texture("Story/8.jpg"));
        images.add(new Texture("Story/9.jpg"));
        images.add(new Texture("Story/10.jpg"));
        this.medievalGame = game;
        this.currentPhase = currentPhase;
        this.batch = batch;
        time = 0;
        if(currentPhase == 0) {
            current = 0;
            last = 5;
        } else if (currentPhase == 1) {
            current = 6;
            last = 6;
        } else if (currentPhase == 2) {
            current = 7;
            last = 7;
        } else if (currentPhase == 3) {
            current = 8;
            last = 10;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if ((time >= 10 || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) && current <= last) {
            if (current == last) {
                if(currentPhase == 0) {
                    this.dispose();
                    medievalGame.setScreen(new Phase1(medievalGame, batch, medievalGame.getPlayer()));
                } else if (currentPhase == 1) {
                    this.dispose();
                    medievalGame.setScreen(new Phase2(medievalGame, batch, medievalGame.getPlayer()));
                } else if (currentPhase == 2) {
                    this.dispose();
                    medievalGame.setScreen(new Phase3(medievalGame, batch, medievalGame.getPlayer()));
                } else if (currentPhase == 3) {
                    this.dispose();
                    medievalGame.setScreen(new MainMenu(medievalGame, batch));
                }
            }
            time = 0;
            current ++;
        } else {
            time += delta;
        }
        batch.begin();
        if (current < images.size())
            batch.draw(images.get(current), 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
