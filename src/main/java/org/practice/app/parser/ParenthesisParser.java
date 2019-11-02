package org.practice.app.parser;

import org.practice.app.operation.raw.UndefinedOperationGroup;

public class ParenthesisParser {
    private UndefinedOperationGroup undefinedOperationGroup;

    public ParenthesisParser(UndefinedOperationGroup undefinedOperationGroup) {
        this.undefinedOperationGroup = undefinedOperationGroup;
    }

    public ParenthesisParser parseParenthesis() {

        return this;
    }

    public UndefinedOperationGroup getUndefinedOperationGroup() {
        return undefinedOperationGroup;
    }
}
