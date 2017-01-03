package web.ve.alphasigma.modelo;


import java.util.ArrayList;
import java.util.List;

public class Modelo {

    private List<Vertice> vertices;
    private List<Arista> aristas;

    public Modelo() {
        vertices = new ArrayList<>();
        aristas = new ArrayList<>();
    }

    public void añadirVertice(Vertice v){
        vertices.add(v);
    }

    public void añadirArista(Arista e) throws IllegalArgumentException{
        if(validarArista(e)) {
            aristas.add(e);
        } else {
            throw new IllegalArgumentException("Nodos que componen la arista no existen en el modelo");
        }
    }

    private boolean validarArista(Arista e){
        return vertices.contains(e.getInicio()) && vertices.contains(e.getFin());
    }

}
