package my.kmucs.com.androidsysinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    Button osInfoBtn, cpuInfoBtn, memoryInfoBtn, diskInfoBtn, exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        osInfoBtn        = (Button)findViewById(R.id.osInfoBtn);
        cpuInfoBtn       = (Button)findViewById(R.id.cpuInfoBtn);
        memoryInfoBtn    = (Button)findViewById(R.id.memoryInfoBtn);
        diskInfoBtn      = (Button)findViewById(R.id.diskInfoBtn);
        exitBtn           = (Button)findViewById(R.id.exitBtn);

        cpuInfoBtn.setOnClickListener(CpuInfoOnClickListener);
        diskInfoBtn.setOnClickListener(DiskInfoOnClickListener);
        exitBtn.setOnClickListener(ExitOnClickListener);
    }

    //버튼 이벤트 부여의 하나의 방식
    private Button.OnClickListener OnInfoOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {

        }
    };
    //버튼 이벤트 부여의 하나의 방식
    private Button.OnClickListener CpuInfoOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            SwitchClass(CpuInfo.class);
        }
    };
    //버튼 이벤트 부여의 하나의 방식
    private Button.OnClickListener MemoryInfoOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {

        }
    };
    //버튼 이벤트 부여의 하나의 방식
    private Button.OnClickListener DiskInfoOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            SwitchClass(DiskInfo.class);
        }
    };
    //버튼 이벤트 부여의 하나의 방식(종료)
    private Button.OnClickListener ExitOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            finish();
        }
    };


    //Generic을 통한 클래스 교체
    private void SwitchClass(Class<?> targetClass ){
        Intent i = new Intent();
        i.setClass(MainActivity.this, targetClass); //넘겨줄 클래스가 어딘지 모르니까
        startActivity(i);
        finish();

    }
}
