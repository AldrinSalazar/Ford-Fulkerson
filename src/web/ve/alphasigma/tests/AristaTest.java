package web.ve.alphasigma.tests;

import org.junit.Test;
import web.ve.alphasigma.modelo.Arista;
import web.ve.alphasigma.modelo.Vertice;

import static org.junit.Assert.*;

public class AristaTest {

    Vertice a;
    Vertice b;

    public AristaTest() {
        a = new Vertice();
        b = new Vertice();
    }

    @Test(expected = IllegalArgumentException.class)
    public void instanciacionFlujoMayorCapacidad(){
        Arista test = new Arista(a, b, 10, 11);
    }

    @Test(expected = IllegalArgumentException.class)
    public void instanciacionFlujoNegativo(){
        Arista test = new Arista(a, b, 10, -10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void instanciacionCapacidadNegativa(){
        Arista test = new Arista(a, b, -10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFlujoNegativo() throws Exception {
        Arista test = new Arista(a, b, 10, 10);
        test.setFlujo(11);
    }

    @Test
    public void invertirTest(){
        Arista test = new Arista(a, b, 10, 10);
    }

}