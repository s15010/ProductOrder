package jp.techinstitute.s15011.productorder;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

public class ChengeMenberInfo extends AppCompatActivity {


    private MyHelper myHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chenge_menber_info);
        Button button = (Button) findViewById(R.id.btnConfirm);
        Button button2 = (Button) findViewById(R.id.btnCancel);
        myHelper = new MyHelper(this);
        set_Prodata();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {



                final ProDate account = new ProDate();

                String err = "";

                EditText Last_name = (EditText) findViewById(R.id.editLastName);
                EditText First_name = (EditText) findViewById(R.id.editFirstName);
                EditText Home_address = (EditText) findViewById(R.id.editAddress);
                EditText Password = (EditText) findViewById(R.id.editPassword);
                EditText Confirm_Password = (EditText) findViewById(R.id.Confirm_Password);
                Spinner spinner = (Spinner) findViewById(R.id.spinner);

                account.last_name = Last_name.getText().toString();
                account.first_name = First_name.getText().toString();
                account.address = Home_address.getText().toString();
                account.password = Password.getText().toString();
                String confirm_password = Confirm_Password.getText().toString();
                account.prefectureId = spinner.getSelectedItem().toString();
                //Log.d("kkkkkkkkkkkkkkk", "qqqqqqqqqqqqqqqqqqqqqqqqq");

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChengeMenberInfo.this);

                if (account.last_name.equals(err)) {
                    alertDialog.setMessage("未入力の項目があります");
                } else if (account.first_name.equals(err)) {
                    alertDialog.setMessage("未入力の項目があります");
                } else if (account.address.equals(err)) {
                    alertDialog.setMessage("未入力の項目があります");
                } else if (account.password.equals(err)) {
                    alertDialog.setMessage("未入力の項目があります");
                } else if (confirm_password.equals(err)) {
                    alertDialog.setMessage("未入力の項目があります");
                } else if (!account.password.equals(confirm_password)) {
                    alertDialog.setMessage("パスワードが一致していません");
                } else {
                    alertDialog.setTitle("以下の内容でよろしいですか?");
                    alertDialog.setMessage("名前: " + account.last_name + account.first_name + "\n"
                            + "都道府県: " + account.prefectureId + "\n"
                            + "住所: " + account.address);
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ProDate send_data = getconf_ET();
                            if(UpdataAccount(send_data)){
                                Toast.makeText(ChengeMenberInfo.this, "更新に成功しました", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(ChengeMenberInfo.this, "更新に失敗しました。", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertDialog.setNegativeButton("CANCEL", null);
                }
                alertDialog.create().show();
            }
        });
    }

    /*private String check(){
        String check_msg = "";
        ProDate old_data = new ProDate();
        old_data = serchDBdata(old_data);
        ProDate
    }*/

    //DBから取得したデータをEditTextに入れる
    private void set_Prodata(){
        ProDate data = new ProDate();
        data = serchDBdata(data);

        EditText set_lastname = (EditText)findViewById(R.id.editLastName);
        EditText set_firstname = (EditText)findViewById(R.id.editFirstName);
        EditText set_address = (EditText)findViewById(R.id.editAddress);
        EditText set_password = (EditText)findViewById(R.id.editPassword);

        set_lastname.setText(data.last_name);
        set_firstname.setText(data.first_name);
        set_address.setText(data.address);
        set_password.setText(data.password);

    }

    //DBからデータを取ってきてdataに入れて返す
    private ProDate serchDBdata(ProDate data){
        String target = getEmail();

        String[] targetcolumns = {MyHelper.AccountColumns.lastName, MyHelper.AccountColumns.firstName,
                MyHelper.AccountColumns.prefectureId, MyHelper.AccountColumns.address, MyHelper.AccountColumns.password};
        String serachcond = MyHelper.AccountColumns.mailAddress + " = ?";
        String[] serachargs = {target};

        SQLiteDatabase db = myHelper.getReadableDatabase();

        Cursor cursor = db.query(MyHelper.ACCOUNT_TABLE_NAME, targetcolumns, serachcond, serachargs, null, null, null);

        while(cursor.moveToNext()){
            data.last_name = cursor.getString(0);
            data.first_name = cursor.getString(1);
            data.prefectureId = cursor.getString(2);
            data.address = cursor.getString(3);
            data.password = cursor.getString(4);
        }
        return data;
    }

    //DBを更新する
    private boolean UpdataAccount(ProDate Accountdata){
        SQLiteDatabase db = myHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyHelper.AccountColumns.lastName,Accountdata.last_name);
        //Log.d("aaaa", "aaaa");
        values.put(MyHelper.AccountColumns.firstName,Accountdata.first_name);
        //Log.d("iiii", "iiii");
        values.put(MyHelper.AccountColumns.prefectureId,Accountdata.prefectureId);
        //Log.d("uuuu", "uuuu");
        values.put(MyHelper.AccountColumns.address,Accountdata.address);
        //Log.d("eeee", "uuuu");
        values.put(MyHelper.AccountColumns.password,Accountdata.password);
        //Log.d("oooo", "oooo");

        String target = getEmail();
        String search = MyHelper.AccountColumns.mailAddress + " = ?";
        String[] args = {target};

        long ret;

        try{
            ret = db.update(myHelper.ACCOUNT_TABLE_NAME, values, search, args);
        }finally {
            db.close();
        }
        if(ret == -1){
            return false;
        }else{
            return true;
        }
    }

    //呼びだされた時のEditText取得処理
    private ProDate getconf_ET(){
        ProDate data = new ProDate();


        EditText set_lastname = (EditText)findViewById(R.id.editLastName);
        EditText set_firstname = (EditText)findViewById(R.id.editFirstName);
        EditText set_address = (EditText)findViewById(R.id.editAddress);
        EditText set_password = (EditText)findViewById(R.id.editPassword);
        Spinner set_prefectureId = (Spinner)findViewById(R.id.spinner);

        data.last_name = set_lastname.getText().toString();
        data.first_name = set_firstname.getText().toString();
        data.address = set_address.getText().toString();
        data.password = set_password.getText().toString();
        data.prefectureId = set_prefectureId.getSelectedItem().toString();


        return data;
    }

    private class ProDate{
        String last_name;
        String first_name;
        String address;
        String password;
        String prefectureId;
    }

    //シェアされているEmailを取ってくる
    private String getEmail(){
        SharedPreferences data = getSharedPreferences("Maildata", Context.MODE_PRIVATE);
        String getdata = data.getString("Mailsave", "");
        Log.d("itman", "yuki");
        return getdata;
    }

}