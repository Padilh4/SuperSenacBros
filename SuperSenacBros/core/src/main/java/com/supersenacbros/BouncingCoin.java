package com.supersenacbros;

public class BouncingCoin {
    public float x, y;
    public float velocityY;
    public float targetY; // O Y do bloco, para saber onde parar de cair
    public boolean shouldRemove = false;

    public BouncingCoin(float x, float y) {
        this.x = x;
        this.y = y;
        this.velocityY = 6f; // Força do pulinho da moeda para cima
        this.targetY = y;    // Guarda de onde ela saiu
    }

    public void update(float deltaTime, float gravity) {
        velocityY += gravity; // A gravidade puxa a moeda para baixo
        y += velocityY;

        // Se ela subiu e caiu de volta na altura do bloco ela some
        if (velocityY < 0 && y <= targetY) {
            shouldRemove = true;
        }
    }
}