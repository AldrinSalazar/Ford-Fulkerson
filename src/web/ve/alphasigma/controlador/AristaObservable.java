/*
 * AristaObservable.java    01/04/2017
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
import web.ve.alphasigma.vista.Dibujable;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * AristaObservable es una abstraccion de la arista basica del modelo, con la capacidad de ser dibujada en una interfaz
 * y de aplicar el patron de ser observable
 *
 * @see web.ve.alphasigma.modelo.Arista
 * @see web.ve.alphasigma.vista.Dibujable
 * @version 1.0.0 01/25/2017
 * @author Aldrin Salazar
 */
public class AristaObservable extends Arista implements Dibujable {

    //Color de la flecha
    private static final Color COLOR_FLECHA = Color.BLACK;
    //Color de la flecha al estar seleccionada
    private static final Color COLOR_FLECHA_SEL = Color.GREEN;
    //Color del texto
    private static final Color COLOR_TEXTO = Color.RED;
    //Color del texto al estar seleccionado
    private static final Color COLOR_TEXTO_SEL = Color.CYAN;
    //Espacio entre centro de la flecha y donde se dibujara efectivamente
    private static final int OFFSET = ((VerticeObservable.DIAMETRO_DIBUJO)/2)+8;
    //Ancho de la linea de la flecha
    private static final int ANCHO_LINEA = 2;
    //Tamaño de la flecha
    private static final int TAMAÑO_FLECHA = 10;
    //Numero de fuente del texto
    private static final int TAMAÑO_TEXTO = 15;

    private Observable observable;
    private Point posicion;
    private boolean seleccionado;

    /**
     * Ver Arista.
     *
     * @see Arista
     * @param inicio Vertice donde inicia la Arista.
     * @param fin Vertice donde finaliza la Arista
     * @param capacidad Flujo maximo que puede correr por la Arista.
     * @param flujo Flujo actual que recorre la Arista.
     * @throws IllegalArgumentException En caso de Flujo mayor a capacidad, Flujo negativo o Capacidad negativa.
     */
    public AristaObservable(Vertice inicio, Vertice fin, int capacidad, int flujo) throws IllegalArgumentException {
        super(inicio, fin, capacidad, flujo);
        observable = new Observable();
        posicion = new Point(0,0);
        seleccionado = false;
    }

    /**
     * Abreviacion para una Arista con flujo actual igual a 0.
     *
     * @param inicio Vertice donde inicia la Arista.
     * @param fin Vertice donde finaliza la Arista
     * @param capacidad Flujo maximo que puede correr por la Arista.
     */
    public AristaObservable(Vertice inicio, Vertice fin, int capacidad) {
        this(inicio, fin, capacidad, 0);
    }

    /**
     * Abreviacion para una Arista con flujo actual igual a 0 y capacidad igual a 0.
     *
     * @param inicio Vertice donde inicia la Arista.
     * @param fin Vertice donde finaliza la Arista
     */
    public AristaObservable(Vertice inicio, Vertice fin) {
        this(inicio, fin, 0);
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

    @Override
    public void dibujar(Graphics g) {
        Point tmpI = ((Dibujable)getInicio()).getPosicion();
        Point tmpF = ((Dibujable)getFin()).getPosicion();

        g.setColor(seleccionado ? COLOR_FLECHA_SEL : COLOR_FLECHA);
        ((Graphics2D) g).setStroke(new BasicStroke(ANCHO_LINEA));

        dibujarFlecha(g, tmpI.x, tmpI.y, tmpF.x, tmpF.y, TAMAÑO_FLECHA, TAMAÑO_FLECHA, OFFSET);
        posicion = new Point((tmpI.x + tmpF.x)/2, (tmpI.y + tmpF.y)/2);

        g.setFont(new Font("TimesRoman", Font.BOLD, TAMAÑO_TEXTO));
        g.setColor(seleccionado ? COLOR_TEXTO : COLOR_TEXTO_SEL);
        g.drawString(getFlujo()+"/"+getCapacidad(), posicion.x, posicion.y);
    }

    /**
     * Dibuja una flecha recta entre dos puntos
     * @param g Buffer a dibujar
     * @param x1o X punto 1
     * @param y1o Y punto 1
     * @param x2o X punto 2
     * @param y2o Y punto 2
     * @param d  Ancho de la flecha
     * @param h  Alto de la flecha
     * @param offset Offset del centro
     */
    private void dibujarFlecha(Graphics g, int x1o, int y1o, int x2o, int y2o, int d, int h, int offset){
        int dxo = x2o - x1o, dyo = y2o - y1o;
        double Do = Math.sqrt(dxo*dxo + dyo*dyo);
        double t = offset/Do;

        int x1 = (int)(((1 - t) * x1o)+(t * x2o));
        int y1 = (int)(((1 - t) * y1o)+(t * y2o));
        int x2 = (int)(((1 - t) * x2o)+(t * x1o));
        int y2 = (int)(((1 - t) * y2o)+(t * y1o));

        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy/D, cos = dx/D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine( x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    @Override
    public void setPosicion(Point p) {
        posicion = p;
    }

    @Override
    public Point getPosicion() {
        return posicion;
    }

    @Override
    public void seleccionar() {
        seleccionado = !seleccionado;
    }

    @Override
    public boolean estaSeleccionado() {
        return seleccionado;
    }
}
