package yaes.framework.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.AbstractMap.SimpleEntry;

import yaes.ui.format.Formatter;
import yaes.ui.text.TextUi;
import yaes.ui.text.TextUiHelper;

public class MarkovChain<T extends Enum<T>> {

	private Map<SimpleEntry<T, T>, Double> trans = new HashMap<>();
	private T initial;
	private T current;
	public T getInitial() {
		return initial;
	}


	public T getCurrent() {
		return current;
	}

	private Class<T> enumType;
	
	public MarkovChain(T initial) {
		this.initial = initial;
		this.current = initial;
		enumType = (Class<T>) initial.getClass();
	    for(T t: enumType.getEnumConstants()) {
		    for(T t2: enumType.getEnumConstants()) {
		    	if (t.equals(t2)) {
		    		addTransition(t, t2, 1.0);
		    	} else {
		    		addTransition(t, t2, 0.0);
		    	}
		    }	    	
	    }
	}
	
	
	public void addTransition(T from, T to, double value) {
		SimpleEntry<T,T> entry = new SimpleEntry<>(from, to);
		trans.put(entry, value);
	}
	
	public double getTransition(T from, T to) {
		SimpleEntry<T,T> entry = new SimpleEntry<>(from, to);
		return trans.get(entry);
	}
	
	
	/**
	 * Generates the next observation in the markov chain
	 * @param r
	 * @param current
	 * @return
	 */
	public T generateNext(Random r) {
		double value = r.nextDouble();
		double accum = 0;
		for(T t: enumType.getEnumConstants()) {
			accum += getTransition(current, t);
			if (value < accum) {
				current = t;
				break;
			}
		}
		return current;
	}

	@Override
	public String toString() {
		Formatter fmt = new Formatter();
		fmt.add("Markov chain: " + initial.getClass().getName());
		fmt.indent();
		fmt.is("initial", initial);
		fmt.is("current", current);
		fmt.add("transitions");
		fmt.indent();
	    for(T t: enumType.getEnumConstants()) {
		    for(T t2: enumType.getEnumConstants()) {
		    	SimpleEntry<T,T> entry = new SimpleEntry<>(t, t2);
		    	double value = getTransition(t, t2);
		    	fmt.add(TextUiHelper.padTo(t, 10) + " --> " + TextUiHelper.padTo(t2, 10) + " = " + Formatter.fmt(value));
		    }	    	
	    }
		return fmt.toString();
	}


	/**
	 * Sets the transitions in one shot
	 * @param from
	 * @param values
	 */
	public void setTransitions(T from, double... values) {
		// verifies that the length is correct
		if (values.length != enumType.getEnumConstants().length) {
			throw new Error("Number of values does not match the parameters");
		}
		double sum = 0.0;
		for(int i = 0; i!= enumType.getEnumConstants().length; i++) {
			T to = enumType.getEnumConstants()[i];
			addTransition(from, to, values[i]);
			sum += values[i];
		}
		if (sum != 1.0) {
			TextUi.println("MarkovChain.setTransitions do not add up to 1.0, they are:" + sum);
		}
	}
	
	
	
	
}
