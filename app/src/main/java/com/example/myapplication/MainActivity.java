package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.parser.ParseException;
import com.example.myapplication.parser.TokenMgrError;
import com.example.myapplication.parser.comp;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayInputStream;

public class MainActivity extends AppCompatActivity {
    TextInputLayout inputText;
    TextView text;
    Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.resultados);
        inputText = (TextInputLayout) findViewById(R.id.codigo);



    }

    public void onClick(View view)  {
        String textoAnalizar = inputText.getEditText().getText().toString();
        ByteArrayInputStream bais = new ByteArrayInputStream(textoAnalizar.getBytes());
        comp ae =new comp(bais);
        try {
            ae.Programa();
            text.setText("Correcto");
        } catch (ParseException ex){
            text.setText(ex.getMessage());
        }catch (TokenMgrError tme){
            text.setText(tme.getMessage());
        }
        
    }
}