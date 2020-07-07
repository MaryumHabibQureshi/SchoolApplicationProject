package com.example.schoolapplicationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.schoolapplicationproject.Model.Chat;
import com.example.schoolapplicationproject.RecyclerViews.ChatAdapter;
import com.example.schoolapplicationproject.databinding.ActivityChatBinding;
import com.example.schoolapplicationproject.databinding.ChatContentMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private ChatContentMainBinding binding;
    private ActivityChatBinding chatbinding;


    ChatAdapter mAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        startListeningForMessages();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatbinding = ActivityChatBinding.inflate(getLayoutInflater());
        View view = chatbinding.getRoot();
        setContentView(view);

        mAdapter = new ChatAdapter(mAuth.getCurrentUser().getUid());
        binding.chatRv.setLayoutManager(new LinearLayoutManager(this));
        binding.chatRv.setAdapter(mAdapter);

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(ChatActivity.this);
                String text = binding.messageEt.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    binding.sendingProgress.setVisibility(View.VISIBLE);
                    Chat message = new Chat(currentUser.getUid(), currentUser.getDisplayName(), text);
                    sendMessage(message);
                }
            }
        });


    }

    private void startListeningForMessages() {
        mFirestore.collection("Chat")
                .orderBy("dateSent")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            //an error has occured
                        } else {
                            ArrayList<Chat> messages = (ArrayList<Chat>) snapshots.toObjects(Chat.class);
//                            ArrayList<MessageDTO> messages = new ArrayList<>();

//                            for (DocumentChange dc : snapshots.getDocumentChanges()) {
//                                if (dc.getType() == DocumentChange.Type.ADDED) {
////                                    Log.d(TAG, "New city: " + dc.getDocument().getData());
//                                    messages.add(dc.getDocument().toObject(MessageDTO.class));
//
//                                }
//                            }

                            mAdapter.setData(messages);
                            binding.chatRv.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                        }
                    }
                });
    }

    private void sendMessage(Chat message) {
        mFirestore.collection("Chat")
                .add(message)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        binding.sendingProgress.setVisibility(View.INVISIBLE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(ChatActivity.this, "Message sending failed", Toast.LENGTH_SHORT).show();
                        } else {
                            binding.messageEt.setText("");
                        }
                    }
                });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}