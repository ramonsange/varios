/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.excelmanager;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author Ramon
 */
class Columna implements Comparable<Columna> {
    private String nombre;
    private int index;
    private List<Cell> listaCeldas;

    public Columna(int index, String nombre, List listaCeldas) {
        this.nombre = nombre;
        this.index = index;
        this.listaCeldas = listaCeldas;
    }

    public Columna() {
        this.listaCeldas = new ArrayList<Cell>();
        
    }
    public void setIndex(int index)
    {
        this.index = index;
    }
    public void setNombre(String nombre)
    {
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Cell> getListaCeldas() {
        return listaCeldas;
    }

    public void setListaCeldas(List<Cell> listaCeldas) {
        this.listaCeldas = listaCeldas;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int compareTo(Columna otra) {
        return this.nombre.compareTo(otra.nombre);
    }
}
