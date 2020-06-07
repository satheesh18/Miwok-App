package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView number = (TextView) findViewById(R.id.numbers);
        number.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent num=new Intent(MainActivity.this,NumbersActivity.class);
                startActivity(num);
            }
        });

        TextView color =(TextView) findViewById((R.id.colors));
        color.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent col=new Intent(MainActivity.this,ColorsActivity.class);
                startActivity(col);
            }
        });
      TextView phrase=(TextView) findViewById(R.id.phrases);
      phrase.setOnClickListener(new View.OnClickListener(){
          public void onClick(View view){
              Intent phr=new Intent(MainActivity.this,PhrasesActivity.class);
              startActivity(phr);
          }
      });
      TextView fam=(TextView) findViewById(R.id.family);
      fam.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
              Intent fa = new Intent(MainActivity.this, FamilyActivity.class);
              startActivity(fa);
          }
      });
    }

}
