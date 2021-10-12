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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;
import org.jdesktop.swingx.auth.UserNameStore;
import org.springframework.stereotype.Service;

/**
 * @author Delcio Amarillo
 */
@Service
public class UserNameStoreService extends UserNameStore {
    
    private List<String> userNames = new ArrayList<>();

    @Override
    public String[] getUserNames() {
        loadUserNames();
        return userNames.toArray(new String[userNames.size()]);
    }

    @Override
    public void setUserNames(String[] names) {
        userNames = Arrays.asList(names);
    }

    @Override
    public void loadUserNames() {
        Preferences prefs = Preferences.userRoot().node(getClass().getName());
        String users = prefs.get("JavaDeepCafeUsers", "");
        userNames = users.isEmpty() ? new ArrayList<String>() : Arrays.asList(users.split(";"));
    }

    @Override
    public void saveUserNames() {
        StringBuilder sb = new StringBuilder();
        for (String user : userNames) {
            sb.append(user).append(";");
        }
        Preferences prefs = Preferences.userRoot().node(getClass().getName());
        prefs.put("JavaDeepCafeUsers", sb.toString());
    }

    @Override
    public boolean containsUserName(String name) {
        return userNames.contains(name);
    }

    @Override
    public void addUserName(String userName) {
        if (!userNames.contains(userName)) {
            userNames.add(userName);
            saveUserNames();
        }
    }

    @Override
    public void removeUserName(String userName) {
        userNames.remove(userName);
        saveUserNames();
    }
}
