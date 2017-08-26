package tw.idv.terry.gintsai539.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import idv.terry.lotto.lib.Engine539;
import idv.terry.lotto.lib.IGuessResultListener;
import tw.idv.terry.gintsai539.GuessNextRunnable;
import tw.idv.terry.gintsai539.R;
import tw.idv.terry.gintsai539.ValidateTerryRunnable;
import tw.idv.terry.gintsai539.presenter.MainActivityPresenter;

import static tw.idv.terry.gintsai539.view.UpdateViewReason.EngineStartFail;
import static tw.idv.terry.gintsai539.view.UpdateViewReason.EngineStarted;


public class MainActivity extends Activity implements IGuessResultListener, IView {

    private Button btn1, btn2;
    private TextView textview;
    private Engine539 mEngine;
    private MainActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainActivityPresenter(this);
        prepareEngines();
        prepareViews();
    }

    private void prepareEngines() {
        mPresenter.igniteEngine();
    }

    private void prepareViews() {
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        textview = (TextView) findViewById(R.id.textView);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button1) {
                    textview.setText("");
                    btn1.setEnabled(false);
                    btn2.setEnabled(false);
                    ValidateTerryRunnable run = new ValidateTerryRunnable(mEngine);
                    new Thread(run).start();
                }
                if (v.getId() == R.id.button2) {

                    btn1.setEnabled(false);
                    btn2.setEnabled(false);
                    textview.setText("");
                    GuessNextRunnable run = new GuessNextRunnable(mEngine);
                    new Thread(run).start();
                }
            }
        };
        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
    }


    @Override
    public void onResult(final String aResultString) {
        Runnable r = new Runnable() {
            @Override
            public void run() {

                btn1.setEnabled(true);
                btn2.setEnabled(true);
                String str = textview.getText() + "\r\n" + aResultString;
                textview.setText(str);
            }
        };
        runOnUiThread(r);
    }

    @Override
    public boolean updateView(UpdateViewReason reason, Bundle bundle) {
        switch (reason) {
            case EngineStartFail:
                Toast.makeText(this, EngineStartFail.name(), Toast.LENGTH_SHORT).show();
                break;

            case EngineStarted:
                Toast.makeText(this, EngineStarted.name(), Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
