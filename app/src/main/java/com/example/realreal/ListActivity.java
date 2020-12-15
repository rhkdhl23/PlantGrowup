package com.example.realreal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realreal.activity.MainActivity;
import com.example.realreal.activity.Plant_Add_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private List<Plant> plantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        findViewById(R.id.button_main).setOnClickListener(onClickListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);    // 커스터마이징 하기 위해 필요
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);      // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        }

        //등록한 글이 RecyclerView에 보여짐
        final RecyclerView recyclerView = findViewById(R.id.main_recyclerView);

        //Firebase에 연결
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        //RealTime Database의 plant 폴더에 저장되고, 보여짐
        firebaseDatabase.getReference().child("plant").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                plantList = new ArrayList<>();
                String fUid = firebaseUser.getUid();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Plant value = snapshot.getValue(Plant.class);
                    if(value != null) {
                        String dUid = value.getUid();
                        if(fUid.equals(dUid)) {
                            value.setKey(snapshot.getKey());
                            plantList.add(value);
                        }
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(plantList, ListActivity.this);
                recyclerView.setAdapter(myRecyclerViewAdapter);
                myRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", databaseError.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(ListActivity.this, Plant_Add_Activity.class);
                startActivity(intent);
                break;
            }

            //chkPlantCnt 으로 선언한 것에 따라 플러스 버튼 클릭하면 식물 추가할 수 있도록 해줌
            case R.id.action_insert: {
                if(chkPlantCnt()) {
                    Intent intent = new Intent(ListActivity.this, AddActivity.class);
                    intent.putExtra("FLAG", "I");
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "식물은 10개까지 등록 가능합니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_main:
                    myStartActivity(MainActivity.class);
                    break;
            }
        }
    };
    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private boolean chkPlantCnt() {
        boolean flag = false;
        //식물 추가를 10개 이하로 추가 가능
        if(plantList.size() <= 10)
            flag = true;
        return flag;
    }
}
