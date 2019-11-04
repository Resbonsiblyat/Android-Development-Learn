package com.casper.model;

import android.content.Context;

import com.casper.testdrivendevelopment.Book;
import com.casper.testdrivendevelopment.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class BookModel {
    ArrayList<Book> bookList = new ArrayList<>();
    Context context;
    public BookModel(Context context){
        this.context = context;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput("Serializable.txt"));
            bookList = (ArrayList<Book>) inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bookList.add(new Book(R.drawable.book_1,"信息安全数学基础"));
            bookList.add(new Book(R.drawable.book_2,"软件项目管理案例教程"));
            bookList.add(new Book(R.drawable.book_no_name,"创新工程实践"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void create(int position , String title){
        bookList.add(position+1,new Book(R.drawable.book_no_name,title));
    }

    public void delete(int position){
        bookList.remove(position);
    }

    public void edit(int position , String title){
        bookList.get(position).setStrTitle(title);
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void save(){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream( context.openFileOutput("Serializable.txt", Context.MODE_PRIVATE));
            outputStream.writeObject(bookList);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
