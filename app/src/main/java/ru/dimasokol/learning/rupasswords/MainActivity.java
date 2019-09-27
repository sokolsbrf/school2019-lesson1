package ru.dimasokol.learning.rupasswords;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText sourceEditText;
    private TextView resultTextView;
    private String[] russians;
    private String[] latins;
    private PasswordsHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceEditText = findViewById(R.id.edit_source);
        resultTextView = findViewById(R.id.text_result);

        russians = getResources().getStringArray(R.array.russians);
        latins = getResources().getStringArray(R.array.latin);

        helper = new PasswordsHelper(russians, latins);

        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                resultTextView.setText(helper.convert(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
