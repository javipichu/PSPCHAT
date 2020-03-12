
package chatservidor;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static jdk.nashorn.tools.ShellFunctions.input;

public class VentanaS extends javax.swing.JFrame {
    private final String DEFAULT_PORT="10101";
    private final Servidor servidor;
   
    public VentanaS() {
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String puerto=getPuerto();
        servidor=new Servidor(puerto, this);
        
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtClientes = new javax.swing.JTextArea();
        Boton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor");
        setBackground(new java.awt.Color(23, 15, 7));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Log del Servidor"));
        jScrollPane1.setForeground(new java.awt.Color(16, 38, 234));

        txtClientes.setEditable(false);
        txtClientes.setColumns(20);
        txtClientes.setForeground(new java.awt.Color(250, 9, 9));
        txtClientes.setRows(5);
        jScrollPane1.setViewportView(txtClientes);

        Boton.setText("Salir");
        Boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Boton, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Boton)
                .addGap(24, 24, 24))
        );

        jScrollPane1.getAccessibleContext().setAccessibleName("Panel del Servidor");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonActionPerformed
 
JOptionPane.showMessageDialog(null, "Has cerrado el servidor correctamente");
System.exit(0);
    }//GEN-LAST:event_BotonActionPerformed

   
    public static void main(String args[]) {


       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Boton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtClientes;
    // End of variables declaration//GEN-END:variables
    
    void agregarLog(String texto) {
        txtClientes.append(texto);
    }
    
    private String getPuerto() {
        String p=DEFAULT_PORT;
        JTextField puerto = new JTextField(20);
        puerto.setText(p);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(2, 1));
        myPanel.add(new JLabel("Puerto:"));
        myPanel.add(puerto);
        int result = JOptionPane.showConfirmDialog(null, myPanel, 
                 "Configuraciones de la comunicación", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
                p=puerto.getText();
        }else{
            System.exit(0);
        }
        return p;
    }
    void addServidorIniciado() {
        txtClientes.setText("Inicializando el servidor... [Ok].Todo correcto");
    }
   
}
