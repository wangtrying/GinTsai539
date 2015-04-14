package tw.idv.terry.gintsai539;

import idv.terry.lotto.lib.Engine539;

/**
 * Created by wangtrying on 2015/4/14.
 */
public class ValidateTerryRunnable implements Runnable {

    private final Engine539 mEngine;

    public ValidateTerryRunnable(Engine539 aEngine){
        mEngine = aEngine;
    }

    @Override
    public void run() {
        try {
            mEngine.validateTerryMethodOverRandomMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
