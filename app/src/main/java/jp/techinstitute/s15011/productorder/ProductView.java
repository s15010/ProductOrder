package jp.techinstitute.s15011.productorder;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ProductView extends AppCompatActivity  {
    private ItemAdapter adapter;
    private ItemAdapter adapter2;
    private MyHelper myHelper;
    private SQLiteDatabase db;

    private List<ProductItem> selectProduct;
    private List<ProductItem> productList;

    private String mailSave;


    private class ProductItem {
        int _id;
        String id;
        String name;
        int price;
        int stock;
    }

    private class ItemAdapter extends ArrayAdapter<ProductItem>{
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

            View view = inflater.inflate(R.layout.list_layout, null, false);
            TextView nameView = (TextView)view.findViewById(R.id.textView23);
            TextView priceView = (TextView)view.findViewById(R.id.price23);
            final ProductItem item = getItem(position);
            nameView.setText(item.name);
            priceView.setText(String.valueOf(item.price));

            if (item != null) {
                final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);

                selectProduct = new ArrayList<>();
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked()) {
                            Log.e("Check :", String.valueOf(checkBox.isChecked()));
//                            item.setCheckFlag(true);
                            selectProduct.add(productList.get(position));
                            Log.e("select:", selectProduct.get(selectProduct.size() - 1).name);
                        } else {
//                            item.setCheckFlag(false);
                            selectProduct.remove(productList.get(position));
                        }
                    }
                });
            }

            Log.e("list :", productList.get(position).name);

            return  view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //menu.add(0, 0, 0, "test");
        Log.d("test", "option");
        return super.onCreateOptionsMenu(menu);
        //return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if  (id == R.id.optLogout) {//ログアウトの処理


        }else if(id == R.id.optChengeAccontInfo) {//ChengeMenberinfoに飛ぶ
            Intent i = new Intent(this, ChengeMenberInfo.class);
            startActivity(i);



        }else if(id == R.id.optDeleateAccount) {//アカウント削除の処理
            LayoutInflater inflater = (LayoutInflater)this.getSystemService(
                    LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.dialog2,
                    (ViewGroup)findViewById(R.id.layout_root));
            setContentView(R.layout.activity_product_view);


            // アラーとダイアログ を生成
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(layout);
            builder.create().show();



            Button btn = (Button)layout.findViewById(R.id.DeleateAccount_OK);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Deleate_OK();

                }
            });

            Button btn2 = (Button)layout.findViewById(R.id.DeleateAccount_cancel);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Deleate_cancel();

                }
            });




        }else if(id == R.id.optOrderCancel){//OrderCancelに移行
            return true;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(
                LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog1,
                (ViewGroup)findViewById(R.id.layout_root));
        setContentView(R.layout.activity_product_view);

        myHelper = MyHelper.getInstance(this);

        //recommend(おすすめ商品)listの作成
        List<ProductItem> recommendlist = new ArrayList<>();
        //TODO recommendlistにデータを入れる


        //Product(商品一覧)listの作成
        productList = new ArrayList<>();
        //listに仮のデータを入れる
        //.1SQLiteDatabaseオブジェクト取得
        db = myHelper.getReadableDatabase();

//        testAccountInsert();

        //2.
        String[] cols = {MyHelper.Columns._ID, MyHelper.Columns.productName, MyHelper.Columns.PRICE};
        Cursor cursor =
                db.query(MyHelper.TABLE_NAME, cols, null, null, null, null,
                        MyHelper.Columns._ID + " ASC");

        //3.
        if (!cursor.moveToFirst()) {
            cursor.close();
            db.close();
            Log.d("YOGI","GIYO");
            return;

        }

        //4.
        int _idIndex = cursor.getColumnIndex(MyHelper.Columns._ID);
        int nameIndex = cursor.getColumnIndex(MyHelper.Columns.productName);
        int priceIndex = cursor.getColumnIndex(MyHelper.Columns.PRICE);

        //5.

        //List<ProductItem> list = new ArrayList<ProductItem>(cursor.getCount());
        productList.removeAll(productList);
        do {
            ProductItem item = new ProductItem();
            item._id = cursor.getInt(_idIndex);
            item.name = cursor.getString(nameIndex);
            item.price = cursor.getInt(priceIndex);

            Log.d("selectProductList",
                    "_id = " + item._id + "\n" +
                            "id = " + item.id + "\n" +
                            "name = " + item.name + "\n" +
                            "price = " + item.price + "\n" +
                            "stock = " + item.stock);


            productList.add(item);

            //読み込み位置を次の行に移動させる
            //次の行がないときはfalseを返すのでループを抜ける
        } while (cursor.moveToNext());

        //6.
        cursor.close();

        //7.
        db.close();


        //Adapterの作成
        adapter = new ItemAdapter(this, 0, productList);
        //
        adapter2 = new ItemAdapter(this, 0, productList);

        //listviewにAdapterを関連付ける
        ListView listView = (ListView) findViewById(R.id.listProducts1);
        listView.setAdapter(adapter);

        ListView listView2 = (ListView) findViewById(R.id.listProducts2);
        listView2.setAdapter(adapter2);

        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.dialog1, null);

        LoginDialog dialogFragment = new LoginDialog();
        dialogFragment.setCancelable(false);
        dialogFragment.show(getFragmentManager(), "dialog_fragment");

        Button btn = (Button)findViewById(R.id.btnTransition);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.e("Mail :", mailSave);

                for (ProductItem productItem : selectProduct) {

                    // 列に対応する値をセットする
                    ContentValues values = new ContentValues();
                    values.put(MyHelper.OrderBeforeColumns.productId, productItem.id);
                    values.put(MyHelper.OrderBeforeColumns.itemName, productItem.name);
                    values.put(MyHelper.OrderBeforeColumns.quantity, 1);
                    values.put(MyHelper.OrderBeforeColumns.price, productItem.price);
                    values.put(MyHelper.OrderBeforeColumns.mailAddress, mailSave);

                    // データベースに行を追加する
                    long id = db.insert(MyHelper.ORDER_BEFORE_NAME, null, values);
                    if (id == -1) {
                        Log.d("Database", "行の追加に失敗したよ");
                    }

                    /*Log.e("_id :", productItem._id + "");
                    Log.e("ProductName :", productItem.name + "");
                    Log.e("ProductPrice :", String.valueOf(productItem.price) + "");*/

                }

                /*Cursor cursor = db.query(MyHelper.ORDER_BEFORE_NAME,
                        new String[]{
                                MyHelper.OrderBeforeColumns.itemName,
                                MyHelper.OrderBeforeColumns.price
                        },
                        String.format("%s = %s",
                                MyHelper.AccountColumns.mailAddress, "\"" + mail + "\""
                        ),
                        null, null, null, null);
                cursor.moveToFirst();
                Log.e("count :", String.valueOf(cursor.getCount()));

//                    Log.e("cursor :", String.valueOf(cursor.getString(0)));

                while(cursor.moveToNext()){
                    Log.e("Oreder :", cursor.getString(0)+ " : " + cursor.getInt(1));
                }*/


                Intent i = new Intent(ProductView.this, MainActivity.class);
                Log.d("YOGI", "Create");
                startActivity(i);

            }
        });
/*
        // アラーとダイアログ を生成
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(textEntryView);
        builder.setView(layout);
        //.setCanceledOnTouchOutside(false);
        builder.create().show();

        Button btn = (Button)textEntryView.findViewById(R.id.optCreateAccount);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CreateAccount();

            }
        });
        Button btn1 = (Button)layout.findViewById(R.id.optLogin);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText mailAddress = (EditText)textEntryView.findViewById(R.id.customDlg_id);
                String mail = "";
                mail = mailAddress.getText().toString();
                Log.d("mail", mail);
                login(mail);

            }
        });*/
    }

    public boolean login(String mail, String pass) {

        Log.e("account :", mail + " " + pass);

        //ログインの処理
        db = myHelper.getReadableDatabase();

        Cursor cursor = db.query(MyHelper.ACCOUNT_TABLE_NAME,
                new String[]{
                        MyHelper.AccountColumns.mailAddress
                },
                String.format("%s = %s AND %s = %s",
                        MyHelper.AccountColumns.mailAddress, "\"" + mail + "\"",
                        MyHelper.AccountColumns.password, "\"" + pass + "\""),
                null, null, null, null);
        cursor.moveToFirst();

        Log.e("cursor :", String.valueOf(cursor.getCount()));

        String test = "";

        while(cursor.moveToNext()){
            test = cursor.getString(0);
        }
        Log.d("test", test + mail);
        if (cursor.getCount() == 0) {
            Log.e("cursor :", "false");
            return false;
        }

        SharedPreferences data = getSharedPreferences("Maildata", Context.MODE_PRIVATE);
        mailSave = data.getString("Mailsave", "none");

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(mailSave);

        return true;
    }



    private void testAccountInsert() {
        db = myHelper.getWritableDatabase();

        // 一旦削除
//        int count = db.delete(MyHelper.ACCOUNT_TABLE_NAME, null, null);
//        Log.d("initTable", "count =" + count);

        ContentValues values = new ContentValues();
        values.put(MyHelper.AccountColumns.firstName, "隆史");
        values.put(MyHelper.AccountColumns.lastName, "山田");
        values.put(MyHelper.AccountColumns.prefectureId, 47);
        values.put(MyHelper.AccountColumns.address, "高知市鏡竹奈路");
        values.put(MyHelper.AccountColumns.mailAddress, "test@gmail.com");
        values.put(MyHelper.AccountColumns.password, "test");

        db.insert(MyHelper.ACCOUNT_TABLE_NAME, null, values);

//        queryAccount();;
    }


    public void CreateAccount() {
        Intent i = new Intent(this, CreateMenber.class);
        Log.d("YOGI", "Create");
        startActivity(i);
    }

    public void Deleate_OK(){
        //TODO アカウント削除OKボタン押した時の処理
    }

    public void Deleate_cancel(){
        //アカウント削除cancelボタンを押した時の処理
    }

    private String getEmail(){
        SharedPreferences data = getSharedPreferences("Maildata", Context.MODE_PRIVATE);
        String getdata = data.getString("Mailsave", "");
        Log.d("itman", "yuki");
        return getdata;
    }

}
