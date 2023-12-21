package org.eclipse.daanse.db.jdbc.util.impl;

import java.util.Optional;

public class SqlType {

    private final Type type;
    private final Optional<String> length;

    public SqlType(Type type, Optional<String> length) {
        this.type = type;
        this.length = length;
    }

    public Type getType() {
        return type;
    }

    public Optional<String> getLength() {
        return length;
    }
}
