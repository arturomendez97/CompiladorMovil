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

import com.example.myapplication.TokenAsignaciones.TokenAsignaciones;
import com.example.myapplication.TokenAsignaciones.MaquinaVirtual;

public class MainActivity extends AppCompatActivity {
    TextInputLayout inputText;
    TextView text;
    Button boton;
    Button boton_tabla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.resultados);
        inputText = (TextInputLayout) findViewById(R.id.codigo);
        boton = (Button) findViewById(R.id.button);
        boton_tabla = (Button) findViewById(R.id.Button_Tabla);

        String Create = "Hola";
        ByteArrayInputStream inicio = new ByteArrayInputStream(Create.getBytes());
        final comp ae =new comp(inicio);

        boton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)  {
                String textoAnalizar = inputText.getEditText().getText().toString();
                ByteArrayInputStream bais = new ByteArrayInputStream(textoAnalizar.getBytes());

                ae.ReInit(bais);


                try {
                    ae.Programa();
                    TokenAsignaciones.reiniciaTodo();
                    text.setText(MaquinaVirtual.getText());
                } catch (ParseException ex){
                    TokenAsignaciones.reiniciaTodo();
                    text.setText(ex.getMessage());
                }catch (TokenMgrError tme){
                    TokenAsignaciones.reiniciaTodo();
                    text.setText(tme.getMessage());
                }

            }
        });


    }


}