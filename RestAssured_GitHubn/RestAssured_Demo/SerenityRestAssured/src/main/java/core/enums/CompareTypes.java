package core.enums;

public enum CompareTypes {
    EQUALS("EQUALS"),
    NOT_EQUAL("NOT_EQUAL"),
    CONTAINS("CONTAINS"),
    NOT_CONTAIN("NOT_CONTAIN"),
    EQUALS_IGNORE_CASE("EQUALS_IGNORE_CASE"),
    NOT_EQUAL_IGNORE_CASE("NOT_EQUAL_IGNORE_CASE"),
    MATCHES("MATCHES"),
    NOT_MATCH("NOT_MATCH");
    private final String text;

    /**
     * @param text
     */
    CompareTypes(final String text) {
        this.text = text;
    }


    public String getValue() {
        return text;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
