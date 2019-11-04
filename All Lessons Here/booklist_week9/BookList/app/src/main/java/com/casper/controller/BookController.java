package com.casper.controller;

import android.content.Context;

import com.casper.model.BookModel;
import com.casper.testdrivendevelopment.Book;

import java.util.ArrayList;

public class BookController {
    private BookModel bookModel;

    public BookController(Context context) {
        bookModel = new BookModel(context);
    }

    public void save(){
        bookModel.save();
    }

    public void create(OnCreateListener onCreateListener,int position , String title){
        bookModel.create(position , title);
        onCreateListener.onComplete();
    }

    public void delete(OnDeleteListener onDeleteListener,int position){
        bookModel.delete(position);
        onDeleteListener.onComplete();
    }

    public void edit(OnEditListener onEditListener,int position , String title){
        bookModel.edit(position,title);
        onEditListener.onComplete();
    }

    public ArrayList<Book> getBookList() {
        return bookModel.getBookList();
    }

    public interface OnCreateListener{
        void onComplete();
    }

    public interface OnEditListener{
        void onComplete();
    }

    public interface OnDeleteListener{
        void onComplete();
    }

}
