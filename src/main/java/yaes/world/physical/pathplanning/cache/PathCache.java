/*
 * Created on Nov 29, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.world.physical.pathplanning.cache;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import yaes.framework.algorithm.search.IHeuristic;
import yaes.ui.text.TextUi;
import yaes.world.physical.map.GrayIsAccessible;
import yaes.world.physical.map.IMap;
import yaes.world.physical.map.WhiteIsAccessible;
import yaes.world.physical.path.AbstractPathCost;
import yaes.world.physical.path.PathLength;
import yaes.world.physical.path.PlannedPath;
import yaes.world.physical.pathplanning.AStarPP;
import yaes.world.physical.pathplanning.DistanceHeuristic;

/**
 * @author Lotzi Boloni
 * 
 */
public class PathCache {
    private static final String PATHCACHE_DAT = "pathcache.dat";
    private static PathCache    theCache;

    /**
     * This function attempts to look up the path in its hashtable, and if it
     * can not find it, computes it.
     * 
     * @param path
     * @param mapName
     * @param whiteAccessible
     */
    public static void findPath(PlannedPath path, IMap map,
            boolean whiteAccessible) {
        final PathPlanDescriptor ppd = new PathPlanDescriptor();
        ppd.setSource(path.getSource());
        ppd.setDestination(path.getDestination());
        ppd.setMapName(map.getMapName());
        ppd.setWhiteAccessible(whiteAccessible);
        final PlannedPath computed = PathCache.theCache.paths.get(ppd);
        if (computed != null) {
            TextUi.println("Path was found in cache...");
            path.getLocationList().addAll(computed.getLocationList());
            // TextUi.println("path:" + path);
            return;
        }
        TextUi.println("Path not found in cache... Computing...");
        final AbstractPathCost pathCost = new PathLength();
        final IHeuristic heuristic = new DistanceHeuristic(path
                .getDestination());
        final AStarPP hc = new AStarPP(path, map, pathCost, heuristic);
        hc.setReturnFirst(true);
        if (whiteAccessible) {
            hc.setAccessibilityChecker(new WhiteIsAccessible());
        } else {
            hc.setAccessibilityChecker(new GrayIsAccessible());
        }
        hc.planPath(path, map);
        PathCache.theCache.paths.put(ppd, path);
        PathCache.flush();
    }

    public static void flush() {
        try {
            PathCache.theCache.save(PathCache.PATHCACHE_DAT);
        } catch (final IOException iox) {
            iox.printStackTrace();
        }
    }

    public static void initPathCache() {
        try {
            PathCache.theCache = new PathCache();
            PathCache.theCache.load(PathCache.PATHCACHE_DAT);
        } catch (final IOException e) {
            TextUi.println("Could not open the path cache "
                    + PathCache.PATHCACHE_DAT);
            TextUi.println("" + e);
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private HashMap<PathPlanDescriptor, PlannedPath> paths = new HashMap<>();

    private void load(String fileName) throws IOException,
            ClassNotFoundException {
        final FileInputStream fis = new FileInputStream(fileName);
        final ObjectInputStream ois = new ObjectInputStream(fis);
        paths = (HashMap<PathPlanDescriptor, PlannedPath>) ois.readObject();
        TextUi.println("Loaded path cache: " + paths.entrySet().size()
                + " items");
        ois.close();
        fis.close();
    }

    private void save(String fileName) throws IOException {
        final FileOutputStream fos = new FileOutputStream(fileName);
        final ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(paths);
        oos.close();
        fos.close();
        TextUi.println("Saved path cache " + paths.entrySet().size() + " items");
    }
}
