package android.bignerdranch.qr3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private UserDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new UserDbHelper(this);

        final EditText etLoginUsername = findViewById(R.id.etLoginUsername);
        final EditText etLoginPassword = findViewById(R.id.etLoginPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String loginUsername = etLoginUsername.getText().toString();
                String loginPassword = etLoginPassword.getText().toString();

                // Retrieve stored user credentials from SQLite database
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String[] columns = {UserDbHelper.COLUMN_USERNAME, UserDbHelper.COLUMN_PASSWORD};
                String selection = UserDbHelper.COLUMN_USERNAME + " = ? AND " +
                        UserDbHelper.COLUMN_PASSWORD + " = ?";
                String[] selectionArgs = {loginUsername, loginPassword};

                Cursor cursor = db.query(
                        UserDbHelper.TABLE_USERS,
                        columns,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        null
                );

                if (cursor.moveToFirst()) {
                    // Successful login, you can navigate to the main activity or perform other actions
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    // Example: Navigating to another activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close the login activity
                } else {
                    // Failed login, show an error message
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
                db.close();
            }
        });
    }
}
