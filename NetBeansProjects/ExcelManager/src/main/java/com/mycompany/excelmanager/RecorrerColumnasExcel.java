package com.mycompany.excelmanager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class RecorrerColumnasExcel {
    public static void main(String[] args) {
        String archivoExcel = "c:/archivo.xlsx";

        try (FileInputStream fis = new FileInputStream(archivoExcel);
             Workbook workbook = new XSSFWorkbook(fis);
             Workbook nuevoWorkbook = new XSSFWorkbook()) 
        {

            // Recorre todas las hojas del archivo
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) 
            {
                
                Sheet sheet = workbook.getSheetAt(i);
                /*if(!sheet.getSheetName().equalsIgnoreCase("Area Contour Point"))
                {
                    continue;
                }*/
                System.out.println("Hoja " + i + ": " + sheet.getSheetName());

                // Obtiene el número máximo de columnas en la hoja
                int maxColumnas = 0;
                for (Row row : sheet) {
                    int numColumnas = row.getLastCellNum();
                    if (numColumnas > maxColumnas) {
                        maxColumnas = numColumnas;
                    }
                }

                // Recorre las columnas
                
                List<Columna> listaColumnas = new ArrayList<Columna>();
                for (int j = 0; j < maxColumnas; j++) 
                {
                    System.out.print("Columna " + j + ":\t");
                    Columna columna = new Columna();
                    columna.setIndex(j);
                    
                    for (Row row : sheet) 
                    {
                        Cell cell = row.getCell(j);
                        
                        if(row.getRowNum()==0&&cell!=null)
                        {
                            columna.setNombre(cell.getStringCellValue());
                        }
                        
                        if (cell != null) 
                        {
                            switch (cell.getCellType()) {
                                case STRING:
                                    System.out.print(cell.getStringCellValue() + "\t");
                                    columna.getListaCeldas().add(cell);
                                    break;
                                case NUMERIC:
                                    System.out.print(cell.getNumericCellValue() + "\t");
                                    columna.getListaCeldas().add(cell);
                                    break;
                                case BOOLEAN:
                                    System.out.print(cell.getBooleanCellValue() + "\t");
                                    columna.getListaCeldas().add(cell);
                                    break;
                                case BLANK:
                                    System.out.print("**BLANK\t");
                                    columna.getListaCeldas().add(cell);
                                    break;
                                default:
                                    System.out.print("**N/A\t");
                                    break;
                            }
                        } else {
                            System.out.print("\t"); // Si la celda está vacía
                        }
                    }
                    listaColumnas.add(columna);
                    System.out.println(); // Salto de línea al final de cada columna
                }
                
                
                Collections.sort(listaColumnas);
                resetIndex(listaColumnas);
                System.out.println(); // Línea en blanco para separar las hojas
                
                //guardamos las celdas en el libro de destino
                guardarEnDestino(nuevoWorkbook,sheet.getSheetName(),listaColumnas );
                
            }
            crearLibroFinal(nuevoWorkbook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void resetIndex(List<Columna> listaColumnas)
    {
        for (int i = 0; i < listaColumnas.size(); i++) 
        {
            Columna columna = (Columna) listaColumnas.get(i);
            columna.setIndex(i);
        }
        
    }
    private static void guardarEnDestino(Workbook nuevoWorkbook, String sheetName, List<Columna> listaColumnas) 
    {
        Sheet nuevaHoja = nuevoWorkbook.createSheet(sheetName);

        // Recorremos las columnas
        for (int k = 0; k < listaColumnas.size(); k++) {
            Columna columna = (Columna) listaColumnas.get(k);
            List<Cell> listaCeldas = (List<Cell>) columna.getListaCeldas();

            // Recorremos las celdas en la columna
            for (int i = 0; i < listaCeldas.size(); i++) {
                Row row = null;
                if (nuevaHoja.getRow(i) == null) {
                    row = nuevaHoja.createRow(i);
                } else {
                    row = nuevaHoja.getRow(i);
                }
                Cell cellOrigen = (Cell) listaCeldas.get(i);
                Cell cellDestino = row.createCell(k);
                copiarCelda(cellOrigen, cellDestino);
            }
        }

    }

    public static void copiarCelda(Cell corigen, Cell cdestino) {
        if (corigen != null) {
            switch (corigen.getCellType()) {
                case STRING:
                    
                    cdestino.setCellValue(corigen.getStringCellValue());
                    break;
                case NUMERIC:
                    
                    cdestino.setCellValue(corigen.getNumericCellValue());
                    break;
                case BOOLEAN:
                    cdestino.setCellValue(corigen.getBooleanCellValue());
                    break;
                case BLANK:
                    cdestino.setCellValue("");
                    
                    break;
                default:
                    cdestino.setCellValue("$$$ERROR$$$");
            }
        } else {
            System.out.print("\t"); // Si la celda está vacía
        }
    }

    private static void crearLibroFinal(Workbook nuevoWorkbook) throws FileNotFoundException, IOException 
    {
        // Guardar el nuevo archivo
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH_mm_ss");
            String date = sdf.format(Calendar.getInstance().getTime());
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Ramon\\Downloads\\"+date+" archivo_reordenado.xlsx");
            nuevoWorkbook.write(fos);
            nuevoWorkbook.close();
            fos.close();
    }
}
