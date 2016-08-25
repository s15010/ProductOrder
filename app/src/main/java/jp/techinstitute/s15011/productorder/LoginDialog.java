package jp.techinstitute.s15011.productorder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by s15010 on 16/08/24.
 */
public class LoginDialog extends DialogFragment {
    private DialogInterface.OnClickListener okClickListener = null;
    private DialogInterface.OnClickListener cancelClickListener = null;
    private EditText editText;
    private View view;

    @Override
    public Dialog onCreateDialog(Bundle safedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.dialog1, null);

        final EditText editMailAddress = (EditText)view.findViewById(R.id.customDlg_id);
        final EditText editPassword = (EditText)view.findViewById(R.id.customDlg_pass);


        //めんどくさいので入力しておく
        editMailAddress.setText("test@gmail.com");
        editPassword.setText("test");

        Button btn3 = (Button)view.findViewById(R.id.optCreateAccount);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
                Log.d("typn", "nakasone");
            }
        });

        Button btn = (Button)view.findViewById(R.id.optLogin);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.e("Log :", "click");

                //入力された値がDBにあるか検索する。
                boolean isCheck = ((ProductView)getActivity())
                        .login(
                                editMailAddress.getText().toString(),
                                editPassword.getText().toString()
                        );

                if (isCheck) {
                    //情報があったらダイアログを非表示にする
                    dismiss();
                }
            }


        });



        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .show();
    }

    public void CreateAccount() {
        Intent i = new Intent(getActivity(), CreateMenber.class);
        Log.d("YOGI", "Create");
        startActivity(i);
    }

//    Button btn3 = (Button)layout.findViewById(R.id.optCreateAccount);
//    btn3.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            CreateAccount();
//            Log.d("typn", "nakasone");
//        }
//    });

}
