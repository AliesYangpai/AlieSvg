package org.alie.aliesvg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import org.alie.aliesvg.view.AlieMapView;
import org.alie.aliesvg.view.OnMapClickListener;
import org.alie.aliesvg.view.Province;

public class MainActivity extends AppCompatActivity {

    private AlieMapView amv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amv = findViewById(R.id.amv);
        amv.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onClick(Province province) {
                Toast.makeText(MainActivity.this,province.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
