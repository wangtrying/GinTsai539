package idv.terry.lotto.lib;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Engine539 {

	private NumberGuesser mGuesser;

	private ConcurrentHashMap<Integer, Integer> mAllColdHotNumbers;
	private ConcurrentHashMap<Integer, Integer> mIanSwanAlready = new ConcurrentHashMap<>();
	private final int RUN_UPPER_BOUND = 100000;

	private static final int NUMBER_THREADS = 10;
	private NumberIChi mLastChi = null;

	private Callable<ConcurrentHashMap<Integer, Integer>> mColdHotCaller;

	private Callable<NumberIChi> mLastChiCaller;

	private ExecutorService mExecutor;

	private Vector<IGuessResultListener> mListeners = new Vector<IGuessResultListener>();

	public void start() throws InterruptedException, ExecutionException {
		if (mExecutor == null) {
			mExecutor = Executors.newFixedThreadPool(NUMBER_THREADS);
		}
		prepareCallables();
		startGetColdHotNumbers();
		startGetLastChiNumber();
	}

	private void startGetLastChiNumber() throws InterruptedException, ExecutionException {
		Future<NumberIChi> lastChiGetter = mExecutor.submit(mLastChiCaller);
		mLastChi = lastChiGetter.get();
	}

	private void prepareCallables() {
		mColdHotCaller = new Callable<ConcurrentHashMap<Integer, Integer>>() {
			@Override
			public ConcurrentHashMap<Integer, Integer> call() throws Exception {
				return WebParser.getInstance().get539();
			}
		};
		mLastChiCaller = new Callable<NumberIChi>() {
			@Override
			public NumberIChi call() throws Exception {
				return WebParser.getInstance().getLast539();
			}
		};
	}

	private void startGetColdHotNumbers() throws InterruptedException, ExecutionException {
		Future<ConcurrentHashMap<Integer, Integer>> coldHotGetter = mExecutor.submit(mColdHotCaller);
		mAllColdHotNumbers = coldHotGetter.get();
	}

	public void validateTerryMethodOverRandomMethod() throws Exception {

		prepareIanSwan();
		Future<String> terryResultGetter = doTerry();
		Future<String> randomResultGetter = doRandom();

		String resultString = terryResultGetter.get();
		notifyListeners(resultString);

		String resultStringR = randomResultGetter.get();
		notifyListeners(resultStringR);

	}

	private Future<String> doRandom() throws Exception {
		Callable<String> randomMethodCaller = new Callable<String>() {

			@Override
			public String call() throws Exception {
				return useRandomMethod();
			}

			private String useRandomMethod() {
				NumberIChi myGuess = null;
				int hit = 5;
				int round = 0;
				int gotIt3 = 0, gotIt4 = 0, gotIt5 = 0;
				do {
					round++;
					myGuess = NumberGuesser.getInstance().randomGuess();
					hit = NumberMatcher.match(myGuess, mLastChi);
					if (hit >= 3) {
						if (hit == 3) {
							gotIt3++;
						}
						if (hit == 4) {
							gotIt4++;
						}
						if (hit == 5) {
							gotIt5++;
						}
						hit = 0;
					}
				} while (round <= RUN_UPPER_BOUND);
				return "  random, 3 hits:" + gotIt3 + ", 4 hits:" + gotIt4 + ", 5 hits:" + gotIt5;

			}
		};
		if (mExecutor == null) {
			throw new Exception("Engine must start() first");
		}
		return mExecutor.submit(randomMethodCaller);
		
	}

	private void prepareIanSwan() {

		mIanSwanAlready = mAllColdHotNumbers;
		ArrayList<Integer> lastChi = mLastChi.getContent();
		for (int i = 0; i < 5; i++) {
			int a1 = mIanSwanAlready.get(lastChi.get(i)) - 1;
			mIanSwanAlready.put(lastChi.get(i), a1);
		}
	}

	private Future<String> doTerry() throws Exception {
		Callable<String> terryMethodCaller = new Callable<String>() {

			@Override
			public String call() throws Exception {
				return useTerryMethod();
			}

			private String useTerryMethod() {
				NumberIChi myGuess;
				int hit;
				int gotIt3 = 0, gotIt4 = 0, gotIt5 = 0;
				int round = 0;
				do {
					round++;
					myGuess = NumberGuesser.getInstance().guess(5,
							mIanSwanAlready);
					hit = NumberMatcher.match(myGuess, mLastChi);
					if (hit >= 3) {
						if (hit == 3) {
							gotIt3++;
						}
						if (hit == 4) {
							gotIt4++;
						}
						if (hit == 5) {
							gotIt5++;
						}
						hit = 0;
					}
				} while (round <= RUN_UPPER_BOUND);
				return "myMethod, 3 hits:" + gotIt3 + ", 4 hits:" + gotIt4
						+ ", 5 hits:" + gotIt5;
			}
		};
		if (mExecutor == null) {
			throw new Exception("Engine must start() first");
		}
		return mExecutor.submit(terryMethodCaller);
	}

	public void addResultListener(IGuessResultListener aListener) {
		if (mListeners.contains(aListener) == false) {
			mListeners.add(aListener);
		}
	}

	private void notifyListeners(String aString) {
		for (IGuessResultListener l : mListeners) {
			l.onResult(aString);
		}
	}

    public void run40TimesAndGatherTheStats() {
        NumberIChi myGuess;

        HashMap<Integer, Integer> statMap = new HashMap<>();
        for (int i = 0; i<= 39; i++){
            statMap.put(i, 0);
        }
        for (int i =1; i<=40; i++){
            myGuess = NumberGuesser.getInstance().guess(5, mAllColdHotNumbers);
            for (int j = 0; j<5; j++){
                int index = myGuess.mNumberList.get(j);
                int appear = statMap.get(index)+1;
                statMap.put(index, appear);
            }
        }

        String result="";

        for (int k: statMap.keySet()){
            if (statMap.get(k) >= 8)
                result = result+"\r\n"+ k+"'s appeareance = "+statMap.get(k);
//                Logger.log(k + "'s appearance = " + statMap.get(k));
        }
        this.notifyListeners(result);
    }

	public void justGuessOnce() {
		NumberIChi myGuess = NumberGuesser.getInstance().guess(5, mAllColdHotNumbers);
		String result = myGuess.toString();
		this.notifyListeners(result);
	}
}
