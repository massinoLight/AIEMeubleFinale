package com.example.massino.aiemeublefinale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private String massino="massino";
    private String light="light";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getApplicationContext(),"bienvenu",Toast.LENGTH_SHORT).show();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        final EditText log;
        final EditText password;

        //les boutons se connecter et s'enregister
        Button submit=findViewById(R.id.log);
        Button registrer=findViewById(R.id.signUp);

        log=(EditText) findViewById(R.id.login);
        password= (EditText) findViewById(R.id.mdp);

        //le clic listener de se connecter il lance le recycler view
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String  ch1= log.getText().toString();
                String  ch2= password.getText().toString();
                if ((ch1.length()<=0)||(ch2.length()<=0))
                {
                    Toast.makeText(getApplicationContext(),"l'un des champs est manquant",Toast.LENGTH_SHORT).show();
                }else{
                    if ((ch1.equals(massino))&&(ch2.equals(light))){


                        Toast.makeText(getApplicationContext(),"bienvenu massino",Toast.LENGTH_SHORT).show();
                        Intent recyclerviewActivity = new Intent(MainActivity.this, com.example.massino.aiemeublefinale.RecyclerActivity.class);
                        startActivity(recyclerviewActivity);

                    }else{
                        Toast.makeText(getApplicationContext(),"Login ou mdp incorrect",Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        //le clic listner de s'enregistrer il lance la page de s'enregister
        registrer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"bienvenu",Toast.LENGTH_SHORT).show();
                Intent signp = new Intent(MainActivity.this, com.example.massino.aiemeublefinale.Signup.class);
                startActivity(signp);

            }
        });



    }
}
