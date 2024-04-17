package com.example.implicltintent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    String[] perms = new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int perm1 = ContextCompat.checkSelfPermission(this, perms[0]);
        int perm2 = ContextCompat.checkSelfPermission(this, perms[1]);
        if (perm1 != PackageManager.PERMISSION_GRANTED || perm2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 1);
        }
    }

    public void onClick(View view) {
        Intent it = null;

        int id = view.getId();
        if (id == R.id.btnCall)
            it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:01089332865"));
        else if (id == R.id.btnDial)
            it = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01089332865"));
        else if (id == R.id.btnSms) {
            it = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto: 0108933265"));
            it.putExtra("sms_body", "스마트모바일 리포트 다 했나요?");
        } else if (id == R.id.btnMap)
            it = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.182.128.193?z=15"));
        else if (id == R.id.btnWeb)
            it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        else if (id == R.id.btnContact)
            it = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
        else if (id == R.id.btnCamera)
            it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (id == R.id.btnCamera)
            startActivityForResult(it, 100);
        else
            startActivity(it);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");
            ImageView imageView = findViewById(R.id.iVCamera);
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Button btnCall = findViewById(R.id.btnCall);
                btnCall.setEnabled(false);
                Toast.makeText(getApplicationContext(), "전화걸기 미승인", Toast.LENGTH_LONG).show();
            }
        }
        if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            Button btnCamera = findViewById(R.id.btnCamera);
            btnCamera.setEnabled(false);
            Toast.makeText(getApplicationContext(), "카메라권한 미승인", Toast.LENGTH_LONG).show();
        }
    }
}