package com.example.shippingapp.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.shippingapp.R;
import com.example.shippingapp.constraints.PrefKey;
import com.example.shippingapp.network.HttpRequestInterceptor;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PreferencesFragment extends PreferenceFragmentCompat {
    @Inject
    public HttpRequestInterceptor mHttpRequestInterceptor;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // パスワードのEditTextPreferenceにパスワードマスクを設定する
        EditTextPreference passwordPreference = findPreference("password");
        if(passwordPreference != null) {
            passwordPreference.setOnBindEditTextListener(editText ->
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(mPreferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(mPreferenceChangeListener);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener mPreferenceChangeListener
            = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
                case PrefKey.SERVER_URL:
                    mHttpRequestInterceptor.setBaseUrl(sharedPreferences.getString(key, ""));
                    break;
                case PrefKey.USER_NAME:
                    mHttpRequestInterceptor.setUser(sharedPreferences.getString(key, ""));
                    break;
                case PrefKey.PASSWORD:
                    mHttpRequestInterceptor.setPassword(sharedPreferences.getString(key, ""));
            }
        }
    };

}
