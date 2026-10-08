package jp.jm23.recentsbutton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean result = RecentsAccessibilityService.showRecents();

        if (!result) {
            Toast.makeText(
                    this,
                    "Recents呼び出し失敗",
                    Toast.LENGTH_SHORT
            ).show();

            startActivity(
                    new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            );
            finish();
            return;
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {

            boolean clicked =
                    RecentsAccessibilityService.clickTorque();

            if (!clicked) {
                Toast.makeText(
                        this,
                        "Torqueカードを検出できません",
                        Toast.LENGTH_SHORT
                ).show();
            }

            finish();

        }, 1000);
    }
}
