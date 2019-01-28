package com.example.test.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.example.test.R;
import com.example.test.data.model.Message;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText messageEditText;
    private Button buttonSend;

    private MessageAdapter adapter;
    private ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // test data
        messages.add(new Message("1", true, "Hi!"));
        messages.add(new Message("1", false, "Hello."));
        messages.add(new Message("1", true, "How are U?"));
        messages.add(new Message("1", false, "I'm fine, thank you)"));
        messages.add(new Message("1", false, "And you?"));
        messages.add(new Message("1", true, "I'm fine too, thanks."));

        recyclerView = findViewById(R.id.rv_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);

        messageEditText = findViewById(R.id.et_message);
        buttonSend = findViewById(R.id.btn_send);
        buttonSend.setOnClickListener(v -> {
            ApiClient.getClient().create(ApiService.class).sendMessage(messageEditText.getText().toString());
        });

    }
}
