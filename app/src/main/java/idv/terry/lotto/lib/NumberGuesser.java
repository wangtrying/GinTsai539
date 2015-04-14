package idv.terry.lotto.lib;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class NumberGuesser {

	private static NumberGuesser mInstance = new NumberGuesser();
	private ConcurrentHashMap<Integer, Integer> mSlots = new ConcurrentHashMap<Integer, Integer>();
	private ConcurrentHashMap<Integer, Integer> mBaseMap = new ConcurrentHashMap<Integer, Integer>();
	private Random mRandom = new Random(System.currentTimeMillis());
	private int mTotalSlot;
	
	private NumberGuesser(){}
	
	public static NumberGuesser getInstance() {
		return mInstance;
	}

	public NumberIChi guess(int aBao, ConcurrentHashMap<Integer, Integer> aAlreadyMap) {
		mBaseMap = aAlreadyMap;
		return generateMyGuess(aBao);
	}

	private NumberIChi generateMyGuess(int aBao) {
		Vector<Integer> myGuess = new Vector<Integer>();
		int genInt = 0;
		for (int i =1; i<=aBao; i++){
			prepareSlotsMap();
			int startPoint = mRandom.nextInt(39)+1;
			genInt = Math.abs(mRandom.nextInt()*10);
			genInt = (genInt%mTotalSlot)+1;
			while(genInt>0){
				genInt = genInt - mSlots.get(startPoint);
				if (genInt<=0){
					mSlots.put(startPoint, 0);
					if (!myGuess.contains(startPoint)){
						myGuess.add(startPoint);
					}else{
						i--;
						break;
					}
					
				}
				startPoint++;
				if (startPoint >39){
					startPoint = 1;
				}
			}
			
		}
		
		NumberIChi number = new NumberIChi(myGuess.get(1), myGuess.get(2), myGuess.get(3), myGuess.get(4), myGuess.get(0));
		
		return number;
	}

	private void prepareSlotsMap() {
		mTotalSlot = 0;
		int max = -1;
		for(int i = 1; i<= 39; i++){
			if (max<mBaseMap.get(i)){
				max = mBaseMap.get(i);
			}
		}
		max++;
		for(int i = 1; i<= 39; i++){
			mTotalSlot = mTotalSlot + max - mBaseMap.get(i);
			mSlots.put(i, max - mBaseMap.get(i));
		}
	}

	public NumberIChi randomGuess(int aBao, ConcurrentHashMap<Integer, Integer> mIanSwanAlready) {
		Vector<Integer> myGuess = new Vector<Integer>();
		int genInt = 0;
		boolean isDone = false;
		do{
			genInt = mRandom.nextInt(39)+1;
			if (myGuess.contains(genInt) == false){
				myGuess.add(genInt);
			}
			if(myGuess.size() == 5){
				isDone = true;
			}
		
		}while(!isDone);
		return new NumberIChi(myGuess.get(1), myGuess.get(2), myGuess.get(3), myGuess.get(4), myGuess.get(0));
		
	}

}
