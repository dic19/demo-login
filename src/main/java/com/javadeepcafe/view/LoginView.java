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

package com.javadeepcafe.view;

import com.javadeepcafe.services.SesionService;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginAdapter;
import org.jdesktop.swingx.auth.LoginEvent;
import org.jdesktop.swingx.auth.LoginService;
import org.jdesktop.swingx.auth.UserNameStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Delcio Amarillo
 */
@Component
public class LoginView {
    
    private JXLoginPane loginPane;
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserNameStore userNameStore;
    
    @Autowired
    private SesionService sesionService;
    
    @Autowired
    private MainView mainView;
    
    public void createAndShowLoginDialog() {
        
        loginService.addLoginListener(new LoginAdapter() {
            @Override
            public void loginFailed(LoginEvent evt) {
                if (evt.getCause() != null) {
                    loginPane.setErrorMessage(evt.getCause().getMessage());
                }
            }
        });
        
        loginPane = new JXLoginPane(loginService);
        loginPane.setUserNameStore(userNameStore);
        loginPane.setSaveMode(JXLoginPane.SaveMode.USER_NAME);
        
        final JXLoginPane.JXLoginDialog dialog = new JXLoginPane.JXLoginDialog((JFrame)null, loginPane);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (dialog.getStatus() == JXLoginPane.Status.SUCCEEDED) {
                    String userName = loginPane.getUserName();
                    sesionService.crearNuevaSesion(userName);
                }
                return null;
            }

            @Override
            protected void done() {
                boolean succeeded = dialog.getStatus() == JXLoginPane.Status.SUCCEEDED;
                dialog.dispose();
                mainView.notifyLoginProcessResult(succeeded);
            }
        };
        worker.execute();
    }
}