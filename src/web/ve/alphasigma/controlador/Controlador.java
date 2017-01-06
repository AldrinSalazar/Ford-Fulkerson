/*
 * Copyright (C) 2017  Aldrin Salazar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see http://www.gnu.org/licenses
 */

package web.ve.alphasigma.controlador;


import web.ve.alphasigma.GUI;
import web.ve.alphasigma.modelo.Arista;
import web.ve.alphasigma.modelo.Identificable;
import web.ve.alphasigma.modelo.Modelo;
import web.ve.alphasigma.modelo.Vertice;
import web.ve.alphasigma.vista.Dibujable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Controlador {
    private Modelo modelo;
    private GUI vista;

    public Controlador() {
        this.modelo = new Modelo();
        this.vista = new GUI(this);
    }

    public VerticeObservable nuevoVertice() {
        VerticeObservable v = new VerticeObservable();
        modelo.añadirVertice(v);
        return v;
    }

    public ArrayList<AristaObservable> nuevaArista() {
        ArrayList<AristaObservable> re = new ArrayList<>();

        List<Dibujable> seleccion = vista.panel_canvas.seleccion.stream()
                .filter((Dibujable d) -> d instanceof Vertice)
                .collect(Collectors.toList());

        vista.panel_canvas.seleccion.forEach(Dibujable::seleccionar);
        vista.panel_canvas.seleccion.clear();

        //TODO:Refactorizar
        if (seleccion.size() == 0) {
            JOptionPane.showMessageDialog(null, "Error: Sin Vertices seleccionados.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (seleccion.size() == 1) {
            JOptionPane.showMessageDialog(null, "Error: Se necesitan al menos dos vertices.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (seleccion.size() == 2) {
            AristaObservable v = new AristaObservable((Vertice) seleccion.get(0), (Vertice) seleccion.get(1));
            re.add(v);
        } else {
            for (int i = 0; i < seleccion.size() - 1; i++) {
                re.add(new AristaObservable((Vertice) seleccion.get(i), (Vertice) seleccion.get(i + 1)));
            }
        }


        re.forEach(modelo::añadirArista);
        return re;
    }

    public void borrar(){
        Set<Identificable> aBorrar = new HashSet<>();

        for(int i = 0; i< vista.panel_canvas.seleccion.size() ; i++){
            if(vista.panel_canvas.seleccion.get(i) instanceof  Vertice){
                aBorrar.addAll(modelo.dependencias((Vertice) vista.panel_canvas.seleccion.get(i)));
            }
        }

        for(int i = 0; i< vista.panel_canvas.seleccion.size() ; i++){
            aBorrar.add((Identificable)vista.panel_canvas.seleccion.get(i));
        }

        aBorrar.forEach(modelo::eliminar);
        aBorrar.forEach((Identificable d) -> vista.panel_canvas.quitarDibujable((Dibujable)d));
    }

    public void debug() {
        System.out.println(modelo.estado());
    }
}