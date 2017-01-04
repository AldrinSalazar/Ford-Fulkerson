package web.ve.alphasigma.modelo;

import java.util.*;
import java.util.stream.Collectors;

public class Modelo {

    private List<Vertice> vertices;
    private List<Arista> aristas;

    private boolean corriendo;

    public Modelo() {
        limpiarGrafoPrincipal();
        corriendo = false;
    }

    public void añadirVertice(Vertice... v){
        vertices.addAll(Arrays.asList(v));
    }

    public void añadirArista(Arista... e){
        Arrays.asList(e).forEach(this::añadirArista);
    }

    public void añadirArista(Arista e) throws IllegalArgumentException{
        if(validarArista(e)) {
            aristas.add(e);
        } else {
            throw new IllegalArgumentException("Nodos que componen la arista no existen en el modelo");
        }
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public List<Arista> getAristas() {
        return aristas;
    }

    public int algoritmoFordFulkerson() throws IllegalStateException{
        if(!redEsValida())
            throw new IllegalStateException("Red invalida");

        int flujoMaximo = 0;
        Vertice origen = encontrarFuente();
        Vertice sumidero = encontrarSumidero();
        setAristasFlujoCero();

        List<Vertice> caminoVertice;

        while ((caminoVertice = existeCamino(origen, sumidero)) != null){
            List<Arista> camino = caminoEntreVertices(caminoVertice);
            int flujoMenor = camino.stream().mapToInt(Arista::getFlujoRestante).min().getAsInt();

            camino.forEach((Arista e) -> e.setFlujo(e.getFlujo() + flujoMenor));
            flujoMaximo += flujoMenor;
        }

        return flujoMaximo;
    }

    private boolean validarArista(Arista e){
        return vertices.contains(e.getInicio()) && vertices.contains(e.getFin());
    }

    public void setAristasFlujoCero(){
        aristas.forEach((Arista e) -> e.setFlujo(0));
    }

    public List<Arista> caminoEntreVertices(List<Vertice> camino){
        ArrayList<Arista> resultado = new ArrayList<>();

        for(int i = 0; i<camino.size()-1; i++){
            final int j = i;
            Optional<Arista> tmp = aristas.stream()
                                    .filter((Arista e) -> e.getInicio() == camino.get(j))
                                    .filter((Arista e) -> e.getFin() == camino.get(j+1))
                                    .findFirst();

            resultado.add(tmp.get());
        }

        return resultado;
    }

    public List<Vertice> existeCamino(Vertice origen, Vertice destino){
        /**
         * Implementacion del algoritmo Depth-first para recorrida de un grafo
         */
        ArrayList<Vertice> visitados = new ArrayList<>();
        Stack<Vertice> control = new Stack<>();

        Vertice actual = origen;

        do {
            visitados.add(actual);
            control.add(actual);

            Optional<Vertice> siguiente = adyacentes(actual).stream()
                                                    .filter((Vertice e) -> !visitados.contains(e))
                                                    .findFirst();

            if(siguiente.isPresent()){
                actual = siguiente.get();
            }else {
                control.pop();
                if(control.empty()) break;
                actual = control.pop();
            }

            if(actual.equals(destino)){
                control.add(actual);
                return new ArrayList<>(control);
            }

        }while (!actual.equals(destino));

        return null;
    }

    public List<Vertice> adyacentes(Vertice v){
        return aristas.stream()
                .filter((Arista e) -> e.getInicio() == v)
                .map(Arista::getFin)
                .collect(Collectors.toList());
    }

    private void limpiarGrafoPrincipal(){
        vertices = new ArrayList<>();
        aristas = new ArrayList<>();
    }

    public boolean redEsValida(){
        Vertice o = encontrarFuente();
        Vertice s = encontrarSumidero();

        if(o == null || s == null)
            return false;

        return existeCamino(o, s) != null;
    }

    public Vertice encontrarFuente(){
        Optional<Vertice> tmp = vertices.stream()
                .filter((Vertice e) -> e.getTipo() == Vertice.Tipo.FUENTE)
                .findFirst();

        return tmp.isPresent() ? tmp.get() : null;
    }

    public Vertice encontrarSumidero(){
        Optional<Vertice> tmp = vertices.stream()
                .filter((Vertice e) -> e.getTipo() == Vertice.Tipo.SUMIDERO)
                .findFirst();

        return tmp.isPresent() ? tmp.get() : null;
    }

}
