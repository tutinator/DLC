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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
/**

 */
public class SQLite_conexion {

    private Connection connection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;
    private String db= "src\\basededatos\\vocabulario.s3db";    


    
    /**
     * Constructor de la clase que se encarga de establecer la conexión con la
     * base de datos.
     */
    public SQLite_conexion()
    {
      try{
         Class.forName("org.sqlite.JDBC");
         connection = DriverManager.getConnection("jdbc:sqlite:" + this.db );
         //System.out.println("Conectado a la base de datos SQLite [ " + this.db + "]");
      }catch(Exception e){
         System.out.println("Error al conectar con la base de datos");
      }

    }

 
 /**
  *Se encarga de insertar un registro en la base de datos. 
  * @param table Nombre la tabla donde se insertará
  * @param fields campos de la tabla donde se insertará
  * @param values valores a insertar.
  * @return true si se incerto con éxito, false de lo contrario. 
  */   
 public boolean insert(String table, String fields, String values)
    {
        boolean res=false;
        //Se arma la consulta
        String q=" INSERT INTO " + table + " ( " + fields + " ) VALUES ( " + values + " ) ";
        //se ejecuta la consulta
        try {
            PreparedStatement pstm = connection.prepareStatement(q);
            pstm.executeUpdate();
            pstm.close();
            res=true;
         }catch(SQLException e){
            System.out.println(e);
            System.out.println("ExcepcionSQL del metodo insert() (SQLite_conexion)");
        }
      return res;
    }
 
  /**
  *Se encarga de actualizar un registro en la base de datos. 
  * @param table Nombre la tabla que se desea actualizar
  * @param set campos y valores a actualizar
  * @param condition condición que permite identificar los registros a actualizar.
  * @return true si se actualizó con éxito, false de lo contrario. 
  */ 
 public boolean update(String table, String set, String condition)
    {
        boolean res=false;
        String q=" UPDATE " + table + " SET " + set + " WHERE " +condition;
        try {
            PreparedStatement pstm = connection.prepareStatement(q);
            pstm.executeUpdate();
            pstm.close();
            res=true;
         }catch(SQLException e){
            System.out.println(e);
            System.out.println("Excepcion SQL del metodo update() (sqlite_conexion)");
        }
      return res;
    }

 /* METODO PARA REALIZAR UNA CONSULTA A LA BASE DE DATOS
 * INPUT:
 * OUTPUT: String con los datos concatenados
*/
 /**
  * Realiza una consulta a la base de datos
  * @param campos campos de la tabla a ser seleccionados
  * @param tablas tabla de donde se seleccionar los datos
  * @param condicion condición que permite identificar los registros
  * a seleccionar
  * @return resultSet con los resultados de la consulta
  */
 public ResultSet select(String campos, String tablas, String condicion)
 {
     resultSet=null;
     try {
      statement = connection.createStatement();
      resultSet = statement.executeQuery("SELECT "+ campos +" FROM " + tablas + " WHERE "+ condicion);
         }
     catch (SQLException ex) {
        System.out.println("Excepcion SQL del metodo select() (SQLite_conexion)");
     }
    return resultSet;
 }
 
 /**
  * Ejecuta una consulta a la base de datos.
  * @param consulta a ejecutar
  * @return resultado de la consulta
  */
 public ResultSet selectConsulta(String consulta)
 {
  resultSet= null;
    try {
      statement = connection.createStatement();
      resultSet = statement.executeQuery(consulta);
         }
     catch (SQLException ex) {
        System.out.println(ex);
        System.out.println("Excepcion SQL del metodo selectConsulta() (SQLite_conexion)");
     }
    return resultSet;
 }
 

 /**
  * Desconecta la base de datos.
  */
  public void desconectar()
    {
        try {
            resultSet.close();
            statement.close();
            connection.close();
            //System.out.println("Desconectado de la base de datos [ " + this.db + "]");
        }
        catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("Excepcion metodo desconectar() sqlite_conexion");
        }
    }
  
  
  /**
   * Inserta una palabra en la base de datos.
   * @param p Palabra a insertar 
   */
    public void guardarPalabra(Palabra p)
    {
        if(p == null)
            return;
        String tabla = "Palabras";
        String campos = "cadenaPalabra, frecuenciaPalabra";
        int frecuencia = (int)p.getFrecuencia();
        String valores = "\"" + p.getCadena()+ "\" , " + frecuencia;
        this.insert(tabla, campos, valores);                  
    }
    
    /**
     * Inserta un texto en la base de datos.
     * @param t Texto a insertar. 
     */
    public void guardarTexto(Texto t)
    {
        if(t==null)
            return;
        String tabla = "Textos";
        String campos = "titulo";
        String valores = "\""+t.getTitulo()+"\"";
        
        this.insert(tabla,campos,valores);
            
    }
    
    /**
     * Verifica si un texto existe en la base de datos, de ser así le asigna 
     * el id correspondiente. Si el texto no existe en la base de datos
     * le asigna id -1.
     * @param t texto a ser verificado
     * @return id del texto
     */
    public int existeTexto(Texto t)
    {
        int id = -1;
        String tabla = "Textos";
        String campos = "id,titulo";
        String condicion = "Textos.titulo = \""+ t.getTitulo()+"\"";
        ResultSet rs = this.select(campos, tabla, condicion);
        boolean existe=false;
        try 
        {           
            existe= rs.next();
            if(existe)
                id = rs.getInt("id");
            rs.close();
        }
        catch (SQLException e)
         {
                System.out.println(e);
                System.out.println("Excepcion SQL del método existeTexto() (SQLite_conexion)");
         }
        
       return id; 
    }
    
    /**
     * Verifica si una palabra existe en la base de datos, de ser así le asigna 
     * el id correspondiente. Si la palabra no existe en la base de datos
     * le asigna id -1.
     * @param p Palabra a ser verificada
     * @return id de la Palabra
     */
    public int existePalabra(Palabra p)
    {
        int id = -1;
        String tabla = "Palabras";
        String campos = "cadenaPalabra, idPalabra";
        String condicion = "Palabras.cadenaPalabra = \""+ p.getCadena()+ "\"";
        ResultSet rs = this.select(campos, tabla, condicion);
        boolean existe=false;
        try 
        {           
            existe= rs.next();
            if(existe)
                id = rs.getInt("idPalabra");
            rs.close();
        }
        catch (SQLException e)
         {
                
                System.out.println(e);
                System.out.println("Excepcion SQL del método existePalabra() (SQLite_conexion)");
         }
        
       return id; 
    }
    
    
    
    /**
     * Aumenta la frecuencia de la palabra almacenada en la base de datos
     * @param p Palabra a aumentar la frecuencia
     */
    private void aumentarFrecuenciaPalabra(Palabra p)
    {
        String tabla = "Palabras";
        String campos = "frecuenciaPalabra";
        String condicion = "cadenaPalabra = \"" +p.getCadena()+"\"";
        
        ResultSet rs = this.select(campos, tabla, condicion);
        int frecuencia=0;
        try
        {   
            rs.next();
            frecuencia = (int) (rs.getInt("frecuenciaPalabra") + p.getFrecuencia());
            
        }
        catch(SQLException e)
        {
            System.out.println(e);
            System.out.println("Excepcion SQL del método aumentarFrecuenciaPalabra() (SQLite_conexion)");
        
        }
        
        String tabla1 = "Palabras";
        String set1 = "frecuenciaPalabra = "+frecuencia;
        String condicion1 = "Palabras.cadenaPalabra = \""+ p.getCadena() + "\"";
        
        
        
        this.update(tabla1, set1, condicion1);
        
    }
    
     
    /**
     * Inserta en la base de datos el registro correspondiente de una palabra
     * con el texto en el que aparece
     * @param idPalabra de la Palabra a ser guardada
     * @param idTexto de el Texto correspondiente
     */
    private void guardarPalabraXTexto(int idPalabra, int idTexto)
    {
        String tabla = "PalabrasXTextos";
        String campos = "idPalabra,idTexto";
        String valores = idPalabra+", "+ idTexto;
        
        this.insert(tabla, campos, valores);
              
    }
    
    /**
     * Si la palabra no existe en la base de datos, la guarda y le asigna 
     * su nuevo id. 
     * Si la palabra ya existe en la base de datos le aumenta su frecuencia.
     * @param p Palabra a ser guardada
     * @return id de la palabra guardada
     */
    public int guardarPrimeraParte(Palabra p)
    {
       int idPalabra;
        
        idPalabra = existePalabra(p);
        if (idPalabra == -1)
        {
            this.guardarPalabra(p);
            idPalabra= existePalabra(p);
        }
        else
        {
            this.aumentarFrecuenciaPalabra(p);
        }
        
        return idPalabra;
        
         
    }
    
    /**
     * Si un texto no existe en la base de datos lo guarda y le asigna el id
     * correspondiente. Finalmente llama al método guardarPalabraXTexto
     * para guardar una palabra que aparece en ese texto
     * @param t Texto a ser guardado
     * @param idPalabra de la Palabra 
     */
    public void guardarSegundaParte(Texto t, int idPalabra)
    {
        int idTexto;
        
        idTexto = existeTexto(t);
        if(idTexto == -1)
        {
            this.guardarTexto(t);
            idTexto = existeTexto(t);
        }
        
        this.guardarPalabraXTexto(idPalabra, idTexto);
    }
    
    /**
     * Lee el contenido de la base de datos y guarda su contenido en
     * un DefaultTableModel
     * @param t DefaultTableModel a ser cargado 
     */
    public void leerDiccionario(DefaultTableModel t)
    {
        
        t.setRowCount(0);
        t.setColumnCount(0);
        ResultSet aux;
        
        String consulta = "SELECT Palabras.cadenaPalabra AS Palabra, Palabras.frecuenciaPalabra AS Frecuencia, Count(PalabrasXTextos.idTexto) AS \"Cantidad de Textos\" "
                + " FROM Palabras INNER JOIN PalabrasXTextos ON (Palabras.idPalabra = PalabrasXTextos.idPalabra) "
                + " GROUP BY Palabras.cadenaPalabra";
      try 
        {
           aux = this.selectConsulta(consulta);
           if(aux!=null)
           {
               int cantColumnas = aux.getMetaData().getColumnCount();
               for (int j = 1; j <= cantColumnas; j++)
               {
                   t.addColumn(aux.getMetaData().getColumnName(j));
               }
               while(aux.next())
               {
                   String[] objetos = new String[cantColumnas];
                   for (int i = 0; i < cantColumnas; i++) 
                   {
                       objetos[i] = aux.getString(i+1);
                   }
                   t.addRow(objetos);
               }
           }

        }
     catch (SQLException ex) 
        {
            System.out.println("Excepcion SQL del método leerDiccionario() (SQLite_conexion)");
        }
      
     
    }
    
    /**
     * Coloca el atributo AutoCommit de la conexión el false.
     */
    public void iniciarTransaccion()
    {
        try{
        this.connection.setAutoCommit(false);
        }
        catch(SQLException e)
        {
            System.out.println("Excepcion SQL del método iniciarTransaccion() (SQLite_conexion)");
        }
    }
    
    
    /**
     * Si el usuario canceló el procesamiento de los textos, realiza un rollback
     * de lo contrario realiza commit.
     * @param cancelo 
     */
    public void finalizarTransaccion(boolean cancelo)
    {
        try
        {
            if(cancelo)
            {
               connection.rollback();
            }
            else
            {
                connection.commit();
            }
            
            connection.setAutoCommit(true);
        }
        
        catch (SQLException e) 
        {
            System.out.println(e);
            System.out.println("Excepcion SQL del método finalizarTransacción() (SQLite_conexion)");
        }
    }
    
    
    /**
     * Consulta los textos asociados a una palabra en la base de datos
     * @param cad String de la Palabra a ser buscada
     * @return lista de textos asociados a la palabra.
     */
    public ArrayList<String> buscarDatosPalabra(String cad)
    {
        ArrayList<String> resp=new ArrayList();
        
        String tabla= "Palabras P INNER JOIN PalabrasXTextos PXT ON (P.idPalabra=PXT.idPalabra) INNER JOIN Textos T ON (T.id=PXT.idTexto)";
        String campos="T.titulo";
        String where= "P.cadenaPalabra = \""+cad+"\"";
        ResultSet rs= this.select(campos, tabla, where);
        try
        {
           
           while(rs.next())
            {
               resp.add(rs.getString("titulo"));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
            System.out.println("Excepcion SQL del método buscarDAtosPalabra() (SQLite_conexion)");
        }
    
    return resp;
    }
    
    
    

}
