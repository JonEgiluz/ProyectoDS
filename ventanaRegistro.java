import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.print.attribute.standard.PagesPerMinuteColor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet.ColorAttribute;
import java.awt.Color;

public class ventanaRegistro extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnAceptar,btnCancelar;
	private JPanel pSur,pCentro,pNorte;
	private JTextField txtNombre,txtContrasenya,txtEmail,txtAltura,txtFrecMax,txtFrecRep,txtPeso,txtfechaNacimiento;
	private JLabel lblNombre,lblContrasenya,lblEmail,lblAltura,lblNacimiento,lblPeso,lblFrecuenciaMax,lblFrecuenciaRep,lblPanel;
	private Date fechaNacimiento;
	

	
	public ventanaRegistro() {
		super();
		JFrame ventana = this;
		setBounds(50, 50, 1200, 600);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("VENTANA DE REGISTRO");
		btnAceptar = new JButton("ACEPTAR");
		btnCancelar = new JButton("CANCELAR");
		getContentPane().setLayout(new BorderLayout());
		
		pNorte = new JPanel();
		
		ImageIcon logo = new ImageIcon("C:\\Users\\jonzp\\eclipse-disenosoftware\\Diseno\\src\\fotos\\strava.png");
		lblPanel = new JLabel();
		lblPanel.setIcon(redimensionImgProd(logo, 300, 100));
		pNorte.add(lblPanel);
		getContentPane().add(pNorte, BorderLayout.NORTH);
		
		pSur = new JPanel();//El Layout por defecto del panel es FlowLayout
		pSur.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		//En un FlowLayout se añaden los componente del izquierda a derecha, empieza desde el centro
		pSur.add(btnAceptar);
		pSur.add(btnCancelar);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		
		pCentro = new JPanel(new GridLayout(8, 2));
		pCentro.setBorder(BorderFactory.createLineBorder(Color.black,2));
		lblNombre = new JLabel("Introduce tu nombre: ");
		txtNombre = new JTextField(20);
		lblContrasenya = new JLabel("Introduce la contraseña: ");
		txtContrasenya = new JTextField(20);
		lblEmail = new JLabel("Introduce el email: ");
		txtEmail = new JTextField(20);
		lblAltura = new JLabel("Introduce tu altura: ");
		txtAltura = new JTextField(5);
		lblFrecuenciaMax = new JLabel("Introduce tu freucencia maxima: ");
		txtFrecMax = new JTextField(5);
		lblNacimiento = new JLabel("Introduce tu fecha de nacimiento: ");
		txtfechaNacimiento = new JTextField(10);
		fechaNacimiento = new Date(System.currentTimeMillis());
		lblPeso = new JLabel("Introduce tu peso: ");
		txtPeso = new JTextField(5);
		lblFrecuenciaRep = new JLabel("Introduce tu frecuencia en reposo: ");
		txtFrecRep = new JTextField(5);
		
		pCentro.add(lblNombre);
		pCentro.add(txtNombre);
		pCentro.add(lblContrasenya);
		pCentro.add(txtContrasenya);
		pCentro.add(lblEmail);
		pCentro.add(txtEmail);
		pCentro.add(lblNacimiento);
		pCentro.add(txtfechaNacimiento);
		pCentro.add(lblAltura);
		pCentro.add(txtAltura);
		pCentro.add(lblPeso);
		pCentro.add(txtPeso);
		pCentro.add(lblFrecuenciaRep);
		pCentro.add(txtFrecRep);
		pCentro.add(lblFrecuenciaMax);
		pCentro.add(txtFrecMax);
		getContentPane().add(pCentro, BorderLayout.CENTER);
	
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nombre = txtNombre.getText();
				String contrasenya = txtContrasenya.getText();
				
				boolean existe = existeUsuario(nombre);
				if(existe==true)
					JOptionPane.showMessageDialog(null, "Nombre de usuario repetido", "ERROR",JOptionPane.ERROR_MESSAGE);
				else {
					try {
						//PrintWriter pw = new PrintWriter("USUARIOS.TXT");//Crea y abre el fichero de nombre USUARIOS.TXT
						PrintWriter pw = new PrintWriter(new FileWriter("USUARIOS.TXT", true));//Si el fichero no existe, lo crea y lo abre. Pero si el fichero existe lo abre para añadir información en él (NO BORRA)
						
						pw.println(nombre+" "+contrasenya);
						/*Las dos siguientes líneas guardan los cambios y cierran el fichero*/
						pw.flush();
						pw.close();
						JOptionPane.showMessageDialog(null, "El registro se ha realizado con éxito", "REGISTRO", JOptionPane.INFORMATION_MESSAGE);		
						ventana.dispose(); //Cerramos la ventana del registro
					} catch (FileNotFoundException e1) {
						e1.printStackTrace(); //Muestra un mensaje de error
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace(); //Muestra un mensaje de error
					} 
					
				}
				txtNombre.setText("");
				txtContrasenya.setText("");
				
				
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ventana.dispose();
			}
		});

		setVisible(true);
	}
	/**
	 * 
	 * @param nombre: Nombre del usuario a buscar en el fichero
	 * @return true si el nombre está repetido y false en caso contrario
	 */
	private boolean existeUsuario(String nombre) {
		boolean existe = false;
		//Comprobar si existe el fichero
		File f = new File("USUARIOS.TXT");
		if(f.exists()) { //Comprobar si existe el fichero
			//Intentar abrir el fichero de nombre USUARIOS.TXT, para leerlo
			
			try {
				BufferedReader br = new BufferedReader(new FileReader("USUARIOS.TXT"));
				String linea = br.readLine(); //linea= nombre contraseña
				while(linea!=null && existe==false) {
					//Buscar el espacio en blanco en la linea
					int pos = linea.indexOf(" ");
					String n = linea.substring(0, pos);//Guarda en n lo que hay en la cadena desde la posición 0 hasta la posición del espacio en blanco
					if(n.equals(nombre))
						existe=true;
					else
						linea = br.readLine();
				}
				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return existe;
	}
	public static ImageIcon redimensionImgProd(ImageIcon imageIcon, int ancho, int alto) {
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(ancho,alto ,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return imageIcon = new ImageIcon(newimg);  // transform it back
	}
	public static void main(String[] args) {
		ventanaRegistro vr = new ventanaRegistro();
		
	}
}




