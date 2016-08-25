package jp.techinstitute.s15011.productorder;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    static MyHelper myHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_check);

        myHelper = new MyHelper(this);

        SQLiteDatabase db = myHelper.getReadableDatabase();


        /*Cursor cursor = db.query(
                "list_table", new String[] {"_id", "data"},
                null, null, null, null, "_id DESC");

        if(!cursor.moveToFirst()){
            cursor.close();
            db.close();
            return;
        }*/

        Cursor cursor = db.query(MyHelper.ORDER_BEFORE_NAME,
                new String[]{
                        MyHelper.OrderBeforeColumns.itemName,
                        MyHelper.OrderBeforeColumns.price
                },
                String.format("%s = %s",
                        MyHelper.AccountColumns.mailAddress, "\"test@gmail.com\""
                ),
                null, null, null, null);
        cursor.moveToFirst();
        Log.e("count :", String.valueOf(cursor.getCount()));

//                    Log.e("cursor :", String.valueOf(cursor.getString(0)));

        while(cursor.moveToNext()) {
            Log.e("Oreder :", cursor.getString(0) + " : " + cursor.getInt(1));
        }

        



        int ProductId = cursor.getColumnIndex(MyHelper.Columns.ID);
        int ProductName = cursor.getColumnIndex(MyHelper.Columns.productName);
        int ProductPrice = cursor.getColumnIndex(MyHelper.Columns.PRICE);
        int ProductStock = cursor.getColumnIndex(MyHelper.Columns.STOCK);



        Button btn = (Button) findViewById(R.id.btnBuy);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView editView = (TextView) findViewById(R.id.editText);
                TextView editView2 = (TextView) findViewById(R.id.editText2);
                TextView editView3 = (TextView) findViewById(R.id.editText3);
                TextView editView4 = (TextView) findViewById(R.id.editText4);

                TextView book_price = (TextView) findViewById(R.id.textView5);
                TextView pen_price = (TextView) findViewById(R.id.textView7);
                TextView note_price = (TextView) findViewById(R.id.textView9);
                TextView pencil_price = (TextView) findViewById(R.id.textView11);
                String getBook_p = book_price.getText().toString();
                String getpen_p = pen_price.getText().toString();
                String getnote_p = note_price.getText().toString();
                String getpencil_p = pencil_price.getText().toString();


                String a = editView.getText().toString();
                String b = editView2.getText().toString();
                String c = editView3.getText().toString();
                String d = editView4.getText().toString();

                String err = "";

                if (a.equals(err)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(" errer");
                    builder.setMessage("個数を入力してください。");
                    builder.setNegativeButton("戻る", null);
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(" 購入しますか？");
                    builder.setMessage("本 " + getBook_p + ": " + a + "個" + "\n" +
                            "鉛筆 " + getpen_p + ": " + b + "個" + "\n" +
                            "ノート " + getnote_p + ": " + c + "個" + "\n" +
                            "筆箱 " + getpencil_p + ": " + d + "個");
                    builder.setPositiveButton("OK", null);
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                }


                if (b.equals(err)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(" errer");
                    builder.setMessage("個数を入力してください。");
                    builder.setNegativeButton("戻る", null);
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(" 購入しますか？");
                    builder.setMessage("本 " + getBook_p + ": " + a + "個" + "\n" +
                            "鉛筆 " + getpen_p + ": " + b + "個" + "\n" +
                            "ノート " + getnote_p + ": " + c + "個" + "\n" +
                            "筆箱 " + getpencil_p + ": " + d + "個");
                    builder.setPositiveButton("OK", null);
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                }


                if (c.equals(err)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(" errer");
                    builder.setMessage("個数を入力してください。");
                    builder.setNegativeButton("戻る", null);
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(" 購入しますか？");
                    builder.setMessage("本 " + getBook_p + ": " + a + "個" + "\n" +
                            "鉛筆 " + getpen_p + ": " + b + "個" + "\n" +
                            "ノート " + getnote_p + ": " + c + "個" + "\n" +
                            "筆箱 " + getpencil_p + ": " + d + "個");
                    builder.setPositiveButton("OK", null);
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                }

                if (d.equals(err)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(" errer");
                    builder.setMessage("個数を入力してください。");
                    builder.setNegativeButton("戻る", null);
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(" 以下の注文をキャンセルします");
                    builder.setMessage("本 " + getBook_p + ": " + a + "個" + "\n" +
                            "鉛筆 " + getpen_p + ": " + b + "個" + "\n" +
                            "ノート " + getnote_p + ": " + c + "個" + "\n" +
                            "筆箱 " + getpencil_p + ": " + d + "個");
                    builder.setPositiveButton("OK", null);
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                }


            }


        });



    }


}
