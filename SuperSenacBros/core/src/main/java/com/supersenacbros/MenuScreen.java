package com.supersenacbros;
import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ScreenUtils;


public class MenuScreen implements Screen {
    private SuperSenacBros game; // Referência para poder mudar de tela
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture background, ssb;
    public int topscore;
    boolean CONFIRM;

    public MenuScreen(SuperSenacBros game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Inicializa Texturas, fontes...
        batch = new SpriteBatch();
        font = new BitmapFont(); 
        if(SelectScreen.NameCharacter.equals("VINI")) {
        	background = new Texture("menuscreen.png");
        }else if(SelectScreen.NameCharacter.equals("LEO")) {
        	background = new Texture("menuscreen2.png");
        }else {
        	background = new Texture("menuscreen3.png");
        }
        
        ssb = new Texture("logo.png");
        try {
			topscore = ManageDB.VerifyScore(RegisterScreen.NamePlayer);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMarioWorldTextBoxRegular-Y86j.ttf"));

    	// configurações da fonte principal (tamanho e cor)
    	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    	parameter.size = 18; // tamanho
    	parameter.color = Color.WHITE; // cor
    	font = generator.generateFont(parameter); // Gera a fonte com os paramentros e guarda na variável 'font'
    	generator.dispose();// descarta para nao pesar a memoria
        
    }

    @Override
    public void render(float delta) {
    	CONFIRM = false;
    	if (Controllers.getControllers().size > 0) {
    		Controller controle = Controllers.getControllers().first();
    		
    		if (controle.getButton(0)){
				CONFIRM = true; 
				
			}
    	
    	}
        ScreenUtils.clear(0, 0, 0, 1); // Fundo preto
        
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(ssb, 125, 240, 400, 175 );
        if (Controllers.getControllers().size > 0) {
        	font.draw(batch, "PRESS X TO START", 194, 200);
        
        }else {
        	font.draw(batch, "PRESS ENTER TO START", 163, 200);
        }
        
        font.draw(batch, "TOP- " + String.format("%06d", topscore) , 230, 140);
        batch.end();

        // Se pressionar Enter muda para a Fase 1
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || CONFIRM) {
game.score = 0;
        	game.coins = 0;
        	game.lifes = 3;
            game.setScreen(new FaseOne(game)); 
            dispose(); // Limpa a memória do menu
        }
    }

    // O LibGDX exige os outros métodos da interface, mas você pode deixar vazios:
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); font.dispose(); }
}