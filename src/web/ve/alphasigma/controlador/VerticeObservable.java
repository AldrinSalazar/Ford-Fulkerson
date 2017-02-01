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

import com.sun.istack.internal.Nullable;
import web.ve.alphasigma.modelo.Vertice;
import web.ve.alphasigma.vista.Dibujable;

import java.awt.*;
import java.util.Observable;

public class VerticeObservable extends Vertice implements Dibujable{
    public final static int DIAMETRO_DIBUJO = 40;
    private final static int RADIO_DIBUJO = DIAMETRO_DIBUJO / 2;
    private final static Color COLOR = Color.ORANGE;
    private final static Color COLOR_SEL = Color.CYAN;

    private boolean seleccionado;
    private Point posicion;
    private Observable observable;

    public VerticeObservable(Tipo tipo, @Nullable String nombre, @Nullable Integer valor) {
        super(tipo, nombre, valor);
        observable = new Observable();
        posicion = new Point(-100, -100); //TODO:Refactorizar?
        seleccionado = false;
    }

    public VerticeObservable(Tipo tipo) {
        this(tipo, null, null);
    }

    public VerticeObservable() {
        this(Tipo.NINGUNO);
    }

    public Observable getObservable() {
        return observable;
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(seleccionado ? COLOR_SEL : COLOR);
        g.fillOval(posicion.x - RADIO_DIBUJO, posicion.y - RADIO_DIBUJO, DIAMETRO_DIBUJO, DIAMETRO_DIBUJO);
        g.setColor(Color.BLACK);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.drawOval(posicion.x - RADIO_DIBUJO, posicion.y - RADIO_DIBUJO, DIAMETRO_DIBUJO, DIAMETRO_DIBUJO);

        g.setFont(new Font("TimesRoman", Font.BOLD, 20));

        if(getTipo() == Tipo.SUMIDERO){
            g.drawString("S", posicion.x - 7, posicion.y + 7);
        }else if(getTipo() == Tipo.FUENTE){
            g.drawString("F", posicion.x - 5, posicion.y + 7);
        }
    }

    @Override
    public void setPosicion(Point p) {
        this.posicion = p;
    }

    @Override
    public Point getPosicion() {
        return this.posicion;
    }

    @Override
    public boolean estaSeleccionado() {
        return seleccionado;
    }

    @Override
    public void seleccionar(){
        seleccionado = !seleccionado;
    }
}
