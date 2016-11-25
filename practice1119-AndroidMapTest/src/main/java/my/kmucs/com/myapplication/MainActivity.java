package my.kmucs.com.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity { //extends를 바꿔준다.
    GoogleMap gmap;
    SeekBar zoomBar;
    int DEFAULT_ZOOM_LEVEL = 5;
    Button moveBtn;
    EditText etLng, etLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Google map 객체얻기
        gmap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        //객체얻기
        zoomBar = (SeekBar)findViewById(R.id.zoombar);
        zoomBar.setOnSeekBarChangeListener(seekBarChangeListener);
        etLng = (EditText)findViewById(R.id.etLng);
        etLat = (EditText)findViewById(R.id.etLat);
        moveBtn = (Button)findViewById(R.id.moveBtn);
        moveBtn.setOnClickListener(moveOnClickListener);

        //map의 타입변경
//        gmap.setMapType(GoogleMap.MAP_TYPE_NONE); //지도 표기 안됨(빈공간나옴)
//        gmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);    //위성(도로명주소도나온다)
//        gmap.setMapType(GoogleMap.MAP_TYPE_SATELLITE); //위성
//        gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);    //기본
//        gmap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);   //고도
//

        //시작점 정하기
        //경도와 위도 37.556720, 126.940345 -> cgv 신촌아트레온
        LatLng startPoint = new LatLng(37.556720, 126.940345);

        //gmap.moveCamera(CameraUpdateFactory.newLatLng(startPoint)); //그냥 카메라
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, DEFAULT_ZOOM_LEVEL)); //줌까지 한것.

        //중앙 좌표 얻기
        gmap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener(){

            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                //카메라가 변경되었을 때 호출
                LatLng location_center = cameraPosition.target;

                double latitude = cameraPosition.target.latitude; //위도
                double longitude = cameraPosition.target.longitude;  //경도도

                //지도위에 이미지 그릭
                setOnScope(latitude, longitude);

               Log.d("location>>>> ", location_center+"");
           }
        });

        //지도를 클릭 시 이동하기
        gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){

            @Override
            public void onMapClick(LatLng latLng) {
                gmap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                //gmap.clear(); //마커안찍힌 지도부분 클릭시 마커 다삭제
            }
        });

        //시작 시 마커생성
        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(startPoint);
        gmap.addMarker(markerOptions);

        //맵을 롱 클릭 시에 마커를 생성해보세요.

        gmap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){

            @Override
            public void onMapLongClick(LatLng latLng) {
                MarkerOptions markerOptions1 = new MarkerOptions();
                markerOptions1.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)); //marker의 아이콘을 변경
                markerOptions1.position(latLng);
                gmap.addMarker(markerOptions1);
                gmap.animateCamera(CameraUpdateFactory.newLatLng(latLng)); //지도이동

            }
        });

        //마커 클릭 이벤트, 마커 숨기기
        gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("longitude", marker.getPosition().longitude + "");

                marker.setVisible(false);
                return false;
            }
        });

    }  //OnCreate 끝

    //사각형을 만드는 함수
    private List<LatLng> createRectangle(LatLng center, Double halfWidth, Double halfHeight){
         return Arrays.asList(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth),
                new LatLng(center.latitude - halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude + halfWidth),
                new LatLng(center.latitude + halfHeight, center.longitude - halfWidth)
        ); //사각형 복사
    }

    //범위에 사각형 그리기
    private void setOnScope(double lat, double lng){
        //그리기 설정
        PolygonOptions options = new PolygonOptions(); //polygonOptions

        //그릴 좌표 설정
        options.addAll(createRectangle(new LatLng(lat, lng), 0.005, 0.005)); //숫자는 뭐지...->배율관련된것이래..

        //빼기
        options.addHole(createRectangle(new LatLng(lat, lng), 0.001, 0.001));


        //채우기
        options.fillColor(0x44ff0000); //alpha값도 준것

        //선
        options.strokeColor(Color.RED);

        //선의 굵기
        options.strokeWidth(5);

        //그리기
        gmap.addPolygon(options);

    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener(){

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            setZoomLevel(progress + 1); //progress값은 0부터 시작하니까 1을 더해주자
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    //줌인 줌아웃
    private void setZoomLevel(int level){
        gmap.animateCamera(CameraUpdateFactory.zoomTo(level));
    }

    //버튼 클릭시 지도 이동
    private Button.OnClickListener moveOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            setMoveMap(etLat.getText().toString(), etLng.getText().toString());
        }
    };

    private void setMoveMap(String strLat, String strLng){
        double lat = 0;
        double lng = 0;

        //위도 및 경도 형변환
        if (strLat.equalsIgnoreCase("") || strLng.equalsIgnoreCase("")) {
            //값이 공백일때
            //GPS 이용해서 내 위치 얻기
            lat = 37.55737;
            lng = 126.940370;
            //cgv 아트레온온        }else {
            lat = Double.parseDouble(strLat);
            lng = Double.parseDouble(strLng);
        }

        LatLng startPoint = new LatLng(lat, lng);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(startPoint));


    }


}
