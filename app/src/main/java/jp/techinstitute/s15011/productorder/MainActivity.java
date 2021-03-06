package jp.techinstitute.s15011.productorder;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.widget.TextView.*;



public class MainActivity extends AppCompatActivity {

	private class ProductItem {
		String name;
		int price;
		String num;
	}

	private class ItemAdapter extends ArrayAdapter<ProductItem> {
		private LayoutInflater inflater;

		public ItemAdapter(Context context, int resouce,
						   List<ProductItem> objects){
			super(context, resouce, objects);
			inflater =
					(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			Log.d("ProductList", "getView");

			View view = inflater.inflate(R.layout.select_list_row, null, false);
			TextView nameView = (TextView)view.findViewById(R.id.select_name);
			TextView priceView = (TextView)view.findViewById(R.id.select_price);
			final ProductItem item = getItem(position);
			nameView.setText(item.name);
			priceView.setText(String.valueOf(item.price));

			return  view;
		}
	}

    @Override
	protected  void  onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_check);

		MyHelper myHelper = new MyHelper(this);
		SQLiteDatabase db = myHelper.getWritableDatabase();

		//String test = "";
		String[] list = new String[10]; //{"", ""};
		list[0] = "";
		list[1] = "";
		list[2] = "";
		list[3] = "";
		list[4] = "";

		List<ProductItem> selectList = new ArrayList<>();

		//String[] columns = {MyHelper.Columns.,MyHelper.Columns.PRICE,MyHelper.Columns.STOCK,MyHelper.Columns.productName  };
		String[] ordername = {MyHelper.OrderBeforeColumns.itemName,MyHelper.OrderBeforeColumns.price,MyHelper.OrderBeforeColumns.quantity};

		SharedPreferences data = getSharedPreferences("Maildata", Context.MODE_PRIVATE);
		String getdata = data.getString("Mailsave", "");

		//String[] columns = new String[]{"id","name" };
		Cursor cursor = db.query(MyHelper.ORDER_BEFORE_NAME, ordername,
				null, null, null, null, null);
		cursor.moveToFirst();
	    Log.e("count :", String.valueOf(cursor.getCount()));

//		StringBuilder text = new StringBuilder();
		//Log.e("col :", String.valueOf(cursor.getColumnCount()));
		while (cursor.moveToNext()) {
			//test += String.format("%s : %s円\r\n",cursor.getString(0),cursor.getString(1));
			Log.e("cursorGet :", cursor.getString(0));

			ProductItem item = new ProductItem();
			item.name = cursor.getString(0);
			item.price = cursor.getInt(1);
			item.num = cursor.getString(2);

			selectList.add(item);

/*
			cursor.moveToNext();
			list[1] += String.format("%s \r\n" , cursor.getString(0));

			cursor.moveToNext();
			list[2] += String.format("%s \r\n", cursor.getString(0));


			cursor.moveToNext();
			list[3] += String.format("%s \r\n", cursor.getString(0));
			cursor.moveToLast();*/
			//list[4] += String.format("%s \r\n", cursor.getString(1));

		}

		db.close();

		ItemAdapter adapter = new ItemAdapter(this, 0, selectList);
		ListView listView = (ListView) findViewById(R.id.productList);
		listView.setAdapter(adapter);

        //製品の名前表示
/*		TextView editView = (TextView) findViewById(R.id.textView4);
		editView.setText(list[0]);

		TextView editView2 = (TextView) findViewById(R.id.textView6);
		editView2.setText(list[1]);

		TextView editView3 = (TextView) findViewById(R.id.textView8);
		editView3.setText(list[2]);

		TextView editView4 = (TextView) findViewById(R.id.textView10);
		editView4.setText(list[3]);

		TextView editView5 = (TextView) findViewById(R.id.textView5);
		editView5.setText(list[4]);*/


		Button btnContinue = (Button)findViewById(R.id.btnContinue);
		btnContinue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});




		/*Button btn = (Button) findViewById(R.id.btnBuy);
        btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				MyHelper myHelper = new MyHelper(MainActivity.this);
				SQLiteDatabase db = myHelper.getWritableDatabase();

				String[] columns = {MyHelper.Columns.STOCK,  };
				String[] list = new String[10]; //{"", ""};
				list[0] = "";
				list[1] = "";
				list[2] = "";
				list[3] = "";

				Cursor cursor = db.query(MyHelper.TABLE_NAME, columns, null, null, null, null, null);
				StringBuilder text = new StringBuilder();


				while (cursor.moveToNext()) {

					 list[0] += String.format("%s  \r\n" ,cursor.getString(0));


				}



			}
		});*/


	}







}







	//myHelper db = new MyHelper(getApplicationContext());

        /*Cursor cursor = db.query(
                "list_table", new String[] {"ID","productname", "price", "stock","prefecture PREFECTURE"},
                null, null, null, null, "_id DESC");



        if(!cursor.moveToFirst()){
            cursor.close();
            db.close();
            return;
        }

        int ProductId = cursor.getColumnIndex(MyHelper.Columns.ID);
        int ProductName = cursor.getColumnIndex(MyHelper.Columns.productName);
        int ProductPrice = cursor.getColumnIndex(MyHelper.Columns.PRICE);
        int ProductStock = cursor.getColumnIndex(MyHelper.Columns.STOCK);
        int productPrefecture = cursor.getColumnIndex(MyHelper.Columns.PREFECTURE);

        // 6. Cursorを閉じる
        cursor.close();

        // 7. データベースを閉じる
        db.close();*/



        /*Button btn = (Button) findViewById(R.id.btnBuy);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView editView = (TextView) findViewById(R.id.editText);
                TextView editView2 = (TextView) findViewById(R.id.editText2);
                TextView editView3 = (TextView) findViewById(R.id.editText3);
               // TextView editView4 = (TextView) findViewById(R.id.editText4);

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
               // String d = editView4.getText().toString();

                String err = "";

                /*if (a.equals(err)) {
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


        });*/



    //}


//}
