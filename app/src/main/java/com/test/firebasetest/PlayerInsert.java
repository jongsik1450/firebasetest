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

public class PlayerInsert extends AppCompatActivity {
    private FirebaseFirestore db;
    EditText teamName, teamArea, playerName, Age;
    Button storeBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_player_info);

        db = FirebaseFirestore.getInstance();

        teamName = (EditText) findViewById(R.id.teamName);
        teamArea = (EditText) findViewById(R.id.teamArea);
        playerName = (EditText) findViewById(R.id.playerName);
        Age = (EditText) findViewById(R.id.playerAge);
        storeBtn = (Button) findViewById(R.id.storeBtn);

        storeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserData();
            }
        });
    }
    private void saveUserData() {
        String tName = teamName.getText().toString();
        String tArea = teamArea.getText().toString();
        String pName = playerName.getText().toString();
        String pAge = Age.getText().toString();

        // 중복 여부를 확인하고 중복된 값이 존재하는지 체크합니다.
        checkDuplicateData(tName, tArea, pName, pAge);
    }

    private void checkDuplicateData(String tName, String tArea, String pName, String pAge) {
        // 중복 여부를 확인하기 위한 쿼리를 생성합니다.
        Query query = db.collection("PlayerData")
                .whereEqualTo("tName", tName)
                .whereEqualTo("tArea", tArea)
                .whereEqualTo("pName", pName)
                .whereEqualTo("age", pAge);

        // 쿼리를 실행하여 중복된 값이 있는지 확인합니다.
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // 쿼리 결과로부터 중복된 값이 존재하는지 확인합니다.
                if (task.getResult().size() > 0) {
                    // 중복된 값이 존재하므로 저장을 중단하고 사용자에게 알립니다.
                    Toast.makeText(PlayerInsert.this, "해당 팀에 이미 존재하는 선수 정보입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 중복된 값이 없으므로 선수 정보를 저장합니다.
                    savePlayerData(tName, tArea, pName, pAge);
                }
            } else {
                // 쿼리 실행 중 오류가 발생한 경우 처리합니다.
                Toast.makeText(PlayerInsert.this, "중복 확인 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void savePlayerData(String tName, String tArea, String pName, String pAge) {
        // PlayerInsertData 객체 생성
        PlayerInsertData playerData = new PlayerInsertData(tName, tArea, pName, pAge);

        // 선수 정보를 저장합니다.
        db.collection("PlayerData")
                .document(pName)
                .set(playerData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(PlayerInsert.this, "선수 정보 입력 성공!", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                    Intent intent = new Intent(PlayerInsert.this, TeamInfo.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PlayerInsert.this, "선수 정보 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearInputFields() {
        teamName.setText("");
        teamArea.setText("");
        playerName.setText("");
        Age.setText("");
    }
}