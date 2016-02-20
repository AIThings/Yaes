/*
 * Created on Oct 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.algorithm.geneticalgorithm;

import java.util.List;

/**
 * @author Linus Luotsinen
 * 
 */
public interface ISelectionMethod {
    public IChromosome SelectChromosomes(List<IChromosome> population);
}
