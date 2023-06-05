//Indicamos en que paquete del proyecto java esta el archivo
package src.app;

//Importamos paquetes necesarios
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.SystemColor;


/***Autor: Josue Rodriguez***/

public class ConsultaQuery1 extends JFrame{
	
	// Definimos los componentes del Jframe
	private JPanel contentPane;
	private static JTable tabla;
	private String Servidor;
	private String Usuario;
	private String Password;
	private String Puerto;
	private static DefaultTableModel modelo;
	private static TablaDeDatos1 tb;
	private static ConexionBD1 conBD;
	private static ConsultaQuery1 ConQ;
	private JComboBox comboBoxTablaBD;
	private String BD;
	private String TablaBD;
	private JButton BottonConsQuery;
	private JButton BottonRein;
	private File archivo;
	private JTextArea CampInf;
	private JTextArea CampQuery;
	private JScrollPane scrollPane_1;

	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConQ = new ConsultaQuery1();
					ConQ.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	*/

	/**
	 * Create the frame.
	 */
	public ConsultaQuery1() {
		//Creamos la ventana JFrame
		setTitle("JPanel");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 827, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Definimos la etiqueta EtiqQuery
		JLabel EtiqQuery = new JLabel("Escriba la Query:");
		EtiqQuery.setBounds(10, 11, 614, 14);
		//A�adimos la Etiqueta al JPanel
		contentPane.add(EtiqQuery);
		
		//Definimos la JTable table
		tabla = new JTable();
		tabla.setBounds(10, 177, 614, 199);
		//A�adimos la JTable al JPanel
		contentPane.add(tabla);
		
		//Definimos la etiqueta EtiqTabla
		JLabel EtiqTabla = new JLabel("Resultado de la Query:");
		EtiqTabla.setBounds(10, 151, 614, 23);
		//A�adimos la Etiqueta al JPanel
		contentPane.add(EtiqTabla);
		
		//Definimos el JScrollPane scrollPane
		JScrollPane scrollPane = new JScrollPane(tabla);
		scrollPane.setBounds(10, 177, 791, 199);
		//A�adimos el JScrollPane el JPanel
		contentPane.add(scrollPane);
		
		//Definimos el Botton BottonConsQuery
		BottonConsQuery = new JButton("Consultar");
		BottonConsQuery.setBounds(71, 387, 137, 23);
		//A�adimos el Botton al JPanel
		contentPane.add(BottonConsQuery);
		
		//Definimos el Botton BottonRein
		BottonRein = new JButton("Reiniciar");
		BottonRein.setBounds(536, 390, 137, 23);
		//A�adimos el Botton al JPanel
		contentPane.add(BottonRein);
		
		//Definimos el JTextArea CampInf
		CampInf = new JTextArea();
		CampInf.setText("INFORMACION:");
		CampInf.setBackground(SystemColor.menu);
		CampInf.setEditable(false);
		CampInf.setBounds(634, 26, 167, 119);
		//A�adimos el JTetArea al JPanel
		contentPane.add(CampInf);
		
		//Definimos el JScrollPane scrollPane_1
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 26, 614, 119);
		//A�adimos el JScrollPane al JPanel
		contentPane.add(scrollPane_1);
		
		//Definimos el JTextArea CampQuery
		CampQuery = new JTextArea();
		 //A�adimos el JScrollPane al JTetArea
		scrollPane_1.setViewportView(CampQuery);
		
		
		//Metodo ActionListener para el BottonConsQuery
		ActionListener BottonConsQueryListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Realizamos la segunda conexion a la base de datos y la guardamos en la variable con1
				Connection con1=conBD.ConexionBaseDatosConBD(Servidor,Puerto, Usuario, Password, BD);
				
				//Definimos la variable Query dandole el valor del campo CampQuery
				String Query=CampQuery.getText();
				
				//Creamos el modelo para la JTable
				modelo=new DefaultTableModel();
				
				//Guardamos en la variable rs la consulta
				ResultSet rs;
				try {
					
					//relizamos la consulta a la base de datos sql server
					rs =ConsultaSelectBDConQuery(con1,Query,CampInf);
					
					//Rellenamos y mostramos la JTable si rs no es null
					if(rs!=null) {
					//Rellenamos la tabla con los datos de la consulta
					rellena(rs, modelo);
					
					//Definimos el modelo de la tabla con los datos
					tomaDatos(modelo);
					
					//LLamamos al metodo ExportarEx de la clase TablaDeDatos
					System.out.println(tb.ExportarExcel(archivo,tabla));
					}
						
					
				} catch (InterruptedException e1) {
					//Sacamos el mensaje de error por pantalla
					System.out.println("La consulta Select no se realizo con exito");
				}						
			
			}
					
			
		};	
		
				//Metodo ActionListener para el BottonRein
				ActionListener BottonReinListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
					//Creamos el modelo para la JTable
					modelo=new DefaultTableModel();
						
					//vaciamos el campo de texto, el text Area y la Jtable
					CampQuery.setText("");
					CampInf.setText("INFORMACION:");
					
					//Definimos el modelo de la tabla con los datos
					tomaDatos(modelo);
					
					}
				};	
		
		//A�adimos el ActionListener al BottonConsQuery para que ejecute el codigo del ActionListener BottonConsQueryListener
		BottonConsQuery.addActionListener(BottonConsQueryListener);
		
		//A�adimos el ActionListener al BottonRein para que ejecute el codigo del ActionListener BottonReinListener
		BottonRein.addActionListener(BottonReinListener);
		
	
	
	}
	
	//Metodo ConsultaSelectBDConQuery
	public static ResultSet ConsultaSelectBDConQuery(Connection con1,String Query,JTextArea CampInf) throws InterruptedException{
		//Definimos y instanciamos las variables para usar en la consulta
		ResultSet rs=null,rs2=null;
		PreparedStatement pst=null;
		String Let="",QueryLet="";
		
		 try {
			 //Guardamos el string de Query en la variable QueryLEt
			 QueryLet=Query;
			 
			 //Sacamos la primera letra o simbolo de la variable QueryLet y la guardamos en la variable Let
			 Let=QueryLet.substring(0,1); 
			 
			 
			 //Calcular la hora actual
			 DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			 Date date = new Date();
			 	 
		
		//Cojemos los milisegundos actuales antes de la ejecucion de la instruccion SQL
		double inicio = System.currentTimeMillis();
		         
		//Creamos el PreparedStatement para realizar la consulta despues
		pst = con1.prepareStatement(Query);
		
		//Comprobamos si la variable let tiene una s o S
		if(Let.equals("s") || Let.equals("S")) {
		//Ejecutamos las executequery si contine S o S la variable Let
		rs = pst.executeQuery();
		
		}else {
			//Sino usamos execute Query
			pst.executeUpdate();
			
		}
		
		//Cojemos los milisegundos actuales despues de la ejecucion de la instruccion SQL
		 double fin = System.currentTimeMillis();
         
		 //Calculamos los milisegundos de la ejecucion de la instruccion SQL
	     double milliseconds = ((fin - inicio));
	     
	     //Convertir de milisegndos a segundos
	     double seconds = milliseconds / 1000.0;
	     
	     //Mostramos en el CampInf la Hora Actual y el tiempo de ejecucion
		 CampInf.setText("INFORMACION:"+"\nHora actual: " + dateFormat.format(date)+"\nTiempo Respuesta:"+seconds +" Seg");
	    
	
		
		//Definir mensaje y mostrarlo por pantalla
		System.out.println("Consulta realizada con exito");
		
		
		 }catch (SQLException e2) {
			//Mostrar mensaje por pantalla
			JOptionPane.showMessageDialog(null,"Por favor escriba la consulta de forma correcta");
 }	
		 //Sacamos fuera del metodo el resulset en la varible rs
		 return rs;
		
	}
	
    /**
     * Mete el modelo que recibe en la tabla.
     * @param modelo
     */
    public static void tomaDatos(DefaultTableModel modelo)
    {
        if (tabla == null)
            tabla = new JTable();
        tabla.setModel(modelo);
    }
    
    
    /**
     * Rellena el DefaultTableModel con los datos del ResultSet.
     * Vac�a el DefaultTableModel completamente y le mete los datos que hay
     * en el ResultSet.
     * @param rs El resultado de lac onsula a base de datos.
     * @param modelo El DefaultTableModel que queremos rellenar
     */
    public static  void rellena(ResultSet rs, DefaultTableModel modelo)
    {
        configuraColumnas(rs, modelo);
        vaciaFilasModelo(modelo);
        anhadeFilasDeDatos(rs, modelo);
    }
    
    /**
     * A�ade al DefaultTableModel las filas correspondientes al ResultSet.
     * @param rs El resultado de la consulta a base de datos
     * @param modelo El DefaultTableModel que queremos rellenar.
     */
    private static void anhadeFilasDeDatos(ResultSet rs,
            DefaultTableModel modelo)
    {
        int numeroFila = 0;
        try
        {
            // Para cada registro de resultado en la consulta 
            while (rs.next())
            {
                // Se crea y  la fila para el modelo de la tabla.
                Object[] datosFila = new Object[modelo.getColumnCount()];
                for (int i = 0; i < modelo.getColumnCount(); i++)
                    datosFila[i] = rs.getObject(i + 1);
                modelo.addRow(datosFila);
                numeroFila++;
            }
            rs.close();
        } catch (Exception e)
        {
        	//Sacar por pantalla un mensaje de error
        	JOptionPane.showMessageDialog(null, "No se puede añadir las filas de datos del modelo");
        }
    }

    /**
     * Borra todas las filas del modelo.
     * @param modelo El modelo para la tabla.
     */
    private static void vaciaFilasModelo(final DefaultTableModel modelo)
    {
        // La llamada se hace in un invokeAndWait para que se ejecute en el
        // hilo de refresco de ventanas y evitar que salten excepciones
        // durante dicho refresco.
        try
        {
                
                    while (modelo.getRowCount() > 0) {
                        modelo.removeRow(0);
                    }
                    
        } catch (Exception e)
        {
        	//Sacar por pantalla un mensaje de error
        	JOptionPane.showMessageDialog(null, "No se puede vaciar las filas del modelo");
        }
    }

    /**
     * Pone en el modelo para la tabla tantas columnas como tiene el resultado
     * de la consulta a base de datos.
     * @param rs Resultado de consulta a base de datos.
     * @param modelo Modelo de la tabla.
     */
    public static void configuraColumnas(final ResultSet rs,
            final DefaultTableModel modelo)
    {
                    try
                    {
                        // Se obtiene los metadatos de la consulta. Con ellos
                        // podemos obtener el n�mero de columnas y el nombre
                        // de las mismas.
                        ResultSetMetaData metaDatos = rs.getMetaData();
                        
                        // Se obtiene el numero de columnas.
                        int numeroColumnas = metaDatos.getColumnCount();
                        
                        // Se obtienen las etiquetas para cada columna
                        Object[] etiquetas = new Object[numeroColumnas];
                        for (int i = 0; i < numeroColumnas; i++)
                        {
                            etiquetas[i] = metaDatos.getColumnLabel(i + 1);
                        }
                        
                        // Se meten las etiquetas en el modelo. El numero
                        // de columnas se ajusta autom�ticamente.
                        modelo.setColumnIdentifiers(etiquetas);
                    } catch (Exception e)
                    {
                       //Sacar por pantalla un mensaje de error
                    	JOptionPane.showMessageDialog(null, "No se puede configurar las columnas del modelo");
                    }

            }

    //Getters and Setters
	public JTextArea getCampQuery() {
		return CampQuery;
	}

	public void setCampQuery(JTextArea campQuery) {
		CampQuery = campQuery;
	}

	public String getServidor() {
		return Servidor;
	}

	public void setServidor(String servidor) {
		Servidor = servidor;
	}

	public String getUsuario() {
		return Usuario;
	}

	public void setUsuario(String usuario) {
		Usuario = usuario;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getPuerto() {
		return Puerto;
	}

	public void setPuerto(String puerto) {
		Puerto = puerto;
	}

	public static TablaDeDatos1 getTb() {
		return tb;
	}

	public static void setTb(TablaDeDatos1 tb) {
		ConsultaQuery1.tb = tb;
	}

	public static ConexionBD1 getConBD() {
		return conBD;
	}

	public static void setConBD(ConexionBD1 conBD) {
		ConsultaQuery1.conBD = conBD;
	}

	public JComboBox getComboBoxTablaBD() {
		return comboBoxTablaBD;
	}

	public void setComboBoxTablaBD(JComboBox comboBoxTablaBD) {
		this.comboBoxTablaBD = comboBoxTablaBD;
	}

	public static ConsultaQuery1 getConQ() {
		return ConQ;
	}

	public static void setConQ(ConsultaQuery1 conQ) {
		ConQ = conQ;
	}

	public String getBD() {
		return BD;
	}

	public void setBD(String bD) {
		BD = bD;
	}

	public String getTablaBD() {
		return TablaBD;
	}

	public void setTablaBD(String tablaBD) {
		TablaBD = tablaBD;
	}
}
