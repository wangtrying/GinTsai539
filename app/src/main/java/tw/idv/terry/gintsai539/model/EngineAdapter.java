package tw.idv.terry.gintsai539.model;

import android.os.Bundle;

import java.util.concurrent.ExecutionException;

import idv.terry.lotto.lib.Engine539;
import idv.terry.lotto.lib.IGuessResultListener;
import tw.idv.terry.gintsai539.ValidateTerryRunnable;
import tw.idv.terry.gintsai539.presenter.MainActivityPresenter;
import tw.idv.terry.gintsai539.presenter.NotifyPresenterReason;

/**
 * Created by wangtrying on 2017/8/26.
 */

public class EngineAdapter implements IGuessResultListener {

    private final MainActivityPresenter mPresenter;
    private Engine539 mEngine;


    public EngineAdapter(MainActivityPresenter presenter) {
        mPresenter = presenter;
    }

    public void ignite() {
        if (mEngine == null) {
            mEngine = new Engine539();
        }
        try {
            mEngine.start();
            mEngine.addResultListener(this);
            mPresenter.notifyPresenter(NotifyPresenterReason.ENGINE_STARTED, null);
        } catch (InterruptedException | ExecutionException e) {
            mPresenter.notifyPresenter(NotifyPresenterReason.ENGINE_START_FAIL, null);
        }
    }

    @Override
    public void onResult(String aResultString) {
        Bundle bundle = new Bundle();
        bundle.putString(NotifyPresenterReason.NEW_RESULT.name(), aResultString);
        mPresenter.notifyPresenter(NotifyPresenterReason.NEW_RESULT, bundle);
    }

    public void validateTerryMethodOverRandomMethod() {
        Runnable run = new ValidateTerryRunnable(mEngine);
        Thread thread = new Thread(run);
        thread.start();
    }
}
