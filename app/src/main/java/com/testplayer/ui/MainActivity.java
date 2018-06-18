package com.testplayer.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.testplayer.PlayerService;
import com.testplayer.R;

public class MainActivity extends AppCompatActivity {

    private static final int DRAW_OVER_OTHER_APP_REQUEST_CODE = 1222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStartService).setOnClickListener(view -> createFloatingPlayer());
        findViewById(R.id.btnStopService).setOnClickListener(view -> stopService(new Intent(this, PlayerService.class)));

    }

    public void createFloatingPlayer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_REQUEST_CODE);
        } else
            startFloatingPlayerService();

    }

    private void startFloatingPlayerService() {
        startService(new Intent(this, PlayerService.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DRAW_OVER_OTHER_APP_REQUEST_CODE) {
            if (resultCode == RESULT_OK)
                createFloatingPlayer();
            else
                Toast.makeText(this, getString(R.string.draw_other_app_permission_denied), Toast.LENGTH_SHORT).show();

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
