package web.ve.alphasigma.modelo;

public class Arista extends Conexion{
    private int capacidad;
    private int flujo;

    public Arista(Vertice inicio, Vertice fin){
        this(inicio, fin, 0, 0);
    }

    public Arista(Vertice inicio, Vertice fin, int capacidad, int flujo) throws IllegalArgumentException{
        super(inicio, fin);

        if(flujo > capacidad)
            throw new IllegalArgumentException("Flujo mayor a capacidad");
        else if (flujo < 0)
            throw new IllegalArgumentException("Flujo negativo");
        else if (capacidad < 0)
            throw new IllegalArgumentException("Capacidad negativa");

        this.capacidad = capacidad;
        this.flujo = flujo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getFlujo() {
        return flujo;
    }

    public void setFlujo(int flujo) throws IllegalArgumentException{
        if(flujo > this.capacidad) {
            throw new IllegalArgumentException("Flujo mayor a capacidad");
        } else if(flujo == capacidad){
            this.flujo = 0;
            invertir();
        }else {
            this.flujo = flujo;
        }
    }

    public int getFlujoRestante(){
        return this.capacidad - this.flujo;
    }

}
