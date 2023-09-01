package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.Phase1;
import com.mygdx.game.Screens.Phase2;
import com.mygdx.game.Screens.Phase3;
import com.mygdx.game.midGameUI.GameOverMenu;
import com.mygdx.game.midGameUI.MainMenu;
import com.mygdx.game.midGameUI.Story;
import com.mygdx.game.player.Player;

public class MedievalGame extends Game {
	// virtual height and width
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 600;
	private MainMenu mainMenu;
	private Phase1 phase1;
	private Phase2 phase2;
	private Phase3 phase3;
	private int currentPhase;
	private boolean changedPhase;
	public SpriteBatch batch;

	public Player player;

	public Player getPlayer() {
		return player;
	}

	public int getCurrentPhase() {
		return currentPhase;
	}
	
	// constructor
	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player(55, 55, 5, 6, 2);
		// setting up screen of the game
		currentPhase = 1;
		changedPhase = false;
		mainMenu = new MainMenu(this, batch);
		currentPhase = 0;
		setScreen(mainMenu);
	}

	// render, delegated to whatever fase is on
	@Override
	public void render () {
		super.render();
	}

}
