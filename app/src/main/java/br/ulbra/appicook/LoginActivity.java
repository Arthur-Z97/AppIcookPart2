package br.ulbra.appicook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button entrar;
    EditText eemail, esenha;
    TextView terro, cad;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        IniciarComponentes();

        cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = eemail.getText().toString();
                String senha = esenha.getText().toString();

                if (email.isEmpty() || senha.isEmpty()) {
                    terro.setText("Preencha todos os campos!");
                } else {
                    terro.setText("");
                    AutenticarUsuario();
                    bar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void AutenticarUsuario() {

        String email = eemail.getText().toString();
        String senha = esenha.getText().toString();


        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    bar.setVisibility(View.VISIBLE);


                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            IniciarTelaProdutos();
                        }
                    }, 3000);
                } else {
                    String erro;

                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        erro = "Erro ao logar usuário!";
                    }
                    terro.setText(erro);
                }
            }
        });
    }

    public void IniciarTelaProdutos() {
        Intent intent = new Intent(LoginActivity.this, Lista_Produtos.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioAtual != null) {

            IniciarTelaProdutos();
        } else {
            bar.setVisibility(View.INVISIBLE);
        }
    }

    public void IniciarComponentes() {
        cad = findViewById(R.id.txtCad);
        eemail = findViewById(R.id.edEmail);
        esenha = findViewById(R.id.edPassword);
        entrar = findViewById(R.id.btEntrar);
        terro = findViewById(R.id.txt_mensagemErro);
        bar = findViewById(R.id.progressbar);
    }
}



