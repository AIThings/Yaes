package yaes.ui.format;

import yaes.ui.text.TextUiHelper;

/**
 * Helps create an indented, headlined toString format
 * 
 * @author Ladislau Boloni
 * 
 */
public class Formatter {

    protected StringBuffer buffer        = new StringBuffer();
    private int          currentIndent = 0;
    private int          indent        = 4;
    private static String       fmtDouble     = "%.3f";

    /**
     * Adds an object at the current level of indent
     * 
     * @param object
     */
    public void add(Object object) {
        String objectString = object.toString();
        String temp = TextUiHelper.indent(currentIndent, objectString);
        buffer.append(temp);
        if (!objectString.endsWith("\n")) {
            buffer.append("\n");
        }
    }

    /**
     * Adds the next object in an indented form
     */
    public void addIndented(Object object) {
        currentIndent = currentIndent + indent;
        add(object);
        currentIndent = currentIndent - indent;
    }

    /**
     * Adds the text shifted, with a margin note inserted at the beginning
     * 
     * Fixme: the 4,
     * 
     * @param prefix
     * @param text
     */
    public void addWithMarginNote(String prefix, String text) {
        StringBuffer buf = new StringBuffer();
        buf.append(prefix);
        text = TextUiHelper.indent(8, text);
        buf.append(text.substring(prefix.length()));
        add(buf);
    }

    /**
     * De-indents the following text
     */
    public void deindent() {
        currentIndent = currentIndent - indent;
        if (currentIndent < 0) {
            throw new Error("Current indent becomes negative here - deindent without indent!!!");
        }
    }

    /**
     * Formats a double
     * 
     * @param d
     * @return
     */
    public static String fmt(double value) {
        return String.format(fmtDouble, value);
    }

    /**
     * Indents the following text
     */
    public void indent() {
        currentIndent = currentIndent + indent;
    }
    
    public void is(String name, Object object) {
        String value;
        if (object instanceof Double) {
            value = fmt((Double) object);
        } else {
            value = object.toString();
        }
        add(name + " = " + value);
    }

    /**
     * Creates a labeled separator (indented with the current value)
     * 
     * @param text
     */
    public void separator(String text) {
        String sep = TextUiHelper.createLabeledSeparator("-" + text);
        add(sep);
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

    public void addNonIndented(Object object) {
        String objectString = object.toString();
        buffer.append(object);
        if (!objectString.endsWith("\n")) {
            buffer.append("\n");
        }
    }

    
}
