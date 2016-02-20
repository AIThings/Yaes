/*
   This file is part of the Yet Another Extensible Simulator
   Created on: Oct 31, 2009
 
   yaes.framework.simulation.parametersweep.dtaProbabilisticRun
 
   Copyright (c) 2008-2009 Ladislau Boloni

   This package is licensed under the LGPL version 2.
 */
package yaes.framework.simulation.parametersweep.actions;

import java.io.File;
import java.util.Random;

/**
 * 
 * <code>yaes.framework.simulation.parametersweep.dtaProbabilisticRun</code>
 * 
 * a run visitor which executes a run with a certain probability
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class dtaProbabilisticRun extends dtaRun {

    private Random random             = new Random();
    private double uniformProbability = 0;

    public dtaProbabilisticRun(double uniformProbability) {
        this.uniformProbability = uniformProbability;
    }

    @Override
    public void action(File inputFile) {
        if (random.nextDouble() <= uniformProbability) {
            super.action(inputFile);
        }
    }

    public double findProbability(File inputFile) {
        return uniformProbability;
    }
}
