/*
 * Created on Oct 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.algorithm.geneticalgorithm;

import java.util.Set;

/**
 * @author Lotzi Boloni
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public interface ICrossover {
    boolean canMate(IChromosome chromosome1, IChromosome chromosome2);

    Set<IChromosome> multipleCrossover(IChromosome chromosome1,
            IChromosome chromosome2);

    IChromosome singleCrossover(IChromosome chromosome1, IChromosome chromosome2);
}
