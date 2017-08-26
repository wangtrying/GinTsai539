package tw.idv.terry.gintsai539.model;

import idv.terry.lotto.lib.Engine539;

/**
 * Created by wangtrying on 2017/8/27.
 */

class BulkGuessNextRunnable implements Runnable {
    private Engine539 mEngine;

    public BulkGuessNextRunnable(Engine539 engine) {
        mEngine = engine;
    }

    @Override
    public void run() {
        try {
            mEngine.run40TimesAndGatherTheStats();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
