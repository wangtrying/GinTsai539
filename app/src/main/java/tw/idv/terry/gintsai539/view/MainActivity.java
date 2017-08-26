package tw.idv.terry.gintsai539.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import idv.terry.lotto.lib.Engine539;
import tw.idv.terry.gintsai539.GuessNextRunnable;
import tw.idv.terry.gintsai539.R;
import tw.idv.terry.gintsai539.presenter.MainActivityPresenter;


public class MainActivity extends Activity implements IView {

    private Button mValidateButton, mGuessButton;
    private TextView mTextview;
    private Engine539 mEngine;
    private MainActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareViews();
        mPresenter = new MainActivityPresenter(this);
        prepareEngines();
    }

    private void prepareEngines() {
        mPresenter.igniteEngine();
    }

    private void prepareViews() {
        mValidateButton = (Button) findViewById(R.id.button1);
        mGuessButton = (Button) findViewById(R.id.button2);
        mTextview = (TextView) findViewById(R.id.textView);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button1) {
                    mTextview.setText("");
                    mValidateButton.setEnabled(false);
                    mGuessButton.setEnabled(false);
                    mPresenter.validateTerryMethod();
                }
                if (v.getId() == R.id.button2) {
                    mValidateButton.setEnabled(false);
                    mGuessButton.setEnabled(false);
                    mTextview.setText("");
                    GuessNextRunnable run = new GuessNextRunnable(mEngine);
                    new Thread(run).start();
                }
            }
        };
        mValidateButton.setOnClickListener(onClickListener);
        mGuessButton.setOnClickListener(onClickListener);
    }

    @Override
    public boolean updateView(UpdateViewReason reason, String resultString) {
        resetButton();
        switch (reason) {
            case NEW_RESULT:
                updateResult(resultString);
                break;
            default:
                Log.d("terry", "updateView..." + reason.name());
                break;

        }
        return false;
    }

    private void resetButton() {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                mValidateButton.setEnabled(true);
                mGuessButton.setEnabled(true);
            }
        };
        runOnUiThread(run);

    }

    private void updateResult(final String resultString) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                String str = mTextview.getText() + "\r\n" + resultString;
                mTextview.setText(str);
            }
        };
        runOnUiThread(run);
    }
}
