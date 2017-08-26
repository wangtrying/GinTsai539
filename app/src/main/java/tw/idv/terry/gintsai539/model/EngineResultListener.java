package tw.idv.terry.gintsai539.model;

import android.os.Bundle;

import idv.terry.lotto.lib.IGuessResultListener;
import tw.idv.terry.gintsai539.presenter.MainActivityPresenter;

import static tw.idv.terry.gintsai539.presenter.NotifyPresenterReason.NewResult;

/**
 * Created by wangtrying on 2017/8/26.
 */

public class EngineResultListener implements IGuessResultListener {
    private final MainActivityPresenter mPresenter;

    public EngineResultListener(MainActivityPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResult(String aResultString) {
        Bundle bundle = new Bundle();
        bundle.putString(NewResult.name(), aResultString);
        mPresenter.notifyPresenter(NewResult, bundle);
    }
}
