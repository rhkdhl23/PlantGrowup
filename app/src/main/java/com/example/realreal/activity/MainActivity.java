package com.example.realreal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.realreal.ListActivity;
import com.example.realreal.R;
import com.example.realreal.activities.AlarmMainActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //선언
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //main 레이아웃 연결
        setContentView(R.layout.activity_main);

        // 각각의 버튼 View.OnClickListener로 불러오도록 미리 선언
        findViewById(R.id.action_alarm).setOnClickListener(onClickListener);
        findViewById(R.id.action_write).setOnClickListener(onClickListener);
        findViewById(R.id.action_catbot).setOnClickListener(onClickListener);
        findViewById(R.id.action_user).setOnClickListener(onClickListener);
        findViewById(R.id.action_info_plant).setOnClickListener(onClickListener);
        findViewById(R.id.plant_add).setOnClickListener(onClickListener);

        //////////////////////////////////////////

        //models 라는 이름으로 배열 선언
        models = new ArrayList<>();

        //위에 선언한 배열 'models'에 한 배열당 하나씩 사진, 이름,
        models.add(new Model(R.drawable.marimo, "마리모", "일주일에 한 번씩 물을 갈아주세요. \n물은 수돗물, 정수기 물 등 상관없이 잘 자란답니다.\n물 온도는 15~20℃의 물이 적절해요."));
        models.add(new Model(R.drawable.cat, "캣닢", "물을 좋아합니다. \n그늘진 곳에 두고 하루에 한 번 \n흙이 마르지 않게 분무기로 물을 뿌려주세요."));
        models.add(new Model(R.drawable.olivetree, "올리브 나무", "물을 싫어하는 대표적인 식물입니다.\n일주일에 두 번 물을 주로 뿌리가 자란 후로는 물을 많이 주지 마세요."));
        models.add(new Model(R.drawable.bean, "콩나물", "가장 중요한 것이 물입니다! \n적어도 하루에 4시간 정도의 간격으로 물을 주어야 \n콩나물이 쑥쑥 자라요."));
        models.add(new Model(R.drawable.leaf, "상추", "습기를 매우 좋아합니다. \n아침, 저녁으로 물을 흠뻑 주면 됩니다. \n한 가지 주의할 점은 상추잎에 물이 닿지 않게 해주세요. \n물이 닿으면 잎이 금방 녹아버려요."));
        models.add(new Model(R.drawable.rosemary, "로즈마리", "물 주기에 많은 신경을 써주어야 해요! \n물을 너무 많이 줄 경우 \n잎이 새까맣게 변하기 때문에 적당히 주어야 합니다.\n대부분 3~5일마다 한 번씩 흠뻑 주는 것이 가장 적당합니다"));
        models.add(new Model(R.drawable.maginata, "드라세나 마지나타", "건조에 강한 식물입니다. \n흙을 항상 잘 말리고 잎이 쳐지는 것 같을 때 \n한 번씩 물을 주면 됩니다. \n공중습도가 높은 것을 좋아해 \n잎사귀에 자주 분무기로 물을 뿌려주는 것이 좋아요."));
        models.add(new Model(R.drawable.sofora, "마오리 소포라", "따로 물 주기가 정해져 있지 않아요!\n겉흙이 마르면 물을 100~150ml 정도의 소량의 물을 주면 됩니다.\n물을 준 후 통풍이 잘 드는 곳에 놓아주시면 좋습니다."));
        models.add(new Model(R.drawable.tillandsia, "틸란드시아", "종류와 환경에 따라 \n3~10일에 한 번씩 비를 맞은 것처럼 흠뻑 물을 줘야 합니다.\n잎이 젖어 흐를 만큼의 충분한 물을 필요로 합니다.\n여름철 관리: 장마 기간을 제외하고 3~7일에 한번 물 주기, \n겨울철 관리: 6~10일에 한번 가능하면 20~30℃의 물을 오전에 주고 오후 3시 이전에 마르게 하는 것이 좋습니다."));
        models.add(new Model(R.drawable.moonshine, "문샤인", "한 달에 한 번씩 물을 주면 됩니다.\n식물의 잎에 주름이 지면 한 번 더 물을 주세요."));
        models.add(new Model(R.drawable.peperomia, "페페로미아", "하루에 한 번 물을 주세요.\n흙 속이 마르면 한 번 더 분무해 주세요."));
        models.add(new Model(R.drawable.stuki, "스투키", "7~10일에 한 번 물을 주면 됩니다.\n스투키에서 2cm 정도 떨어진 높이에서 물을 주세요."));

        //위에 models 들을 Adapter 기능으로 연결
        adapter = new Adapter(models, this);

        //viewpager 안에 adapter 로 연결된 배열들이 보여지도록 설정
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(30, 0, 30, 0);
        //////////////////////////////////////////
    }

    //위에 선언된 버튼들을 myStartActivity 함수 선언 후 onClickListener 설정
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_alarm:
                    myStartActivity(AlarmMainActivity.class);
                    break;
                    case R.id.action_write:
                        myStartActivity(WriteActivity.class);
                        break;
                        case R.id.action_catbot:
                            myStartActivity(Chatbot_01_Activity.class);
                            break;
                            case R.id.action_user:
                                myStartActivity(UserActivity.class);
                                break;
                                case R.id.action_info_plant:
                                    myStartActivity(Plant_list_Activity.class);
                                    break;
                                    case R.id.plant_add:
                                    myStartActivity(ListActivity.class);
                                    break;
            }
        }
    };

    //스택에 기존에 사용하던 Activity가 있다면 그 위의 스택을 전부 제거해 주고 호출
    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
