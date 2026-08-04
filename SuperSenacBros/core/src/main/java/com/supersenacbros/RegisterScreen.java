package com.supersenacbros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ScreenUtils;


public class RegisterScreen implements Screen {
    private SuperSenacBros game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Sound SelectSound; 
    private Sound ConfirmSound;
    
    public static String NamePlayer = ""; 
    
    // variavels para controlar o alfabeto 
    private char[] Letters = {'A', 'A', 'A'};
    private int CurrentLetterPosition = 0; // qual das 3 letras vai mudar
    private float BlinkTimer = 0;
    boolean DOWN, UP, RIGHT, LEFT, CONFIRM;
    
    

    public RegisterScreen(SuperSenacBros game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMarioWorldTextBoxRegular-Y86j.ttf"));

    	// configurações da fonte principal (tamanho e cor)
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 18; // tamanho
    	parameter.color = Color.WHITE; // cor
    	font = generator.generateFont(parameter); // Gera a fonte com os paramentros e guarda na variável 'font'
        generator.dispose();
        
        ConfirmSound = Gdx.audio.newSound(Gdx.files.internal("SelectMenu.wav"));
		SelectSound = Gdx.audio.newSound(Gdx.files.internal("select2.wav"));
    }

    @Override
    public void render(float delta) {
    	
    	UP = false;
        DOWN = false;
        LEFT = false;
        RIGHT = false;
        CONFIRM = false;
        
    	if (game.cooldownControl > 0) {
    	    game.cooldownControl -= Gdx.graphics.getDeltaTime();
    	}
    
    	if (game.cooldownControl <= 0 && Controllers.getControllers().size > 0) {
    	    Controller controle = Controllers.getControllers().first();
    	    
    	    

    	    
    	   
			if (controle.getButton(12)) {
				DOWN = true; 
				game.cooldownControl = game.WAIT_TIMER;
			}
    	    
			if (controle.getButton(11)) {
				UP = true; 
				game.cooldownControl = game.WAIT_TIMER;
			}
    	    
    	    
			if (controle.getButton(14)) {
				RIGHT = true; 
				game.cooldownControl = game.WAIT_TIMER;
			}
    	    
			if (controle.getButton(13)) {
				LEFT = true; 
				game.cooldownControl = game.WAIT_TIMER;
			}
			
			if (controle.getButton(0)) {
				CONFIRM = true; 
				game.cooldownControl = game.WAIT_CONFIRM_TIMER;
			}
    	}

    	
    	
    	
        ScreenUtils.clear(0, 0, 0, 1); // Fundo preto 

        // ========================================== LOGICA DE CONTROLE ==========================================
        
        // Seta para CIMA e BAIXO mudam a letra atual (A, B, C...)
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || UP) {
            Letters[CurrentLetterPosition]++;
            SelectSound.play(0.5f);
            if (Letters[CurrentLetterPosition] > 'Z') Letters[CurrentLetterPosition] = 'A';
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || DOWN) {
            Letters[CurrentLetterPosition]--;
            SelectSound.play(0.5f);
            if (Letters[CurrentLetterPosition] < 'A') Letters[CurrentLetterPosition] = 'Z';
        }

        // seta  pros lados mudam de posicao entre as letras
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || RIGHT) {
        	SelectSound.play(0.5f);
            if (CurrentLetterPosition < 2) CurrentLetterPosition++;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || LEFT) {
        	SelectSound.play(0.5f);
            if (CurrentLetterPosition > 0) CurrentLetterPosition--;
        }

        // tecla enter confirma o nome
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || CONFIRM) {
            NamePlayer = "" + Letters[0] + Letters[1] + Letters[2];
            System.out.println("Jogador registrado: " + NamePlayer);
            ConfirmSound.play(0.5f);
            game.setScreen(new SelectScreen(game)); //vai pru menu
            return;
        }

        // ========================================== RENDERIZAÇÃO NA TELA ==========================================
        BlinkTimer += delta;
        batch.begin();

        font.draw(batch, "INSERT YOUR INITIALS", 178, 400);

        // faz as tres letra ter espaco ebtre elas
        for (int i = 0; i < 3; i++) {
            // se a letra tiver selecionada ela pisca 0_0
            if (i == CurrentLetterPosition && (int)(BlinkTimer * 3) % 2 == 0) {
                font.draw(batch, "-", 270 + (i * 40), 300); // faz um traco picando
            } else {
                font.draw(batch, String.valueOf(Letters[i]), 270 + (i * 40), 300);
            }
        }
        font.draw(batch, "USE THE ARROWS TO CHANGE\n" + "     ENTER TO CONFIRM", 120, 150);
    
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    
    @Override 
    public void dispose() {
        if (batch != null) batch.dispose();
        if (font != null) font.dispose();
    }
}