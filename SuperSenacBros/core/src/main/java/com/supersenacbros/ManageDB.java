package com.supersenacbros;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageDB {
    private static final String URL = "jdbc:mysql://localhost:3306/TOPSCORES";
    private static final String USER = "root";
    private static final String PASS = "root";

    // Método para salvar o topscore quando o jogador morre ou ganha
    public static void SaveScore(String name, int NewScore) {
        new Thread(() -> {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                
                //Consulta para ver se o jogador já tem um topscore
                String sql = "SELECT score FROM Players WHERE name = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, name);
                    ResultSet rs = stmt.executeQuery();
                    
                    if (rs.next()) {
                        // jogador existe logo verifico o antigo score
                        int OldScore = rs.getInt("score");
                        
                        // testa se e maior q o antigo
                        if (NewScore > OldScore) {
                        	
                            String sqlUP = "UPDATE Players SET score = ? WHERE name = ?";
                            try (PreparedStatement stmt2 = conn.prepareStatement(sqlUP)) {
                                stmt2.setInt(1, NewScore);
                                stmt2.setString(2, name);
                                stmt2.executeUpdate();
                                System.out.println("Novo topscore, score atualizado para " + NewScore);
                            }
                        } else {
                            System.out.println("score atual (" + NewScore + ") não superou o recorde de (" + OldScore + ").");
                        }
                    } else {
                        // se o jogador nao existe ainda, logo crio-o
                        String sqlIN = "INSERT INTO Players (name, score) VALUES (?, ?)";
                        try (PreparedStatement stmt3 = conn.prepareStatement(sqlIN)) {
                            stmt3.setString(1, name);
                            stmt3.setInt(2, NewScore);
                            stmt3.executeUpdate();
                            System.out.println("primeiro score do player " + name);
                        }
                    }
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    
    public static int VerifyScore(String name) throws SQLException {
    	try {
    	    // forca o java a iniciar o driver do mysql
    	    Class.forName("com.mysql.cj.jdbc.Driver");
    	} catch (ClassNotFoundException e) {
    	    System.err.println("O driver do MySQL não foi encontrado no projeto!");
    	    e.printStackTrace();
    	}
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            	String sql = "SELECT score FROM Players WHERE name = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, name);
                    ResultSet rs = stmt.executeQuery();
                    
                    // Verifica se encontrou o jogador antes de pedir o score
                    if (rs.next()) {
                        return rs.getInt("score");
                    }
                    return 0; // Se o jogador não existir no banco ainda, retorna score 0
    }
        
}
    }
}