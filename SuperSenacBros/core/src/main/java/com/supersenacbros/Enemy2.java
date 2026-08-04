package com.supersenacbros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Enemy2 {
    public Rectangle hitbox;
    public Texture texture;
    
    
    public float velocityX = 0f;
    public float velocityY = 0f;
    public float speed = 1.5f; 
    public boolean inFloor = false;
    public int hp = 3; 
    public boolean isDead = false;
    float deadBVelocityY = 0f;
    Animation<Texture> walkAnimation;
    private float stateTime = 0f;
    private Sound FireballSound = Gdx.audio.newSound(Gdx.files.internal("FireballSound.wav"));; // Som da bola de fogo do boss
    
    private float fireballTimer = 0f;
    private float FIREBALL_COOLDOWN = 3.0f; 

    public Enemy2(Rectangle bounds, Animation<Texture> animation) {
        this.hitbox = bounds;
        this.walkAnimation = animation;
        // Define a textura inicial como o primeiro frame da animação
        this.texture = walkAnimation.getKeyFrame(0);
    }



	// pega o x do jogador para o Boss perseguir
    public void update(float deltaTime, Array<Block> platforms, float playerX, float gravity, Array<BossFireball> fireballsList) {
        if (isDead) return;

        stateTime += deltaTime;
        this.texture = walkAnimation.getKeyFrame(stateTime);
        
        // segue o jogador no eixo X
        if (hitbox.x < playerX) {
            velocityX = speed; // se o jogador está na direita, vai pra direita
        } else {
            velocityX = -speed; // se o jogador está na esquerda, vai pra esquerda
        }
        hitbox.x += velocityX;

        // sensor para detectar paredes ou buracos
        float sensorX = (velocityX > 0) ? hitbox.x + hitbox.width + 4 : hitbox.x - 4;
        float sensorY = hitbox.y - 2;
        boolean FoundFloor = false;

        for (Block b : platforms) {
            if (b.name != null && b.name.equals("Coin")) continue;
            if (b.bounds.contains(sensorX, sensorY)) {
                FoundFloor = true;
                break;
            }
        }

        //se detectou uma parede e esta no chao ele pula
        if ((!FoundFloor || CollisionSide(platforms)) && inFloor) {
            velocityY = 8f; // pulo do boss
            inFloor = false;
        }

        velocityY += gravity;
        hitbox.y += velocityY;
        inFloor = false;

        for (Block b : platforms) {
            if (b.name != null && b.name.equals("Coin")) continue;
            if (hitbox.overlaps(b.bounds)) {
                if (velocityY <= 0) { // Caindo
                    hitbox.y = b.bounds.y + b.bounds.height;
                    velocityY = 0;
                    inFloor = true;
                } else if (velocityY > 0) { // cabeça
                    hitbox.y = b.bounds.y - hitbox.height;
                    velocityY = 0;
                }
                break;
            }
        }
        if(hp == 2) {
        	FIREBALL_COOLDOWN = 2.0f; // Aumenta a frequência de ataque  
        }
        if(hp == 1) {
        	FIREBALL_COOLDOWN = 1.5f; // Aumenta ainda mais a frequência de ataque
        	speed = 2.0f; // Aumenta a velocidade do boss
        }

        // lógica para soltar bolas de fogo
        fireballTimer += deltaTime;
        if (fireballTimer >= FIREBALL_COOLDOWN) {
            fireballTimer = 0f;
            System.out.println("boss atirou bola de fogo");
            FireballSound.play();	
            float direcao = (hitbox.x < playerX) ? 1f : -1f; // Ataca pro lado que o jogador ta
            fireballsList.add(new BossFireball(this.hitbox.x, this.hitbox.y + 10, direcao));
        }
    }

    private boolean CollisionSide(Array<Block> platforms) {
        for (Block b : platforms) {
            if (b.name != null && b.name.equals("Coin")) continue;
            
            if (hitbox.overlaps(b.bounds) && (hitbox.y + 4 < b.bounds.y + b.bounds.height)) {
                return true;
            }
        }
        return false;
    }
        
    	public void draw(SpriteBatch batch) {
            
            boolean flipX = (velocityX < 0);//maior que zero vai pra direita, menor que zero vai pra esquerda
            
            batch.draw(texture, hitbox.x, hitbox.y, hitbox.width, hitbox.height, 0, 0, texture.getWidth(), texture.getHeight(), flipX, false
            );
    }
}