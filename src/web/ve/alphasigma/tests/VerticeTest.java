package web.ve.alphasigma.tests;

import org.junit.Test;
import web.ve.alphasigma.modelo.Vertice;

import static org.junit.Assert.*;

public class VerticeTest {

    @Test
    public void instanciaYValores(){
        Vertice v = new Vertice();
        assertEquals(Vertice.Tipo.NINGUNO, v.getTipo());
        assertNull(v.getValor());
        assertNull(v.getNombre());
        v.setTipo(Vertice.Tipo.SUMIDERO);
        assertEquals(Vertice.Tipo.SUMIDERO, v.getTipo());
    }

}