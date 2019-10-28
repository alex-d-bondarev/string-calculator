package org.practice.app.operation.raw;

public class SingleUndefinedOperation implements UndefinedOperation{
    private final char VALUE;

    public SingleUndefinedOperation(char value) {
        VALUE = value;
    }

    @Override
    public char getValue() {
        return VALUE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof SingleUndefinedOperation)) {
            return false;
        }

        SingleUndefinedOperation other = (SingleUndefinedOperation) o;

        return VALUE == other.getValue();
    }
}
