package com.mycompany.calculadordeintersecciones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.operation.linemerge.LineMerger;
import org.locationtech.jts.operation.overlay.snap.SnapIfNeededOverlayOp;

public class GeoToolsIntersectionCalculator 
{

    public void calcular(String line1Start,String line1End, String line2Start, String line2End ) {
        // Coordenadas de las líneas en formato de cadena
        

        // Convertir las coordenadas al formato numérico
        double[] line1StartCoords = new CalculadorDeIntersecciones().convertToNumeric(line1Start);
        double[] line1EndCoords = new CalculadorDeIntersecciones().convertToNumeric(line1End);
        double[] line2StartCoords = new CalculadorDeIntersecciones().convertToNumeric(line2Start);
        double[] line2EndCoords = new CalculadorDeIntersecciones().convertToNumeric(line2End);

        // Convertir las coordenadas numéricas en LineStrings
        LineString line1 = createLineString(line1StartCoords, line1EndCoords);
        LineString line2 = createLineString(line2StartCoords, line2EndCoords);

        // Calcular la intersección
        Point intersectionPoint = calculateIntersection(line1, line2);

        // Imprimir el resultado
        System.out.println("El punto de intersección es: " + intersectionPoint.getCoordinate());
        double[] xxx={intersectionPoint.getCoordinate().getY(),intersectionPoint.getCoordinate().getX()};
        
        System.out.println("El punto de intersección es: " +new CalculadorDeIntersecciones().formatCoordinates(xxx));
        
        
    }

    

    private static LineString createLineString(double[] startCoords, double[] endCoords) {
        // Crear un objeto LineString a partir de las coordenadas
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Coordinate[] coords = {new Coordinate(startCoords[1], startCoords[0]), new Coordinate(endCoords[1], endCoords[0])};
        return geometryFactory.createLineString(coords);
    }

    private  Point calculateIntersection(LineString line1, LineString line2) {
        // Calcular la intersección de las líneas
    Geometry intersection = line1.intersection(line2);

    // Verificar el tipo de intersección
    if (intersection instanceof Point) {
        // Si la intersección es un punto, lo devolvemos como resultado
        return (Point) intersection;
    } else {
        // Si la intersección no es un punto (líneas paralelas o no se cruzan), lanzamos una excepción
        throw new IllegalArgumentException("Las líneas son paralelas o no tienen intersección.");
    }

    }
}

