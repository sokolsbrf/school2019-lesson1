package ru.dimasokol.learning.rupasswords;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements PasswordsApp {

    public static final int MIN_LENGTH = 8;
    private EditText sourceEditText;
    private TextView strengthTextView;
    private TextView lengthTextView;
    private TextView generatedTextView;

    private ImageView strengthImageView;

    private SeekBar seekPasswordLength;

    private View generateButton;

    private CompoundButton digitsCheck, capsCheck, symbolsCheck;

    private String[] russians;
    private String[] latins;
    private PasswordsHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceEditText = findViewById(R.id.edit_source);
        strengthTextView = findViewById(R.id.password_strength);
        generatedTextView = findViewById(R.id.text_generated);
        lengthTextView = findViewById(R.id.text_password_length);
        strengthImageView = findViewById(R.id.image_password_strength);

        generateButton = findViewById(R.id.button_generate);

        digitsCheck = findViewById(R.id.check_digits);
        capsCheck = findViewById(R.id.check_caps);
        symbolsCheck = findViewById(R.id.check_symbols);

        seekPasswordLength = findViewById(R.id.seek_password_length);

        russians = getResources().getStringArray(R.array.russians);
        latins = getResources().getStringArray(R.array.latin);

        helper = new PasswordsHelper(russians, latins);

        generateButton.setOnClickListener(view -> {
            showGeneratedPassword(helper.generatePassword(MIN_LENGTH + seekPasswordLength.getProgress(),
                    capsCheck.isChecked(), digitsCheck.isChecked(), symbolsCheck.isChecked()));
        });

        seekPasswordLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateCount(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showTranslatedPassword(helper.convert(charSequence));
                updateStrengthInfo();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Эта проверка означает наше первое попадание в активити, немножко будущего материала
        if (savedInstanceState == null) {
            updateCount(0);
        }
    }

    private void updateCount(int progress) {
        int total = MIN_LENGTH + progress;
        String added = getResources().getQuantityString(R.plurals.symbols_count, progress);
        String result = getResources().getQuantityString(R.plurals.symbols_count, total);
        lengthTextView.setText(getString(R.string.length_format, progress, added, total, result));
    }

    private void updateStrengthInfo() {
        String password = sourceEditText.getText().toString();
        int strength = helper.detectStrength(password);
        strengthTextView.setText(getResources().getStringArray(R.array.strengths)[strength]);
        strengthImageView.getDrawable().setLevel(strength * 1000);
    }

    @Override
    public void showTranslatedPassword(String password) {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(getString(R.string.translated_tag));

        if (fragment instanceof PasswordHolder) {
            ((PasswordHolder) fragment).showPassword(password);
        }
    }

    @Override
    public void showGeneratedPassword(String password) {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(getString(R.string.generated_tag));

        if (fragment instanceof PasswordHolder) {
            ((PasswordHolder) fragment).showPassword(password);
        }
    }
}
