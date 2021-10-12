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
package com.javadeepcafe.services;

import com.javadeepcafe.entities.Sesion;
import com.javadeepcafe.entities.UsuarioLogin;
import com.javadeepcafe.repositories.SesionRepository;
import com.javadeepcafe.repositories.UsuarioLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Delcio Amarillo
 */
@Service
public class SesionService {
    
    private Sesion sesionActiva;
    
    @Autowired
    private SesionRepository sesionRepository;
    
    @Autowired
    private UsuarioLoginRepository usuarioLoginRepository;
    
    @Transactional
    public void crearNuevaSesion(String userName) {
        if (userName == null || userName.isEmpty()) {
             throw new IllegalArgumentException("El parámetro [userName] no puede ser null ni vacío!");
        }
        UsuarioLogin usuario = usuarioLoginRepository.getUsuario(userName);
        sesionActiva = sesionRepository.crearSesion(usuario);
    }
    
    @Transactional
    public void finalizarSesionActiva() {
        if (sesionActiva != null) {
            sesionRepository.finalizarSesion(sesionActiva);
        }
    }

    public Sesion getSesionActiva() {
        return sesionActiva;
    }
}
