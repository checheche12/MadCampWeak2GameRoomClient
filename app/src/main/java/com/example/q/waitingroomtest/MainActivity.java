package com.example.q.waitingroomtest;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;

public class MainActivity extends AppCompatActivity {

    static public Socket mSocket;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button makenew;
    final String BASIC_ROOM_NAME = "/4 Person Here Room ";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            mSocket = IO.socket("http://143.248.140.106:2180");
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        button1 = findViewById(R.id.waitingroom1);
        button2 = findViewById(R.id.waitingroom2);
        button3 = findViewById(R.id.waitingroom3);
        button4 = findViewById(R.id.waitingroom4);

        button1.setText("0"+BASIC_ROOM_NAME+"1");
        button2.setText("0"+BASIC_ROOM_NAME+"2");
        button3.setText("0"+BASIC_ROOM_NAME+"3");
        button4.setText("0"+BASIC_ROOM_NAME+"4");
        makenew = findViewById(R.id.makenew);
        mSocket.on("waitingroomuser",waitingroomuser);
        button1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Room1");
                JSONObject data = new JSONObject();
                try{
                    data.put("room", 1);
                    mSocket.emit("enterroom",data);
                    Intent intent=new Intent(MainActivity.this,SubActivity.class);
                    intent.putExtra("room",1);
                    startActivity(intent);

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        button2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Room2");
                JSONObject data = new JSONObject();
                try{
                    data.put("room", 2);
                    mSocket.emit("enterroom",data);
                    Intent intent=new Intent(MainActivity.this,SubActivity.class);
                    intent.putExtra("room",2);
                    startActivity(intent);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        button3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Room3");
                JSONObject data = new JSONObject();
                try{
                    data.put("room", 3);
                    mSocket.emit("enterroom",data);
                    Intent intent=new Intent(MainActivity.this,SubActivity.class);
                    intent.putExtra("room",3);
                    startActivity(intent);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        button4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Room4");
                JSONObject data = new JSONObject();
                try{
                    data.put("room", 4);
                    mSocket.emit("enterroom",data);
                    Intent intent=new Intent(MainActivity.this,SubActivity.class);
                    intent.putExtra("room",4);
                    startActivity(intent);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        makenew.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Make New");
                mSocket.emit("refreshmain");
            }
        });
    }

    private Emitter.Listener waitingroomuser = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject receivedData = (JSONObject) args[0];
            String room1 = "0";
            String room2 = "0";
            String room3 = "0";
            String room4 = "0";
            try {
                room1 = receivedData.getString("room1");
                room2 = receivedData.getString("room2");
                room3 = receivedData.getString("room3");
                room4 = receivedData.getString("room4");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            button1.setText(room1+BASIC_ROOM_NAME+"1");
            button2.setText(room2+BASIC_ROOM_NAME+"2");
            button3.setText(room3+BASIC_ROOM_NAME+"3");
            button4.setText(room4+BASIC_ROOM_NAME+"4");
        }
    };
}
