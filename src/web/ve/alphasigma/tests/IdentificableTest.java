package web.ve.alphasigma.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import web.ve.alphasigma.modelo.Identificable;

public class IdentificableTest {//TODO: Documentacion
    private final Identificable i;

    public IdentificableTest() {
        i = new Identificable();
    }

    @Test
    public void getIdDiferentes() throws Exception {
        Identificable j = new Identificable();
        assertFalse(j.equals(i));

        //Heisenberg plz
        assertTrue(j.equals(j));
        assertTrue(i.equals(i));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setValorNegativo() {
        i.setValor(-1);
    }

    @Test
    public void setValor(){
        i.setValor(Integer.MAX_VALUE);
        assertEquals((Integer)Integer.MAX_VALUE, i.getValor());
    }

    @Test
    public void setNombre(){
        String test = "test";
        i.setNombre(test);
        assertEquals(test, i.getNombre());
    }

}