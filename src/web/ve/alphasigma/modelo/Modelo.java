/*
 * Modelo.java       01/04/2017
 * Copyright (C) 2017  Aldrin Salazar.
 * https://github.com/AldrinSalazar/Ford-Fulkerson
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see http://www.gnu.org/licenses
 *
 */
package web.ve.alphasigma.modelo;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Modelo es la representacion de un campo de Grafos dirigidos, en especifico una red donde cada Arista posee un flujo
 * y una capacidad de flujo. Esta red debe poseer solamente un Vertice de tipo FUENTE y solamente un Vertice de tipo
 * SUMIDERO para la aplicacion del algoritmo de Ford-Fulkerson.
 *
 * @see Arista
 * @see Vertice
 * @version 1.0.1 1/25/2017
 * @author Aldrin Salazar
 */
public class Modelo {
    /**
     * El campo de Grafos dirigidos es par ordenado G(V, A), donde V es el conjunto de todos los Vertices, y A es el
     * conjunto de todas las Aristas. En esta implementacion cada conjunto es guardado en una Lista
     */

    /** Lista de todos los vertices*/
    private List<Vertice> vertices;

    /** Lista de todas las aristas*/
    private List<Arista> aristas;

    /** Control al correr el algoritmo paso por paso*/
    private boolean corriendo;

    /**
     * El Modelo no requiere argumento alguno para su instanciacion.
     */
    public Modelo() {
        //Inicializa las listas donde se almacenan los vertces y aristas.
        limpiarGrafoPrincipal();
        //El estado inicial es no corriendo.
        corriendo = false;
    }

    /**
     * Indica el estado del modelo, en forma de string.
     * @return Texto con el numero de aristas y vertices incluidos en el modelo.
     */
    public String estado(){
        return String.format("Modelo, %d Aristas, %d Vertices.", aristas.size(), vertices.size());
    }

    /**
     * Limpia la lista de Vertices y Aristas del Modelo.
     */
    private void limpiarGrafoPrincipal(){
        vertices = new ArrayList<>();
        aristas = new ArrayList<>();
    }

    /**
     * Añade los Vertices al modelo.
     *
     * @param v Array con los vertices a añadir, en caso de ser varios.
     */
    public void añadirVertice(Vertice... v){
        vertices.addAll(Arrays.asList(v));
    }

    /**
     * Añade las Aristas al modelo. Cada arista es añadida individualmente para su validacion.
     *
     * @param e Array con las aristas a añadir, en caso de ser varias.
     */
    public void añadirArista(Arista... e){
        Arrays.asList(e).forEach(this::añadirArista);
    }

    /**
     * Añade una Arista al modelo, ambos Vertices de la Arista deben pertenecer al Modelo.
     *
     * @param e Arista a añadir.
     * @throws IllegalArgumentException En caso de que los Vertices que componen la Arista no pertenezcan al Modelo.
     */
    public void añadirArista(Arista e) throws IllegalArgumentException{
        if(validarArista(e)) {
            aristas.add(e);
        } else {
            throw new IllegalArgumentException("Vertices que componen la arista no existen en el modelo.");
        }
    }

    /**
     * Elimina un elemento del modelo, puede ser una arista o un vertice, ya que se basa en un identificable.
     * @see Identificable
     * @param d Identificable a eliminar
     */
    public void eliminar(Identificable d){
        for (int i = 0; i<aristas.size(); i++){
            if(aristas.get(i).equals(d)){
                aristas.remove(i);
                return;
            }
        }

        for (int i = 0; i<vertices.size(); i++){
            if(vertices.get(i).equals(d)){
                vertices.remove(i);
                return;
            }
        }
    }

    /**
     * Busca y retorna todas las aristas que dependen de un vertice, estas son aquellas que parten o llegan a el.
     * @param e Vertice a buscar sus dependencias.
     * @return Lista con todas las dependencias del vertice.
     */
    public List<Arista> dependencias(Vertice e){
        List<Arista> re;
        re = aristas.stream()
                .filter((Arista t) -> t.getFin().equals(e) || t.getInicio().equals(e))
                .collect(Collectors.toList());

        return re;
    }

    /**
     * Obtiene la Lista de Vertices del Modelo.
     *
     * @return Lista con los Vertices del Modelo.
     */
    public List<Vertice> getVertices() {
        return vertices;
    }

    /**
     * Obtiene la Lista de Aristas del Modelo.
     *
     * @return Lista con las Aristas del Modelo.
     */
    public List<Arista> getAristas() {
        return aristas;
    }

    /**
     * Valida que una Arista posea ambos Vertices en el Modelo.
     *
     * @param e Arista a validar
     * @return Si ambos Vertices estan o no en el modelo.
     */
    private boolean validarArista(Arista e){
        return vertices.contains(e.getInicio()) && vertices.contains(e.getFin());
    }

    /**
     * Asigna a 0 el flujo de todas las Aristas del Modelo.
     */
    public void setAristasFlujoCero(){
        aristas.forEach((Arista e) -> e.setFlujo(0));
    }

    /**
     * Busca en la lista de Vertices alguno de tipo FUENTE.
     *
     * @see Vertice
     * @return Instancia de Vertice de tipo FUENTE encontrado, o null de no existir.
     */
    public Vertice encontrarFuente(){
        Optional<Vertice> tmp = vertices.stream()
                .filter((Vertice e) -> e.getTipo() == Vertice.Tipo.FUENTE)
                .findFirst();

        return tmp.isPresent() ? tmp.get() : null;
    }

    /**
     * Busca en la lista de Vertices alguno de tipo SUMIDERO.
     *
     * @see Vertice
     * @return Instancia de Vertice de tipo SUMIDERO encontrado, o null de no existir.
     */
    public Vertice encontrarSumidero(){
        Optional<Vertice> tmp = vertices.stream()
                .filter((Vertice e) -> e.getTipo() == Vertice.Tipo.SUMIDERO)
                .findFirst();

        return tmp.isPresent() ? tmp.get() : null;
    }

    /**
     * Obtiene la lista de Vertices adyacentes a otro, esto es, todos los Vertices que son inmediatamente accesibles
     * desde el.
     *
     * @param v Vertice a consultar
     * @return Lista con los Vertices adyacentes. En caso de no tener adyacentes sera una lista vacia.
     */
    public List<Vertice> adyacentes(Vertice v){
        return aristas.stream()                           //Para cada arista ..
                .filter((Arista e) -> e.getInicio() == v) //.. todas las Aristas que inician en v ..
                .map(Arista::getFin)                      //.. todos los Vertices que son el final de estas Aristas ..
                .collect(Collectors.toList());            //.. se convierte a una lista
    }

    /**
     * Valida si una red cumple con las condiciones para calcular el flujo maximo:
     * -Poseer un Vertice FUENTE
     * -Poseer un Vertice SUMIDERO
     * -Existe un camino desde FUENTE a SUMIDERO
     *
     * @return Si la red cumple las condiciones o no.
     */
    public boolean redEsValida(){
        Vertice o = encontrarFuente();
        Vertice s = encontrarSumidero();

        if(o == null || s == null)                       //Si falta la fuente o el sumidero ..
            return false;                                //.. es una red invalida.

        return existeCamino(o, s) != null;               //Si existe camino entre fuente y sumidero.
    }

    /**
     * Busca un camino entre dos Vertices, desde el origen al destino, el orden es importante al ser un grafo dirigido,
     * usa el algoritmo de recorrida Depth-First para la busqueda en el grafo.
     *
     * @param origen Vertice de donde inicia la busqueda.
     * @param destino Vertice objetivo a llegar.
     * @return Lista con los Vertices que componen el camino ordenados en el orden a visitar, o null de no existir camino.
     */
    public List<Vertice> existeCamino(Vertice origen, Vertice destino){
        /**
         * Implementacion del algoritmo Depth-first para recorrida de un Grafo.
         */

        //Se necesita marcar cada nodo ya visitado
        ArrayList<Vertice> visitados = new ArrayList<>();
        //Se guarda el orden en que recorremos en una pila, para hacer backtracking y para obtener el recorrido
        Stack<Vertice> control = new Stack<>();

        //Inicia en el origen
        Vertice actual = origen;

        do {
            visitados.add(actual);                                      //Se marca el actual como visitado ..
            control.add(actual);                                        //.. se añade el actual al camino tentativo ..

            Optional<Vertice> siguiente = adyacentes(actual).stream()   //Para cada Vertice adyacente al actual ..
                    .filter((Vertice e) -> !visitados.contains(e))      //.. todos los Vertices no visitados ..
                    .findFirst();                                       //.. se toma el primero.

            if(siguiente.isPresent()){                                  //Si existe un adyacente sin visitar ..
                actual = siguiente.get();                               //.. es el siguiente a iterar.
            }else {                                                     //De lo contrario ..
                control.pop();                                          //Se quita el tope de la pila de camino tentativo ..
                if(control.empty()) return null;                        //.. si la pila esta vacia, no hay camino posible ..
                actual = control.pop();                                 //De lo contrario, se desapila el ultimo, y se itera.
            }

            if(actual.equals(destino)){                                 //Si el Vertice donde se esta es el buscado ..
                control.add(actual);                                    //.. se añade a el camino ..
                return new ArrayList<>(control);                        //.. se convierte a lista.
            }

        }while (!actual.equals(destino));

        return null;
    }

    /**
     * Dado una lista de Vertices, en orden a recorrer, esta funcion da las Aristas, en orden a recorrer, que deben ser
     * tomadas para llegar desde el primero en la lista al ultimo. Asume un camino posible entre ellos.
     *
     * @param camino Lista de Vertices en orden, a buscar las Aristas a recorrer.
     * @return Lista con las Aristas en orden para llegar desde el primero al ultimo.
     */
    public List<Arista> caminoEntreVertices(List<Vertice> camino){
        ArrayList<Arista> resultado = new ArrayList<>();

        for(int i = 0; i<camino.size()-1; i++){
            final int j = i;
            Optional<Arista> tmp = aristas.stream()                         //Para cada Arista ..
                    .filter((Arista e) -> e.getInicio() == camino.get(j))   //.. que inicia en el actual ..
                    .filter((Arista e) -> e.getFin() == camino.get(j+1))    //.. y termina en el siguiente ..
                    .findFirst();                                           //.. se toma el primero (Unico).

            resultado.add(tmp.get());                                       //Se añade al camino.
        }

        return resultado;
    }

    /**
     * Implementacion del algoritmo Ford-Fulkerson para determinar el flujo maximo de una red de Grafos dirigidos.
     *
     * @return El flujo maximo entre la fuente y el sumidero de la red.
     * @throws IllegalStateException En caso de no ser valida la red.
     */
    public int algoritmoFordFulkerson() throws IllegalStateException{
        /**
         * Algoritmo Ford-Fulkerson, con busqueda Depth-First.
         */

        //La red debe ser valida
        if(!redEsValida())
            throw new IllegalStateException("Red invalida");

        //Flujo maximo de la red
        int flujoMaximo = 0;
        Vertice origen = encontrarFuente();
        Vertice sumidero = encontrarSumidero();
        //Camino desde Origen a Sumidero
        List<Vertice> caminoVertice;

        setAristasFlujoCero();                                              //1- Establecer a 0 el flujo de todas las aristas
        while ((caminoVertice = existeCamino(origen, sumidero)) != null){   //2- Mientras exista un camino entre el Origen y el Sumidero
            List<Arista> camino = caminoEntreVertices(caminoVertice);
            int flujoMenor = camino.stream()                                //3- Para cada Arista del camino ...
                    .mapToInt(Arista::getFlujoRestante)                     //   .. se toma el flujo que puede aumentar ..
                    .min()                                                  //   .. el menor flujo ..
                    .getAsInt();                                            //   .. se guarda.

            camino.forEach((Arista e) -> e.setFlujo(e.getFlujo() + flujoMenor)); //4- Se aumenta el flujo en cada Arista del camino en esa cantidad
            flujoMaximo += flujoMenor;                                           //5- Esta cantidad se suma al flujo maximo.
        }

        return flujoMaximo;
    }
}