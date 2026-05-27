import React, { createContext, useEffect, useMemo, useState } from 'react';

const STORAGE_KEYS = {
  userId: 'userId',
  userName: 'userName',
  userEmail: 'userEmail',
  userRole: 'userRole',
};

const readStoredUser = () => {
  const userId = localStorage.getItem(STORAGE_KEYS.userId);

  if (!userId) {
    return null;
  }

  return {
    id_usuario: userId,
    nombre: localStorage.getItem(STORAGE_KEYS.userName) || '',
    email: localStorage.getItem(STORAGE_KEYS.userEmail) || '',
    rol: localStorage.getItem(STORAGE_KEYS.userRole) || '',
  };
};

const normalizeUser = (userData) => {
  if (!userData) {
    return null;
  }

  return {
    id_usuario: String(userData.id_usuario ?? userData.id ?? userData.userId ?? ''),
    nombre: userData.nombre ?? userData.name ?? userData.nombre_usuario ?? '',
    apellido: userData.apellido ?? '',
    email: userData.email ?? '',
    nombre_usuario: userData.nombre_usuario ?? '',
    rol: userData.rol ?? '',
  };
};

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => readStoredUser());

  useEffect(() => {
    if (user) {
      localStorage.setItem(STORAGE_KEYS.userId, String(user.id_usuario));
      localStorage.setItem(STORAGE_KEYS.userName, user.nombre || user.nombre_usuario || '');
      localStorage.setItem(STORAGE_KEYS.userEmail, user.email || '');
      localStorage.setItem(STORAGE_KEYS.userRole, user.rol || '');
    }
  }, [user]);

  const login = (userData) => {
    setUser(normalizeUser(userData));
  };

  const logout = () => {
    Object.values(STORAGE_KEYS).forEach((key) => localStorage.removeItem(key));
    setUser(null);
  };

  const value = useMemo(() => ({
    user,
    isAuthenticated: Boolean(user?.id_usuario),
    login,
    logout,
  }), [user]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export const useAuth = () => React.useContext(AuthContext);