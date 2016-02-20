/*
 * Created on Oct 12, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package yaes.framework.algorithm.geneticalgorithm;

import java.util.Comparator;

/**
 * @author Lotzi Boloni
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class FittnessFunctionComparator implements Comparator<IChromosome> {
    private final IFittnessFunction function;

    /**
     * @param function
     */
    public FittnessFunctionComparator(IFittnessFunction function) {
        super();
        this.function = function;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(IChromosome chrom0, IChromosome chrom1) {
        final Double d0 = new Double(function.fittness(chrom0));
        final Double d1 = new Double(function.fittness(chrom1));
        return d0.compareTo(d1);
    }
}
