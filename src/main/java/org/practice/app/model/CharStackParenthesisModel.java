package org.practice.app.model;

public class CharStackParenthesisModel {
    private char openingParenthesis;
    private char closingParenthesis;

    public char getOpeningParenthesis() {
        return openingParenthesis;
    }

    public char getClosingParenthesis() {
        return closingParenthesis;
    }

    private void setOpeningParenthesis(char openingParenthesis) {
        this.openingParenthesis = openingParenthesis;
    }

    private void setClosingParenthesis(char closingParenthesis) {
        this.closingParenthesis = closingParenthesis;
    }

    public static final class Builder {
        private char openingParenthesis;
        private char closingParenthesis;

        private Builder() {
        }

        public static Builder aModel() {
            return new Builder();
        }

        public Builder withOpeningParenthesis(char openingParenthesis) {
            this.openingParenthesis = openingParenthesis;
            return this;
        }

        public Builder withClosingParenthesis(char closingParenthesis) {
            this.closingParenthesis = closingParenthesis;
            return this;
        }

        public CharStackParenthesisModel build() {
            CharStackParenthesisModel charStackParenthesisModel = new CharStackParenthesisModel();
            charStackParenthesisModel.setOpeningParenthesis(openingParenthesis);
            charStackParenthesisModel.setClosingParenthesis(closingParenthesis);
            return charStackParenthesisModel;
        }
    }
}
