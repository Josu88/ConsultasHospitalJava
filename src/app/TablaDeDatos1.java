//Indicamos en que paquete del proyecto java esta el archivo
package src.app;

//Importamos paquetes necesarios
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.awt.Component;
import javax.swing.filechooser.FileFilter;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.SystemColor;

/***Autor: Josue Rodriguez***/

public class TablaDeDatos1 extends JFrame  {
	// Definimos los componentes del Jframe
		private JPanel contentPane;
		private String Servidor;
		private String Usuario;
		private String Password;
		private String Puerto;
		private static DefaultTableModel modelo;
		private static TablaDeDatos1 tb;
		private static ConexionBD1 conBD;
		private JComboBox comboBoxTablaBD;
		private JComboBox comboBoxBD;
		private String BD;
		private static String TablaBD;
		private JButton BottonSelect;
		private JButton BottonImportEx;
		private static JTable tabla;
		private Row fila;
		Workbook wb;
		File archivo;
		private JLabel EtiqImport;
		private static JTextArea CampInf;

		
		/**
		 * Launch the application.
		 */
		/*
		 public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						//Creamos el objeto de la clase tabladeDatos2
						tb = new TablaDeDatos1();
						tb.setVisible(true);
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
	public TablaDeDatos1() {
		//Creamos la ventana JFrame
		setTitle("Resultado Consulta");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 997, 543);
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
				comboBoxTablaBD.setBounds(115, 10, 191, 21);
				//A�adimos la comboBox al JPanel
				contentPane.add(comboBoxTablaBD);
				
				//Definimos la JTable table
				tabla = new JTable();
				tabla.setBounds(10, 103, 886, 389);
				//A�adimos la JTable al JPanel
				contentPane.add(tabla);
				
				//Definimos el JScrollPane scrollPane
				JScrollPane scrollPane = new JScrollPane(tabla);
				scrollPane.setBounds(10, 103, 961, 389);
				//A�adimos el JScrollPane el JPanel
				contentPane.add(scrollPane);
				
				//Definimos el Botton BottonExportEx
				JButton BottonExportEx = new JButton("Exportar Excel");
				BottonExportEx.setBounds(316, 53, 143, 23);
				//A�adimos el Botton al JPanel
				contentPane.add(BottonExportEx);
				
				//Definimos el Botton BottonSentQue
				JButton BottonSentQue = new JButton("Ejecutar Sentencia Campo de texto Query");
				BottonSentQue.setBounds(469, 53, 279, 23);
				//A�adimos el Botton al JPanel
				contentPane.add(BottonSentQue);
				
				//Definimos el JTextField CampInf
				CampInf = new JTextArea();
				CampInf.setText("INFORMACION:");
				CampInf.setFont(new Font("Monospaced", Font.PLAIN, 13));
				CampInf.setBackground(SystemColor.menu);
				CampInf.setForeground(SystemColor.desktop);
				CampInf.setEditable(false);
				CampInf.setBounds(755, 10, 216, 73);
				CampInf.setColumns(10);
				//A�adimos el JTextField al JPanel
				contentPane.add(CampInf);
				
				
		
					
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
							ResultSet rs;
							try {
								//relizamos la consulta a la base de datos sql server
								rs = ConsultaSelectBD(con1,TablaBD);
								
								//Rellenamos la tabla con los datos de la consulta
								rellena(rs, modelo);
								
								//Definimos el modelo de la tabla con los datos
								tomaDatos(modelo);
								
							} catch (InterruptedException e1) {
								//Sacamos el mensaje de error por pantalla
								System.out.println("La consulta Select no se realizo con exito");
							}						
						
						}

					};
					
					
					//Metodo ActionListener para el BottonSentQue
					ActionListener BottonSentQueListener = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							
						//Creamos un objeto de la clase ConsultaQuery
						ConsultaQuery1 ConQ=new ConsultaQuery1();
						
						//Asignamos el valor del comboboxTablaBD a la variable TaablaBD
						TablaBD=comboBoxTablaBD.getSelectedItem().toString();
						
						//Pasamos valores al Jframe ConsultaQuery
						ConQ.setServidor(Servidor);
						ConQ.setUsuario(Usuario);
						ConQ.setPassword(Password);
						ConQ.setPuerto(Puerto);
						ConQ.setTablaBD(TablaBD);
						ConQ.setBD(BD);
						ConQ.setConBD(conBD);
						ConQ.setTb(tb);
						
						//Mostrar JFrame
						ConQ.setVisible(true);
						
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
					
					//Metodo ActionListener para el BottonExportEx
					ActionListener BottonExportExListener = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {	
							
							//LLamamos al metodo ExportExcel
							System.out.println(ExportarExcel(archivo,tabla));		
						
						}

					};
					
					//A�adimos el ActionListener al BottonSelect para que ejecute el codigo del ActionListener BottonSelectListener
					BottonSelect.addActionListener(BottonSelectListener);
					
					//A�adimos el ActionListener al BottonImportEx para que ejecute el codigo del ActionListener BottonImportExListener
					BottonImportEx.addActionListener(BottonImportExListener);
					
					//A�adimos el ActionListener al BottonExportEx para que ejecute el codigo del ActionListener BottonExportExListener
					BottonExportEx.addActionListener(BottonExportExListener);
					
					//A�adimos el ActionListener al BottonSentQue para que ejecute el codigo del ActionListenerBottonSentQueListener
					BottonSentQue.addActionListener(BottonSentQueListener);
				
				
	}
	
	
	//Metodo ImportExcel
			public  void ImportExcel(Connection con,String TablaBD,DefaultTableModel modelo,JTable tabla) throws FileNotFoundException, IOException, SQLException{
				//Definimos las variables que van se van usar en el metodo
				String Ruta="",Insert="",CadIns="",NomColumns="";
				int ContColumn=0,Columns=0,ExCol=0,j=0;
				Workbook ex1;
				PreparedStatement pst;
				Object[] ListaColumna;
				
				
				
				
				//Definimos el modelo de la tabla
				tabla.setModel(modelo);
				
				//1.Paso de Excel a Jtable
				
				//Definimos el Filechooser para elegir la ruta del archivo del excel del que extraer la informacion
				JFileChooser fileChooser = new JFileChooser();
				int seleccion = fileChooser.showOpenDialog(fileChooser);
				if (seleccion == JFileChooser.APPROVE_OPTION)
				{
					Ruta=fileChooser.getSelectedFile().getAbsolutePath();
					System.out.println(Ruta);
				   File fichero = fileChooser.getSelectedFile();
				   
				}
				
				try {
				//Creamos el fichero Excel y le indicamos la ruta del fichero excel que tiene la informacion
				ex1=WorkbookFactory.create(new FileInputStream(Ruta));
				//Creamos una hoja del fichero excel
				Sheet hoja=ex1.getSheetAt(0);
				Iterator filaIterator=hoja.rowIterator();
				int indicefila=-1;
				
				//Recorremos el fichero excel antes indicado para cojer los datos de su filas
				while(filaIterator.hasNext()) {
					indicefila++;
				
				fila=(Row) filaIterator.next();
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
			
				if(indicefila!=0)
				
				//Guardampos los datos en el modelo de la JTable
				modelo.addRow(ListaColumna);
					
				}
				//Mostrar mensaje por pantalla
				JOptionPane.showMessageDialog(this,"Paso de Excel a JTable Exitoso");
				
					
				} catch (EncryptedDocumentException e) {
					//Mostrar mensaje de error por pantalla
					JOptionPane.showMessageDialog(this,"Fallo en Paso de Excel a JTable");
				}
			
			
				//2.Pasar la tabla a la tabla indicada de la Base de Datos indicada del Sql Server
				
				//Contar las columnas que tiene una tabla especifica
				try {
					int Cont=0;
					
					//Creamos el PreparedStatement de la conexion para realizar la consulta
					pst = con.prepareStatement("SELECT count(COLUMN_NAME) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='"+TablaBD+"'"); 
					
					
					//Contamos las columnas de la tabla especifica 
					ResultSet rs=pst.executeQuery(); 
					
					while (rs.next()) {
						if(Cont>=2) {
							NomColumns+=rs.getString(1)+",";
							}
							Cont++;
					}
				
					
					//Sacar mensaje por pantalla
					System.out.println(" contar las columnas de la tabla Exitoso");
					
				}catch (SQLException ex) {
					
					//Sacar mensaje de error por pantalla
					System.out.println("No se pudo contar las columnas de la tabla");
					
				}
				
	
					
					try {
						
						//Creamos el PreparedStatement de la conexion para realizar la consulta
						pst = con.prepareStatement("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='"+TablaBD+"'"); 
						
						
						//Consultamos la tabla para sacar los nombres de las columnas
						ResultSet rs=pst.executeQuery(); 
						
						while (rs.next()) {
							NomColumns+=rs.getString(1)+",";
						}
						
						//Guardamos el numero de Columnas de la tabla en unavarible y le restamos 1 para que no este restando uno cada vuelta del bucle
						int Columnas=tabla.getColumnCount()-1;
						
						//Guardamos el numero de filas de la tabla en unavarible y le restamos 1 para que no este restando uno cada vuelta del bucle
						int Filas=tabla.getRowCount()-1;
						
						//Le quitamos la ultima coma al String
						String NC=NomColumns.substring(0,NomColumns.length()-1);
						
						//Comenzamos a crear el insert
						CadIns="INSERT INTO "+TablaBD+" ("+NC+")"+" VALUES ";
						
						//A�adimos los datosfila a fila al insert
						for(int x=0;x<tabla.getRowCount();x++) {
							
						//Al principio de cada vuelta del bucle agregamos el prentesis de abertura al string
						CadIns+="(";
						
					  for(int i=0;i<tabla.getColumnCount();i++) {
						  
						  
						 //Controlamos lo que metemos en el String
						  if(i<Columnas) {
						CadIns+="'"+tabla.getValueAt(x,i)+"'"+",";  					
						}else {
							CadIns+="'"+tabla.getValueAt(x,i)+"'"; 
						}
					  }
					  
					//Controlamos lo que metemos en el String
					  if(x<Filas) {
					  CadIns+="),";
					  }else {
						  CadIns+=")";
					  }
					  
							}
					  
					//Creamos el PreparedStatement de la conexion para realizar la consulta
					pst = con.prepareStatement(CadIns); 
						
					//Ejecutamos el insert
					pst.execute();
					
					//Cerrar la conexion
					con.close();
					
					//Sacar mensaje por pantalla
					JOptionPane.showMessageDialog(this,"La importacion de los Datos a la tabla exitosa");
					
					}catch (Exception e) {
					
						//Segundo Insert si el primero falla ejecutamos el segundo atraves del catch
						
						try {
							//resetear variables
							int i=0,x=0,Columnas1=0,Filas1=0;
							String CadIns1="",CadIns2="";
							String NC1="";
							String NomColumns1="";
							
							
							//Creamos el PreparedStatement de la conexion para realizar la consulta
							pst = con.prepareStatement("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='"+TablaBD+"'"); 
							
							
							//Consultamos la tabla para sacar los nombres de las columnas
							ResultSet rs=pst.executeQuery(); 
							
							int Cont=0;
							
							while (rs.next()) {
								if(Cont>=1) {
								NomColumns1+=rs.getString(1)+",";
								}
								Cont++;
							
							}
							
							//Guardamos el numero de Columnas de la tabla en unavarible y le restamos 1 para que no este restando uno cada vuelta del bucle
							Columnas1=tabla.getColumnCount()-1;
							
							//Guardamos el numero de filas de la tabla en unavarible y le restamos 1 para que no este restando uno cada vuelta del bucle
							Filas1=tabla.getRowCount()-1;
							
							//Le quitamos la ultima coma al String
							NC1=NomColumns1.substring(0,NomColumns1.length()-1);
							
							//Comenzamos a crear el insert
							CadIns1="INSERT INTO "+TablaBD+" ("+NC1+")"+" VALUES ";
							
							//A�adimos los datosfila a fila al insert
							for(x=0;x<tabla.getRowCount();x++) {
								
							//Al principio de cada vuelta del bucle agregamos el prentesis de abertura al string
							CadIns1+="(";
							
						  for(i=0;i<tabla.getColumnCount();i++) {
							   
							  //Controlamos lo que metemos en el String
							  if(i<Columnas1) {
							CadIns1+="'"+tabla.getValueAt(x,i)+"'"+",";  					
							}else {
								CadIns1+="'"+tabla.getValueAt(x,i)+"');"; 
							} 					
						  }
						  
						//Le quitamos la ultima coma al String
						CadIns2=CadIns1.substring(0,CadIns1.length()-1);
						  
							  
								}
							
						//Creamos el PreparedStatement de la conexion para realizar la consulta
						pst = con.prepareStatement(CadIns2); 
						  
						 //Ejecutamos el insert
						 pst.execute();
						 
							//Cerrar la conexion
							con.close();
						
						//Sacar mensaje por pantalla
						JOptionPane.showMessageDialog(this,"La importacion de los Datos a la tabla exitosa");
						
						}catch (Exception e1) {
							
							//Sacar mensaje de error por pantalla
							JOptionPane.showMessageDialog(this,"Las columnas del Excel y la tabla no son iguales,La importacion de los Datos a la tabla no funciona ");
							
							
							//Definimos variables a usar en el  catch
							int ContFil=0;
							PreparedStatement pst2;
							ResultSet rs=null;
								 
							//Definir mensaje de error y mostrarlo por pantalla
							System.out.println("Algun Dato de la consulta es erroneo, no se puede rellenar el comboBoxTablaBD");
							
							//Calculamos las filas totales de la base de datos
							pst2 = con.prepareStatement("SELECT  count(*) FROM "+TablaBD);
							
							//Realizar la consulta a la Bases de Datos
							rs = pst2.executeQuery();
							
									while (rs.next()) {
										ContFil=rs.getInt(1);
									}
							
							//Reiniciamos el el campo autonumerico si falla
							
							//Creamos el PreparedStatement de la conexion para realizar la consulta
							pst2 = con.prepareStatement("DBCC CHECKIDENT('"+TablaBD+"', RESEED,"+ContFil+")");
							
							
							//Realizar la consulta a la Bases de Datos
							rs = pst2.executeQuery();
								
						}				
					
					}				
				
				
			}
			
			//Metodo ExportarExcel
			public String ExportarExcel(File archivo,JTable tablaD) {
				//Definimos las variables que van se van usar en el metodo
				String respuesta="No se realiza con exito la exportacion.";
				int numFila=tablaD.getRowCount(), numColumna=tablaD.getColumnCount();
				 
				  //Creamos el filechososer para decirle al exportar donde guardareamos el fichero excel
				  JFileChooser guardar = new JFileChooser("C:/");
				    guardar.showSaveDialog(null);
				    guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				    archivo = guardar.getSelectedFile();
				    
				 
				//En funcion de que la extension sea xls o no usamos un metodo o otro de las librerias poi de java
				if(archivo.getName().endsWith("xls")) {
					wb = new HSSFWorkbook();
				} else {
					wb = new XSSFWorkbook();
				}
				//Creamos una hoja para el fichero excel
				Sheet hoja = wb.createSheet("Pruebita");
				try {
					//recorremos las filas del fichero excel para colocar la informacion guardada en la Jtable en el excel
					for (int i = -1; i < numFila; i++) {
						Row fila = hoja.createRow(i+1);
						for (int j = 0; j < numColumna; j++) {
							Cell celda = fila.createCell(j);
							if(i==-1) {
								celda.setCellValue(String.valueOf(tablaD.getColumnName(j)));
							} else {
								celda.setCellValue(String.valueOf(tablaD.getValueAt(i, j)));
							}
							//Guardamos todo en el File archivo
							wb.write(new FileOutputStream(archivo));
						}
					}
					//Guardamos en el string unos datos si es exitosa la importacion del excel
					respuesta="Lectura de archivo Excel exitosa.";
				} catch (Exception e) {
					//Definimos un mensaje de Error si falla la eportacion del excel
					respuesta="Exportacion del excel Fallida";
				}
				//Sacamos fuera del metodo el String respuesta
				return respuesta;
			
			}
	
	//Metodo ConsultaSelectBD
			public static ResultSet ConsultaSelectBD(Connection con1, String TablaBD) throws InterruptedException{
				//Definimos y instanciamos las variables para usar en la consulta
				ResultSet rs=null,rs2=null;
				PreparedStatement pst=null;
				String SQL="SELECT * FROM "+TablaBD;
				
				
				 try {
					 //Calcular la hora actual
					 DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
					 Date date = new Date();
					 	 
				
				//Cojemos los milisegundos actuales antes de la ejecucion de la instruccion SQL
				double inicio = System.currentTimeMillis();
				         
				//Creamos el PreparedStatement para luego ejecutar la consulta
				pst = con1.prepareStatement(SQL);
				
				//Ejecutamos las consultas
				rs = pst.executeQuery();
				
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
				
		        	//Definir mensaje de error y mostrarlo por pantalla
					 System.out.println("No se puede realizar la Consulta");
		 }	
				 //Sacamos fuera del metodo el Resultset guardado en la variable rs
				 return rs;
				
			}
				
	
			
			//Metodo RellenarComboBoxTablaBD
			public static void RellenarComboBoxTablaBD(String Servidor,String Puerto,String Usuario,String Password,JComboBox comboBoxTablaBD,ConexionBD1 conBD,String BD) throws SQLException {
				//Definimos las variables para usar en la consulta sql
				Connection con=null;
				 PreparedStatement pst=null;
				 ResultSet rs=null;
				
				  
				//LLamamos al metodo conexionBaseDatos del objeto conBD1 para crear la conexion con la Base de Datos
				con=conBD.ConexionBaseDatosConBD(Servidor,Puerto, Usuario, Password,BD);
				
				try {
				//Creamos el PreparedStatement de la conexion para realizar la consulta
				pst = con.prepareStatement("SHOW TABLES;");
				
				//Realizar la consulta a la Bases de Datos
				rs = pst.executeQuery();
				
						while (rs.next()) {
							//Guardamos las bases de datos en el combobox
							comboBoxTablaBD.addItem(rs.getString(1));
						}
						//Definir mensaje de exito en la ejecucion y mostrarlo por pantalla
						System.out.println("ComboBoxTablaBD rellenado con las Bases de Datos Actuales");
				
					} catch (SQLException e3) {
						//Definir mensaje de error en la ejecucion y mostrarlo por pantalla
						System.out.println("ComboBoxTablaBD no ha sido rellenado con las Bases de datos actuales");
					}
					
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
	                        // podemos obtener el numero de columnas y el nombre
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
			TablaDeDatos1.modelo = modelo;
		}

		public static TablaDeDatos1 getTb() {
			return tb;
		}

		public static void setTb(TablaDeDatos1 tb) {
			TablaDeDatos1.tb = tb;
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

		public static void setConBD(ConexionBD1 conBD) {
			TablaDeDatos1.conBD = conBD;
		}

		public String getPuerto() {
			return Puerto;
		}

		public void setPuerto(String puerto) {
			Puerto = puerto;
		}

		public static ConexionBD1 getConBD() {
			return conBD;
		}	
}