package programaderegistro;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RegistroPersonal extends JFrame {

    private final JTextField nombre;//Text field with set size
    private final JTextField edad;
    private final JTextField peso;
    private final JTextField altura;
    private final JTextField imc;
    private final JTextField nombreno;
    private final JTextField edadno;
    private final JTextField pesono;
    private final JTextField alturano;
    private final JTextField imcno;

    private final JButton guardar;
    private final JButton crear;
    private final Container container;

    public RegistroPersonal() {
        
        super("Captura de informacion personal");
        setLayout(new FlowLayout());
        container = getContentPane();

        nombreno = new JTextField("Nombre:", 5);
        nombreno.setEditable(false);
        add(nombreno);
        nombre = new JTextField(10);
        add(nombre);

        edadno = new JTextField("Edad:", 4);
        edadno.setEditable(false);
        add(edadno);
        edad = new JTextField(3);
        add(edad);

        pesono = new JTextField("Peso:", 3);
        pesono.setEditable(false);
        add(pesono);
        peso = new JTextField(3);
        add(peso);

        alturano = new JTextField("Altura", 3);
        alturano.setEditable(false);
        add(alturano);
        altura = new JTextField(3);
        add(altura);

        imcno = new JTextField("IMC", 3);
        imcno.setEditable(false);
        add(imcno);
        imc = new JTextField(3);
        add(imc);

        guardar = new JButton("Guardar");
        add(guardar);
        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarEnArchivo();
            }
        });
        crear = new JButton("Nuevo registro");
        add(crear);
        crear.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent a){
            crearUnNuevoArchivo();
        }

            private void crearUnNuevoArchivo() {
            RegistroPersonal interfaz = new RegistroPersonal();
            interfaz.setSize(300, 200);
            interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            interfaz.setVisible(true);
            dispose();
            }
    });
    }
    private void guardarEnArchivo() {
        try {
            String rutaArchivo = "Informacion Personal.txt";

            FileWriter fileWriter = new FileWriter(rutaArchivo, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String txtnombre = nombre.getText();
            String txtedad = edad.getText();
            String txtpeso = peso.getText();
            String txtaltura = altura.getText();
            String txtimc = imc.getText();

            bufferedWriter.write(String.format("Nombre: %s.\nEdad: %s.\nPeso: %s.\nAltura: %s.\nIMC: %s%n%n", txtnombre, txtedad, txtpeso, txtaltura, txtimc));

            bufferedWriter.close();

            JOptionPane.showMessageDialog(null, "Información guardada en el archivo con éxito.");

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la información en el archivo.");
        }
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        Color color1 = new Color(255, 0, 0); // rojo
        Color color2 = new Color(0, 255, 255); // azul

        // Difuminado de morado a gris de arriba hacia abajo
        GradientPaint gp = new GradientPaint(0, 0, color2, 0, h, color1);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
    }
