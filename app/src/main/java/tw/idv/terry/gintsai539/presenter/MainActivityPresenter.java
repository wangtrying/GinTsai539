package tw.idv.terry.gintsai539.presenter;

import android.os.Bundle;

import java.util.concurrent.ExecutionException;

import idv.terry.lotto.lib.Engine539;
import tw.idv.terry.gintsai539.model.EngineResultListener;
import tw.idv.terry.gintsai539.view.IView;
import tw.idv.terry.gintsai539.view.UpdateViewReason;

/**
 * Created by wangtrying on 2017/8/26.
 */

public class MainActivityPresenter implements IPresenter {

    private final IView mActivity;
    private Engine539 mEngine;
    private EngineResultListener mEngineResultListener;

    public MainActivityPresenter(IView mainActivity) {
        mActivity = mainActivity;
    }

    @Override
    public boolean notifyPresenter(NotifyPresenterReason reason, Bundle bundle) {
        switch (reason) {
            case NewResult:
                mActivity.updateView(UpdateViewReason.NewResult, bundle);
                break;
        }
        return false;
    }


    public void igniteEngine() {
        mEngine = new Engine539();
        mEngineResultListener = new EngineResultListener(this);
        try {
            mEngine.start();
            mEngine.addResultListener(mEngineResultListener);
            mActivity.updateView(UpdateViewReason.EngineStarted, null);
        } catch (InterruptedException | ExecutionException e) {
            mActivity.updateView(UpdateViewReason.EngineStartFail, null);
        }
    }
}
