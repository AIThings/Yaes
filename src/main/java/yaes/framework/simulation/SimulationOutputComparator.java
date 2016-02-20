package yaes.framework.simulation;

import java.util.Comparator;

public class SimulationOutputComparator implements Comparator<SimulationOutput> {
    RandomVariable.Probe probe;
    private final String value;

    public SimulationOutputComparator(String value, RandomVariable.Probe probe) {
        this.value = value;
        this.probe = probe;
    }

    @Override
    public int compare(SimulationOutput arg0, SimulationOutput arg1) {
        final Double arg0v = arg0.getRandomVar(value).getValue(probe);
        final Double arg1v = arg1.getRandomVar(value).getValue(probe);
        return arg0v.compareTo(arg1v);
    }
}
