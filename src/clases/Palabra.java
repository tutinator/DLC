package clases;


import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Santi
 */
public class Palabra {
    
    private String cadena;
    private long frecuencia;
    private LinkedList<Texto> misTextos;

    public Palabra(String c)
    {
       cadena = this.convertirAMinuscula(c);
       frecuencia = 1;
       misTextos = new LinkedList<>();
    }
    

    public LinkedList<Texto> getMisTextos() {
        return misTextos;
    }

    public void setMisTextos(LinkedList<Texto> misTextos) {
        this.misTextos = misTextos;
    }
    
   
    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public long getFrecuencia() {
        return frecuencia;
    }
    
    /**
     * Incrementa la frecuencia de la palabra en 1.
     */
    public void incrementarFrecuencia()
    {
        this.frecuencia++;
    }

    /**
     * Agrega un texto a la lista de textos asociada a la palabra.
     * @param t Texto a ser agregado 
     */
    public void agregarTexto(Texto t)
    {
        misTextos.add(t);
    }
    /**
     * Verifica si un Texto esta en la lista de textos de la palabra.
     * @param t Texto a verificar
     * @return true si la palabra ya aparece en ese texto, false de lo contrario
     */
    public boolean yaEsMiTexto(Texto t)
    {
        return (misTextos.contains(t));
    }
    
    /**
     * Convierte todos los caracteres de una palabra a minúscula.
     * @param cadena String a convertir
     * @return cadena convertida a minúsucla
     */
    private String convertirAMinuscula(String cadena)
    {
        return cadena.toLowerCase();
        
    }
    
    
}
