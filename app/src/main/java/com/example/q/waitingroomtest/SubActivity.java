package com.example.q.waitingroomtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import io.socket.client.IO;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SubActivity extends AppCompatActivity {

    private int room_number;
    private Button readyButton;
    private TextView nameBox1;
    private TextView nameBox2;
    private TextView nameBox3;
    private TextView nameBox4;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        readyButton = (Button)findViewById(R.id.readycancle);


        MainActivity.mSocket.on("gogameserver",gogameserver);
        readyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                JSONObject data = new JSONObject();
                try {
                    data.put("room", room_number);
                    MainActivity.mSocket.emit("gamestart",data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        nameBox1 = (TextView)findViewById(R.id.player1);
        nameBox2 = (TextView)findViewById(R.id.player2);
        nameBox3 = (TextView)findViewById(R.id.player3);
        nameBox4 = (TextView)findViewById(R.id.player4);
        room_number = getIntent().getIntExtra("room",1);
    }
    @Override
    protected void onStart(){
        super.onStart();
        JSONObject data = new JSONObject();
        try {
            data.put("room", room_number);
            MainActivity.mSocket.emit("intextroom",data);
            MainActivity.mSocket.on("fillnamebox",setName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        JSONObject data = new JSONObject();
        try {
            data.put("room", room_number);
            MainActivity.mSocket.emit("exitroom",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener setName = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject receivedData = (JSONObject) args[0];
            String player1name = "Empty";
            String player2name = "Empty";
            String player3name = "Empty";
            String player4name = "Empty";
            try {
                player1name = receivedData.getString("player1");
                player2name = receivedData.getString("player2");
                player3name = receivedData.getString("player3");
                player4name = receivedData.getString("player4");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            nameBox1.setText(player1name);
            nameBox2.setText(player2name);
            nameBox3.setText(player3name);
            nameBox4.setText(player4name);
        }
    };

    private Emitter.Listener gogameserver = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            System.out.println("GO New Game!");
            try {
                MainActivity.mSocket.disconnect();
                MainActivity.mSocket = IO.socket("http://143.248.140.106:2180/game"+room_number);
                MainActivity.mSocket.connect();
                Intent intent=new Intent(SubActivity.this, GameActivity.class);
                intent.putExtra("room",room_number);
                startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    };

}
