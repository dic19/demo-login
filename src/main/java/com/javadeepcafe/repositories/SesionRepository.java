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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import com.javadeepcafe.entities.Sesion;
import com.javadeepcafe.entities.UsuarioLogin;
import java.math.BigInteger;
import javax.persistence.ParameterMode;

/**
 * @author Delcio Amarillo
 */
@Repository
public class SesionRepository {
    
    @PersistenceContext(unitName = "DemoLoginPU")
    private EntityManager entityManager;
    
    public Sesion crearSesion(UsuarioLogin usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El parámetro [usuario] no puede ser null!");
        }
        Sesion sesion = new Sesion();
        sesion.setUsuario(usuario);
        entityManager.persist(sesion);
        entityManager.flush();
        entityManager.refresh(sesion);
        return sesion;
    }
    
    public void finalizarSesion(Sesion sesion) {
        if (sesion == null) {
            throw new IllegalArgumentException("El parámetro [sesion] no puede ser null");
        }
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("finalizarSesion");
        query.registerStoredProcedureParameter("sesion_id", BigInteger.class, ParameterMode.IN);
        query.setParameter("sesion_id", sesion.getId());
        query.execute();
    }
}
