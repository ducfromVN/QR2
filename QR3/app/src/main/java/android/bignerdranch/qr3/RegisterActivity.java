package android.bignerdranch.qr3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private UserDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new UserDbHelper(this);

        final EditText etUsername = findViewById(R.id.etUsername);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnSwitchToLogin = findViewById(R.id.btnSwitchToLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Check if username already exists
                if (isUsernameTaken(username)) {
                    // Username is taken, show an error message
                    Toast.makeText(RegisterActivity.this, "Username already taken", Toast.LENGTH_SHORT).show();
                } else {
                    // Store user credentials in SQLite database
                    try {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(UserDbHelper.COLUMN_USERNAME, username);
                        values.put(UserDbHelper.COLUMN_PASSWORD, password);
                        db.insertOrThrow(UserDbHelper.TABLE_USERS, null, values);
                        db.close();

                        // Registration successful, show a success message
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                        // Navigate to the login activity or perform other actions as needed
                    } catch (SQLException e) {
                        // Handle other errors, if any
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set OnClickListener for the switch to login button
        btnSwitchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Switch to LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
    }

    private boolean isUsernameTaken(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {UserDbHelper.COLUMN_USERNAME};
        String selection = UserDbHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                UserDbHelper.TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isTaken = cursor.moveToFirst();
        cursor.close();
        db.close();

        return isTaken;
    }
}
