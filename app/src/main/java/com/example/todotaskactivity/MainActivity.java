package com.example.todotaskactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todotaskactivity.Adapter.ToDoAdapter;
import com.example.todotaskactivity.Model.ToDoModel;
import com.example.todotaskactivity.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;

// Ensure the correct import for OnDialogCloseListner
import com.example.todotaskactivity.OnDialogCloseListner;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecylerview;
    private FloatingActionButton fab;
    private DataBaseHelper myDB;
    private List<ToDoModel> mList;
    private ToDoAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecylerview=findViewById(R.id.recyclerview);
        fab=findViewById(R.id.fabe);
        myDB=new DataBaseHelper(MainActivity.this);
        mList=new ArrayList<>();
        adapter=new ToDoAdapter(myDB,MainActivity.this);

        mRecylerview.setHasFixedSize(true);
        mRecylerview.setLayoutManager(new LinearLayoutManager(this));
        mRecylerview.setAdapter(adapter);

        mList=myDB.getAllTasks();
        Collections.reverse(mList);
        adapter.setTask(mList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);

            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecylerview);

    }
    public void onDialogClose(DialogInterface dialogInterface){
        mList=myDB.getAllTasks();
        Collections.reverse(mList);
        adapter.setTask(mList);
        adapter.notifyDataSetChanged();

    }
}


