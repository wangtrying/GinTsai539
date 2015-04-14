package idv.terry.lotto.lib;

import java.util.Vector;

public class NumberMatcher {

	public static int match(NumberIChi myGuess, NumberIChi mLastChi) {
		int hit = 0;
		for(int i = 0; i<5; i++){
			int a = myGuess.mNumberList.get(i);
			if (mLastChi.mNumberList.contains(a)){
				hit++;
			}
		}
		
		return hit;
	}

}
