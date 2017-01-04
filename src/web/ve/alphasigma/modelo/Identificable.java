/*
 * Identificable.java       01/04/2017
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
import java.util.UUID;

/**
 * Identificable es una clase que contiene formas unicas de identificarse para su comparacion.
 *
 * @version 1.0.0 1/4/2017
 * @author Aldrin Salazar
 */
public class Identificable {

    /** UUID (Universally Unique IDentifier) es un estandar de identificacion (ISO/IEC 9834-8:2005)
     *  que permite asignar aleatoriamente una ID con una probabilidad de colision de 5 * 10^-11 */
    private UUID id;

    /** Nombre si se desea asignar alguno*/
    private String nombre;

    /** Valor si se desea asignar alguno*/
    private Integer valor;

    /**
     * Es posible crear uno con nombre y valor nulos, ya que son complementarios al UUID
     *
     * @param nombre Nombre si se desea asignar alguno
     * @param valor Valor si se desea asignar alguno
     */
    Identificable(@Nullable String nombre, @Nullable Integer valor) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.valor = valor;
    }

    /**
     * Constructor de abreviacion en caso de no usar ambos parametros opcionales
     */
    public Identificable() {
        this(null, null);
    }

    /**
     * Obtiene el UUID asignado a la instancia.
     *
     * @return UUID asignado a la instancia
     */
    public UUID getId() {
        return id;
    }

    /**
     * Obtiene el nombre asignado a la instancia.
     *
     * @return Nombre asignado a la instancia. Puede ser null en caso de no tener nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna un nombre a la instancia.
     *
     * @param nombre Nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el valor asignado a la instancia.
     *
     * @return Valor asignado a la instancia. Puede ser null en caso de no tener valor.
     */
    public Integer getValor() {
        return valor;
    }

    /**
     * Asigna un valor positivo a la instancia. En caso de ser negativo arroja una IllegalArgumentException
     *
     * @param valor El valor a asignar. Debe ser positivo.
     * @throws IllegalArgumentException
     */
    public void setValor(Integer valor) throws IllegalArgumentException{
        if(valor < 0)
            throw new IllegalArgumentException("Valor negativo");
        else
            this.valor = valor;
    }

    /**
     * La funcion de comparacion es sobreescrita para comparar dos objetos por su UUID en lugar de la funcionalidad
     * definida por defecto.
     *
     * @param obj Objeto a comparar.
     * @return Si sus UUID son iguales o no.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Identificable && this.id.equals(((Identificable) obj).getId());
    }
}