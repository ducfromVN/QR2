package android.bignerdranch.qr3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOpenQRScanner = findViewById(R.id.btnOpenQRScanner);

        btnOpenQRScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open QR scanner activity
                Intent intent = new Intent(MainActivity.this, QRScannerActivity.class);
                startActivity(intent);
            }
        });
    }
}
