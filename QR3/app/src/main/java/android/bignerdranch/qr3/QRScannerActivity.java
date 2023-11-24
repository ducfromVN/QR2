package android.bignerdranch.qr3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.Context;

public class QRScannerActivity extends AppCompatActivity {

    private static final String TAG = "QRScannerActivity";
    private UserDbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       setContentView(R.layout.activity_qr_scanner);
        dbHelper = new UserDbHelper(this);

        // Start QR code scanning
        startQRCodeScanning();
    }

    private void startQRCodeScanning() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CustomScannerActivity.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan a QR Code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Handle when user presses back or scan is unsuccessful
                Toast.makeText(this, "Scan canceled", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Handle the scanned result
                String scanTime = getCurrentTime();
                String scanInfo = getScanInfo(result.getContents());

                // Pass the data to the result activity
                Intent intent = new Intent(this, ScanResultActivity.class);
                intent.putExtra("scanTime", scanTime);
                intent.putExtra("scanInfo", scanInfo);
                startActivity(intent);
            }
            finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date now = new Date();
        return dateFormat.format(now);
    }

   private String getScanInfo(String qrCodeContent) {
        // In your actual implementation, perform a lookup in your SQLite database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {UserDbHelper.COLUMN_USERNAME};
        String selection = UserDbHelper.COLUMN_QR_CODE_CONTENT + " = ?";
        String[] selectionArgs = {qrCodeContent};

        Cursor cursor = db.query(
                UserDbHelper.TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String scanInfo;
        if (cursor.moveToFirst()) {
            String loginUsername = cursor.getString(cursor.getColumnIndex(UserDbHelper.COLUMN_USERNAME));
            scanInfo = loginUsername + " attending!!";
        } else {
            // If the QR code content doesn't match any user, handle accordingly
            scanInfo = "Unknown user";
        }

        cursor.close();
        db.close();

        return scanInfo;
    }

}
