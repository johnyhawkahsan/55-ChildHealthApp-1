package com.johnyhawkdesigns.a55_childhealthapp_1.activities;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.ListPreference;

import android.util.Log;

import com.johnyhawkdesigns.a55_childhealthapp_1.R;

public class PreferenceActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SettingsFragment settingsFragment = new SettingsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content, settingsFragment);
        transaction.commit();

    }


    public static class SettingsFragment extends PreferenceFragmentCompat {

        private static final String TAG = SettingsFragment.class.getSimpleName();

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            // Load the preferences from an XML resource
            // setPreferencesFromResource(R.xml.pref_settings, rootKey);
            addPreferencesFromResource(R.xml.pref_settings);

            // Bind the summaries of EditText/List preferences to their values. When their values change, their summaries are updated to reflect the new value, per the Android Design guidelines.
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_duration)));
            //bindPreferenceSummaryToValue(findPreference("example_list"));
        }


        // Method to bind Preferences to value stored in our SharedPreferences using key value pair i.e; "key_duration" key to retrieve value
        // Attaches a listener so the summary is always updated with the preference value. Also fires the listener once, to initialize the summary (so it shows up before the value is changed.
        private static void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }


        /**
         * A preference value change listener that updates the preference's summary to reflect its new value.
         */
        private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String stringValue = newValue.toString();
                Log.d(TAG, "onPreferenceChange: newValue = " + stringValue);

                //We want to fetch weather task again if unit is changed from metric to imperial

                //If this preference is a list Preference, we need to retrieve correct data according to index values because they were stored using array (Unit is ListPreference)
                if (preference instanceof android.support.v7.preference.ListPreference) {
                    // For list preferences, look up the correct display value in the preference's 'entries' list (since they have separate labels/values).
                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(stringValue);
                    Log.d(TAG, "onPreferenceChange: index of List Item = " + index);

                    // Set the summary to reflect the new value.
                   if (index >= 0) {
                        preference.setSummary(listPreference.getEntries()[index]);
                    }

                }
                else {
                    //Our data falls within this category because it's something other than ListPreference
                    Log.d(TAG, "onPreferenceChange: else = something other than ListPreference");
                    //preference.setSummary(stringValue);
                }
                return true;
            }
        };





    }














}


