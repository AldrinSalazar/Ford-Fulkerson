/*
 * Arista.java       01/04/2017
 * Copyright (C) 2017  Aldrin Salazar
 * https://github.com/AldrinSalazar/Ford-Fulkerson
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

import java.io.Serializable;

/**
 * Arista representa una Arista unidireccional en un campo de Grafos dirigidos.
 *
 * @see Conexion
 * @version 1.0.0 1/4/2017
 * @author Aldrin Salazar
 */
public class Arista extends Conexion implements Serializable{
    /**
     * En esta implementacion de una Arista, esta posee solamente una capacidad y un flujo.
     */

    /** Capacidad es el flujo maximo que puede circular por la Arista*/
    private int capacidad;

    /** *Flujo es el flujo actual circulando en la Arista*/
    private int flujo;
    private boolean invertida;
    /**
     * Una Arista requiere tener un Vertice de origen, un Vertice de destino ademas de una capacidad y flujo positivos.
     * La direccion esta en el sentido Inicio -> Fin:
     *          G(V, A) => A(u, v) = u -> v
     *
     * @param inicio Vertice donde inicia la Arista.
     * @param fin Vertice donde finaliza la Arista
     * @param capacidad Flujo maximo que puede correr por la Arista.
     * @param flujo Flujo actual que recorre la Arista.
     * @throws IllegalArgumentException En caso de Flujo mayor a capacidad, Flujo negativo o Capacidad negativa.
     */
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
        this.invertida = false;
    }

    /**
     * Abreviacion para una Arista con flujo actual igual a 0.
     *
     * @param inicio Vertice donde inicia la Arista.
     * @param fin Vertice donde finaliza la Arista
     * @param capacidad Flujo maximo que puede correr por la Arista.
     */
    public Arista(Vertice inicio, Vertice fin, int capacidad){
        this(inicio, fin, capacidad, 0);
    }

    /**
     * Abreviacion para una Arista con flujo actual igual a 0 y capacidad igual a 0.
     *
     * @param inicio Vertice donde inicia la Arista.
     * @param fin Vertice donde finaliza la Arista
     */
    public Arista(Vertice inicio, Vertice fin){
        this(inicio, fin, 0, 0);
    }

    /**
     * Obtiene la Capacidad de la instancia.
     *
     * @return Capacidad de la instancia.
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Asigna la Capacidad de la instancia.
     *
     * @param capacidad Capacidad a asignar.
     */
    public void setCapacidad(int capacidad) throws IllegalArgumentException{
        if(capacidad < 0){
            throw new IllegalArgumentException("Capacidad negativa.");
        }

        this.capacidad = capacidad;
    }

    /**
     * Obtiene el Flujo de la instancia.
     *
     * @return Flujo de la instancia.
     */
    public int getFlujo() {
        return flujo;
    }

    /**
     * Asigna un nuevo flujo que recorre la Arista, este debe cumplir con:
     *      0 <= flujo <= capacidad
     *
     *  La asignacion de un flujo representa simbolicamente una Arista con Capacidad igual a capacidad-flujo pero
     *  en sentido contrario, es decir, la asignacion de un flujo igual a la capacidad de la Arista resulta en el
     *  cambio de sentido de esta, con la misma capacidad y un flujo 0.
     *
     * @param flujo Flujo nuevo a asignar.
     * @throws IllegalArgumentException En caso de no cumplir con 0 <= flujo <= capacidad.
     */
    public void setFlujo(int flujo) throws IllegalArgumentException{
        if(flujo > this.capacidad) {
            throw new IllegalArgumentException("Flujo mayor a capacidad");
        } else if(flujo == capacidad){
            /** Al cumplirse implica la existencia de una Arista con capacidad 0, a fines de la implementacion, esto es
            invertir el sentido de la Arista, conservando la capacidad pero con flujo 0.*/
            this.flujo = 0;
            invertir();
            this.invertida = true;
        }else if(flujo < 0) {
            throw new IllegalArgumentException("Flujo negativo");
        }else {
            this.flujo = flujo;
        }
    }

    public void overFlujo(int flujo){
        this.flujo = flujo;
    }

    /**
     * Obtiene el flujo restante que se puede asignar a la Arista, es decir, la diferencia entre Capacidad-Flujo.
     *
     * @return Diferencia entre Capacidad-Flujo.
     */
    public int getFlujoRestante(){
        return this.capacidad - this.flujo;
    }

    public boolean isInvertida() {
        return invertida;
    }

    public void setInvertida(boolean invertida) {
        this.invertida = invertida;
    }
}