package com.supersenacbros;
import com.badlogic.gdx.Game;

public class SuperSenacBros extends Game {
	public int score = 0;
    public int coins = 0;
    public int lifes = 3;
    float cooldownControl = 0f;
    final float WAIT_TIMER = 0.2f;
    final float WAIT_CONFIRM_TIMER = 1.5f;
    @Override
    public void create() {
        // O jogo inicializa abrindo a tela de Menu
    	this.setScreen(new RegisterScreen(this));
    }
    @Override
    public void render() {
        super.render(); //faz a Screen atual rodar o seu próprio render.
    }
}