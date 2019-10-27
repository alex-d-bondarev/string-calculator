package org.practice.app.parser;


public class UndefinedOperationParser {
    private final char value;

    public UndefinedOperationParser(char value){
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof UndefinedOperationParser)) {
            return false;
        }

        UndefinedOperationParser other = (UndefinedOperationParser) o;

        return value == other.getValue();
    }
}
