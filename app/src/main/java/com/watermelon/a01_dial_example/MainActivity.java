package com.watermelon.a01_dial_example;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载一个布局
        setContentView(R.layout.activity_main);

        /**
         * 获取editText对象
         */
        editText=findViewById(R.id.editText3);

        Button button=findViewById(R.id.button01);
        button.setOnClickListener(new handleClickListener());


    }

    private class handleClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String number=editText.getText().toString().trim();
            if("".equals(number)) {
                /**
                 * 加入Toast，Toast:安卓内置的友好的提示工具
                 */
                Toast.makeText(MainActivity.this, "您还未输入电话号码!!!", Toast.LENGTH_LONG).show();
                return ;
            }
            /**
             * 进行拨打电话
             */

            /**
             * 判断是否有权限拨打电话
             */
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                /**
                 * 如果没有权限拨打电话，则创建一个申请拨打电话的监听器，监听用户是否允许拨打电话
                 */
                ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.CALL_PHONE }, 1);
            } else {
                call();
            }
        }
    }

    /**
     * 拨打电话的方法
     */
    private void call() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + editText.getText().toString().trim()));
        startActivity(intent);
    }

    /**
     * 监听用户是否允许app的拨打电话操作
     * @param requestCode　操作码，允许或不允许
     * @param permissions　申请的权限
     * @param grantResults 授权结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 1:{
                if(grantResults.length>0&&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call();
                }
            }
            default:
        }
    }
}
