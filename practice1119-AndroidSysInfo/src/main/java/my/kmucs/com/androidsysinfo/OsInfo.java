package my.kmucs.com.androidsysinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Koo on 2016-11-19.
 */

public class OsInfo extends Activity {
    Button backBtn;
    TextView sysInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoview);

        backBtn = (Button)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(BackOnClickListener);

        sysInfo = (TextView)findViewById(R.id.sysInfo);
    }

    //버튼 이벤트 부여의 하나의 방식(종료)
    private Button.OnClickListener BackOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(OsInfo.this, MainActivity.class); //인텐트를 돌려준다, 뒤로가기

            startActivity(intent);
            finish();
        }
    };
}
