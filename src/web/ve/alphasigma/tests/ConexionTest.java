package web.ve.alphasigma.tests;

import org.junit.Test;
import web.ve.alphasigma.modelo.Conexion;
import web.ve.alphasigma.modelo.Vertice;
import static org.junit.Assert.*;

public class ConexionTest {//TODO: Documentacion

    @Test
    public void invertir() throws Exception {
        Vertice inicio = new Vertice();
        Vertice fin = new Vertice();

        Conexion test = new Conexion(inicio, fin);
        assertEquals(fin, test.getFin());
        assertEquals(inicio, test.getInicio());

        test.invertir();

        assertEquals(inicio, test.getFin());
        assertEquals(fin, test.getInicio());
    }
}