package web.ve.alphasigma.modelo;

import com.sun.istack.internal.Nullable;

public class Vertice extends Identificable{

    public enum Tipo{
        SUMIDERO,
        FUENTE,
        NINGUNO
    }

    Tipo tipo;

    public Vertice(Tipo tipo, @Nullable String nombre, @Nullable Integer valor) {
        super(nombre, valor);
        this.tipo = tipo;
    }

    public Vertice(Tipo tipo) {
        this(tipo, null, null);
    }

    public Vertice() {
        this(Tipo.NINGUNO);
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

}
