package idv.terry.lotto.lib;

import java.util.ArrayList;
import java.util.Vector;

public class NumberIChi {
//	public Vector<Integer> mNumberList = new Vector<Integer>(5);
	public ArrayList<Integer> mNumberList = new ArrayList<Integer>(5);
	
	public NumberIChi(int a1, int a2, int a3, int a4, int a5){
		mNumberList.add(a1);
		mNumberList.add(a2);
		mNumberList.add(a3);
		mNumberList.add(a4);
		mNumberList.add(a5);
	}

	@Override
	public String toString() {
		return mNumberList.get(0) + ", " + mNumberList.get(1)+ ", "+mNumberList.get(2)+ ", "+mNumberList.get(3)+ ", "+ mNumberList.get(4);
	}

	public ArrayList<Integer> getContent(){
		return mNumberList;
	}
	
}
