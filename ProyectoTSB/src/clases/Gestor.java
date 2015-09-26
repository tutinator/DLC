package clases;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import trabajopractico.VentanaPalabra;
import trabajopractico.VentanaPrincipal;
import trabajopractico.VentanaProcesando;



/**
 *
 * @author Santi Giuli y Agus
 */
public class Gestor extends SwingWorker<Void,Integer> {
    
    private SQLite_conexion conection;
    private LinkedList<File> listaTextos;
    private HashMap<String,Palabra> hm;
    private VentanaPrincipal ventPrinc;
    private VentanaProcesando ventProc;
    private String textosSinProcesar;
    private VentanaPalabra ventPalabra;
    private int atributoNuevo;

   public Gestor(VentanaPrincipal v){
       listaTextos = new LinkedList<>();
       hm= new HashMap<>();
       ventPrinc=v;
   }
   
    public SQLite_conexion getConection() {
        return conection;
    }

    public void setConection(SQLite_conexion conection) {
        this.conection = conection;
    }

    public LinkedList<File> getListaTextos() {
        return listaTextos;
    }

    public void setListaTextos(LinkedList<File> listaTextos) {
        this.listaTextos = listaTextos;
    }

    public HashMap<String, Palabra> getHm() {
        return hm;
    }

    public void setHm(HashMap<String, Palabra> hm) {
        this.hm = hm;
    }
    
    /**
     * Carga los textos que se van a procesar, verificando que no se hayan 
     * procesado anteriormente.
     * @param f vector de Files que se quieren procesar
     * @return String con aquellos textos que ya se habían procesado
     * anteriormente
     */
   public String setTextosAProcesar(File[] f)
   {
       conection = new SQLite_conexion();
       textosSinProcesar="";
       for (int i = 0; i < f.length; i++) {
           if(conection.existeTexto(new Texto(f[i].getName()))== -1)
                listaTextos.add(f[i]);
           else
               textosSinProcesar+=f[i].getName()+", ";
       }
       conection.desconectar();
       return textosSinProcesar;
       
       
   }
   /**
    * Carga un HashMap con las palabras que aparecen en los textos con sus
    * respectivas frecuencias, verificando que las palabras sean válidas.
    */
   public void procesar()
   {
       StringTokenizer st; 
       Iterator<File> I = listaTextos.iterator();
       File F;
       Texto T;
       String cadena;
       while(I.hasNext())
        {   
            F = I.next();
            
            T = new Texto(F.getName());
       
            try(FileInputStream FIS =new FileInputStream(F))
            {
                
                InputStreamReader ISR = new InputStreamReader(FIS,"ISO-8859-1");
                BufferedReader BR = new BufferedReader(ISR);
                String linea;
                while((linea = BR.readLine()) != null)
                 {
                     st = new StringTokenizer(linea, " .:;-_,!¡¿?()[]{}\"'/*<>=«»$#%&+@^|~");
                    while(st.hasMoreTokens())
                    {
                      cadena = st.nextToken();
                      if(cadena.compareTo("")==0)
                            System.out.println("aca");
                      if(!this.palabraInvalida(cadena))                        
                        this.agregarPalabra(cadena.toLowerCase(),T);
                    }
                 }
             }

            catch(FileNotFoundException e)
            {
                System.out.println("El archivo es inexistente");
            }
            catch(IOException e)
            {
                System.out.println("Error de lectura");
            }
            
        }  
       listaTextos = new LinkedList();
    }
   
   /**
    * Agrega las palabras al HashMap, y si la palabra ya existe aumenta su 
    * frecuencia. También agrega a lista de textos de la palabra el texto
    * donde fue encontrada.
    * @param palabra a ser agregada
    * @param t texto en el cual aparece la palabra
    */
   public void agregarPalabra(String palabra, Texto t)
   {
      Palabra p;
      if(hm.containsKey(palabra)) 
        {
          p = hm.get(palabra);
          p.incrementarFrecuencia();
          if(!p.yaEsMiTexto(t))
              p.agregarTexto(t);
        }
      else
      {
          p= new Palabra(palabra);
          p.agregarTexto(t);
          hm.put(palabra, p);
      }
   }
   
   
   /**
    * Verifica que la palabra sea valida
    * Que tenga dos o más caracteres y no contenga números.
    * @param cadena String a ser validado.
    * @return true si es valida, false si no lo es
    */
   private boolean palabraInvalida(String cadena)
    {
        if(cadena.length()<2)
            return true;
        for (int i = 0; i < cadena.length(); i++) {
             if(cadena.charAt(i)>=48 && cadena.charAt(i)<=57 )
                 return true;
        }
        return false;
    }
   
   /**
    * Se encarga de la persistencia del HashMap en la base de datos.
    */
   public void guardarHash() 
   {
       conection = new SQLite_conexion();
       int idPalabra;
       LinkedList textosPalabra;
       Palabra p;
       Iterator I = hm.entrySet().iterator();
       Iterator<Texto> I2;
       Map.Entry e;
       int totalHash  = hm.size();
       int contadorParcial = 0;
       
       conection.iniciarTransaccion();
       while(I.hasNext())
       {
           e =(Map.Entry) I.next();
           p =(Palabra) e.getValue();
           idPalabra = conection.guardarPrimeraParte(p);
           textosPalabra = p.getMisTextos();
           I2 = textosPalabra.iterator();
           while(I2.hasNext())
               conection.guardarSegundaParte(I2.next(), idPalabra);
           contadorParcial ++;
           if(contadorParcial%50==0 || contadorParcial == totalHash)
               this.publish(contadorParcial*100/totalHash);
           if(this.isCancelled())
            {
                
                conection.finalizarTransaccion(true);
                hm = new HashMap<>();
                
                return;
           }
       }
       conection.finalizarTransaccion(false);
       
       hm = new HashMap<>();
       conection.desconectar();
       
   }
   
   /**
    * Carga la grilla con la información de cada palabra de la base de datos.
    * @param t JTable a cargar 
    */
   public void cargarGrilla(JTable t)
   {
       conection = new SQLite_conexion();
       String [] titulos = { "Palabras", "Cantidad de Documentos", "Frecuencia"};
       conection.leerDiccionario((DefaultTableModel)t.getModel());
       conection.desconectar();
            
             
   }

   @Override
    protected Void doInBackground() {
        try
        {this.procesar();
        this.guardarHash();
        }
        catch (Exception e)
        {
            System.out.println(e);
        };
        return null;
    }
    
    @Override
    protected void process(List<Integer> chunks)
    {
        ventProc.getCtr_progressBar().setValue(chunks.get(0));
    }
    
    @Override
    protected void done()
    {
        ventProc.getCtr_progressBar().setValue(100);
        this.cargarGrilla(ventPrinc.getGrid_grilla());
        if(textosSinProcesar != "")
            JOptionPane.showMessageDialog(ventProc, "Los textos "+ textosSinProcesar +" no se procesaron, porque ya fueron procesados anteriormente","Aviso", JOptionPane.YES_OPTION);
        conection.desconectar();
        ventProc.dispose();
    }
    
    public void setVentanaProcesamiento(VentanaProcesando vp)
    {
        ventProc= vp;
    }
    
    /**
     * Busca los textos asociados a una palabra
     * @param cad String de la palabra a buscar.
     * @param frec frecuencia de la palabra buscada.
     */
    public void traerDatosPalabra(String cad, String frec)
    {
        conection= new SQLite_conexion();
        ventPalabra = new VentanaPalabra (cad, frec, conection.buscarDatosPalabra(cad));
        ventPalabra.setVisible(true);
        conection.desconectar();
    }
    
      
}
   
    
    
    
