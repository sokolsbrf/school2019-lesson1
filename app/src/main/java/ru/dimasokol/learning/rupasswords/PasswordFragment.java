package ru.dimasokol.learning.rupasswords;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PasswordFragment extends Fragment implements PasswordHolder {

    private TextView mPasswordView;
    private View mCopyButton;

    public PasswordFragment() {
        super(R.layout.fragment_password);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        mPasswordView = root.findViewById(R.id.text_generated);
        mCopyButton = root.findViewById(R.id.button_copy_generated_password);

        mCopyButton.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(
                    ClipData.newPlainText(getString(R.string.clip_title), mPasswordView.getText().toString())
            );
        });

        mCopyButton.setEnabled(mPasswordView.getText().length() > 0);

        return root;
    }

    @Override
    public void showPassword(String password) {
        mPasswordView.setText(password);
        mCopyButton.setEnabled(mPasswordView.getText().length() > 0);
    }

}
