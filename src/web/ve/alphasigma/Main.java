/*
 * Main.java       01/04/2017
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
package web.ve.alphasigma;

import web.ve.alphasigma.controlador.Controlador;

public class Main {

    public static void main(String[] args) {//TODO: Documentacion
        javax.swing.SwingUtilities.invokeLater( ()->{
            Utils.LogI("Iniciar GUI");
            new Controlador();
            Utils.LogI("GUI iniciado");
        } );
    }
}
