//Indicamos en que paquete del proyecto java esta el archivo
package src.app;

//Importamos paquetes necesarios
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JPasswordField;

public class ConexionBD extends JFrame {
	
	// Definimos los componentes del Jframe
	private JPanel contentPane;
	private JTextField CampUsu;
	private JTextField CampPuerto;
	private JTextField CampServ;
	private JComboBox comboBoxBD;
	private static ConexionBD conBD;
	private JPasswordField CampContra;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Creamos un objeto de la clase ConexionBD
					//ConexionBD frame = new ConexionBD();
					//Hacemos visible el Jframe1
					//frame.setVisible(true);
					//Creamos un objeto de la clase consulta1
					//cons1= new Consulta1();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConexionBD() {
		//Creamos la ventana JFrame
		setTitle("Conexion a la Bases de Datos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 392, 269);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		// Definimos los componentes del Jframe
		
				
				//Definimos el Botton BottonConec
				JButton BottonConec = new JButton("Conectar");
				BottonConec.setBounds(149, 204, 89, 23);
				//A�adimos el Botton al JPanel
				contentPane.add(BottonConec);
				
				//Definimos el Botton BottonLimp
				JButton BottonLimp = new JButton("Limpiar");
				BottonLimp.setBounds(277, 204, 89, 23);
				//A�adimos el Botton al JPanel
				contentPane.add(BottonLimp);
				
				//Definimos la etiqueta EtiqServ
				JLabel EtiqServ = new JLabel("Servidor:");
				EtiqServ.setBounds(10, 76, 158, 23);
				//A�adimos la Etiqueta al JPanel
				contentPane.add(EtiqServ);
				
				//Definimos el campo CampServ
				CampServ = new JTextField();
				CampServ.setBounds(170, 75, 196, 23);
				//A�adimos el campo de texto al JPanel
				contentPane.add(CampServ);
				CampServ.setColumns(10);
				
				//Definimos la etiqueta EtiqUsu
				JLabel EtiqUsu = new JLabel("Usuario:");
				EtiqUsu.setBounds(10, 25, 158, 14);
				//A�adimos la Etiqueta al JPanel
				contentPane.add(EtiqUsu);
				
				//Definimos la etiqueta EtiqCont
				JLabel EtiqCont = new JLabel("Contrase\u00F1a:");
				EtiqCont.setBounds(10, 50, 158, 14);
				//A�adimos la Etiqueta al JPanel
				contentPane.add(EtiqCont);
				
				//Definimos el campo CampUsu
				CampUsu = new JTextField();
				CampUsu.setBounds(170, 22, 196, 20);
				//A�adimos el campo de texto al JPanel
				contentPane.add(CampUsu);
				CampUsu.setColumns(10);
				
				//Definimos el campo CampPuerto
				CampPuerto = new JTextField();
				CampPuerto.setText("1433");
				CampPuerto.setBounds(170, 107, 196, 20);
				//A�adimos el campo de texto al JPanel
				contentPane.add(CampPuerto);
				CampPuerto.setColumns(10);
				
				//Definimos la JLabel EtiqBD
				JLabel EtiqBD = new JLabel("Base de Datos:");
				EtiqBD.setBounds(10, 137, 158, 14);
				//A�adimos la JLabel al JPanel
				contentPane.add(EtiqBD);
				
				//Definimos el JComboBox ComboBoxBD
				comboBoxBD = new JComboBox();
				comboBoxBD.setBounds(170, 133, 196, 23);
				comboBoxBD.setModel(new DefaultComboBoxModel(new String[] {"Seleccione una opci\u00F3n"}));
				//A�adimos el JComboBox al JPanel
				contentPane.add(comboBoxBD);
				
				//Definimos el Jbutton BottonVerBD
				JButton BottonVerBD = new JButton("Mostrar Bases de Datos");
				BottonVerBD.setBounds(170, 167, 196, 23);
				//A�adimos la JButton al JPanel
				contentPane.add(BottonVerBD);
				
				//Definimos la JLabel EtiqPuerto
				JLabel EtiqPuerto = new JLabel("Puerto:");
				EtiqPuerto.setBounds(10, 110, 150, 14);
				//A�adimos la JLabel al JPanel
				contentPane.add(EtiqPuerto);
				
				//Definimos el JPasswordField CampContra
				CampContra = new JPasswordField();
				CampContra.setBounds(170, 47, 196, 23);
				//A�adimos el JPasswordField al JPanel
				contentPane.add(CampContra);
			
			
			
				//Metodo ActionListener para el BottonConec
				ActionListener BottonConecListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
	
						
						
					//Definimos las variables para usar en el metodo del listener
					String Servidor=CampServ.getText();
					String Usuario=CampUsu.getText();
					String Password= new String(CampContra.getPassword());
					String Puerto=CampPuerto.getText();
					
					//Asignamos el valor del comboboxBD a la variable BD
					String BD=comboBoxBD.getSelectedItem().toString();
			
					//Realizamos la conexion con el sl Server
					ConexionBaseDatos(Servidor,Puerto,Usuario,Password);
		
					//Creamos un Objeto de la Clase TablaDeDatos
					TablaDeDatos tb=new TablaDeDatos();
					
					//Pasamos los valores de los atributos de ConeionBD a TablaDeDatos
					tb.setConBD(conBD);
					tb.setServidor(Servidor);
					tb.setUsuario(Usuario);
					tb.setPassword(Password);
					tb.setTb(tb);
					tb.setBD(BD);
					tb.setPuerto(Puerto);
					
					//Definimos las variables para usar en los Metodos
					JComboBox TablaBD=tb.getcomboBoxTablaBD();;
					
					//Rellenamos el ComboBoxTablasBD
					tb.RellenarComboBoxTablaBD(Servidor,Puerto,Usuario,Password,TablaBD,conBD,BD);
					//Mostrar el JFrame de la tabla
					tb.setVisible(true);
				
																	
					
					}
				};
				
				//Metodo ActionListener para el BottonLimp
						ActionListener BottonLimpListener = new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
							//Poner vacios los campos
							CampUsu.setText("");
							CampContra.setText("");
							CampPuerto.setText("");
							CampServ.setText("");
							
									
						
							}
							
						};
						
						//Metodo ActionListener para el  BottonVerBD
						ActionListener BottonVerBDListener = new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
							//Definimos las variables para usar en el metodo del listener
							String Servidor=CampServ.getText();
							String Usuario=CampUsu.getText();
							String Password=new String(CampContra.getPassword());
							String Puerto=CampPuerto.getText();
							

						 RellenarComboBoxBD(Servidor,Puerto,Usuario,Password,comboBoxBD,conBD);
							}
							
						};
						
						//A�adimos el ActionListener al BottonVerBD para que ejecute el codigo del ActionListener BottonVerBDListener
						BottonVerBD.addActionListener(BottonVerBDListener);
						
						//A�adimos el ActionListener al BottonConec para que ejecute el codigo del ActionListener BottonConecListener
						BottonConec.addActionListener(BottonConecListener);
						
						//A�adimos el ActionListener al BottonLimp para que ejecute el codigo del ActionListener BottonlimpListener
						BottonLimp.addActionListener(BottonLimpListener);
						
			
				
			}
	
	

			//Metodo ConexionBaseDatos
			public Connection ConexionBaseDatos(String Servidor,String Puerto,String Usuario,String Password) {
				//Definimos las variables para la conexion con la Base de Datos
			    //final String BD=comboBoxBases.getSelectedItem().toString();
			    final String url="jdbc:mysql://"+Servidor+":"+Puerto;
				final String Driver="com.mysql.cj.jdbc.Driver";
				ResultSet rs=null;
			    Connection conexion=null;
			 
			  
			    
			   			try {
							//Realizar la definicion del driver conector de java
							Class.forName(Driver);
							
						} catch (ClassNotFoundException e1) {
							//Definir mensaje de error y mostrarlo por pantalla
							System.out.println("No se cargo correctamente el Driver del conector del Mysql Server para java");
								
							}	
							
							

			   			try  {
						        	//Realizar Conexion a la Base de datos
						        	conexion = DriverManager.getConnection(url,Usuario,Password);

						        	
				
						        	//Comprobamos si se realizo la conexion mandando por pantalla un mensaje si se realizo la conexion
							        if(conexion!=null) {
							        System.out.println("Conexion Realizada con el Servidor de Base de Datos");
						        	}
						       
						        }
						        // Handle any errors that may have occurred.
			   			catch (SQLException e2) {
						        	//Definir mensaje de error y mostrarlo por pantalla
									System.out.println("Algun Dato de la Conexion es Erroneo con lo cual no se puede Conectar con el Servidor");
						 }		
			
						
						 
						//Sacamos fuera del metodo el resultado de st
						return conexion;
							
	
			}
			
			//Metodo ConexionBaseDatosConBD
			public Connection ConexionBaseDatosConBD(String Servidor,String Puerto,String Usuario,String Password,String BD) {
				final String Driver="com.mysql.cj.jdbc.Driver";
				final String url="jdbc:mysql://"+Servidor+":"+Puerto+"/"+BD;
				ResultSet rs=null;
			    Connection conexion=null;
			 
			  
			    
			   			try {
							//Realizar la definicion del driver conector de java
							Class.forName(Driver);
							
						} catch (ClassNotFoundException e1) {
							//Definir mensaje de error y mostrarlo por pantalla
							System.out.println("No se cargo correctamente el Driver del conector del Mysql Server para java");
								
							}	
							
							

			   			try  {
						        	//Realizar Conexion a la Base de datos
						        	conexion = DriverManager.getConnection(url,Usuario,Password);

						        	
				
						        	//Comprobamos si se realizo la conexion mandando por pantalla un mensaje si se realizo la conexion
							        if(conexion!=null) {
							        System.out.println("Conexion Realizada");
						        	}
						       
						        }
						        // Handle any errors that may have occurred.
			   			catch (SQLException e2) {
						        	//Definir mensaje de error y mostrarlo por pantalla
									System.out.println("Algun Dato de la Conexion es Erroneo con lo cual no se puede Conectar con el Servidor");
						 }		
			
						
						 
						//Sacamos fuera del metodo el resultado de st
						return conexion;
							
	
			}
			
			//Metodo RellenarComboBoxBD
			public void RellenarComboBoxBD(String Servidor,String Puerto,String Usuario,String Password,JComboBox comboBoxBD,ConexionBD conBD) {
				//Definimos las variables para usar en la consulta sql
				Connection con=null;
				 Statement st=null;
				 ResultSet rs=null;
				  
				//LLamamos al metodo conexionBaseDatos del objeto conBD1 para crear la conexion con la Base de Datos
				con=conBD.ConexionBaseDatos(Servidor,Puerto, Usuario, Password);
				
				try {
				
				//Creamos el Statement de la conexion para realizar la consulta
				st = con.createStatement();
				
				//Realizar la consulta a la Bases de Datos
				rs = st.executeQuery("show DATABASES;");
				
						while (rs.next()) {
							//Guardamos las bases de datos en el combobox
							comboBoxBD.addItem(rs.getString(1));
						}
					
						System.out.println("ComboBoxBD rellenado con las Bases de Datos Actuales");
				
					} catch (SQLException e3) {
					System.out.println("Algun Dato de la consulta es erroneo, no se puede rellenar el ComboBoxBD");
					}
					
			}
			
			
			//Getters and Setters

			public JTextField getCampUsu() {
				return CampUsu;
			}

			public void setCampUsu(JTextField campUsu) {
				CampUsu = campUsu;
			}

			public JTextField getCampCont() {
				return CampPuerto;
			}

			public void setCampCont(JTextField campCont) {
				CampPuerto = campCont;
			}

			public JTextField getCampIP() {
				return CampServ;
			}


			public static ConexionBD getConBD() {
				return conBD;
			}

			public static void setConBD(ConexionBD conBD) {
				ConexionBD.conBD = conBD;
			}

			public JTextField getCampServ() {
				return CampServ;
			}

			public void setCampServ(JTextField campServ) {
				CampServ = campServ;
			}

			public JComboBox getComboBoxBD() {
				return comboBoxBD;
			}

			public void setComboBoxBD(JComboBox comboBoxBD) {
				this.comboBoxBD = comboBoxBD;
			}

			public JTextField getCampPuerto() {
				return CampPuerto;
			}

			public void setCampPuerto(JTextField campPuerto) {
				CampPuerto = campPuerto;
			}

			public JPasswordField getCampContra() {
				return CampContra;
			}

			public void setCampContra(JPasswordField campContra) {
				CampContra = campContra;
			}
			
}
