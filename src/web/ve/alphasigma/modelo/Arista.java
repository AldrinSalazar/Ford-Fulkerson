/*
 * Arista.java       01/04/2017
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
 *
 */
package web.ve.alphasigma.modelo;

/**
 * Arista representa una Arista en un campo de Grafos.
 *
 * @see Conexion
 * @version 1.0.0 1/4/2017
 * @author Aldrin Salazar
 */
public class Arista extends Conexion{ //TODO:Terminar Documentacion
    private int capacidad;
    private int flujo;

    public Arista(Vertice inicio, Vertice fin){
        this(inicio, fin, 0, 0);
    }

    public Arista(Vertice inicio, Vertice fin, int capacidad){
        this(inicio, fin, capacidad, 0);
    }

    public Arista(Vertice inicio, Vertice fin, int capacidad, int flujo) throws IllegalArgumentException{
        super(inicio, fin);

        if(flujo > capacidad)
            throw new IllegalArgumentException("Flujo mayor a capacidad");
        else if (flujo < 0)
            throw new IllegalArgumentException("Flujo negativo");
        else if (capacidad < 0)
            throw new IllegalArgumentException("Capacidad negativa");

        this.capacidad = capacidad;
        this.flujo = flujo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getFlujo() {
        return flujo;
    }

    public void setFlujo(int flujo) throws IllegalArgumentException{
        if(flujo > this.capacidad) {
            throw new IllegalArgumentException("Flujo mayor a capacidad");
        } else if(flujo == capacidad){
            this.flujo = 0;
            invertir();
        }else {
            this.flujo = flujo;
        }
    }

    public int getFlujoRestante(){
        return this.capacidad - this.flujo;
    }

}
