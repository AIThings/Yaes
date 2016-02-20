package yaes.ui.visualization;

import yaes.ui.format.IDetailLevel;
import yaes.ui.format.ToStringDetailed;
import yaes.world.physical.location.INamed;

public class GenericInspector implements IInspector, IDetailLevel {

    /**
     * Gets a line descriptor based on what it has
     * 
     * @param o
     * @return
     */
    @Override
    public String getLineDescriptor(Object o) {
        if (o instanceof INamed) {
            return o.getClass().getSimpleName() + ": " + ((INamed) o).getName();
        }
        if (o instanceof ToStringDetailed) {
            return ((ToStringDetailed) o)
                    .toStringDetailed(IDetailLevel.MIN_DETAIL);
        }
        return "Class: " + o.getClass().toString();
    }

    /**
     * 
     * @param o
     * @param detailLevel
     * @return
     */
    @Override
    public String getLongDescriptor(Object o, int detailLevel) {
        if (o instanceof ToStringDetailed) {
            return ((ToStringDetailed) o).toStringDetailed(detailLevel);
        }
        return o.toString();
    }
}
