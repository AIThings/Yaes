/*
 * Created on Nov 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.world.physical.location;

import yaes.world.physical.path.Path;

public interface IPathRecorder {
    Path getRecordedPath();

    void startPathRecording();

    void stopPathRecording();
}
