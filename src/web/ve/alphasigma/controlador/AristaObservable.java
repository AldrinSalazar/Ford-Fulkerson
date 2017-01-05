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


import web.ve.alphasigma.modelo.Arista;
import web.ve.alphasigma.modelo.Vertice;

import java.util.Observable;
import java.util.Observer;

public class AristaObservable extends Arista {

    private Observable observable;

    public AristaObservable(Vertice inicio, Vertice fin, int capacidad, int flujo) throws IllegalArgumentException {
        super(inicio, fin, capacidad, flujo);
    }

    public AristaObservable(Vertice inicio, Vertice fin, int capacidad) {
        super(inicio, fin, capacidad);
    }

    public AristaObservable(Vertice inicio, Vertice fin) {
        super(inicio, fin);
    }

    public void añadirObservador(Observer o){
        observable.addObserver(o);
    }

    public void quitarObservador(Observer o){
        observable.deleteObserver(o);
    }

    @Override
    public void setCapacidad(int capacidad) {
        super.setCapacidad(capacidad);
        this.observable.notifyObservers();
    }

    @Override
    public void setFlujo(int flujo) throws IllegalArgumentException {
        super.setFlujo(flujo);
        this.observable.notifyObservers();
    }
}
