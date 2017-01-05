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
import web.ve.alphasigma.modelo.Modelo;
import web.ve.alphasigma.modelo.Vertice;
import web.ve.alphasigma.vista.Dibujable;

import java.util.List;
import java.util.stream.Collectors;

public class Controlador {
    private Modelo modelo;
    private GUI vista;

    public Controlador() {
        this.modelo = new Modelo();
        this.vista = new GUI(this);
    }

    public VerticeObservable nuevoVertice(){
        VerticeObservable v = new VerticeObservable();
        modelo.añadirVertice(v);
        return v;
    }

    public AristaObservable nuevaArista(){
        List<Dibujable> seleccion = vista.panel_canvas.seleccion.stream()
                                            .filter((Dibujable d) -> d instanceof Vertice)
                                            .collect(Collectors.toList());

        vista.panel_canvas.seleccion.forEach(Dibujable::seleccionar);
        vista.panel_canvas.seleccion.clear();

        //TODO:Validar seleccion vacia
        AristaObservable v = new AristaObservable((Vertice)seleccion.get(0), (Vertice)seleccion.get(1));
        modelo.añadirArista(v);
        return v;
    }
}
