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
package com.javadeepcafe.repositories;

import com.javadeepcafe.entities.UsuarioLogin;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 * @author Delcio Amarillo
 */
@Repository
public class UsuarioLoginRepository {
    
    private static final Logger LOGGER = 
        Logger.getLogger(UsuarioLoginRepository.class.getName());
    
    @PersistenceContext(unitName = "DemoLoginPU")
    private EntityManager entityManager;
    
    public UsuarioLogin getUsuario(String userName) {
        UsuarioLogin usuario = null;
        try {
            String jpql = "SELECT u FROM UsuarioLogin u WHERE u.nombreUsuario = :userName";
            TypedQuery<UsuarioLogin> query = entityManager.createQuery(jpql, UsuarioLogin.class);
            query.setParameter("userName", userName);
            usuario = query.getSingleResult();
        } catch (NoResultException ex) {
            LOGGER.log(Level.FINE, userName, ex);
        }
        return usuario;
    }
}
