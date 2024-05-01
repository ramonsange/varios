/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tools4gis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.UnionDocument;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.util.AffineTransformation;




/**
 *
 * @author Ramon
 */
public class Tools 
{
    Window window = null;

    public Tools(Window window) 
    {
        this.window=window;
    }

    public Point createPoint(GSMpoint gSMpoint) {
        // Create a GeometryFactory
        GeometryFactory geometryFactory = new GeometryFactory();

        // Create a Coordinate for the point
        Coordinate pointCoord = new Coordinate(convertGMStoDec(gSMpoint.getLatG(), gSMpoint.getLatMin(), gSMpoint.getLatSec(), gSMpoint.getLatOr()), convertGMStoDec(gSMpoint.getLonG(), gSMpoint.getLonMin(), gSMpoint.getLonSec(), gSMpoint.getLonOr()));

        // Use the GeometryFactory to create a Point from the Coordinate
        return geometryFactory.createPoint(pointCoord);
    }

    public Polygon createPolygone(List<GSMpoint> area) {
        // Create a GeometryFactory
        GeometryFactory geometryFactory = new GeometryFactory();

        // Create a Coordinate array for the polygon
        Coordinate[] coordinates = new Coordinate[area.size()];

        for (int i = 0; i < area.size(); i++) {
            GSMpoint p = area.get(i);
            double lat = convertGMStoDec(p.getLatG(), p.getLatMin(), p.getLatSec(), p.getLatOr());
            double lon = convertGMStoDec(p.getLonG(), p.getLonMin(), p.getLonSec(), p.getLonOr());
            coordinates[i] = new Coordinate(lat, lon);
            //System.out.println("Contour coordinate "+lat+" "+lon);
        }
        GSMpoint p = area.get(0);
        double lattt = convertGMStoDec(p.getLatG(), p.getLatMin(), p.getLatSec(), p.getLatOr());
        double lonnn = convertGMStoDec(p.getLonG(), p.getLonMin(), p.getLonSec(), p.getLonOr());
        coordinates[area.size() - 1] = new Coordinate(lattt, lonnn);
        //System.out.println("Contour coordinate "+lattt+" "+lonnn);

        // Use the GeometryFactory to create a Polygon from the Coordinate array
        Polygon polygon = null;
        try
        {
            polygon = geometryFactory.createPolygon(coordinates);
        }
        catch(java.lang.IllegalArgumentException ex)
        {
            //System.out.println("*Invalid contour. Less than 3 points ");
        }

        return polygon;
    }

    private static double convertGMStoDec(int degrees, int minutes, int seconds, String orientation) {
        double val = (double) degrees + ((double) minutes / (double) 60) + ((double) seconds / (double) 3600);
        double mul = -1;
        if (orientation.equalsIgnoreCase("W") || orientation.equalsIgnoreCase("S")) {
            val = mul * val;
        }
        //System.out.println(degrees+""+minutes+""+seconds+""+orientation+" < -- > "+val);

        return val;
    }

    void checkPointsInsideArea(String pathAdaptationExcelFile) throws FileNotFoundException, IOException 
    {
        FileInputStream adaptationExcelFile = new FileInputStream(pathAdaptationExcelFile);
        Workbook workbook = new XSSFWorkbook(adaptationExcelFile);

        Sheet sheet = workbook.getSheet("Area_Contour_Points");

        List contoutPointsList = new ArrayList<GSMpoint>();
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Cell cellName = row.getCell(0);
            Cell cellLatLon = row.getCell(2);

            String name = cellName.getStringCellValue();
            if (name.equalsIgnoreCase("INTEREST0001")) {
                String latLong = cellLatLon.getStringCellValue();
                String lat = latLong.substring(0, 7);
                String lon = latLong.substring(7, 15);

                GSMpoint p = stringToGSMPoint(name, lat, lon);
                contoutPointsList.add(p);
            }

        }
        Polygon polygone = this.createPolygone(contoutPointsList);
        System.out.println("");

        sheet = workbook.getSheet("Fixpoints_Guide");
        System.out.println("================");
        System.out.println("POINTS SITUATION");
        System.out.println("================");

        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Cell cellName = row.getCell(0);
            Cell cellLat = row.getCell(2);
            Cell cellLon = row.getCell(3);

            String name = cellName.getStringCellValue();
            String lat = cellLat.getStringCellValue();
            String lon = cellLon.getStringCellValue();

            System.out.print(StringUtils.rightPad(name, 6, ' ') + " " + lat + " " + lon);
            GSMpoint p = stringToGSMPoint(name, lat, lon);

            Point point = this.createPoint(p);

            boolean isIn = polygone.contains(point);
            if (isIn == true) {
                System.out.println(" -- > IN");
            } else {
                System.out.println(" -- > OUT");
            }
        }

    }
    
    private GSMpoint stringToGSMPoint(String name, String lat, String lon)
        {
            GSMpoint p=new GSMpoint();
            
            p.setName(name);
            
            String ggLat=lat.substring(0,2);
            String mmLat=lat.substring(2,4);
            String ssLat=lat.substring(4,6);
            String orLat=lat.substring(6,7);
            String ggLon=lon.substring(0,3);
            String mmLon=lon.substring(3,5);
            String ssLon=lon.substring(5,7);
            String orLon=lon.substring(7,8);
            
            p.setLatG(Integer.parseInt(ggLat));
            p.setLatMin(Integer.parseInt(mmLat));
            p.setLatSec(Integer.parseInt(ssLat));
            p.setLatOr(orLat);
            p.setLonG(Integer.parseInt(ggLon));
            p.setLonMin(Integer.parseInt(mmLon));
            p.setLonSec(Integer.parseInt(ssLon));
            p.setLonOr(orLon);
            
            return p;
            
        }
    
    
    
    public void checkPointsInsideArea(String pathAdaptationExcelFile, String areaContoursSheet, String pointsSheet, ArrayList listPoints, ArrayList listAreaContours) throws FileNotFoundException, IOException 
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date startTime= Calendar.getInstance().getTime();
        
        FileInputStream adaptationExcelFile = new FileInputStream(pathAdaptationExcelFile);
        Workbook workbook = new XSSFWorkbook(adaptationExcelFile);

        for(int k=0;k<listAreaContours.size();k++)
        {
            Sheet sheet = workbook.getSheet(areaContoursSheet);

            List contoutPointsList = new ArrayList<GSMpoint>();
            for (int i = 1; i < sheet.getLastRowNum(); i++) 
            {
                Row row = sheet.getRow(i);
                Cell cellName = row.getCell(0);
                Cell cellLatLon = row.getCell(2);

                String name = cellName.getStringCellValue();
                
                if (name.equalsIgnoreCase((String)listAreaContours.get(k))) {
                    String latLong = cellLatLon.getStringCellValue();
                    String lat = latLong.substring(0, 7);
                    String lon = latLong.substring(7, 15); 

                    GSMpoint p = stringToGSMPoint(name, lat, lon);
                    contoutPointsList.add(p);
                }

                
            }
            System.out.println("================================================");
            System.out.println("                CHECKING AREA "+(String)listAreaContours.get(k));
            System.out.println("================================================");
            Polygon polygone = this.createPolygone(contoutPointsList);
            if(polygone!=null)
            {
                for(int j=0;j<listPoints.size();j++)
                {
                    sheet = workbook.getSheet(pointsSheet);
                    /*System.out.println("================");
                    System.out.println("POINTS SITUATION");
                    System.out.println("================");*/

                    for (int m = 1; m < sheet.getLastRowNum(); m++) {
                        Row row = sheet.getRow(m);
                        Cell cellName = row.getCell(0);
                        Cell cellLat = row.getCell(2);
                        Cell cellLon = row.getCell(3);

                        String name = cellName.getStringCellValue();
                        if(name.equalsIgnoreCase((String)listPoints.get(j)))
                        {
                            String lat = cellLat.getStringCellValue();
                            String lon = cellLon.getStringCellValue();

                            System.out.print(StringUtils.rightPad(name, 6, ' ') + " " + lat + " " + lon);
                            GSMpoint p = stringToGSMPoint(name, lat, lon);
                            Point point = this.createPoint(p);


                            boolean isIn = polygone.contains(point);
                            if (isIn == true) {
                                System.out.println(" -- > IN");
                            } else {
                                System.out.println(" -- > OUT");
                            }
                        }

                    }
                }
            }
            else
            {
                
                System.out.println("Invalid contour. Less than 3 points");
                

            }
        }
        
        
        Date endTime= Calendar.getInstance().getTime();
        System.out.println("Start time:"+sdf.format(startTime));
        System.out.println("End   time:"+sdf.format(endTime));
        
    }

    
     public void printSheet(String pathAdaptationExcelFile, String sheetName) throws FileNotFoundException, IOException 
      {
        FileInputStream adaptationExcelFile = new FileInputStream(pathAdaptationExcelFile);
        Workbook workbook = new XSSFWorkbook(adaptationExcelFile);
        
        Sheet sheet = workbook.getSheet(sheetName);

        for(int i=0;i<sheet.getLastRowNum();i++)
        {
            Row row = sheet.getRow(i);
            
            for(int j=0;j<row.getLastCellNum();j++)
            {
                Cell cell = row.getCell(j);
                
                switch (cell.getCellType()) 
                {
                    case NUMERIC:
                        System.out.print(StringUtils.rightPad(cell.getNumericCellValue()+"", 3, ' ')+" ");
                        break;
                    case STRING:
                        if(cell.getColumnIndex()==0)
                        {
                            System.out.print(StringUtils.rightPad(cell.getStringCellValue()+"", 15, ' ')+" ");
                        }
                        else
                        {
                            System.out.print(StringUtils.rightPad(cell.getStringCellValue()+"", 5, ' ')+" ");
                        }
                        break;
                    default:
                        System.out.print(" Not treated ");
                        break;
                }
            }
            System.out.println("");
            
        }
        workbook.close();
    }

    public List loadSectorsByType(String pathToExcelFile,String sectorType) throws FileNotFoundException, IOException 
    {
        FileInputStream adaptationExcelFile = new FileInputStream(pathToExcelFile);
        Workbook workbook = new XSSFWorkbook(adaptationExcelFile);
        ArrayList sectors = new ArrayList();
        Sheet sheet=null;
        //v1
        if(window.getSelectedSystem().equalsIgnoreCase("iTEC v1"))
        {
            sheet = workbook.getSheet("SECTOR_VOLUMES");
        }
        else if(window.getSelectedSystem().equalsIgnoreCase("iTEC v2"))
        {
            sheet = workbook.getSheet("SECTOR VOLUME");
        }
        else
        {
            JOptionPane.showMessageDialog(window, "System "+window.getSelectedSystem().toString()+" not treated","Error", JOptionPane.ERROR_MESSAGE );
            
        }

        for(int i=1;i<sheet.getLastRowNum();i++)
        {
            Row row = sheet.getRow(i);
            String type = row.getCell(2).getStringCellValue();
            if(type.equalsIgnoreCase(sectorType))
            {
                Sector s= new Sector();
                String id= row.getCell(0).getStringCellValue();
                s.setId(id);
                s.setType(type);

                sectors.add(s);
            }
        }
        workbook.close();
        return sectors;
    }
    public List loadAllSectors(String pathToExcelFile) throws FileNotFoundException, IOException 
    {
        FileInputStream adaptationExcelFile = new FileInputStream(pathToExcelFile);
        Workbook workbook = new XSSFWorkbook(adaptationExcelFile);
        ArrayList sectors = new ArrayList();
        
        Sheet sheet = null;
        
        if(window.getSelectedSystem().equalsIgnoreCase("iTEC v1"))
        {
            sheet = workbook.getSheet("SECTOR_VOLUMES");
        }
        else if(window.getSelectedSystem().equalsIgnoreCase("iTEC v2"))
        {
            sheet = workbook.getSheet("SECTOR VOLUME");
        }
        else
        {
            JOptionPane.showMessageDialog(window, "System "+window.getSelectedSystem().toString()+" not treated","Error", JOptionPane.ERROR_MESSAGE );
            
        }
        
        

        for(int i=1;i<sheet.getLastRowNum();i++)
        {
            Row row = sheet.getRow(i);
            String type = row.getCell(2).getStringCellValue();
            
            Sector s= new Sector();
            String id= row.getCell(0).getStringCellValue();
            s.setId(id);
            s.setType(type);
            
            String condition = null;
            if(window.getSelectedSystem().equalsIgnoreCase("iTEC v1"))
            {
                condition= "C";
            }
            else if(window.getSelectedSystem().equalsIgnoreCase("iTEC v2"))
            {
                condition= row.getCell(3).getStringCellValue();
            }
            s.setCondition(condition);
            

            sectors.add(s);
            
        }
        workbook.close();
        return sectors;
    }

    void loadRVs(List sectors, String pathToExcelFile) throws FileNotFoundException, IOException 
    {
        FileInputStream adaptationExcelFile = new FileInputStream(pathToExcelFile);
        Workbook workbook = new XSSFWorkbook(adaptationExcelFile);
        
        
        for(int k=0;k<sectors.size();k++)
        {
            Sector sector = (Sector)sectors.get(k);
            //v1 
            //Sheet sheet = workbook.getSheet("responsibility_volumes");
            //v2
            Sheet sheet = null;
            if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                sheet = workbook.getSheet("responsibility_volumes");
            } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                sheet = workbook.getSheet("sector responsibility volumes");
            } else {
                JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            
            List<Volumen> listRV=new ArrayList<>();
            for(int i=1;i<sheet.getLastRowNum();i++)
            {
                Row row = sheet.getRow(i);
                String sectorName = row.getCell(1).getStringCellValue();
                if(sector.getId().equalsIgnoreCase(sectorName))
                {
                    Volumen v = new Volumen();
                    v.setId(row.getCell(0).getStringCellValue());
                    listRV.add(v);
                }
            }
            sector.setResponsinilityVolumes(listRV);
        }
        workbook.close();
    }
    void loadIVs(List sectors, String pathToExcelFile) throws FileNotFoundException, IOException 
    {
        FileInputStream adaptationExcelFile = new FileInputStream(pathToExcelFile);
        Workbook workbook = new XSSFWorkbook(adaptationExcelFile);
        
        
        for(int k=0;k<sectors.size();k++)
        {
            Sector sector = (Sector)sectors.get(k);
            Sheet sheet = null;
            
            if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                sheet = workbook.getSheet("interest_volumes");
            } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                sheet = workbook.getSheet("sector interest volumes");
            } else {
                JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            List<Volumen> listIV=new ArrayList<>();
            for(int i=1;i<sheet.getLastRowNum();i++)
            {
                Row row = sheet.getRow(i);
                String sectorName = row.getCell(1).getStringCellValue();
                if(sector.getId().equalsIgnoreCase(sectorName))
                {
                    Volumen v = new Volumen();
                    v.setId(row.getCell(0).getStringCellValue());
                    listIV.add(v);
                }
            }
            sector.setInterestVolumes(listIV);
        }
        workbook.close();
    }
    
    void loadRVsAndIVsLevels(List sectors, String pathToExcelFile) throws FileNotFoundException, IOException 
    {
        FileInputStream adaptationExcelFile = new FileInputStream(pathToExcelFile);
        Workbook workbook = new XSSFWorkbook(adaptationExcelFile);
        
        
        for(int j=0;j<sectors.size();j++)
        {
            Sector sector = (Sector)sectors.get(j);
            String secId=sector.getId();
            
            List listRVs = sector.getResponsinilityVolumes();
            for(int k=0;k<listRVs.size();k++)
            {
                Sheet sheet = null;
                if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                    sheet = workbook.getSheet("airspace_volumes_guide");
                } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                    sheet = workbook.getSheet("airspace volume");
                } else {
                    JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                
                
                Volumen rv = (Volumen)listRVs.get(k);
                for(int l=1;l<sheet.getLastRowNum();l++)
                {
                    Row row = sheet.getRow(l);
                    String airspaceName = row.getCell(0).getStringCellValue();
                    if(airspaceName.equalsIgnoreCase(rv.getId()))
                    {
                        if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                            rv.setLoLimit(Integer.parseInt(row.getCell(5).getStringCellValue()));
                            rv.setUpLimit(Integer.parseInt(row.getCell(6).getStringCellValue()));
                        } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                            rv.setLoLimit((int)row.getCell(4).getNumericCellValue());
                            rv.setUpLimit((int)row.getCell(5).getNumericCellValue());
                        } else {
                            JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                        
                        
                        
                        Contour c = new Contour();
                        
                        
                        if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                            c.setId(row.getCell(2).getStringCellValue());
                        } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                            c.setId(row.getCell(6).getStringCellValue());
                        } else {
                            JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        rv.setContour(c);
                        break;
                    }
                }
                
            }
            
            List listIVs = sector.getInterestVolumes();
            for(int k=0;k<listIVs.size();k++)
            {
                Sheet sheet = null;
                if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                    sheet = workbook.getSheet("airspace_volumes_guide");
                } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                    sheet = workbook.getSheet("airspace volume");
                } else {
                    JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                
                Volumen iv = (Volumen)listIVs.get(k);
                for(int l=1;l<sheet.getLastRowNum();l++)
                {
                    Row row = sheet.getRow(l);
                    String airspaceName = row.getCell(0).getStringCellValue();
                    if(airspaceName.equalsIgnoreCase(iv.getId()))
                    {
                        if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                            iv.setLoLimit(Integer.parseInt(row.getCell(5).getStringCellValue()));
                            iv.setUpLimit(Integer.parseInt(row.getCell(6).getStringCellValue()));
                        } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                            iv.setLoLimit((int)row.getCell(4).getNumericCellValue());
                            iv.setUpLimit((int)row.getCell(5).getNumericCellValue());
                        } else {
                            JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        
                        
                        Contour c = new Contour();
                        if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                            c.setId(row.getCell(2).getStringCellValue());
                        } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                            c.setId(row.getCell(6).getStringCellValue());
                        } else {
                            JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        iv.setContour(c);
                        break;
                    }
                }
                
            }
        }
        workbook.close();
        
    }

    void loadAreaContours(List sectors, String pathToExcelFile) throws FileNotFoundException, IOException {
        FileInputStream adaptationExcelFile = new FileInputStream(pathToExcelFile);
        Workbook workbook = new XSSFWorkbook(adaptationExcelFile);
        
        System.out.println("====================================");
        System.out.println("     Responsibility Volumes data");
        System.out.println("====================================");
        Sheet sheet = null;
                
        if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
            sheet = workbook.getSheet("area_contour_points");
        } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
            sheet = workbook.getSheet("area contour point");
        } else {
            JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        for(int j=0;j<sectors.size();j++)
        {
            Sector sector = (Sector)sectors.get(j);
            List listRVs = sector.getResponsinilityVolumes();
            for(int k=0;k<listRVs.size();k++)
            {
                /*Sheet sheet = null;
                
                if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                    sheet = workbook.getSheet("area_contour_points");
                } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                    sheet = workbook.getSheet("area contour point");
                } else {
                    JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
                }*/
                
                
                
                Volumen rv = (Volumen)listRVs.get(k);
                System.out.println(String.format("%-15S", rv.getId())+" "+
                                   String.format("%3d", rv.getLoLimit())+" "+
                                   String.format("%3d", rv.getUpLimit()));
                List pointList= new ArrayList();
                int conta=0;
                for(int l=1;l<sheet.getLastRowNum();l++)
                {
                    Row row = sheet.getRow(l);
                    String areaContourId = row.getCell(0).getStringCellValue();
                    
                    if(areaContourId.equalsIgnoreCase(rv.getContour().getId()))
                    {
                        if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                            String latLong = null;
                            latLong = row.getCell(2).getStringCellValue();
                            System.out.println("--- "+latLong);
                            String lon = latLong.substring(7, 15);                        
                            String lat = latLong.substring(0, 7);

                            GSMpoint gsm = stringToGSMPoint(null, lat, lon);
                            gsm.setSeq(Integer.parseInt(row.getCell(3).getStringCellValue()));
                            pointList.add(conta,gsm);
                        } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                            String latLong = null;
                            latLong = row.getCell(1).getStringCellValue();
                            System.out.println("--- "+latLong);
                            String lat = latLong.substring(0, 7);
                            String lon = latLong.substring(7, 15);                        

                            GSMpoint gsm = stringToGSMPoint(null, lat, lon);
                            gsm.setSeq((int) row.getCell(2).getNumericCellValue());
                            pointList.add(conta,gsm);
                        } else {
                            JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        conta++;
                    }
                }
                rv.getContour().setPoints(pointList);
                //ordenar y agregar el primero con secuencia size+1;
                Collections.sort(pointList);
                GSMpoint copy = new GSMpoint().getAcopy((GSMpoint)pointList.get(0));
                GSMpoint last = (GSMpoint)pointList.get(pointList.size()-1);
                copy.setSeq(last.getSeq()+1);
                pointList.add(copy);
                Collections.sort(pointList);
                
            }
            
            
            
            
            List listIVs = sector.getInterestVolumes();
            for(int k=0;k<listIVs.size();k++)
            {
                /*Sheet sheet = null;
                
                if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                    sheet = workbook.getSheet("area_contour_points");
                } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                    sheet = workbook.getSheet("area contour point");
                } else {
                    JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
                }*/
                
                
                Volumen iv = (Volumen)listIVs.get(k);
                List pointList= new ArrayList();
                int conta=0;
                for(int l=1;l<sheet.getLastRowNum();l++)
                {
                    Row row = sheet.getRow(l);
                    String areaContourId = row.getCell(0).getStringCellValue();
                    
                    if(areaContourId.equalsIgnoreCase(iv.getContour().getId()))
                    {
                        if (window.getSelectedSystem().equalsIgnoreCase("iTEC v1")) {
                            String latLong = null;
                            latLong = row.getCell(2).getStringCellValue();
                            String lon = latLong.substring(7, 15);                        
                            String lat = latLong.substring(0, 7);

                            GSMpoint gsm = stringToGSMPoint(null, lat, lon);
                            gsm.setSeq(Integer.parseInt(row.getCell(3).getStringCellValue()));
                            pointList.add(conta,gsm);
                        } else if (window.getSelectedSystem().equalsIgnoreCase("iTEC v2")) {
                            String latLong = null;
                            latLong = row.getCell(1).getStringCellValue();
                            String lat = latLong.substring(0, 7);
                            String lon = latLong.substring(7, 15);                        

                            GSMpoint gsm = stringToGSMPoint(null, lat, lon);
                            gsm.setSeq((int) row.getCell(2).getNumericCellValue());
                            pointList.add(conta,gsm);
                        } else {
                            JOptionPane.showMessageDialog(window, "System " + window.getSelectedSystem().toString() + " not treated", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                iv.getContour().setPoints(pointList);
                //ordenar y agregar el primero con secuencia size+1;
                Collections.sort(pointList);
                GSMpoint copy = new GSMpoint().getAcopy((GSMpoint)pointList.get(0));
                GSMpoint last = (GSMpoint)pointList.get(pointList.size()-1);
                copy.setSeq(last.getSeq()+1);
                pointList.add(copy);
                Collections.sort(pointList);
                
            }
        }
        workbook.close();
        
    }

    
    
    public List create3DVolumes(List sectors) 
    {
        // Create a GeometryFactory to create geometries
        GeometryFactory geometryFactory = new GeometryFactory();
        
        List<Polygon> poligonesList=new ArrayList<>();
        for(int i=0;i<sectors.size();i++)
        {
            List linearRings=new ArrayList();
            List listRVs=((Sector)sectors.get(i)).getResponsinilityVolumes();
            for(int k=0;k<listRVs.size();k++)
            {
                Volumen v =(Volumen)listRVs.get(k);
                List<GSMpoint> listPoints=v.getContour().getPoints();
                Polygon poligon=this.createPolygone(listPoints);
            }
        }
        
        
        
        return poligonesList;    
    }
    
    

   

    void checkForOverlaps(List sectors, String condition) 
    {
        //If (highest altitude of first volume >= lowest altitude of second volume) AND (lowest altitude of first volume <= highest altitude of second volume)
        String condi=null;
        if(condition.equalsIgnoreCase("C"))
        {
            condi="Civil";
        }
        else
        {
            condi="Mil";
        }
        System.out.println("====================================");
        System.out.println("           "+condi+" Overlaps");
        System.out.println("====================================");
        List allRV=new ArrayList();
        
        int conta=1;
        List allPoly=new ArrayList();
        for(int p=0;p<sectors.size();p++)    
        {
            Sector s =(Sector)sectors.get(p);
            if(s.getCondition().equalsIgnoreCase(condition))
            {

                List rvs=s.getResponsinilityVolumes();
                for(int i=0;i<rvs.size();i++)
                {
                    allRV.add(rvs.get(i));
                }
            }
            else{
                int pppp=0;
            }
        }
        
        for(int i=0;i<allRV.size()-1;i++)
        //for(int i=0;i<allRV.size();i++)
        {
            Volumen v1 = (Volumen)allRV.get(i);
            List<GSMpoint> lp1=v1.getContour().getPoints();
            Polygon p1=this.createPolygone(lp1);
            
            p1.setUserData(new UserData(v1.getId(), v1.getLoLimit(), v1.getUpLimit()));
            
            allPoly.add(p1);//adds upp all the polygons so we can create later a Multipolygon object to check gaps
            

            
            for(int j=i+1;j<allRV.size();j++)
            //for(int j=1;j<allRV.size();j++)
            {
                Volumen v2 = (Volumen)allRV.get(j);
                if(v1.getId().equalsIgnoreCase(v2.getId())!=true)
                {
                
                    List<GSMpoint> lp2=v2.getContour().getPoints();
                    Polygon p2=this.createPolygone(lp2);
                    

                    //System.out.println("****Checking "+v1.getId()+" and "+v2.getId());

                    boolean overlap = checkIf2PolygonesOverlapWithEachother(p1, p2);
                    if(((v1.getUpLimit()>v2.getLoLimit()) && (v1.getLoLimit()<v2.getUpLimit())) && overlap==true)
                    {
                        System.out.println("Overlap <"+conta++ +">");
                        System.out.println("\t"+v1.getId()+"["+ v1.getLoLimit()+" - "+v1.getUpLimit()+"]"  +" "+v2.getId()+"["+ v2.getLoLimit()+" - "+v2.getUpLimit()+"]\n");
                    }
                }

            }
        }
        
        
        
        
        
        System.out.println("====================================");
        System.out.println("           "+condi+" Gaps");
        System.out.println("====================================");
        
        GeometryFactory factory = new GeometryFactory();
        Polygon[] polygons = (Polygon[])allPoly.toArray(new Polygon[0]); // create or retrieve the polygons
        MultiPolygon multiPolygon = factory.createMultiPolygon(polygons);
        
        Polygon mergedPolygon = (Polygon) multiPolygon.union();
        
        
        for(int i=0;i<mergedPolygon.getNumInteriorRing();i++)
        {
            System.out.println("Gap<"+(i+1)+">");
            LinearRing gap =  mergedPolygon.getInteriorRingN(i);
            
            for(int p=0;p<gap.getNumPoints();p++)
            {
                
                Point point = gap.getPointN(p);
                System.out.println("\t"+coordinateToHHMMSS("LAT", point.getX(),"%02d%02d%02d")+" "+coordinateToHHMMSS("LON",point.getY(),"%03d%02d%02d")+"    "+point);
            }
            System.out.println("");
        }
        
        
        
        
        
        
        
        /*
        try {
            // Create a GeometryFactory
            GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();

            // Create a MultiPolygon object from the list of polygons
            MultiPolygon multiPolygon = geometryFactory.createMultiPolygon((Polygon[]) allPoly.toArray(new Polygon[allPoly.size()]));

            // Use the MultiPolygon's union method to combine the polygons into a single geometry
            Geometry combinedPolygons = multiPolygon.union();

            // Use the MultiPolygon's difference method to find the gaps between the polygons
            Geometry gaps = multiPolygon.difference(combinedPolygons);

        } catch (Exception ex) {
            System.out.println("Fix all overlaps before checking gaps\nContact your system administrator if the error persist");
        }*/
        
       
        
        
    }
    
    public String coordinateToHHMMSS(String type, double coordinate, String mask)
    {
        String end=null;
        
        if(type.equalsIgnoreCase("LON"))
        {
            if(coordinate>=0)
            {
                end="E";
            }
            else
            {
                end="W";
            }
        }
        else if(type.equalsIgnoreCase("LAT"))
        {
            if(coordinate>=0)
            {
                end="N";
            }
            else
            {
                end="S";
            }
        }
        else
        {
            System.out.println("Coordinate "+type+" type not treated");
        }
    

        
        coordinate=Math.abs(coordinate);
        
        int degrees = (int) coordinate;
        int minutes = (int) ((coordinate - degrees) * 60);
        int seconds = (int)((((coordinate - degrees) * 60) - minutes) * 60);
        
        
        String hms = String.format(mask, degrees, minutes, seconds);
        
        
        return hms+end;
    }
    
    public boolean checkIf2PolygonesOverlapWithEachother(Polygon polygon1, Polygon polygon2)
    {
        boolean sw =false;
        
        
        if(polygon1.overlaps(polygon2))
        {
            sw=true;
        }
        else
        {
            sw=false;
        }
        return sw;
    }
}

    


