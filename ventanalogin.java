package programaderegistro;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import static javafx.application.Application.launch;
import javax.swing.JFrame;

public class ventanalogin extends Application {

    private static final String USERS_FILE = "users.txt";
    private Map<String, String> usuarios;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Sistema de Inicio de Sesión");

        // Cargar usuarios desde el archivo
        usuarios = new HashMap<>();
        cargarUsuarios();

        // Crear la interfaz de usuario
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label usuariono = new Label("Nombre de usuario:");
        grid.add(usuariono, 0, 0);

        TextField usuario = new TextField();
        grid.add(usuario, 1, 0);

        Label contraseñano = new Label("Contraseña:");
        grid.add(contraseñano, 0, 1);

        PasswordField contraseña = new PasswordField();
        grid.add(contraseña, 1, 1);

        Button BotonInicio = new Button("Iniciar sesión");
        grid.add(BotonInicio, 1, 2);

        Button botonRegistro = new Button("Registrarse");
        grid.add(botonRegistro, 1, 3);

        Label estatus = new Label();
        grid.add(estatus, 1, 4);

        BotonInicio.setOnAction(e -> {
            String username = usuario.getText();
            String password = contraseña.getText();
            if (autentificacion(username, password)) {
                estatus.setText("Inicio de sesión exitoso.");
                RegistroPersonal registroPersonal = new RegistroPersonal();
                registroPersonal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                registroPersonal.setSize(275, 180);
                registroPersonal.setVisible(true);
                primaryStage.close();
            } else {
                estatus.setText("Nombre de usuario o contraseña incorrectos.");
            }
        });

        botonRegistro.setOnAction(e -> {
            String username = usuario.getText();
            String password = contraseña.getText();
            inscribirse(username, password);
            estatus.setText("Usuario registrado con éxito.");
        });

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void cargarUsuarios() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                usuarios.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.err.println("Error cargando usuarios: " + e.getMessage());
        }
    }

    private void guardarUsuario(String username, String password) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE, true))) {
            writer.println(username + ":" + contraseñaHash(password));
        } catch (IOException e) {
            System.err.println("Error guardando usuario: " + e.getMessage());
        }
    }

    private String contraseñaHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error de hash de contraseña: " + e.getMessage());
            return null;
        }
    }

    private boolean autentificacion(String nombreUsuario, String contraseña) {
        String storedPassword = usuarios.get(nombreUsuario);
        return storedPassword != null && storedPassword.equals(contraseñaHash(contraseña));
    }

    void inscribirse(String username, String password) {
        if (!usuarios.containsKey(username)) {
            usuarios.put(username, contraseñaHash(password));
            guardarUsuario(username, password);
        }
    }
    public static void main(String[] args) {
       launch(args);
    }
}

