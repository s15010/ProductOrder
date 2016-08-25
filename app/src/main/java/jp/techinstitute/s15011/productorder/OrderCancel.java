package jp.techinstitute.s15011.productorder;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderCancel extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_cancel);
/*
		Button btn = (Button) findViewById(R.id.btncancel);
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

				AlertDialog.Builder builder = new AlertDialog.Builder(OrderCancel.this);
				builder.setTitle(" 購入しますか？");
				builder.setMessage("本 " + getBook_p + ": " + a + "個" + "\n" +
						"鉛筆 " + getpen_p + ": " + b + "個" + "\n" +
						"ノート " + getnote_p + ": " + c + "個" + "\n" +
						"筆箱 " + getpencil_p + ": " + d + "個");
				builder.setPositiveButton("OK", null);
				builder.setNegativeButton("Cancel", null);
				builder.show();


			}


		});*/

	}
}