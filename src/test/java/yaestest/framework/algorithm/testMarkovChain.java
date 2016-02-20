package yaestest.framework.algorithm;

import java.util.Random;

import org.junit.Test;

import yaes.framework.algorithm.MarkovChain;
import yaes.ui.text.TextUi;

public class testMarkovChain {

	enum MCEnum {One, Two, Three};
	
	
	@Test
	public void test() {
		MarkovChain<MCEnum> mc = new MarkovChain<>(MCEnum.One);
		mc.setTransitions(MCEnum.One, 0.2, 0.3, 0.5);
		mc.setTransitions(MCEnum.Two, 0.1, 0.8, 0.1);
		mc.setTransitions(MCEnum.Three, 0.1, 0.8, 0.1);
		TextUi.println(mc);
		Random r = new Random();
		for(int i = 0; i != 20; i++) {
			mc.generateNext(r);
			TextUi.println(mc.getCurrent());
		}
	}
	
	
}
