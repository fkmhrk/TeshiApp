package jp.tsur.teshiapp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jp.tsur.teshiapp.app.MatsuyaApp;
import jp.tsur.teshiapp.app.impl.MatsuyaAppImpl;

public class MatsuyaActivity extends Activity {

    @InjectView(R.id.edit_text)
    EditText mEditText;
    @InjectView(R.id.result_label)
    TextView mResultLabel;

    private MatsuyaApp mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matsuya);
        this.mApp = new MatsuyaAppImpl();
        ButterKnife.inject(this);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @OnClick(R.id.to_yen_button)
    void toYen() {
        assert mEditText.getText() != null;
        int matsuya = Integer.parseInt(mEditText.getText().toString());
        int yen = mApp.toYen(matsuya);
        mResultLabel.setText(getString(R.string.label_result_yen, String.format("%1$,3d", yen)));
    }

    @OnClick(R.id.to_matsuya_button)
    void toMaysuya() {
        assert mEditText.getText() != null;
        int yen = Integer.parseInt(mEditText.getText().toString());
        double matsuya = mApp.toMatsuya(yen);
        mResultLabel.setText(getString(R.string.label_result_matsuya, String.format("%1$,3f", matsuya)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
