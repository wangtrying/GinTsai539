package tw.idv.terry.gintsai539;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import idv.terry.lotto.lib.Engine539;
import idv.terry.lotto.lib.IGuessResultListener;


public class MainActivity extends Activity implements IGuessResultListener {

    Button btn1, btn2;
    TextView textview;
    Engine539 mEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareEngines();
        prepareViews();
    }

    private void prepareEngines() {
        mEngine = new Engine539();
        try {
            mEngine.start();
            mEngine.addResultListener(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void prepareViews() {
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        textview = (TextView) findViewById(R.id.textView);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button1){
                    textview.setText("");
                    btn1.setEnabled(false);
                    btn2.setEnabled(false);
                    ValidateTerryRunnable run = new ValidateTerryRunnable(mEngine);
                    new Thread(run).start();
                }
                if (v.getId() == R.id.button2){

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
}
