package com.test.firebasetest;
// MainActivity.java
import static java.lang.System.exit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private EditText editTextUserId, editTextPassword;
    private Button buttonSave, buttonExit;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db = FirebaseFirestore.getInstance();

        editTextUserId = findViewById(R.id.editTextUserId);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSave = findViewById(R.id.buttonSave);
        buttonExit = findViewById(R.id.buttonExit);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
    }

    private void exit() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    private void login() {
        String userId = editTextUserId.getText().toString();
        String password = editTextPassword.getText().toString();

        // 입력된 아이디를 기준으로 회원 가입 데이터베이스에서 사용자 정보를 검색
        db.collection("GaipInfo")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // 해당 아이디의 사용자 정보를 가져옴
                        User user = documentSnapshot.toObject(User.class);

                        // 비밀번호 검증
                        if (user.getPassword().equals(password)) {
                            // 로그인 성공
                            Toast.makeText(Login.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            // 로그인 성공 후 처리할 로직을 추가할 수 있습니다.
                            // 예를 들어, 메인 페이지로 이동하는 코드 등을 추가할 수 있습니다.
                            Intent intent = new Intent(Login.this, MainPage.class);
                            startActivity(intent);
                        } else {
                            // 비밀번호가 일치하지 않는 경우
                            Toast.makeText(Login.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 해당 아이디의 사용자 정보가 존재하지 않는 경우
                        Toast.makeText(Login.this, "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // 데이터베이스 접근 실패 등의 오류 처리
                    Toast.makeText(Login.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                });
    }
}
