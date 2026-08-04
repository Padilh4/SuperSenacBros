package com.supersenacbros;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Mushroom {
	public Rectangle hitbox;
	public Texture texture;
	public float velocityX = 2f, velocityY = 0, y, x;
	public boolean inFloor = false;

	
	public Mushroom(float x, float y, Rectangle bounds, Texture texture) {
	    this.hitbox = bounds;
	    this.hitbox.setPosition(x,y);
	    this.texture = texture;
	}
	
	public void update(float deltaTime, Array<Block> platforms, Array<Enemy1> EnemiesList) {
	    
	    hitbox.x += velocityX;//Faz o cogumelo  andar sozinho alterando o X da hitbox dele
	    for(Block b : platforms) {
	    	if (b.name != null && b.name.equals("Coin")) continue;
	    	if (hitbox.overlaps(b.bounds) && (hitbox.y + 4 < b.bounds.y + b.bounds.height)) { //testa se colidiu com um deles, com 4 pixels de folga
	            // se colidiu aqui, foi com certeza na lateral
	            if (velocityX > 0) {// Se anda para a direita, bate e volta para a esquerda
	                hitbox.x = b.bounds.x - hitbox.width;
	                velocityX = -2f; // Inverte o sentido
	            } else if (velocityX < 0) {
	                hitbox.x = b.bounds.x + b.bounds.width;  // Se anda para a esquerda, bate e volta para a direita
	                velocityX = 2f; // Inverte o sentido
	            }
	            break;
	        }
	    }

	    
	        hitbox.y += velocityY;
	        inFloor = false;
	    
	        for (Block b : platforms) {
	            if (b.name != null && b.name.equals("Coin")) continue; // Ignora moedas
	            if (hitbox.overlaps(b.bounds)) {
	                if (velocityY <= 0) { // Caindo ou pisando no chão
	                    hitbox.y = b.bounds.y + b.bounds.height; // Fixa o cogumelo no topo do bloco
	                    velocityY = 0f; // Para de cair
	                    inFloor = true; // Está no chão
	                    break;
	                }
	            }
	    }
	        
	        }
	    }
	
	
	
	
	
	
	
	
	
	

