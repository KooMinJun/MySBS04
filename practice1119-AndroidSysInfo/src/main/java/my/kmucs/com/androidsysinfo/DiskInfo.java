package my.kmucs.com.androidsysinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by Koo on 2016-11-19.
 */

public class DiskInfo extends Activity {
    Button backBtn;
    TextView sysInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoview);

        backBtn = (Button)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(BackOnClickListener);


        sysInfo = (TextView)findViewById(R.id.sysInfo);
        sysInfo.setText(readDiskInfo());
    }

    //DistInfo클릭시 나타나는 함수
    public String readDiskInfo(){
        ProcessBuilder cmd; //리눅스 명령어 칠 수 있게 하려고
        String result = "";

        try{
            String[] args = {"/system/bin/df"}; //df명령어는  disk info를 보여주는 명령어이다.
            cmd = new ProcessBuilder(args); //cmd창을 띄워라, 그리고 명령어를 입력해라.

            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[1024]; //한번에 몇 바이트씩 짤라서 가져올거야? => 1024바이트
            while(in.read(re) != -1){ //-1은 마지막인지 나타냄
                System.out.println(new String(re));
                result = result + new String(re);

            }
            in.close();;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }





    //버튼 이벤트 부여의 하나의 방식(종료)
    private Button.OnClickListener BackOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(DiskInfo.this, MainActivity.class); //인텐트를 돌려준다, 뒤로가기

            startActivity(intent);
            finish();
        }
    };
}
