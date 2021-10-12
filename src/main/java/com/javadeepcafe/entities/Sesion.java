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
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Delcio Amarillo
 */
@Entity
@Table(name = "Sesion")
public class Sesion implements Serializable {
    
    private static final long serialVersionUID = -1221718121500357998L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private BigInteger idSesion;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "inicio_sesion", insertable = false)
    private Date inicioSesion;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fin_sesion", insertable = false)
    private Date finSesion;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioLogin usuario;
    
    public Sesion() {}

    public BigInteger getId() {
        return idSesion;
    }

    public Date getInicioSesion() {
        return inicioSesion != null ? new Date(inicioSesion.getTime()) : null;
    }
    
    public Date getFinSesion() {
        return finSesion != null ? new Date(finSesion.getTime()) : null;
    }
    
    public UsuarioLogin getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioLogin usuario) {
        this.usuario = usuario;
    }
    
    /*
     * Overriden from Object
     */

    @Override
    public int hashCode() {
        return idSesion != null ? idSesion.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sesion)) {
            return false;
        }
        Sesion other = (Sesion) object;
        return !((this.idSesion == null && other.idSesion != null) 
            || (this.idSesion != null && !this.idSesion.equals(other.idSesion)));
    }

    @Override
    public String toString() {
        return String.format("%1$1s[%2$1s]", getClass().getName(), idSesion);
    }
}
