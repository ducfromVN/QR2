package android.bignerdranch.qr3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_QR_CODE_CONTENT = "qr_code_content";

    // SQL statement to create the users table
    private static final String DATABASE_CREATE = "create table "
            + TABLE_USERS + "(" + COLUMN_USERNAME
            + " text primary key not null, " + COLUMN_PASSWORD
            + " text not null, " + COLUMN_QR_CODE_CONTENT
            + " text not null);";

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade policy if needed
    }

    public String getUsername(String usernameToQuery) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USERNAME};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {usernameToQuery};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        String result = null;

        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            cursor.close();
        }

        return result;
    }
}
