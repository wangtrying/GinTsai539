package tw.idv.terry.gintsai539;

import idv.terry.lotto.lib.Engine539;

/**
 * Created by wangtrying on 2015/4/14.
 */
public class GuessNextRunnable implements Runnable {

    private final Engine539 mEngine;

    public GuessNextRunnable(Engine539 aEngine){
        mEngine = aEngine;
    }

    @Override
    public void run() {
        try {
            mEngine.justGuessOnce();
//            mEngine.run40TimesAndGatherTheStats();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
