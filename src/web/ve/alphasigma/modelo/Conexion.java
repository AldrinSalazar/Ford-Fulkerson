package web.ve.alphasigma.modelo;


public class Conexion extends Identificable{
    private Vertice inicio;
    private Vertice fin;

    public Conexion(Vertice inicio, Vertice fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    public Vertice getInicio() {
        return inicio;
    }

    public void setInicio(Vertice inicio) {
        this.inicio = inicio;
    }

    public Vertice getFin() {
        return fin;
    }

    public void setFin(Vertice fin) {
        this.fin = fin;
    }
}
