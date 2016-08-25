package jp.techinstitute.s15011.productorder;

import android.accounts.Account;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by s15011 on 16/08/04.
 */
public class CreateDatebase extends SQLiteOpenHelper {

    private static final String DB_NAME = "Account.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "Account";

    private static final String sql_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    AccountColumns.LastName + " TEXT," +
                    AccountColumns.FirstName + " TEXT," +
                    AccountColumns.MailAddress + " TEXT primary key," +
                    AccountColumns.PrefectureId + " TEXT," +
                    AccountColumns.Address + " TEXT," +
                    AccountColumns.Password + " TEXT)";

    public interface AccountColumns extends BaseColumns {
        public static final String FirstName = "FirstName";
        public static final String LastName = "LastName";
        public static final String PrefectureId = "PrefectureId";
        public static final String Address = "Address";
        public static final String MailAddress = "MailAddress";
        public static final String Password = "Password";
    }

    public CreateDatebase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
