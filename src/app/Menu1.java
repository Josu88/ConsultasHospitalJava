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
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;

public class Menu1 extends JFrame{
	
	// Definimos los componentes del Jframe
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu1 frame = new Menu1();
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
	public Menu1() {
		//Creamos la ventana JFrame
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 714, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Definimos los componentes del Jframe
		
		//Definimos la etiqueta llamada EtiqMenu
		JLabel EtiqMenu = new JLabel("Conexion a Mysql Server");
		EtiqMenu.setForeground(Color.RED);
		EtiqMenu.setHorizontalAlignment(SwingConstants.CENTER);
		EtiqMenu.setFont(new Font("Tahoma", Font.BOLD, 20));
		EtiqMenu.setBounds(10, 11, 678, 46);
		//A�adimos la etiqueta al contentPane
		contentPane.add(EtiqMenu);
		
		//Definimos el Boton llamado BotonSalir
		JButton BotonSalir = new JButton("Salir");
		BotonSalir.setBounds(276, 227, 99, 23);
		//A�adimos el Boton al contentPane
		contentPane.add(BotonSalir);
		
		//Definimos el Boton llamado BotonConSqlServerConConsSen
		JButton BotonConSqlServerConConsSen = new JButton("Conexion a Mysql Server con consulta Sencilla");
		BotonConSqlServerConConsSen.setBounds(10, 69, 320, 46);
		//A�adimos el Boton al contentPane
		contentPane.add(BotonConSqlServerConConsSen);
		
		
		//Metodo ActionListener para el BottonConec
		ActionListener BotonConSqlServerConConsSenListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				//Creamos el objeto de la clase ConexionBD
				ConexionBD1 con= new ConexionBD1();
				//pruebaConexion con= new pruebaConexion();
				
				//Guardamos el objeto del tipo ConexionBD en la variable con
				con.setConBD(con);
				
				//Mostramos la ventana Jframe
				con.show();
			
			}
		};
			
		

		
				
				//Metodo ActionListener para el BottonSalir
				ActionListener BottonSalirListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
					
					//Hacemos que salga del programa
					System.exit(0);
					
					}
				};
		
		//Añadimos el ActionListener al BottonSalir para que ejecute el codigo del ActionListener BottonSalirListener
		BotonSalir.addActionListener(BottonSalirListener);
		
		//Añadimos el ActionListener al BotonConSqlServerConConsSen para que ejecute el codigo del ActionListenerBotonConSqlServerConConsSenListener
		BotonConSqlServerConConsSen.addActionListener( BotonConSqlServerConConsSenListener);
	}
}
