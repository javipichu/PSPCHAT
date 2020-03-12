package chatcliente;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Cliente extends Thread {
   
    private Socket socket;
    private ObjectOutputStream objectOutputStream; 
    private ObjectInputStream objectInputStream;      
    private final VentanaC ventana;    
    private String identificador;
    private boolean escuchando;
    private final String host;
    private final int puerto;
    
    
    Cliente(VentanaC ventana, String host, Integer puerto, String nombre) {
        this.ventana=ventana;        
        this.host=host;
        this.puerto=puerto;
        this.identificador=nombre;
        escuchando=true;
        this.start();
    }
    public void run(){
        try {
            socket=new Socket(host, puerto);
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            System.out.println("Conexion realizada ");
            this.enviarSolicitudConexion(identificador);
            this.escuchar();
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(ventana, "Error en la conexión,\n"
                                                 + "puede que haya ingresado una ip incorrecta\n"
                                                 + "o que el servidor no se esté ejecutando.\n"
                                                 + "Esta ventana se cerrará.");
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(ventana, "Conexión fallida\n"
                                                 + "puede que haya ingresado una ip o un puerto\n"
                                                 + "incorrecto, o que el servidor no este corriendo.\n"
                                                 + "Esta aplicación se cerrará.");
            System.exit(0);
        }

    }
    
    public void desconectar(){
        try {
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();  
            escuchando=false;
            System.out.println("desconectado");
        } catch (Exception e) {
            System.out.println("Error al cerrar los elementos de comunicación del cliente.");
        }
    }
    
    public void enviarMensaje(String cliente_receptor, String mensaje){
        LinkedList<String> lista=new LinkedList<>();
        lista.add("MENSAJE");
        lista.add(identificador);
        lista.add(cliente_receptor);
        lista.add(mensaje);
        try {
            objectOutputStream.writeObject(lista);
        } catch (IOException ex) {
            System.out.println("Error al enviar mensaje al servidor.");
        }
    }
     
    public void escuchar() {
        try {
            while (escuchando) {
                Object aux = objectInputStream.readObject();
                if (aux != null) {
                    if (aux instanceof LinkedList) {
                     
                        ejecutar((LinkedList<String>)aux);
                    } else {
                        System.out.println("Error desconocido");
                    }
                } else {
                    System.out.println("error al recibir el mensaje");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, "Perdido la conesion con el servidor\n"
                                                 + "chat finalizado.\n");
            System.exit(0);
            JOptionPane.showMessageDialog(ventana, "Gracias por usar la aplicación\n"
                                                 + "Hasta la proxima.\n");
        }
    }
    
    public void ejecutar(LinkedList<String> lista){
        String tipo=lista.get(0);
        switch (tipo) {
            case "CONEXION_ACEPTADA":
                identificador=lista.get(1);
                ventana.sesionIniciada(identificador);
                for(int i=2;i<lista.size();i++){
                    ventana.addContacto(lista.get(i));
                }
                break;
            case "NUEVO_USUARIO_CONECTADO":
                ventana.addContacto(lista.get(1));
                ventana.conexionUser(lista.get(1));
               
                break;
            case "USUARIO_DESCONECTADO":
               
                ventana.desconexionUser(lista.get(1));
                break;                
            case "MENSAJE":
                ventana.addMensaje(lista.get(1), lista.get(3));
                break;
            default:
                break;
        }
    }
 
    private void enviarSolicitudConexion(String identificador) {
        LinkedList<String> lista=new LinkedList<>();
       
        lista.add("SOLICITUD_CONEXION");
    
        lista.add(identificador);
        try {
            objectOutputStream.writeObject(lista);
        } catch (IOException ex) {
            System.out.println("Error de lectura y escritura al enviar mensaje al servidor.");
        }
    }
    
    void confirmarDesconexion() {
        LinkedList<String> lista=new LinkedList<>();
        lista.add("SOLICITUD_DESCONEXION");        
        lista.add(identificador);
        try {
            objectOutputStream.writeObject(lista);
        } catch (IOException ex) {
            System.out.println("Error de lectura y escritura al enviar mensaje al servidor.");
        }
    }
    
    String getIdentificador() {
        return identificador;
    }
}