/*
 * Modelo.java       01/04/2017
 * Copyright (C) 2017  Aldrin Salazar
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
package web.ve.alphasigma.controlador;


import web.ve.alphasigma.GUI;
import web.ve.alphasigma.modelo.Identificable;
import web.ve.alphasigma.modelo.Modelo;
import web.ve.alphasigma.modelo.Vertice;
import web.ve.alphasigma.vista.Dibujable;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class Controlador {
    private final Modelo modelo;
    private final GUI vista;

    public Controlador() {
        this.modelo = new Modelo();
        this.vista = new GUI(this);
    }

    public VerticeObservable nuevoVertice() {
        VerticeObservable v = new VerticeObservable();
        modelo.añadirVertice(v);
        return v;
    }

    public ArrayList<AristaObservable> nuevaArista() {
        ArrayList<AristaObservable> re = new ArrayList<>();

        List<Dibujable> seleccion = vista.panel_canvas.seleccion.stream()
                .filter((Dibujable d) -> d instanceof Vertice)
                .collect(Collectors.toList());

        vista.panel_canvas.seleccion.forEach(Dibujable::seleccionar);
        vista.panel_canvas.seleccion.clear();

        //TODO:Refactorizar
        if (seleccion.size() == 0) {
            JOptionPane.showMessageDialog(null, "Error: Sin Vertices seleccionados.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (seleccion.size() == 1) {
            JOptionPane.showMessageDialog(null, "Error: Se necesitan al menos dos vertices.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (seleccion.size() == 2) {
            AristaObservable v = new AristaObservable((Vertice) seleccion.get(0), (Vertice) seleccion.get(1));
            re.add(v);
        } else {
            for (int i = 0; i < seleccion.size() - 1; i++) {
                re.add(new AristaObservable((Vertice) seleccion.get(i), (Vertice) seleccion.get(i + 1)));
            }
        }


        re.forEach(modelo::añadirArista);
        return re;
    }

    public void borrar(){
        Set<Identificable> aBorrar = new HashSet<>();

        for(int i = 0; i< vista.panel_canvas.seleccion.size() ; i++){
            if(vista.panel_canvas.seleccion.get(i) instanceof  Vertice){
                aBorrar.addAll(modelo.dependencias((Vertice) vista.panel_canvas.seleccion.get(i)));
            }
        }

        for(int i = 0; i< vista.panel_canvas.seleccion.size() ; i++){
            aBorrar.add((Identificable)vista.panel_canvas.seleccion.get(i));
        }

        aBorrar.forEach(modelo::eliminar);
        aBorrar.forEach((Identificable d) -> vista.panel_canvas.quitarDibujable((Dibujable)d));
    }

    //TODO:Documentar, refactorizar
    public void editar(){
        Dibujable a_editar;

        if(vista.panel_canvas.seleccion.size() == 0){
            JOptionPane.showMessageDialog(null, "Error: Sin elementos seleccionados.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else {
            a_editar = vista.panel_canvas.seleccion.get(0);
        }

        if(a_editar instanceof VerticeObservable){
            VerticeObservable selVertice = (VerticeObservable) a_editar;
            Vertice.Tipo tipo = (Vertice.Tipo) JOptionPane.showInputDialog(null, "Tipo de Vertice:",
                    "Seleccionar tipo de Vertice",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    Vertice.Tipo.values(),
                    selVertice.getTipo());
            selVertice.setTipo(tipo);
            vista.panel_canvas.repaint();
            vista.panel_canvas.grabFocus();
        }else if(a_editar instanceof AristaObservable){
            AristaObservable selArista = (AristaObservable) a_editar;

            JTextField capacidad = new JTextField();

            capacidad.setText(""+selArista.getCapacidad());
            //capacidad.setText(""+new Random().nextInt(500));

            final JComponent[] inputs = new JComponent[] {
                    new JLabel("Capacidad"),
                    capacidad,
            };

            JOptionPane.showConfirmDialog(null, inputs, "Editar Arista", JOptionPane.DEFAULT_OPTION);

            try {
                selArista.setCapacidad(Integer.parseInt(capacidad.getText()));
            }catch (IllegalArgumentException e){
                JOptionPane.showMessageDialog(null, "Capacidad negativa.", "Error", JOptionPane.WARNING_MESSAGE);
            }

            selArista.overFlujo(0);

            vista.panel_canvas.repaint();
            vista.panel_canvas.grabFocus();
        }

        vista.panel_canvas.seleccion.forEach(Dibujable::seleccionar);
        vista.panel_canvas.seleccion.clear();
    }

    public void resolver(){
        vista.panel_canvas.seleccion.forEach(Dibujable::seleccionar);
        vista.panel_canvas.seleccion.clear();

        int flujo;
        try {
            flujo = modelo.algoritmoFordFulkerson();
        }catch (IllegalStateException e){
            JOptionPane.showMessageDialog(null, "No se puede aplicar algoritmo, red inválida.\n Debe tener fuente, sumidero, y ser conexa.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, "Flujo maximo de la red: "+ flujo, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        vista.panel_canvas.repaint();
    }

    public void resolverPaso(){
        Thread t = new Thread(()->{
            modelo.algoritmoFordFulkersonPaso(vista.panel_canvas);
        }, "Calcular paso por paso.");

        t.start();
    }

    public void debug() {
        System.out.println(modelo.estado());
    }

    public void guardar(){
        JFileChooser f = new JFileChooser();
        f.setApproveButtonText("Seleccionar");
        f.setDialogTitle("Seleccionar carpeta a guardar");
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);

        if(f.getSelectedFile() != null)
            modelo.guardar(f.getSelectedFile());
    }

    public void cargar(){
        JFileChooser f = new JFileChooser();
        f.setApproveButtonText("Seleccionar");
        f.setDialogTitle("Seleccionar carpeta a cargar");
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);

        if(f.getSelectedFile() != null)
            modelo.cargar(vista.panel_canvas, f.getSelectedFile());
    }
}
