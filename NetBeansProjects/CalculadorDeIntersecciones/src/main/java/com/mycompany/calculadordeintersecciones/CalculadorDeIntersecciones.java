/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.calculadordeintersecciones;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ramon
 */
public class CalculadorDeIntersecciones {

  


    public void calcular(String line1Start,String line1End, String line2Start, String line2End ) 
    {
        

        // Convertir las coordenadas a formato numérico
        double[] line1StartCoords = convertToNumeric(line1Start);
        double[] line1EndCoords = convertToNumeric(line1End);
        double[] line2StartCoords = convertToNumeric(line2Start);
        double[] line2EndCoords = convertToNumeric(line2End);

        // Calcular el punto de intersección
        double[] intersection = calculateIntersection(line1StartCoords, line1EndCoords, line2StartCoords, line2EndCoords);

        // Imprimir el resultado
        System.out.println("El punto de intersección es:");
        System.out.println(formatCoordinates(intersection));
    }

    // Método para convertir las coordenadas a formato numérico
    public double[] convertToNumeric(String coordinates) {

        Pattern pattern = Pattern.compile("(\\d+)(\\d{2})(\\d{2})([NS]),(\\d+)(\\d{2})(\\d{2})([EW])");
        Matcher matcher = pattern.matcher(coordinates);

        if (matcher.matches()) {
            int latDegrees = Integer.parseInt(matcher.group(1));
            int latMinutes = Integer.parseInt(matcher.group(2));
            int latSeconds = Integer.parseInt(matcher.group(3));
            double lat = latDegrees + (latMinutes / 60.0) + (latSeconds / 3600.0);
            if (matcher.group(4).equals("S")) {
                lat = -lat;
            }

            int lonDegrees = Integer.parseInt(matcher.group(5));
            int lonMinutes = Integer.parseInt(matcher.group(6));
            int lonSeconds = Integer.parseInt(matcher.group(7));
            double lon = lonDegrees + (lonMinutes / 60.0) + (lonSeconds / 3600.0);
            if (matcher.group(8).equals("W")) {
                lon = -lon;
            }

            return new double[]{lat, lon};
        } else {
            throw new IllegalArgumentException("El formato de las coordenadas es incorrecto.");
        }
    }

    
    public double[] calculateIntersection(double[] line1Start, double[] line1End, double[] line2Start, double[] line2End) {
            double x1 = line1Start[0];
    double y1 = line1Start[1];
    double x2 = line1End[0];
    double y2 = line1End[1];
    double x3 = line2Start[0];
    double y3 = line2Start[1];
    double x4 = line2End[0];
    double y4 = line2End[1];

    double denominator = ((x1 - x2) * (y3 - y4)) - ((y1 - y2) * (x3 - x4));

    // Verificar si las líneas son paralelas
    if (denominator == 0) {
        throw new IllegalArgumentException("Las líneas son paralelas y no tienen intersección.");
    }

    double xNumerator = ((x1 * y2 - y1 * x2) * (x3 - x4)) - ((x1 - x2) * (x3 * y4 - y3 * x4));
    double yNumerator = ((x1 * y2 - y1 * x2) * (y3 - y4)) - ((y1 - y2) * (x3 * y4 - y3 * x4));

    double x = xNumerator / denominator;
    double y = yNumerator / denominator;

    return new double[]{x, y};
    }

    
    public String formatCoordinates(double[] coordinates) 
    {
        int latDegrees = (int) Math.abs(coordinates[0]);
    double latMinutesSeconds = (Math.abs(coordinates[0]) - latDegrees) * 60.0;
    int latMinutes = (int) latMinutesSeconds;
    double latSeconds = (latMinutesSeconds - latMinutes) * 60.0;

    int lonDegrees = (int) Math.abs(coordinates[1]);
    double lonMinutesSeconds = (Math.abs(coordinates[1]) - lonDegrees) * 60.0;
    int lonMinutes = (int) lonMinutesSeconds;
    double lonSeconds = (lonMinutesSeconds - lonMinutes) * 60.0;

    char latDirection = (coordinates[0] >= 0) ? 'N' : 'S';
    char lonDirection = (coordinates[1] >= 0) ? 'E' : 'W';

    return String.format("%02d%02d%02d%c,%03d%02d%02d%c", latDegrees, latMinutes, (int)latSeconds, latDirection, lonDegrees, lonMinutes, (int)lonSeconds, lonDirection);

        

    }
}