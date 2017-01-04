package web.ve.alphasigma.modelo;

import java.util.ArrayList;
import java.util.List;

public class Modelo {

    private List<Vertice> vertices;
    private List<Arista> aristas;

    private boolean corriendo;

    public Modelo() {
        limpiarGrafoPrincipal();
        corriendo = false;
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

    public int algoritmoFordFulkerson() throws IllegalStateException{
        if(!redEsValida())
            throw new IllegalStateException("Red invalida");

        int flujoMaximo = 0;
        Vertice origen = encontrarOrigen();
        Vertice sumidero = encontrarSumidero();
        setAristasFlujoCero();

        while (existeCamino(origen, sumidero)){
            List<Arista> camino = caminoEntreVertices(origen, sumidero);
            int flujoMenor = camino.stream().mapToInt(Arista::getFlujoRestante).min().getAsInt();

            camino.forEach((Arista e) -> e.setFlujo(e.getFlujo() + flujoMenor));
            flujoMaximo += flujoMenor;
        }

        return flujoMaximo;
    }

    private boolean validarArista(Arista e){
        return vertices.contains(e.getInicio()) && vertices.contains(e.getFin());
    }

    private void setAristasFlujoCero(){
        aristas.forEach((Arista e) -> e.setFlujo(0));
    }

    //TODO:Implementar
    private List<Arista> caminoEntreVertices(Vertice a, Vertice b){
        List<Arista> camino = new ArrayList<>();

        return camino;
    }

    //TODO:Implementar
    private boolean existeCamino(Vertice a, Vertice b){
        return true;
    }

    private void limpiarGrafoPrincipal(){
        vertices = new ArrayList<>();
        aristas = new ArrayList<>();
    }

    //TODO:Implementar
    private boolean redEsValida(){
        return true;
    }

    //TODO:Implementar
    private Vertice encontrarOrigen(){
        return null;
    }

    //TODO:Implementar
    private Vertice encontrarSumidero(){
        return null;
    }

}
