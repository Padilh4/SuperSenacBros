package com.supersenacbros;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy1 {
public Rectangle hitbox;
public Texture texture;
private Animation<Texture> walkAnimation;
private float stateTime = 0f;
public float velocityX = -2f, velocityY = 0, eposy, eposx, deadEVelocityY;
public boolean inFloor = false, iseDead = false;


public Enemy1(Rectangle bounds, Animation<Texture> animation) {
    this.hitbox = bounds;
    this.walkAnimation = animation;
    // Define a textura inicial como o primeiro frame da animação
    this.texture = walkAnimation.getKeyFrame(0);
}

public void update(float deltaTime, Array<Block> platforms, Array<Enemy1> EnemiesList) {
    stateTime += deltaTime;//Faz o cronômetro da animação do inimigo rodar
    
    texture = walkAnimation.getKeyFrame(stateTime);//Atualiza a textura atual baseada no tempo
    
    hitbox.x += velocityX;//Faz o inimigo andar sozinho alterando o X da hitbox dele
    // Futuramente checar se ele bateu num bloco 
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
    
    for (Enemy1 other : EnemiesList) {
        // faz o inimigo não poder colidir com ele mesmo
        if (other == this) continue; 
        
        if (hitbox.overlaps(other.hitbox)) {
            if (velocityX > 0) {        
                hitbox.x = other.hitbox.x - hitbox.width;// Se anda para a direita e bate em alguém, volta para a esquerda
                velocityX = -2f;
                other.velocityX = 2f;   // o outro inimigo também vira na hora 
            } else if (velocityX < 0) {
                hitbox.x = other.hitbox.x + other.hitbox.width; // anda para a esquerda e bate em alguém = voltar para a direita
                velocityX = 2f;
                other.velocityX = -2f;
            }
            break; // Já colidiu entao para de checa os outros neste frame
        }
    }
    
        hitbox.y += velocityY;
        inFloor = false;
    
        for (Block b : platforms) {
            if (b.name != null && b.name.equals("Coin")) continue; // Ignora moedas
            if (hitbox.overlaps(b.bounds)) {
                if (velocityY <= 0) { // Caindo ou pisando no chão
                    hitbox.y = b.bounds.y + b.bounds.height; // Fixa o inimigo no topo do bloco
                    velocityY = 0f; // Para de cair
                    inFloor = true; // Está no chão
                    break;
                }
            }
    }
        if (inFloor) {
            // sensor invisível  na frente do inimigo
            float sensorX;
            if (velocityX > 0) {
                sensorX = hitbox.x + hitbox.width + 4;// Se anda para a direita o sensor olha 4 pixels depois da borda direita dele
            } else {
                sensorX = hitbox.x - 4;// Se anda para a esquerda, o sensor olha 4 pixels antes da borda esquerda dele
            }
            float sensorY = hitbox.y - 2;// O sensor fica 2 pixels abaixo do inimigo 
            boolean achouChaoNaFrente = false;

           
            for (Block b : platforms) { // Varre os blocos para ver se o sensor está tocando em algum bloco
                if (b.name != null && b.name.equals("Coin")) continue;
                if (b.bounds.contains(sensorX, sensorY)) {// Se o ponto do sensor estiver dentro das coordenadas de algum bloco
                    achouChaoNaFrente = true;
                    break; // então esta em um chao e pode sair
                }
            }
            // Se o loop acabou e o sensor nao achou nenhum bloco embaixo dele entao é um buraco, vira
            if (!achouChaoNaFrente) {
                velocityX = -velocityX; // Inverte o sentido do movimento (muda de 2f para -2f ou vice-versa)
            }
        }
    }

public void draw(SpriteBatch batch) {
	if (iseDead) {
        // Se estiver morto inverte a textura
        Texture frameMorto = walkAnimation.getKeyFrame(0); // Pega o primeiro frame
        batch.draw(frameMorto, hitbox.x, hitbox.y, hitbox.width, hitbox.height, 0, 0, frameMorto.getWidth(), frameMorto.getHeight(), false, true);
    } else {
    	 if (texture != null) {
    	        batch.draw(texture, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    	    }
    }
}
   
}
