package com.test.firebasetest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class Gaip extends AppCompatActivity {
    Button join_btn;
    Button exit_btn;
    private FirebaseFirestore db;
    private EditText id, password, password_chk, email;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaip);

        db = FirebaseFirestore.getInstance();

        join_btn = (Button) findViewById(R.id.join_button);
        exit_btn = (Button) findViewById(R.id.exit_button);

        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        password_chk = (EditText) findViewById(R.id.password_chk);
        email = (EditText) findViewById(R.id.email);

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserData();
                Intent intent = new Intent(Gaip.this, MainPage.class);
                startActivity(intent);
            }
        });

        /*
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gaip.this, MainPage.class);
                startActivity(intent);
            }
        });
        */

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
        String userPassword_chk = password_chk.getText().toString();
        String userEmail = email.getText().toString();

        // 유효성 검사 (예: 빈 문자열 확인)

        // 사용자 객체 생성
        GaipUser gaipuser = new GaipUser(userId, userPassword, userPassword_chk, userEmail);

        // Firestore에 사용자 정보 저장
        db.collection("GaipInfo")
                .document(userId)
                .set(gaipuser)
                .addOnSuccessListener(aVoid -> {
                    // 성공적으로 저장되었을 때의 처리
                    id.setText("");
                    password.setText("");
                    password_chk.setText("");
                    email.setText("");
                    // 추가적인 로직을 여기에 추가할 수 있습니다.
                })
                .addOnFailureListener(e -> {
                    // 저장 실패 시의 처리
                    // 추가적인 로직을 여기에 추가할 수 있습니다.
                });
    }
}
