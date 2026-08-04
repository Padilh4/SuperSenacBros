package com.supersenacbros;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Block {
public Rectangle bounds;
public String name;
public Texture texture;
public int hits;
public float targetY = 0;
public float bumpTimer = 0;
public boolean isBumping = false;
public String conteudo;

public Block(Rectangle bounds, String name, Texture initialTexture, int maxhits) {
	this.bounds = bounds;
	this.name = name != null ? name : "normal";
	this.texture = initialTexture;
	this.hits = maxhits;
}

public void update(float deltaTime) {
    if (isBumping) {
        bumpTimer += deltaTime;
        
        // Uma função de seno cria um efeito suave de sobe e desce
        // 0.15f é a duração total do pulo
        if (bumpTimer < 0.15f) {
            // Sobe e desce até no máximo 8 pixels
            targetY = (float) Math.sin((bumpTimer / 0.15f) * Math.PI) * 8f;
        } else {
            // se o Efeito terminou reseta o bloco para a posição original
            targetY = 0;
            bumpTimer = 0;
            isBumping = false;
        }
    }
}

public void draw(SpriteBatch batch) {
    if (texture != null) {
    	batch.draw(texture, bounds.x, bounds.y + targetY, bounds.width, bounds.height);
    	}
}
}
