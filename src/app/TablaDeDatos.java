//Indicamos en que paquete del proyecto java esta el archivo
package src.app;

//Importamos paquetes necesarios
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.sql.*;
import java.util.Iterator;

public class TablaDeDatos extends JFrame  {
	// Definimos los componentes del Jframe
		private JPanel contentPane;
		private String Servidor;
		private String Usuario;
		private String Password;
		private String Puerto;
		private static DefaultTableModel modelo;
		private static TablaDeDatos tb;
		private static ConexionBD conBD;
		private JComboBox comboBoxTablaBD;
		private JComboBox comboBoxBD;
		private String BD;
		private String TablaBD;
		private JButton BottonSelect;
		private JButton BottonImportEx;
		private static JTable tabla;
		

		
		/**
		 * Launch the application.
		 */

		 public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						//Creamos el objeto de la clase Consulta1
						TablaDeDatos frame = new TablaDeDatos();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			
			});
		}
		
	/**
	 * Create the frame.
	 */
	public TablaDeDatos() {
		//Creamos la ventana JFrame
		setTitle("Resultado Consulta");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 922, 542);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
				//Definimos la etiqueta EtiqTabla
				JLabel etiqTablaBD = new JLabel("Elige una tabla:");
				etiqTablaBD.setBounds(10, 11, 107, 19);
				//A�adimos la Etiqueta al JPanel
				contentPane.add(etiqTablaBD);
				
				//Definimos el Botton BottonImportEx
				BottonImportEx = new JButton("Importar Excel");
				BottonImportEx.setBounds(163, 53, 143, 23);
				//A�adimos el Botton al JPanel
				contentPane.add(BottonImportEx);
				
				//Definimos el Botton BottonSelect
				BottonSelect = new JButton("Ver Datos Tabla");
				BottonSelect.setBounds(10, 53, 143, 23);
				//A�adimos la Botton al JPanel
				contentPane.add(BottonSelect);
				
				//Definimos el comboBox ComoboTablaBD
				comboBoxTablaBD = new JComboBox();
				comboBoxTablaBD.setModel(new DefaultComboBoxModel(new String[] {"Seleccione una opci\u00F3n"}));
				comboBoxTablaBD.setBounds(115, 10, 150, 21);
				//A�adimos la comboBox al JPanel
				contentPane.add(comboBoxTablaBD);
				
				//Definimos la JTable table
				tabla = new JTable();
				tabla.setBounds(10, 103, 886, 389);
				//A�adimos la JTable al JPanel
				contentPane.add(tabla);
				
				//Definimos el JScrollPane scrollPane
				JScrollPane scrollPane = new JScrollPane(tabla);
				scrollPane.setBounds(10, 103, 886, 389);
				//A�adimos el JScrollPane el JPanel
				contentPane.add(scrollPane);
					
				
				
			
			
					
					
					//Metodo ActionListener para el BottonSelect
					ActionListener BottonSelectListener = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							
							//Asignamos el valor del comboboxTablaBD a la variable TaablaBD
							TablaBD=comboBoxTablaBD.getSelectedItem().toString();
							
							//Realizamos la segunda conexion a la base de datos y la guardamos en la variable con1
							Connection con1=conBD.ConexionBaseDatosConBD(Servidor,Puerto, Usuario, Password, BD);
							
							//Creamos el modelo para la JTable
							DefaultTableModel modelo=new DefaultTableModel();
							
							//Guardamos en la variable rs la consulta
							ResultSet rs=ConsultaSelectBD(con1,TablaBD);
							
							
							//Rellenamos la tabla con los datos de la consulta
							rellena(rs, modelo);
							
							//Definimos el modelo de la tabla con los datos
							tomaDatos(modelo);
						
							
							
						
						}

					};
					
					//Metodo ActionListener para el BottonImportEx
					ActionListener BottonImportExListener = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {	
							//Asignamos el valor del comboboxTablaBD a la variable TaablaBD
							TablaBD=comboBoxTablaBD.getSelectedItem().toString();
							
							//Realizamos la segunda conexion a la base de datos y la guardamos en la variable con1
							Connection con1=conBD.ConexionBaseDatosConBD(Servidor,Puerto, Usuario, Password, BD);
							
							//Creamos el modelo para la JTable
							DefaultTableModel modelo=new DefaultTableModel();
							
							//LLamamos al metodo ImportExcel
							try {
								ImportExcel(con1,TablaBD,modelo,tabla);
							} catch (IOException | SQLException e1) {
								// TODO Auto-generated catch block
								System.out.println("El import no funciona");							}
														
							//Definimos el modelo de la tabla con los datos
							tomaDatos(modelo);
						}

					};
					
					
					

					
					
					
					
					
					//A�adimos el ActionListener al BottonSelect para que ejecute el codigo del ActionListener BottonSelectListener
					BottonSelect.addActionListener(BottonSelectListener);
					
					//A�adimos el ActionListener al BottonImportEx para que ejecute el codigo del ActionListener BottonImportExListener
					BottonImportEx.addActionListener(BottonImportExListener);
				
				
	}
	
	//Metodo ImportExcel
			public  void ImportExcel(Connection con,String TablaBD,DefaultTableModel modelo,JTable tabla) throws FileNotFoundException, IOException, SQLException{
				//Definimos las variables que van se van usar en el metodo
				String Ruta="";
				Workbook ex1;
				Statement st;
				Object[] ListaColumna;
				
				//Definimos el modelo de la tabla
				tabla.setModel(modelo);
				
				//1.Paso de Excel a Jtable
				
				JFileChooser fileChooser = new JFileChooser();
				int seleccion = fileChooser.showOpenDialog(fileChooser);
				if (seleccion == JFileChooser.APPROVE_OPTION)
				{
					Ruta=fileChooser.getSelectedFile().getAbsolutePath();
					System.out.println(Ruta);
				   File fichero = fileChooser.getSelectedFile();
				   
				}
				
				try {
				ex1=WorkbookFactory.create(new FileInputStream(Ruta));
				Sheet hoja=ex1.getSheetAt(0);
				Iterator filaIterator=hoja.rowIterator();
				int indicefila=-1;
				
				try {
					
				 st = con.createStatement();
											
					System.out.println("LLEGO AQUI");
							ResultSet rs2 = st.executeQuery("select * from "+TablaBD);
							while (rs2.next())
							{
							System.out.println("CODIGO="+rs2.getString("codigo")+
							", Nombre="+rs2.getString("Nombre"));
							}
					} catch (Exception e) {
						e.printStackTrace();
					}
				
				while(filaIterator.hasNext()) {
					indicefila++;
				
				Row fila=(Row) filaIterator.next();
				Iterator ColumnaIterator=fila.cellIterator();
				ListaColumna=new Object[10];
				int indiceColumna=-1;
				while(ColumnaIterator.hasNext()) {
					indiceColumna++;
					Cell celda=(Cell) ColumnaIterator.next();
					if(indicefila==0) {
						modelo.addColumn(celda.getRichStringCellValue());
						
					}else {
						if(celda!=null) {
							switch(celda.getCellType()){
							case NUMERIC:
							ListaColumna[indiceColumna]=(int)Math.round(celda.getNumericCellValue());
							break;
							
							case STRING:
							ListaColumna[indiceColumna]=celda.getStringCellValue();
							break;
							
							case BOOLEAN:
							ListaColumna[indiceColumna]=celda.getBooleanCellValue();
							break;
							
							default:
							ListaColumna[indiceColumna]=celda.getDateCellValue();
							break;
							}
						}
					}
					
				}
			
				if(indicefila!=0)modelo.addRow(ListaColumna);
					
				}
				//Mostrar mensaje por pantalla
				System.out.println("Paso de Excel a JTable Exitoso");
				
					
				} catch (EncryptedDocumentException e) {
					//Mostrar mensaje de error por pantalla
					System.out.println(" Fallo en Paso de Excel a JTable");
				}
			
					
		
			
				System.out.println("LLego has aqui");
			
				//2.Pasar la tabla a la tabla indicada de la Base de Datos indicada del Sql Server
				 
			if(tabla.getRowCount()>0) {
					
					try {
					//Creamos el Statement de la conexion para realizar la consulta
					st = con.createStatement();
					
					
					  for(int i=0;i<tabla.getRowCount();i++) {
						st.execute("INSERT INTO "+TablaBD+" VALUES("
								+"'"+tabla.getValueAt(i, 0)+"'"+","+"'"+tabla.getValueAt(i,1)+"'"+")");  
				
						}
					con.close();
					
					//Sacar mensaje por pantalla
					System.out.println("La importacion de los Datos a la tabla exitosa");
					
					}catch (SQLException ex) {
						//Logger.getLogger(TablaDeDatos.class.getName()).log(Level.SEVERE, null, ex);
						
						//Sacar mensaje de error por pantalla
						System.out.println("La importacion de los Datos a la tabla no funcion�");
						
					}
					
				
			}else {
				JOptionPane.showMessageDialog(this,"La tabla se encuentra vacia");
				
			}
				
			}
	
	//Metodo ConsultaSelectBD
			public static ResultSet ConsultaSelectBD(Connection con1, String TablaBD){
				//Definimos y instanciamos las variables para usar en la consulta
				ResultSet rs=null;
				Statement st=null;
				String SQL="SELECT * FROM "+TablaBD;
				
				 try {
				
				st = con1.createStatement();
				
				rs = st.executeQuery(SQL);
				
				//Definir mensaje y mostrarlo por pantalla
				System.out.println("Consulta realizada con exito");
				
				 }catch (SQLException e2) {
		        	//Definir mensaje de error y mostrarlo por pantalla
					System.out.println("No se puede realizar la Consulta");
		 }	
				 return rs;
				
			}
			
			//Metodo ConsultaContID
			public int ConsultaContID(Connection con1){
				//Definimos y instanciamos las variables para usar en la consulta
				ResultSet rs=null;
				Statement st=null;
				int contador = 0;
				
				 try {
				
				st = con1.createStatement();
				
				rs = st.executeQuery("SELECT * FROM persona ");
				
				

				while (rs.next()) {

				    contador++;
				}

				
				 }catch (SQLException e2) {
		        	//Definir mensaje de error y mostrarlo por pantalla
					System.out.println("No se puede realizar la Consulta");
		 }	
				 return contador;
				
			}
			
				
			
	
			
			//Metodo RellenarComboBoxTablaBD
			public static void RellenarComboBoxTablaBD(String Servidor,String Puerto,String Usuario,String Password,JComboBox comboBoxTablaBD,ConexionBD conBD,String BD) {
				//Definimos las variables para usar en la consulta sql
				Connection con=null;
				 Statement st=null;
				 ResultSet rs=null;
				  
				//LLamamos al metodo conexionBaseDatos del objeto conBD1 para crear la conexion con la Base de Datos
				con=conBD.ConexionBaseDatosConBD(Servidor,Puerto, Usuario, Password,BD);
				
				try {
				
				//Creamos el Statement de la conexion para realizar la consulta
				st = con.createStatement();
				
				//Realizar la consulta a la Bases de Datos
				rs = st.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES;");
				
						while (rs.next()) {
							//Guardamos las bases de datos en el combobox
							comboBoxTablaBD.addItem(rs.getString(1));
						}
					
						System.out.println("ComboBoxTablaBD rellenado con las Bases de Datos Actuales");
				
					} catch (SQLException e3) {
					System.out.println("Algun Dato de la consulta es erroneo, no se puede rellenar el comboBoxTablaBD");
					}
					
			}	
		
			

			
			
			//Metodo ObtenerNumColumBD
			public int ObtenerNumColumBD(Connection con1) {
				//Definimos las variables a unar en el metodo
				int columnCount=0;
				try {
				//Creamos el Statement de la conexion para realizar la consulta
				Statement st = con1.createStatement();
				ResultSet rs = st.executeQuery("SELECT a, b, c FROM TABLE2");
				ResultSetMetaData rsmd = rs.getMetaData();
				columnCount = rsmd.getColumnCount();

				// The column count starts from 1
				for (int i = 1; i <= columnCount; i++ ) {
				     String name = rsmd.getColumnName(i);
				     // Do stuff with name
				}
				 }catch (SQLException e2) {
			        	//Definir mensaje de error y mostrarlo por pantalla
						System.out.println("No se puede obtener los nombres de las columnas de la tabla");
			 }
				
				return columnCount;
			}
			
			//Metodo ObtenerNomColumBD
				public String ObtenerNomColumBD(Connection con1) {
					//Definimos las variables a unar en el metodo
					int columnCount=0;
					String name="";
					
					try {
					//Creamos el Statement de la conexion para realizar la consulta
					Statement st = con1.createStatement();
					ResultSet rs = st.executeQuery("SELECT a, b, c FROM TABLE2");
					ResultSetMetaData rsmd = rs.getMetaData();
					columnCount = rsmd.getColumnCount();

					// The column count starts from 1
					for (int i = 1; i <= columnCount; i++ ) {
					     name = rsmd.getColumnName(i);
					     // Do stuff with name
					}
					 }catch (SQLException e2) {
				        	//Definir mensaje de error y mostrarlo por pantalla
							System.out.println("No se puede obtener los nombres de las columnas de la tabla");
				 }
					
					return name;
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
	        	JOptionPane.showMessageDialog(null, "No se puede a�adir las filas de datos del modelo");
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

		public static DefaultTableModel getModelo() {
			return modelo;
		}

		public static void setModelo(DefaultTableModel modelo) {
			TablaDeDatos.modelo = modelo;
		}

		public static TablaDeDatos getTb() {
			return tb;
		}

		public static void setTb(TablaDeDatos tb) {
			TablaDeDatos.tb = tb;
		}

		public String getServidor() {
			return Servidor;
		}

		public void setServidor(String Serv) {
			this.Servidor = Serv;
		}

		public String getUsuario() {
			return Usuario;
		}

		public void setUsuario(String usuario) {
			this.Usuario = usuario;
		}

		public String getPassword() {
			return Password;
		}

		public void setPassword(String password) {
			this.Password = password;
		}

		public String getBD() {
			return BD;
		}

		public void setBD(String bD) {
			this.BD = bD;
		}

		public String getTablaBD() {
			return TablaBD;
		}

		public void setTablaBD(String tablaBD) {
			this.TablaBD = tablaBD;
		}

		public JComboBox getComboBoxBD() {
			return comboBoxBD;
		}

		public void setComboBoxBD(JComboBox cBBD) {
			this.comboBoxBD = cBBD;
		}

		public JComboBox getcomboBoxTablaBD() {
			return comboBoxTablaBD;
		}

		public void setComboBoxTablas(JComboBox comboBoxTablaBD) {
			this.comboBoxTablaBD = comboBoxTablaBD;
		}

		public JComboBox getComboBoxTablaBD() {
			return comboBoxTablaBD;
		}

		public void setComboBoxTablaBD(JComboBox comboBoxTablaBD) {
			this.comboBoxTablaBD = comboBoxTablaBD;
		}

		public static void setConBD(ConexionBD conBD) {
			TablaDeDatos.conBD = conBD;
		}

		public String getPuerto() {
			return Puerto;
		}

		public void setPuerto(String puerto) {
			Puerto = puerto;
		}

		public static ConexionBD getConBD() {
			return conBD;
		}	
		
		
		
		
}