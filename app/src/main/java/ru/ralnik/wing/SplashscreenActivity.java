package ru.ralnik.wing;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import ru.ralnik.wing.json.JSONParser;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        new JSONParser(getApplicationContext(),"http://feeds.l-invest.ru/realred/");
//
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        startApp();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }
                }).check();
    }

    private void startApp() {
        Intent intent = new Intent(SplashscreenActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
