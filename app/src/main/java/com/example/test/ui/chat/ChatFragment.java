package com.example.test.ui.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.data.model.Message;
import com.example.test.data.network.ApiClient;
import com.example.test.data.network.ApiService;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText messageEditText;
    private Button buttonSend;

    private MessageAdapter adapter;
    private ArrayList<Message> messages = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // test data
        messages.add(new Message("1", true, "Hi!"));
        messages.add(new Message("1", false, "Hello."));
        messages.add(new Message("1", true, "How are U?"));
        messages.add(new Message("1", false, "I'm fine, thank you)"));
        messages.add(new Message("1", false, "And you?"));
        messages.add(new Message("1", true, "I'm fine too, thanks."));

        recyclerView = view.findViewById(R.id.rv_chat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);

        messageEditText = view.findViewById(R.id.et_message);
        buttonSend = view.findViewById(R.id.btn_send);
        buttonSend.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
            messages.add(new Message("1", true, messageEditText.getText().toString()));
            recyclerView.scrollToPosition(messages.size() + 1);
            adapter.notifyDataSetChanged();
            messageEditText.setText("");
        });
    }
}
