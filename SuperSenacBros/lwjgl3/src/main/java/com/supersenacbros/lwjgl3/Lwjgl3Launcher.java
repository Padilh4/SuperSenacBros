package com.supersenacbros.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.supersenacbros.SuperSenacBros;


public class Lwjgl3Launcher {
    public static void main(String[] args) {
    	Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("SuperSenacBros");

        configuration.setForegroundFPS(60);
        configuration.setWindowedMode(640, 480);
        configuration.setResizable(false);
        new Lwjgl3Application(new SuperSenacBros(), configuration);
    
    
    }

  

    
        


    
}