/*
 * Copyright (C) 2014 Delcio Amarillo
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
package com.javadeepcafe.main;

import com.javadeepcafe.config.BeansConfig;
import com.javadeepcafe.view.MainView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Delcio Amarillo
 */
public class DemoMainClass {

     public static void main(String[] args) {
         
         final ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);
         
         SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                 try {
                     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                 } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                     Logger.getLogger(DemoMainClass.class.getName()).log(Level.SEVERE, null, ex);
                 } finally {
                     MainView mainView = context.getBean(MainView.class);
                     mainView.createAndShowGUI();
                 }
             }
         });
    }
}
