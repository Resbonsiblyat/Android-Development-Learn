package com.casper.testdrivendevelopment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;



public class BookListMainActivity extends AppCompatActivity {
    final int EDITTITLE = 1;
    final int CREAT = 2;
    ListView listViewBooks;
    BookAdapter bookAdapter;
    ArrayList<Book> bookList = new ArrayList<>();
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        listViewBooks = findViewById(R.id.list_view_books);
        //ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book(R.drawable.book_1,getString(R.string.book_1)));
        bookList.add(new Book(R.drawable.book_2,getString(R.string.book_2)));
        bookList.add(new Book(R.drawable.book_no_name,getString(R.string.book_no_name)));

        bookAdapter = new BookAdapter(BookListMainActivity.this, R.layout.list_item_layout, bookList);

        listViewBooks.setAdapter(bookAdapter);

        this.registerForContextMenu(listViewBooks);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_item_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        Intent intent = new Intent(BookListMainActivity.this, EditBookActivity.class);
        AdapterView.AdapterContextMenuInfo info;
        switch (item.getItemId()) {
            case R.id.ct_create:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                position = info.position;
                startActivityForResult(intent, CREAT);
                break;

            case R.id.ct_delete:
                final AlertDialog.Builder deleteDialog =
                        new AlertDialog.Builder(BookListMainActivity.this);
                deleteDialog.setTitle("删除");
                deleteDialog.setMessage("你确定要删除此内容吗?");
                deleteDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                                bookList.remove(info.position);
                                bookAdapter.notifyDataSetChanged();
                                Toast.makeText(BookListMainActivity.this, "删除成功",Toast.LENGTH_SHORT).show();}
                        });
                deleteDialog.setNegativeButton("关闭",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                deleteDialog.show();


                break;

            case R.id.ct_about:
                Toast.makeText(BookListMainActivity.this, "图书列表",Toast.LENGTH_SHORT).show();
                break;

            case R.id.ct_edit:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                position = info.position;
                intent.putExtra("Title",bookList.get(position).getTitle());
                startActivityForResult(intent, EDITTITLE);
                break;
    }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case EDITTITLE:
                if(resultCode == RESULT_OK){
                    String strTitle = data.getStringExtra("Title");
                    bookList.get(position).setTitle(strTitle);
                    bookAdapter.notifyDataSetChanged();
                }
                else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(BookListMainActivity.this, "书名获取失败", Toast.LENGTH_SHORT).show();
                }

            case CREAT:
                if(resultCode == RESULT_OK){
                    String strTitle = data.getStringExtra("Title");
                    bookList.add(position,new Book(R.drawable.book_no_name,strTitle));
                    bookAdapter.notifyDataSetChanged();
                }
                else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(BookListMainActivity.this, "新建失败", Toast.LENGTH_SHORT).show();
                }
                default:

        }
    }
}
