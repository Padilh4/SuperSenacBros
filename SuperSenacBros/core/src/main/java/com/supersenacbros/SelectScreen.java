package com.supersenacbros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ScreenUtils;

public class SelectScreen implements Screen{
	private SuperSenacBros game;
    private SpriteBatch batch;
    private BitmapFont font;
    Texture CharacterV, CharacterL, Characterl;
    private Sound SelectSound; 
    private Sound ConfirmSound;
    boolean RIGHT, LEFT, CONFIRM;
    
    
    // variavels para controlar a seleção 
 // Lista de nomes para facilitar o controle
    private String[] characterNames = {"VINI", "LEO", "LUIS"};
    
    private int selectedIndex = 0; // 0 = Vini, 1 = Leo, 2 = Luis
    
    // Variável estática para as outras telas saberem quem foi escolhido
    public static String NameCharacter = "";
    
    
    public SelectScreen(SuperSenacBros game) {
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
        
        CharacterV = new Texture("vini_parado.png"); 
        CharacterL = new Texture("leo_parado.png");
        Characterl = new Texture("luis_parado.png");
        
        ConfirmSound = Gdx.audio.newSound(Gdx.files.internal("SelectMenu.wav"));
		SelectSound = Gdx.audio.newSound(Gdx.files.internal("select2.wav"));
	}

	@Override
    public void render(float delta) {


        LEFT = false;
        RIGHT = false;
        CONFIRM = false;
        
    	if (game.cooldownControl > 0) {
    		game.cooldownControl -= Gdx.graphics.getDeltaTime();
    	}
    
    	if (game.cooldownControl <= 0 && Controllers.getControllers().size > 0) {
    	    Controller controle = Controllers.getControllers().first();
    	    
  
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

        // ========================================== LOGICA DE CONTROLE (TECLADO) ==========================================
     // direita avança na lista
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || RIGHT) {
            selectedIndex++;
            SelectSound.play(0.5f);
            if (selectedIndex >= characterNames.length) {
                selectedIndex = 0; // Volta pro primeiro
            }
        }
        
        // esquerda volta na lista
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || LEFT) {
            selectedIndex--;
            SelectSound.play(0.5f);
            if (selectedIndex < 0) {
                selectedIndex = characterNames.length - 1; // Vai pro último
            }
        }

        // enter confirma o personagem selecionado
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || CONFIRM) {
            NameCharacter = characterNames[selectedIndex];
            ConfirmSound.play(0.5f);
            System.out.println("Personagem selecionado e registrado: " + NameCharacter);
            
            game.setScreen(new MenuScreen(game)); // Vai pro menu
            dispose(); //limpa a memória ao sair da tela
            return;
        }

        // ========================================== RENDERIZAÇÃO NA TELA ==========================================
        
        batch.begin();

        // Desenha o titulo da tela
        font.draw(batch, "SELECT YOUR CHARACTER", 160, 420);

        // Desenha o nome do personagem selecionado
        font.draw(batch, characterNames[selectedIndex], 285, 340);

        // Desenha a textura do personagem selecionado
        
        if (selectedIndex == 0) {
            batch.draw(CharacterV, 290, 220, 48, 77); // Desenha o Vini
        } else if (selectedIndex == 1) {
            batch.draw(CharacterL, 290, 220, 48, 77); // Desenha o Leo
        } else if (selectedIndex == 2) {
            batch.draw(Characterl, 290, 220, 48, 77); // Desenha o Luis
        }

        // Desenha as instruções
        font.draw(batch, "USE THE ARROWS TO CHANGE\n" + "     ENTER TO CONFIRM", 120, 120);
    
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