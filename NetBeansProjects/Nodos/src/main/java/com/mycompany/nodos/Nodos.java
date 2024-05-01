/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.nodos;

import java.util.Random;


public class Nodos {
    public static void main(String[] args) {
        int dimension = 15;

        for (char origen = 'A'; origen < 'A' + dimension; origen++) {
            for (char destino = 'A'; destino < 'A' + dimension; destino++) {
                int x = origen - 'A';
                int y = destino - 'A';
                int z = (int) (Math.random() * 10);  // Puedes ajustar según tus necesidades

                String combination = String.format("%c,%c,%d,%d,%d", origen, destino,  x, y, z);
                System.out.println(combination);
            }
        }
    }
}
