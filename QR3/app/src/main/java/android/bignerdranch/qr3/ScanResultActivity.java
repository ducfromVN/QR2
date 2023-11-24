package android.bignerdranch.qr3;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ScanResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        TextView tvScanTime = findViewById(R.id.tvScanTime);
        TextView tvScanInfo = findViewById(R.id.tvScanInfo);

        // Retrieve data from the intent
        String scanTime = getIntent().getStringExtra("scanTime");
        String scanInfo = getIntent().getStringExtra("scanInfo");

        // Display scan time and scan info
        tvScanTime.setText("Scan Time: " + scanTime);
        tvScanInfo.setText("Scan Info: " + scanInfo);
    }
}
