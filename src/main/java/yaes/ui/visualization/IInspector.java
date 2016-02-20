package yaes.ui.visualization;

/**
 * An interface describing a type of objects which represent the visualization
 * of an object in the text form. Normally used in the right-hand panel of
 * VisualMap
 * 
 * @author Lotzi Boloni
 * 
 */
public interface IInspector {
    public String getLineDescriptor(Object o);

    public String getLongDescriptor(Object o, int detailLevel);
}
