/*
 * Vertice.java       01/04/2017
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

import com.sun.istack.internal.Nullable;

/**
 * Vertice representa un Vertice en un campo de Grafos.
 *
 * @see web.ve.alphasigma.modelo.Identificable
 * @version 1.0.0 1/4/2017
 * @author Aldrin Salazar
 */
public class Vertice extends Identificable{
    /**
     * Esta implementacion en especifico asigna a cada instancia un tipo.
     */

    /** Posibles tipos de Vertice.
     *      SUMIDERO: Es el destino del flujo en una red de Grafos.
     *      FUENTE: Es el origen del flujo en una red de Grafos.
     *      NINGUNO: Es un Vertice sin algun tipo especifico.
     * */
    public enum Tipo{
        SUMIDERO,
        FUENTE,
        NINGUNO
    }

    /** El tipo de Vertice*/
    private Tipo tipo;

    /**
     * Cada instancia es necesaria que posea un tipo.
     *
     * @param tipo El tipo del Vertice
     * @param nombre El nombre asignado. Opcional.
     * @param valor El valor asignado. Opcional.
     */
    public Vertice(Tipo tipo, @Nullable String nombre, @Nullable Integer valor) {
        super(nombre, valor);
        this.tipo = tipo;
    }

    /**
     * Abreviacion para un Vertice sin nombre ni valor.
     *
     * @param tipo El tipo del vertice.
     */
    public Vertice(Tipo tipo) {
        this(tipo, null, null);
    }

    /**
     * Abreviacion para un Vertice sin nombre, valor y sin tipo especifico.
     */
    public Vertice() {
        this(Tipo.NINGUNO);
    }

    /**
     * Obtiene el tipo de la instancia.
     *
     * @return Tipo del vertice.
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Asigna un tipo a la instancia.
     *
     * @param tipo Tipo a asignar.
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}