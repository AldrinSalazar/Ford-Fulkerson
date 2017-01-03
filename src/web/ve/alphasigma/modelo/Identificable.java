package web.ve.alphasigma.modelo;

import com.sun.istack.internal.Nullable;
import java.util.UUID;

public class Identificable {
    private UUID id;
    private String nombre;
    private Integer valor;

    public Identificable(@Nullable String nombre, @Nullable Integer valor) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.valor = valor;
    }

    public Identificable() {
        this.id = UUID.randomUUID();
        this.nombre = null;
        this.valor = null;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) throws IllegalArgumentException{
        if(valor < 0)
            throw new IllegalArgumentException("Valor negativo");
        else
            this.valor = valor;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Identificable && this.id.equals(((Identificable) obj).getId());
    }
}
