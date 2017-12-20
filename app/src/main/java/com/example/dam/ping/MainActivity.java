package com.example.dam.ping;

import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainActivity extends AppCompatActivity {

    private Button btPing, btClear, btIp;
    private TextView tvPing, tvIp;
    private EditText etPing;

    private void doPing(String ping) {
        ProcessBuilder pb = new ProcessBuilder().command("/system/bin/ping", "-c 5", ping);

        try {
            Process process = pb.start();

            InputStream processStdOutput = process.getInputStream();
            Reader r = new InputStreamReader(processStdOutput);
            BufferedReader br = new BufferedReader(r);
            String linea;
            while ((linea = br.readLine()) != null) {
                tvPing.append(linea + "\n");
            }
        } catch (IOException ioe) {
            ioe.getMessage();
            tvPing.setText("Error al ejecutar");
        }
    }

    private void events(){
        btPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ping = etPing.getText().toString();
                doPing(ping);
            }
        });

        btIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIp();
            }
        });

        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPing.setText("");
                tvIp.setText("");
                etPing.setText("");
            }
        });
    }

    private void getIp(){
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        tvIp.setText(ip);
    }

    private void init() {
        btPing = findViewById(R.id.btPing);
        btIp = findViewById(R.id.btIp);
        tvPing = findViewById(R.id.tvPing);
        tvIp = findViewById(R.id.tvIp);
        btClear = findViewById(R.id.btClear);
        etPing = findViewById(R.id.etPing);

        events();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }
}