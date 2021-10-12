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
package com.javadeepcafe.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Delcio Amarillo
 */
@Entity
@Table(name = "Usuario_Login")
public class UsuarioLogin implements Serializable {
    
    private static final long serialVersionUID = 2796360555765047008L;
    
    @Id 
    @Column(name = "id_usuario")
    private BigInteger idUsuario;
    
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "bloqueado")
    private Boolean bloqueado;
    
    public UsuarioLogin() {}

    public BigInteger getId() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }
    
    /*
     * Overriden from Object
     */

    @Override
    public int hashCode() {
        return idUsuario != null ? idUsuario.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UsuarioLogin)) {
            return false;
        }
        UsuarioLogin other = (UsuarioLogin) object;
        return !((this.idUsuario == null && other.idUsuario != null) 
                    || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario)));
    }

    @Override
    public String toString() {
        return String.format("%1$1s[%2$1s]", getClass().getName(), idUsuario);
    }
}
