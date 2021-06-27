package com.caco3.lox.environment;

import com.caco3.lox.util.Nullable;

public interface Environment {
    Object getByName(String name);

    <T> T getByName(String name, Class<T> type);

    void put(String name, Object value);

    boolean hasVariable(String name);

    @Nullable
    Environment parent();

    void assign(String name, Object value);
}
