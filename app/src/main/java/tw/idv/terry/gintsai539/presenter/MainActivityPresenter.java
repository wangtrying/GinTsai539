package tw.idv.terry.gintsai539.presenter;

import android.os.Bundle;
import android.util.Log;

import tw.idv.terry.gintsai539.model.EngineAdapter;
import tw.idv.terry.gintsai539.view.IView;
import tw.idv.terry.gintsai539.view.UpdateViewReason;

import static tw.idv.terry.gintsai539.presenter.NotifyPresenterReason.NEW_RESULT;

/**
 * Created by wangtrying on 2017/8/26.
 */

public class MainActivityPresenter implements IPresenter {

    private final IView mActivity;
    private EngineAdapter mEngineAdapter;

    public MainActivityPresenter(IView mainActivity) {
        mActivity = mainActivity;
    }

    @Override
    public boolean notifyPresenter(NotifyPresenterReason reason, Bundle bundle) {
        Log.d("terry", "notifyPresenter..."+reason.name());
        switch (reason) {
            case NEW_RESULT:
                String result = bundle.getString(NEW_RESULT.name());
                mActivity.updateView(UpdateViewReason.NEW_RESULT, result);
                break;
            case ENGINE_STARTED:
                mActivity.updateView(UpdateViewReason.ENGINE_STARTED, "");
                break;
            case ENGINE_START_FAIL:
                mActivity.updateView(UpdateViewReason.ENGINE_START_FAIL, "");
                break;
        }
        return false;
    }


    public void igniteEngine() {
        if (mEngineAdapter == null) {
            mEngineAdapter = new EngineAdapter(this);
            mEngineAdapter.ignite();
        }
    }

    public void validateTerryMethod() {
        mEngineAdapter.validateTerryMethodOverRandomMethod();
    }

    public void guessNext() {
        mEngineAdapter.guessNext();
    }

    public void bulkGuess() {
        mEngineAdapter.bulkGuess();
    }
}
