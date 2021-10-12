/*
 * Copyright (C) 2015 Delcio Amarillo.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.javadeepcafe.view;

import com.javadeepcafe.services.SesionService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Delcio Amarillo
 */
@Component
public class MainView {
    
    private JFrame frame;
    
    @Autowired
    private LoginView loginView;
    
    @Autowired
    private SesionService sesionService;
    
    public void createAndShowGUI() {
        frame = new JFrame("Bienvenido!");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainView.this.disposeGUI();
            }
        });
        loginView.createAndShowLoginDialog();
    }
    
    public void notifyLoginProcessResult(boolean succeeded) {
        if (succeeded) {
            frame.add(getMainPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }
    
    private JPanel getMainPanel() {
        
        BigInteger idSesion = sesionService.getSesionActiva().getId();
        Date inicioSesion = sesionService.getSesionActiva().getInicioSesion();
        BigInteger idUsuario = sesionService.getSesionActiva().getUsuario().getId();
        String nombreUsuario = sesionService.getSesionActiva().getUsuario().getNombreUsuario();
        Boolean usuarioBloqueado = sesionService.getSesionActiva().getUsuario().getBloqueado();
        
        JTextArea textArea = new JTextArea(15, 60);
        textArea.setEditable(false);
        
        textArea.append(
            String.format("Id sesión        : %s%n", idSesion)
        );
        textArea.append(
            String.format("Inicio sesión    : %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL%n", inicioSesion)
        );
        textArea.append(
            String.format("Id usuario       : %s%n", idUsuario)
        );
        textArea.append(
            String.format("Nombre de usuario: %s%n", nombreUsuario)
        );
        textArea.append(
            String.format("Usuario bloqueado: %s%n", usuarioBloqueado)
        );
        
        JButton cerrarSesionButton = new JButton(new AbstractAction("Finalizar sesión") {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView.this.disposeGUI();
            }
        });
        
        JLabel label = new JLabel();
        label.setText(
            String.format("Bienvenido de nuevo %s", nombreUsuario)
        );
        label.setFont(
            label.getFont().deriveFont(Font.ITALIC | Font.BOLD, 18)
        );
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        controlPanel.add(cerrarSesionButton);
        
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(label, BorderLayout.PAGE_START);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.PAGE_END);
        return panel;
    }
    
    private void disposeGUI() {
        frame.setVisible(false);
        
        JLabel label = new JLabel("Cerrando sesión, por favor espere...");
        label.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
        
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(label);
        
        final JDialog dialog = new JDialog(frame, "Finalizando aplicación");
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setModal(false);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
        
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                sesionService.finalizarSesionActiva();
                return null;
            }
            
            @Override
            protected void done() {
                dialog.dispose();
                frame.dispose();
            }
        };
        worker.execute();
    }
}