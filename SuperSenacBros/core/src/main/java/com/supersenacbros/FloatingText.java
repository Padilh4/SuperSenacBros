package com.supersenacbros;

public class FloatingText {
	public String text;
    public float x, y;
    public float duration; // Tempo que o texto fica na tela (em segundos)

    public FloatingText(String text, float x, float y, float duration) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.duration = duration;
    }

    public void update(float deltaTime) {
        duration -= deltaTime; // O tempo vai diminuindo
        y += deltaTime * 55f;  // faz o texto subir 
    }
}