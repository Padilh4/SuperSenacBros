package com.supersenacbros;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class BossFireball {
    public Rectangle hitbox;
    public float velocityX;
    public boolean shouldRemove = false;

    

    public BossFireball(float x, float y, float directionX) {
        
        this.hitbox = new Rectangle(x, y, 48, 48);//tamanho da bola de fogo

        this.velocityX = directionX * 4f;  //velocidade

    }

    public void update(float deltaTime, Array<Block> platforms) {
        hitbox.x += velocityX;

        // Se bater em qualquer bloco, a bola de fogo some
        for (Block b : platforms) {
            if (b.name != null && b.name.equals("Coin")) continue;
            if (hitbox.overlaps(b.bounds)) {
                shouldRemove = true;
                break;
            }
        }

        // Se sai da tela some
        if (hitbox.x < 0 || hitbox.x > 9000) { 
            shouldRemove = true;
        }
    }

    public void draw(SpriteBatch batch, TextureRegion currentFrame) {
       
        boolean flipX = (velocityX < 0);//se for menor q zero esta indo para esquerda, se for maior q zero esta indo para direita

        // Se o flipX for verdadeiro, espelha a imagem, se nao fica a escala normal
        float scaleX = flipX ? -1f : 1f;

        batch.draw(currentFrame, hitbox.x, hitbox.y, hitbox.width / 2f, hitbox.height / 2f, hitbox.width, hitbox.height, scaleX, 1f, 0f);
    
    }}