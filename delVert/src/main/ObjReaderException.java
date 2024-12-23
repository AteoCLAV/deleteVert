package main;

public class ObjReaderException extends RuntimeException {
    private final int lineIndex;

    public ObjReaderException(String message, int lineIndex) {
        super(String.format("Error at line %d: %s", lineIndex, message));
        this.lineIndex = lineIndex;
    }

    public ObjReaderException(String message, int lineIndex, Throwable cause) {
        super(String.format("Error at line %d: %s", lineIndex, message), cause);
        this.lineIndex = lineIndex;
    }

    public int getLineIndex() {
        return lineIndex;
    }
}