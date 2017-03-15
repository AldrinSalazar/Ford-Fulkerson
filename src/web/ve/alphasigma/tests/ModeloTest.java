package web.ve.alphasigma.tests;

import org.junit.Test;
import web.ve.alphasigma.modelo.Arista;
import web.ve.alphasigma.modelo.Modelo;
import web.ve.alphasigma.modelo.Vertice;

import java.util.List;

import static org.junit.Assert.*;


public class ModeloTest {//TODO: Documentacion
    Modelo modelo;

    public ModeloTest() {
        modelo = new Modelo();
    }

    //#1 Los vertices del modelo deben tener aristas en el modelo
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

    //Un vertice añadido al modelo lo contiene
    @Test
    public void añadirVerticeTest(){
        Vertice a = new Vertice();
        modelo.añadirVertice(a);
        assertTrue(modelo.getVertices().contains(a));
    }

    //Se pueden colocar todos los flujos en 0
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

    //#2 Prueba de calcular los vertices adyacentes a un vertice
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

    //Modelo con fuente
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
        assertNull(modelo.existeCamino(a, b));
    }

    @Test
    public void existeCamino2(){
        Vertice a = new Vertice();
        Vertice b = new Vertice();
        Vertice c = new Vertice();
        Arista ab = new Arista(a, b);

        modelo.añadirVertice(a, b, c);
        modelo.añadirArista(ab);

        assertNotNull(modelo.existeCamino(a, b));
        assertNull(modelo.existeCamino(a, c));
    }

    //Prueba los caminos vacios
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

        assertNotNull(modelo.existeCamino(a, e));
        assertNotNull(modelo.existeCamino(a, b));
        assertNotNull(modelo.existeCamino(c, f));
        assertNotNull(modelo.existeCamino(a, c));
        assertNotNull(modelo.existeCamino(c, z));
        assertNull(modelo.existeCamino(z, c));
        assertNull(modelo.existeCamino(z, a));
    }

    //un modelo vacio no es valido
    @Test
    public void redEsValida1(){
        assertFalse(modelo.redEsValida());
    }

    //Diferentes pruebas de validez
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

    //Prueba la lista de aristas entre dos vertices
    @Test
    public void caminoEntreVertices1(){
        int v1 = 1996;
        int v2 = 2078;
        int v3 = 1313;

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
        de.setValor(v3);
        Arista ef = new Arista(e, f);
        Arista fc = new Arista(f, c);
        fc.setValor(v1);
        Arista ca = new Arista(c, a);
        ca.setValor(v2);
        Arista cb = new Arista(c, b);
        Arista ce = new Arista(c, e);
        Arista cz = new Arista(c, z);

        modelo.añadirVertice(a, b, c, d, e, f, z);
        modelo.añadirArista(ab, bd, de, ef, fc, ca, cb, ce, cz);

        List<Vertice> camino = modelo.existeCamino(f, a);
        List<Arista> caminoAristas = modelo.caminoEntreVertices(camino);

        assertTrue(caminoAristas.contains(fc));
        assertTrue(caminoAristas.contains(ca));
        assertFalse(caminoAristas.contains(cz));
        assertFalse(caminoAristas.contains(cb));
        assertEquals(2, caminoAristas.size());
        assertEquals((Integer)v1, caminoAristas.get(0).getValor());
        assertEquals((Integer)v2, caminoAristas.get(1).getValor());

        camino = modelo.existeCamino(d, e);
        caminoAristas = modelo.caminoEntreVertices(camino);

        assertTrue(caminoAristas.contains(de));
        assertEquals(1, caminoAristas.size());
        assertEquals((Integer)v3, caminoAristas.get(0).getValor());
    }

    //Prueba el flujo maximo en una red conocida
    @Test
    public void algoritmoFordFulkerson1(){
        Vertice a = new Vertice(Vertice.Tipo.FUENTE);
        Vertice b = new Vertice();
        Vertice c = new Vertice();
        Vertice d = new Vertice();
        Vertice e = new Vertice();
        Vertice f = new Vertice(Vertice.Tipo.SUMIDERO);

        Arista ab = new Arista(a, b, 13);
        Arista bc = new Arista(b, c, 5);
        Arista cf = new Arista(c, f, 3);
        Arista ad = new Arista(a, d, 10);
        Arista de = new Arista(d, e, 35);
        Arista ef = new Arista(e, f, 20);
        Arista cd = new Arista(c, d, 50);

        modelo.añadirVertice(a, b, c, d, e, f);
        modelo.añadirArista(ab, bc, cf, ad, de, ef, cd);

        assertEquals(15, modelo.algoritmoFordFulkerson());
    }

    //#5 Prueba de flujo maximo en una red conocida
    @Test
    public void algoritmoFordFulkerson2(){
        Vertice s = new Vertice(Vertice.Tipo.FUENTE);
        Vertice o = new Vertice();
        Vertice p = new Vertice();
        Vertice q = new Vertice();
        Vertice r = new Vertice();
        Vertice t = new Vertice(Vertice.Tipo.SUMIDERO);

        Arista so = new Arista(s, o, 3);
        Arista sp = new Arista(s, p, 3);
        Arista op = new Arista(o, p, 2);
        Arista oq = new Arista(o, q, 3);
        Arista pr = new Arista(p, r, 2);
        Arista rt = new Arista(r, t, 3);
        Arista qr = new Arista(q, r, 4);
        Arista qt = new Arista(q, t, 2);

        modelo.añadirVertice(s, o, p, q, r, t);
        modelo.añadirArista(so, sp, op, oq, pr, rt, qr, qt);

        assertEquals(5, modelo.algoritmoFordFulkerson());
    }

}