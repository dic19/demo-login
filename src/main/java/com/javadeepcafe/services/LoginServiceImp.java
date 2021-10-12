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

package com.javadeepcafe.services;

import org.jdesktop.swingx.auth.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import com.javadeepcafe.entities.UsuarioLogin;
import com.javadeepcafe.repositories.UsuarioLoginRepository;
import org.springframework.stereotype.Service;

/**
 * @author Delcio Amarillo
 */
@Service
public class LoginServiceImp extends LoginService {
    
    @Autowired
    private UsuarioLoginRepository usuarioLoginRepository;

    @Override
    public boolean authenticate(String userName, char[] password, String server) throws Exception {
        UsuarioLogin usuario = usuarioLoginRepository.getUsuario(userName);
        return usuario != null 
            && String.valueOf(password).equals(usuario.getPassword()) 
            && !usuario.getBloqueado();
    }
}
