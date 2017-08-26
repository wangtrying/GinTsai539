package tw.idv.terry.gintsai539.presenter;

import android.os.Bundle;
import android.os.Message;

/**
 * Created by wangtrying on 2017/8/26.
 */

public interface IPresenter {
    boolean notifyPresenter(NotifyPresenterReason reason, Bundle bundle);
}
