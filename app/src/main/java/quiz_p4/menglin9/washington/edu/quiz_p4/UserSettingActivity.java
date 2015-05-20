package quiz_p4.menglin9.washington.edu.quiz_p4;

import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class UserSettingActivity extends PreferenceActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.user_settings);

        Preference button = (Preference)getPreferenceManager().findPreference("exitlink");
        if (button != null) {
            button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference arg0) {
                    //Intent next = new Intent(UserSettingActivity.this, MainActivity.class);
                    //startActivity(next);
                    finish();
                    return true;
                }
            });
        }

    }

}
