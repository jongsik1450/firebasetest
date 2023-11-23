package com.test.firebasetest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Gaip extends AppCompatActivity {
    Button join_btn;
    Button exit_btn;
    private FirebaseFirestore db;
    private EditText id, password, email;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaip);

        db = FirebaseFirestore.getInstance();

        join_btn = (Button) findViewById(R.id.join_button);
        exit_btn = (Button) findViewById(R.id.exit_button);

        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);

        // 가입하기 버튼
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserData();
            }
        });

        // 이전 페이지로 나가는 버튼
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gaip.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveUserData() {
        String userId = id.getText().toString();
        String userPassword = password.getText().toString();
        String userEmail = email.getText().toString();

        checkDuplicateData(userId, userPassword, userEmail);
    }

    private void checkDuplicateData(String userId, String userPassword, String userEmail) {
        // 중복 여부를 확인하기 위한 쿼리를 생성합니다.
        Query query = db.collection("GaipInfo")
                .whereEqualTo("userId", userId)
                .whereEqualTo("userEmail", userEmail);

        // 쿼리를 실행하여 중복된 값이 있는지 확인합니다.
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // 쿼리 결과로부터 중복된 값이 존재하는지 확인합니다.
                if (task.getResult().size() > 0) {
                    // 중복된 값이 존재하므로 저장을 중단하고 사용자에게 알립니다.
                    Toast.makeText(this, "이미 존재하는 사용자 정보입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 중복된 값이 없으므로 팀 정보를 저장합니다.
                    saveTeamData(userId, userPassword, userEmail);
                }
            } else {
                // 쿼리 실행 중 오류가 발생한 경우 처리합니다.
                Toast.makeText(this, "중복 확인 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveTeamData(String userId, String userPassword, String userEmail) {
        // TeamInsertData 객체 생성
        // 사용자 객체 생성
        GaipUser gaipuser = new GaipUser(userId, userPassword, userEmail);

        // 팀 정보를 저장합니다.
        db.collection("GaipInfo")
                .document(userId)
                .set(gaipuser)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Gaip.this, "회원 가입 성공!", Toast.LENGTH_SHORT).show();
                    clearInputFields();
                    Intent intent = new Intent(Gaip.this, MainPage.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "사용자 정보 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearInputFields() {
        id.setText("");
        password.setText("");
        email.setText("");
    }
}
