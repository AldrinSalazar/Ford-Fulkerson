/*
 * Conexion.java       01/04/2017
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

import com.sun.istack.internal.NotNull;

/**
 * Conexion representa la union unidireccional entre dos Vertices. Hereda de Identificable para poder ser comparada
 * facilmente en la implementacion de los algoritmos.
 *
 * @see web.ve.alphasigma.modelo.Identificable
 * @version 1.0.0 1/4/2017
 * @author Aldrin Salazar
 */
public class Conexion extends Identificable{

    /** Vertices de inicio y fin.*/
    private Vertice inicio;
    private Vertice fin;

    /**
     * Son siempre necesarios ambos vertices para tener una conexion.
     *
     * @see Vertice
     * @param inicio Vertice de inicio.
     * @param fin Vertice de destino.
     */
    public Conexion(@NotNull Vertice inicio, @NotNull Vertice fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    /**
     * Obtiene el Vertice de inicio de la instancia.
     *
     * @return Vertice de inicio de la conexion.
     */
    public Vertice getInicio() {
        return inicio;
    }

    /**
     *Asigna un nuevo Vertice de inicio a la instancia.
     *
     * @param inicio Vertice de inicio a asignar.
     */
    public void setInicio(@NotNull Vertice inicio) {
        this.inicio = inicio;
    }

    /**
     * Obtiene el Vertice de destino de la instancia.
     *
     * @return Vertice de destino de la conexion.
     */
    public Vertice getFin() {
        return fin;
    }

    /**
     *Asigna un nuevo Vertice de destino a la instancia.
     *
     * @param fin Vertice de destino a asignar.
     */
    public void setFin(@NotNull Vertice fin) {
        this.fin = fin;
    }

    /**
     * Invierte la direccion de la conexion, esto es, intercambiar los valores de inicio y destino.
     */
    public void invertir(){
        Vertice tmp = this.inicio;
        this.inicio = this.fin;
        this.fin = tmp;
    }
}