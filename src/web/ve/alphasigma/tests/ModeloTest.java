package web.ve.alphasigma.tests;

import org.junit.Test;
import web.ve.alphasigma.modelo.Arista;
import web.ve.alphasigma.modelo.Modelo;
import web.ve.alphasigma.modelo.Vertice;

import static org.junit.Assert.*;


public class ModeloTest {

    @Test(expected = IllegalArgumentException.class)
    public void validarArista(){
        Modelo modelo = new Modelo();
        Vertice v1 = new Vertice();
        Vertice v2 = new Vertice();
        Vertice v3 = new Vertice();

        Arista a1 = new Arista(v1, v2, 10, 10);
        Arista a2 = new Arista(v2, v3, 10, 10);

        modelo.añadirVertice(v1);
        modelo.añadirVertice(v2);
        modelo.añadirArista(a1);
        modelo.añadirArista(a2);
    }


}