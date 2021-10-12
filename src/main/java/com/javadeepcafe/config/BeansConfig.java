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
package com.javadeepcafe.config;

import com.javadeepcafe.view.LoginView;
import com.javadeepcafe.view.MainView;
import com.javadeepcafe.repositories.SesionRepository;
import com.javadeepcafe.repositories.UsuarioLoginRepository;
import com.javadeepcafe.services.LoginServiceImp;
import com.javadeepcafe.services.SesionService;
import com.javadeepcafe.services.UserNameStoreService;
import org.jdesktop.swingx.auth.LoginService;
import org.jdesktop.swingx.auth.UserNameStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Delcio Amarillo
 */
@Configuration
@EnableTransactionManagement
public class BeansConfig {
    
    @Bean
    public LocalEntityManagerFactoryBean createEntityManagerFactory() {
        LocalEntityManagerFactoryBean factory = 
                new LocalEntityManagerFactoryBean();
        factory.setPersistenceUnitName("DemoLoginPU");
        return factory;
    }
    
    @Bean
    public PlatformTransactionManager createPlatformTransactionManager() {
        return new JpaTransactionManager(createEntityManagerFactory().getObject());
    }
    
    @Bean
    public SesionRepository createSesionRepository() {
        return new SesionRepository();
    }
    
    @Bean
    public UsuarioLoginRepository createUsuarioLoginRepository() {
        return new UsuarioLoginRepository();
    }
    
    @Bean
    public LoginService createLoginService() {
        return new LoginServiceImp();
    }
    
    @Bean
    public SesionService createSesionService() {
        return new SesionService();
    }
    
    @Bean
    public UserNameStore createUserNameStore() {
        return new UserNameStoreService();
    }
    
    @Bean
    public LoginView createLoginView() {
        return new LoginView();
    }
    
    @Bean
    public MainView createMainView() {
        return new MainView();
    }
}