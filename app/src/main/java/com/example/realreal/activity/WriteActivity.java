package com.example.realreal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realreal.OnPostListener;
import com.example.realreal.PostInfo;
import com.example.realreal.R;
//import com.example.realreal.activities.AlarmMainActivity;
import com.example.realreal.activities.AlarmMainActivity;
import com.example.realreal.adapter.HomeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class WriteActivity extends BasicActivity {
    private static final String TAG = "WriteActivity";
    private FirebaseFirestore firebaseFirestore;
    private HomeAdapter homeAdapter;
    private ArrayList<PostInfo> postList;
    private boolean updating;
    private boolean topScrolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

            firebaseFirestore = FirebaseFirestore.getInstance();
            postList = new ArrayList<>();
            homeAdapter = new HomeAdapter(this, postList);
            homeAdapter.setOnPostListener(onPostListener);

            final RecyclerView recyclerView = findViewById(R.id.recyclerView);

            //recyclerview 화면
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(homeAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int firstVisibleItemPosition = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();

                    if(newState == 1 && firstVisibleItemPosition == 0){
                        topScrolled = true;
                    }
                    if(newState == 0 && topScrolled){
                        postsUpdate(true);
                        topScrolled = false;
                    }
                }

                //RecyclerView 화면 스크롤
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                    super.onScrolled(recyclerView, dx, dy);

                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
                    int lastVisibleItemPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();

                    if(totalItemCount - 3 <= lastVisibleItemPosition && !updating){
                        postsUpdate(false);
                    }

                    if(0 < firstVisibleItemPosition){
                        topScrolled = false;
                    }
                }
            });
            postsUpdate(false);
            return;
    }

    public void onPause(){
        super.onPause();
        homeAdapter.playerStop();
    }

    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete(PostInfo postInfo) {
            postList.remove(postInfo);
            homeAdapter.notifyDataSetChanged();

            Log.e("로그: ","삭제 성공");
        }

        @Override
        public void onModify() {
            Log.e("로그: ","수정 성공");
        }
    };

    //Firebase의 Realtime Database에 "posts" 폴더에 저장이 되어있는 내용들 불러옴
    private void postsUpdate(final boolean clear) {
        updating = true;
        Date date = postList.size() == 0 || clear ? new Date() : postList.get(postList.size() - 1).getCreatedAt();
        CollectionReference collectionReference = firebaseFirestore.collection("posts");
        collectionReference.orderBy("createdAt", Query.Direction.DESCENDING).whereLessThan("createdAt", date).limit(10).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(clear){
                                postList.clear();
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                postList.add(new PostInfo(
                                        document.getData().get("title").toString(),
                                        (ArrayList<String>) document.getData().get("contents"),
                                        (ArrayList<String>) document.getData().get("formats"),
                                        document.getData().get("publisher").toString(),
                                        new Date(document.getDate("createdAt").getTime()),
                                        document.getId()));
                            }
                            homeAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        updating = false;
                    }
                });

        findViewById(R.id.action_alarm).setOnClickListener(onClickListener);
        findViewById(R.id.action_main).setOnClickListener(onClickListener);
        findViewById(R.id.action_catbot).setOnClickListener(onClickListener);
        findViewById(R.id.action_user).setOnClickListener(onClickListener);

        //식물 추가 버튼
        findViewById(R.id.floatingActionButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_alarm:
                    myStartActivity(AlarmMainActivity.class);
                    break;
                case R.id.action_main:
                    myStartActivity(MainActivity.class);
                    break;
                case R.id.action_catbot:
                    myStartActivity(Chatbot_01_Activity.class);
                    break;
                case R.id.action_user:
                    myStartActivity(UserActivity.class);
                    break;
                case R.id.floatingActionButton: //버튼 클릭시 식물 추가하는 화면으로 넘어감
                    myStartActivity(WritePostActivity.class);
                    break;
            }
        }
    };

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
