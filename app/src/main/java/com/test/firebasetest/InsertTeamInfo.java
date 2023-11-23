package com.test.firebasetest;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import androidx.appcompat.app.AppCompatActivity;

public class InsertTeamInfo extends AppCompatActivity {
    private FirebaseFirestore db;
    EditText name, area, intro;
    Button storeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_team_info);

        db = FirebaseFirestore.getInstance();

        name = (EditText) findViewById(R.id.teamName);
        area = (EditText) findViewById(R.id.teamArea);
        intro = (EditText) findViewById(R.id.teamIntro);
        storeBtn = (Button) findViewById(R.id.storeBtn);

        // 저장하기 버튼 클릭하면 정보 DB에 정보 저장
        storeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserData();
                Intent intent = new Intent(InsertTeamInfo.this, TeamInfo.class);
                startActivity(intent);
            }
        });
    }

    private void saveUserData() {
        String teamName = name.getText().toString();
        String teamArea = area.getText().toString();
        String teamIntro = intro.getText().toString();

        // 중복 여부를 확인하고 중복된 값이 존재하는지 체크합니다.
        checkDuplicateData(teamName, teamArea, teamIntro);
    }

    private void checkDuplicateData(String teamName, String teamArea, String teamIntro) {
        // 중복 여부를 확인하기 위한 쿼리를 생성합니다.
        Query query = db.collection("TeamData")
                .whereEqualTo("teamName", teamName)
                .whereEqualTo("teamArea", teamArea);

        // 쿼리를 실행하여 중복된 값이 있는지 확인합니다.
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // 쿼리 결과로부터 중복된 값이 존재하는지 확인합니다.
                if (task.getResult().size() > 0) {
                    // 중복된 값이 존재하므로 저장을 중단하고 사용자에게 알립니다.
                    Toast.makeText(this, "이미 존재하는 팀 정보입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 중복된 값이 없으므로 팀 정보를 저장합니다.
                    saveTeamData(teamName, teamArea, teamIntro);
                }
            } else {
                // 쿼리 실행 중 오류가 발생한 경우 처리합니다.
                Toast.makeText(this, "중복 확인 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTeamData(String teamName, String teamArea, String teamIntro) {
        // TeamInsertData 객체 생성
        TeamInsertData teamdata = new TeamInsertData(teamName, teamArea, teamIntro);

        // 팀 정보를 저장합니다.
        db.collection("TeamData")
                .document(teamName)
                .set(teamdata)
                .addOnSuccessListener(aVoid -> {
                    name.setText("");
                    area.setText("");
                    intro.setText("");
                    Toast.makeText(this, "팀 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "팀 정보 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
    }
}

