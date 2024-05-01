/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.calculadordeintersecciones;

import java.text.DecimalFormat;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

/**
 *
 * @author Ramon
 */
public class Calculator 
{
    private double[] convertirCoordenadasGeograficasADecimal(String coordenadas) //"402646N0442599W"
    {
        System.out.println(coordenadas);
        double latitud = Double.parseDouble(coordenadas.substring(0, 2)) + 
                         Double.parseDouble(coordenadas.substring(2, 4)) / 60.0 + 
                         Double.parseDouble(coordenadas.substring(4, 6)) / 3600.0;
        
        double longitud = Double.parseDouble(coordenadas.substring(7, 10)) + 
                          Double.parseDouble(coordenadas.substring(10, 12)) / 60.0 + 
                          Double.parseDouble(coordenadas.substring(12, 14)) / 3600.0;
        
        if (coordenadas.charAt(6) == 'S') {
            latitud *= -1;
        } 
        
        if (coordenadas.charAt(14) == 'W') {
            longitud *= -1;
        }
        
        return new double[] {latitud, longitud};
    }
    
    public void calcularInterserccion(String linea1Inicio, String linea1Fin, String linea2Inicio, String linea2Fin)
    {
        double[] coordenadasLinea1Inicio = convertirCoordenadasGeograficasADecimal(linea1Inicio);
        double[] coordenadasLinea1Fin = convertirCoordenadasGeograficasADecimal(linea1Fin);
        double[] coordenadasLinea2Inicio = convertirCoordenadasGeograficasADecimal(linea2Inicio);
        double[] coordenadasLinea2Fin = convertirCoordenadasGeograficasADecimal(linea2Fin);
        Coordinate[] coordinatesLinea1 = new Coordinate[]{
                new Coordinate(coordenadasLinea1Inicio[0], coordenadasLinea1Inicio[1]), 
                new Coordinate(coordenadasLinea1Fin[0], coordenadasLinea1Fin[1])      
        };
        Coordinate[] coordinatesLinea2 = new Coordinate[]{
                new Coordinate(coordenadasLinea2Inicio[0], coordenadasLinea2Inicio[1]), 
                new Coordinate(coordenadasLinea2Fin[0], coordenadasLinea2Fin[1])      
        };
        
        
        // Crea una instancia de GeometryFactory
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

        // Crea una línea a partir de las coordenadas
        LineString linea1 = geometryFactory.createLineString(coordinatesLinea1);
        LineString linea2 = geometryFactory.createLineString(coordinatesLinea2);

        // Define el sistema de referencia de coordenadas (CRS)
        CoordinateReferenceSystem crs = DefaultGeographicCRS.WGS84;

        // Imprime la línea creada
        System.out.println("Linea 1: " + linea1.toString());
        System.out.println("Linea 2: " + linea2.toString());
        
        System.out.println("CRS: " + crs.getName());
        
        //calcular la interseccion
        Geometry intersection = linea1.intersection(linea2);
        if (intersection instanceof Point) {
            Point point = (Point) intersection;
            Coordinate intersectionCoordinate = point.getCoordinate();
            
            System.out.println("Las lineas se cortan en: " + intersectionCoordinate);
            
            String latitudGeografica = convertirCoordenadaDecimalAGeografica(intersectionCoordinate.getX(), 'N', 'S',"LAT");
            String longitudGeografica = convertirCoordenadaDecimalAGeografica(((Point) intersection).getY(), 'E', 'W',"LON");

            System.out.println("Las lineas se cortan en: " + latitudGeografica+","+longitudGeografica);
            
            
            
        } else {
            System.out.println("Los objetos LineString no se intersectan.");
        }
        
        
        
        
    }
    
    public double redondear(double numero) {
        
        
        DecimalFormat formato = new DecimalFormat("#.##");
        System.out.println(formato.format(numero));
        return Double.parseDouble(formato.format(numero));
        
    }
    
    private String convertirCoordenadaDecimalAGeografica(double coordenada, char positivo, char negativo, String latLon) {
        int grados = (int) coordenada;
        double minutosDecimales = (Math.abs(coordenada) - Math.abs(grados)) * 60;
        int minutos = (int) minutosDecimales;
        double segundos = (minutosDecimales - minutos) * 60;
        
        char direccion = coordenada >= 0 ? positivo : negativo;
        if(latLon.equals("LAT"))
        {
            return String.format("%02d%02d%02d%c", Math.abs(grados), Math.abs(minutos), (int)segundos, direccion);
        }
        else
        {
            return String.format("%03d%02d%02d%c", Math.abs(grados), Math.abs(minutos), (int)segundos, direccion);
        }
        
        
    }
        
}
