package kobe.preference_example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final String KEY_CHECK_BOX_PREF = "check_box_preference";
    public final String KEY_SWITCH_PREF = "switch_preference";
    public final String KEY_LIST_PREF = "list_preference";

    private SharedPreferences mPref;
    private TextView mCbPrefTextView;
    private TextView mSwitchPrefTextView;
    private TextView mListPrefTextView;
    private SharedPreferences.OnSharedPreferenceChangeListener mPrefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //>> get preference values
        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        mCbPrefTextView = (TextView) findViewById(R.id.tv_check_box);
        mSwitchPrefTextView = (TextView) findViewById(R.id.tv_switch);
        mListPrefTextView = (TextView) findViewById(R.id.tv_list);

        String cbText = "Check Box Pref Value: " + String.valueOf(mPref.getBoolean(KEY_CHECK_BOX_PREF,false));
        mCbPrefTextView.setText(cbText);

        String switchText = "Switch Pref Value: " + String.valueOf(mPref.getBoolean(KEY_SWITCH_PREF, false));
        mSwitchPrefTextView.setText(switchText);

        String listText = "List Pref Value: " + mPref.getString(KEY_LIST_PREF, "none");
        mListPrefTextView.setText(listText);

        //>> prepare OnSharedPreferenceChangeListener
        mPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                String text;

                if (key.equals(KEY_CHECK_BOX_PREF)) {
                    text = "Check Box Pref Value: " + String.valueOf(sharedPreferences.getBoolean(KEY_CHECK_BOX_PREF,false));
                    mCbPrefTextView.setText(text);
                }
                else if (key.equals(KEY_SWITCH_PREF)) {
                    text = "Switch Pref Value: " + String.valueOf(sharedPreferences.getBoolean("switch_preference", false));
                    mSwitchPrefTextView.setText(text);
                }
                else if (key.equals(KEY_LIST_PREF)) {
                    text = "List Pref Value: " + sharedPreferences.getString(KEY_LIST_PREF, "none");
                    mListPrefTextView.setText(text);
                }
            }
        };

        //>> register OnSharedPreferenceChangeListener
        mPref.registerOnSharedPreferenceChangeListener(mPrefListener);
    }

    @Override
    protected void onDestroy() {
        mPref.unregisterOnSharedPreferenceChangeListener(mPrefListener);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
