package my.kmucs.com.mysbs04;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    FragmentManager manager;            //fragment를 관리하는 클래스의 참조변수, 모든 fragment를 가져와서 관리하려고 만드는 것
    FragmentTransaction transaction;   //추가, 삭제, replace를 관리하는 객체 : 거래의 흐름의 최소단위

    Fragment frag1, frag2, frag3;     //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //액티비티의 Fragment들 가져와서 manager하기
        manager = (FragmentManager)getFragmentManager();

        frag1 = new AnalogFragment();
        frag2 = new DigitalFragment();
        frag3 = new CalendarFragment();
    }

    public void mOnClick(View v){
        switch(v.getId()){
            case R.id.btn01:        //Analog btn 클릭시
                transaction = manager.beginTransaction();       //transaction 시작하겠다.

                //transaction.add(R.id.container, frag1);        //#fragment 추가하겠다.
                transaction.replace(R.id.container, frag1);    //fragment를 교체하겠다.
                transaction.addToBackStack(null);               //백스택에 보여질 Fragment 배치
                transaction.commit();                            //transaction을 실행하라.
                break;
            case R.id.btn02:
                transaction = manager.beginTransaction();       //transaction 시작하겠다.

                //transaction.add(R.id.container, frag2);        //#fragment 추가하겠다.
                transaction.replace(R.id.container, frag2);    //fragment를 교체하겠다.
                transaction.addToBackStack(null);               //백스택에 보여질 Fragment 배치
                transaction.commit();                            //transaction을 실행하라.
                break;
            case R.id.btn03:
                transaction = manager.beginTransaction();       //transaction 시작하겠다.

                //transaction.add(R.id.container, frag3);        //#fragment 추가하겠다.
                transaction.replace(R.id.container, frag3);    //fragment를 교체하겠다.
                transaction.commit();                            //transaction을 실행하라.
                transaction.addToBackStack(null);               //백스택에 보여질 Fragment 배치
                break;

        }

    }
}
