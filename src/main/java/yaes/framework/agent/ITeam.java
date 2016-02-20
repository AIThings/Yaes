/*
 * Created on Sep 25, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package yaes.framework.agent;

import java.util.Iterator;

/**
 * @author Lotzi Boloni
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public interface ITeam {
    Iterator<IAgent> getAgents();

    boolean join(IAgent agent); // returns false if not accepted

    void leave(IAgent agent);
}
