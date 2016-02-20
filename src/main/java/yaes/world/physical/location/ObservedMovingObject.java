package yaes.world.physical.location;

import yaes.world.physical.path.Path;

/**
 * This is a moving object which is an external, moving object. It is not under
 * our control, so there is no agent attached to it.
 * 
 * @author lboloni
 * 
 */
@Deprecated
public class ObservedMovingObject extends AbstractNamedMoving implements
        IPathRecorder {

    /**
     * 
     */
    private static final long serialVersionUID = -2754369536311845765L;
    private boolean           isRecording      = false;
    private Path              recordedPath;

    public ObservedMovingObject(String name, Location location) {
        super(name, location);
    }

    @Override
    public Path getRecordedPath() {
        return recordedPath;
    }

    @Override
    public void startPathRecording() {
        isRecording = true;
        recordedPath = new Path();
    }

    @Override
    public void stopPathRecording() {
        isRecording = false;
        recordedPath = null;
    }

    @Override
    public String toString() {
        return "ObservedMovingObject [ " + getName() + " ] at " + getLocation();
    }

    public void update(double time, Location location) {
        if (isRecording) {
            recordedPath.addLocation(getLocation());
        }
        setLocation(location);
    }

}
