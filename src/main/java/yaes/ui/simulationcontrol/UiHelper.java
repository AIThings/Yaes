package yaes.ui.simulationcontrol;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class UiHelper {
    public static final int BORDER               = 5;
    public static final int INTERBUTTON_DISTANCE = 10;
    public static final int INTERFIELD_DISTANCE  = 5;
    public static int       ROWDISTANCE          = 5;

    /**
     * Create a text area with a background color and sunken appearance.
     * 
     * @param width
     * @return
     */
    public static JTextField createDisplayField(int width) {
        final JTextField field = new JTextField(width);
        Dimension prefSize = field.getPreferredSize();
        field.setMinimumSize(new Dimension(prefSize.width, 0));
        field.setPreferredSize(new Dimension(prefSize.width, prefSize.height));
        field.setMaximumSize(new Dimension(prefSize.width, prefSize.height));
        field.setEditable(false);
        field.setBackground(Color.yellow);
        final Border border = BorderFactory.createLoweredBevelBorder();
        field.setBorder(border);
        return field;
    }

    /**
     * Creates a textfield appropriate for this component
     * 
     * @param size
     */
    public static JTextField createTextField(int size) {
        final JTextField txtField = new JTextField(size);
        txtField.setEnabled(false);
        txtField.setEditable(false);
        final Border border = BorderFactory.createLoweredBevelBorder();
        txtField.setBorder(border);
        return txtField;
    }
}
