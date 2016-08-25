package jp.techinstitute.s15011.productorder;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class CreateMenber extends AppCompatActivity {

    //private MyHelper createDatebase;
    private MyHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //createDatebase = new CreateDatebase(this);
        //myHelper = MyHelper.getInstance(this);
        myHelper = new MyHelper(this);


        setContentView(R.layout.activity_create_menber);

        Button button = (Button)findViewById(R.id.btnEntry);
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final String err = "";

                final CreateMenberStr account = new CreateMenberStr();

                EditText Last_name = (EditText) findViewById(R.id.editLastName);
                EditText First_name = (EditText) findViewById(R.id.editFirstName);
                EditText Mail = (EditText) findViewById(R.id.editMail);
                EditText Home_Address = (EditText) findViewById(R.id.editAddress);
                EditText Password = (EditText) findViewById(R.id.editPassword);
                EditText Confirm_Password = (EditText) findViewById(R.id.Confirm_Password);
                Spinner spinner = (Spinner) findViewById(R.id.spinner);

                account.last_name = Last_name.getText().toString();
                account.first_name = First_name.getText().toString();
                account.mailaddress = Mail.getText().toString();
                account.address = Home_Address.getText().toString();
                account.password = Password.getText().toString();
                String confirm_password = Confirm_Password.getText().toString();
                account.prefectureid = spinner.getSelectedItem().toString();
                Log.d("aiueo", "kakikukeko");

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateMenber.this);

                Log.d("NOW", "address : " + account.address.toString());
                Log.d("NOW", "err : " + err);
                if(account.last_name.equals(err)){
                    alertDialog.setMessage("未入力の項目があります");
                }else if(account.first_name.equals(err)){
                    alertDialog.setMessage("未入力の項目があります");
                }else if(account.mailaddress.equals(err)){
                    alertDialog.setMessage("未入力の項目があります");
                }else if(account.address.equals(err)){
                    alertDialog.setMessage("未入力の項目があります");
                }else if(account.password.equals(err)){
                    alertDialog.setMessage("未入力の項目があります");
                }else if(confirm_password.equals(err)){
                    alertDialog.setMessage("未入力の項目があります");
                } else if(!account.password.equals(confirm_password)){
                    alertDialog.setMessage("パスワードが一致していません");
                }else {
                    alertDialog.setTitle("以下の内容でよろしいですか?");
                    alertDialog.setMessage("名前: " + account.last_name + account.first_name + "\n"
                            + "Email: " + account.mailaddress + "\n"
                            + "都道府県: " + account.prefectureid + "\n"
                            + "住所: " + account.address);
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            setShare(account.mailaddress);
                            Log.d("unko", "unti");

                            String err_msg = insertMenber(account);
                            if(err_msg.equals("")){
                                chengeactivity();

                            }else  {
                                alertDialog.setMessage("アドレス被ってる");
                                alertDialog.create().show();
                            }

                        }
                    });
                    alertDialog.setNegativeButton("CANCEL", null);
                }
                alertDialog.create().show();
            }
        });
    }

    private void chengeactivity(){
        Intent intent = new Intent(this, ProductView.class);
        startActivity(intent);
    }

    private class CreateMenberStr{
        String first_name;
        String last_name;
        String prefectureid;
        String address;
        String mailaddress;
        String password;
    }

    private String insertMenber(CreateMenberStr item) {
        String err_msg = "";

        //SQLiteDatabaseオブジェクト取得
        SQLiteDatabase db_q = myHelper.getReadableDatabase();

        // queryを呼び、検索を行う
        String where = MyHelper.AccountColumns.mailAddress + "=?";
        String[] args = {item.mailaddress};
        Cursor cursor = db_q.query(
                MyHelper.ACCOUNT_TABLE_NAME, null, where, args, null, null, null);


        //読込位置を先頭にする。trueの場合は結果1件以上
        if(cursor.moveToFirst()) {
            cursor.close();
            db_q.close();
            err_msg = "そのアドレスはすでに存在してます";
            return err_msg;
        }else{
            cursor.close();
            db_q.close();
        }

        SQLiteDatabase db = myHelper.getWritableDatabase();

        //列に対応する値をセット
        ContentValues values = new ContentValues();

        values.put(MyHelper.AccountColumns.firstName, item.first_name);
        values.put(MyHelper.AccountColumns.lastName, item.last_name);
        values.put(MyHelper.AccountColumns.prefectureId, item.prefectureid);
        values.put(MyHelper.AccountColumns.address, item.address);
        values.put(MyHelper.AccountColumns.mailAddress, item.mailaddress);
        values.put(MyHelper.AccountColumns.password, item.password);
        Log.d("la", "la");

        // データベースに行を追加する
        long id = db.insert(MyHelper.ACCOUNT_TABLE_NAME, null, values);
        if (id == -1) {
            Log.d("Database", "行の追加に失敗");
        }
        db.close();

        return err_msg;
    }

    private void setShare(String mail){
        SharedPreferences data = getSharedPreferences("Maildata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putString("Mailsave", mail);
        editor.putInt("Totalordersave",0);
        editor.apply();
    }
}
