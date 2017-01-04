package web.ve.alphasigma.tests;

import org.junit.Test;
import web.ve.alphasigma.modelo.Arista;
import web.ve.alphasigma.modelo.Modelo;
import web.ve.alphasigma.modelo.Vertice;

import java.util.List;

import static org.junit.Assert.*;


public class ModeloTest {
    Modelo modelo;

    public ModeloTest() {
        modelo = new Modelo();
    }

    @Test(expected = IllegalArgumentException.class)
    public void validarAristaConVerticeSinIncluir(){
        Vertice v1 = new Vertice();
        Vertice v2 = new Vertice();
        Vertice v3 = new Vertice();

        Arista a1 = new Arista(v1, v2, 10, 10);
        Arista a2 = new Arista(v2, v3, 10, 10);

        modelo.añadirVertice(v1, v2);
        modelo.añadirArista(a1, a2);
    }

    @Test
    public void añadirVerticeTest(){
        Vertice a = new Vertice();
        modelo.añadirVertice(a);
        assertTrue(modelo.getVertices().contains(a));
    }

    @Test
    public void aristasFlujoCeroTest(){
        Vertice a = new Vertice();
        Vertice b = new Vertice();
        Vertice c = new Vertice();

        Arista ab = new Arista(a, b, 10, 10);
        Arista bc = new Arista(b, c, 5, 3);

        modelo.añadirVertice(a, b, c);
        modelo.añadirArista(ab, bc);
        modelo.setAristasFlujoCero();

        assertEquals(0, ab.getFlujo());
        assertEquals(0, bc.getFlujo());
    }

    @Test
    public void adyacentesTest(){
        Vertice a = new Vertice();
        Vertice b = new Vertice();
        Vertice c = new Vertice();
        Vertice d = new Vertice();
        Vertice e = new Vertice();
        Vertice f = new Vertice();

        Arista ab = new Arista(a, b);
        Arista bd = new Arista(b, d);
        Arista de = new Arista(d, e);
        Arista ef = new Arista(e, f);
        Arista fc = new Arista(f, c);
        Arista ca = new Arista(c, a);
        Arista cb = new Arista(c, b);
        Arista ce = new Arista(c, e);

        modelo.añadirVertice(a, b, c, d, e, f);
        modelo.añadirArista(ab, bd, de, ef, fc, ca, cb, ce);

        List<Vertice> adyacentes = modelo.adyacentes(c);

        assertTrue(adyacentes.contains(a));
        assertTrue(adyacentes.contains(b));
        assertTrue(adyacentes.contains(e));
        assertEquals(3, adyacentes.size());
        assertFalse(adyacentes.contains(f));

        adyacentes.clear();

        adyacentes = modelo.adyacentes(a);

        assertTrue(adyacentes.contains(b));
        assertFalse(adyacentes.contains(f));
        assertEquals(1, adyacentes.size());
    }

    @Test
    public void encontrarOrigen1(){
        assertEquals(null, modelo.encontrarFuente());
    }

    @Test
    public void encontrarOrigen2(){
        modelo.añadirVertice(new Vertice());
        assertEquals(null, modelo.encontrarFuente());
    }

    @Test
    public void encontrarOrigen3(){
        Vertice f = new Vertice(Vertice.Tipo.FUENTE);
        modelo.añadirVertice(f);
        assertTrue(f.equals(modelo.encontrarFuente()));
    }

    @Test
    public void existeCamino1(){
        Vertice a = new Vertice();
        Vertice b = new Vertice();
        modelo.añadirVertice(a, b);
        assertFalse(modelo.existeCamino(a, b));
    }

    @Test
    public void existeCamino2(){
        Vertice a = new Vertice();
        Vertice b = new Vertice();
        Vertice c = new Vertice();
        Arista ab = new Arista(a, b);

        modelo.añadirVertice(a, b, c);
        modelo.añadirArista(ab);

        assertTrue(modelo.existeCamino(a, b));
        assertFalse(modelo.existeCamino(a, c));
    }

    @Test
    public void existeCamino3(){
        Vertice a = new Vertice();
        Vertice b = new Vertice();
        Vertice c = new Vertice();
        Vertice d = new Vertice();
        Vertice e = new Vertice();
        Vertice f = new Vertice();
        Vertice z = new Vertice();

        Arista ab = new Arista(a, b);
        Arista bd = new Arista(b, d);
        Arista de = new Arista(d, e);
        Arista ef = new Arista(e, f);
        Arista fc = new Arista(f, c);
        Arista ca = new Arista(c, a);
        Arista cb = new Arista(c, b);
        Arista ce = new Arista(c, e);
        Arista cz = new Arista(c, z);

        modelo.añadirVertice(a, b, c, d, e, f, z);
        modelo.añadirArista(ab, bd, de, ef, fc, ca, cb, ce, cz);

        assertTrue(modelo.existeCamino(a, e));
        assertTrue(modelo.existeCamino(a, b));
        assertTrue(modelo.existeCamino(c, f));
        assertTrue(modelo.existeCamino(a, c));
        assertTrue(modelo.existeCamino(c, z));
        assertFalse(modelo.existeCamino(z, c));
        assertFalse(modelo.existeCamino(z, a));
    }

    @Test
    public void redEsValida1(){
        assertFalse(modelo.redEsValida());
    }

    @Test
    public void redEsValida2(){
        Vertice a = new Vertice();
        Vertice b = new Vertice();
        Vertice c = new Vertice();
        Vertice d = new Vertice();
        Vertice e = new Vertice();
        Vertice f = new Vertice();
        Vertice z = new Vertice();

        Arista ab = new Arista(a, b);
        Arista bd = new Arista(b, d);
        Arista de = new Arista(d, e);
        Arista ef = new Arista(e, f);
        Arista fc = new Arista(f, c);
        Arista ca = new Arista(c, a);
        Arista cb = new Arista(c, b);
        Arista ce = new Arista(c, e);
        Arista cz = new Arista(c, z);

        modelo.añadirVertice(a, b, c, d, e, f, z);
        modelo.añadirArista(ab, bd, de, ef, fc, ca, cb, ce, cz);

        assertFalse(modelo.redEsValida());

        a.setTipo(Vertice.Tipo.SUMIDERO);

        assertFalse(modelo.redEsValida());

        e.setTipo(Vertice.Tipo.FUENTE);

        assertTrue(modelo.redEsValida());
    }

}