package clases;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Santi
 */
public class Texto {
    
    private String titulo;

    public Texto(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        System.out.println("Me cambiaron el titulo");
    }
    
    public boolean mismoNombre(Texto T)
    {
        return (this.titulo.compareTo(T.getTitulo()) == 0);
    }
    
    
    
}
