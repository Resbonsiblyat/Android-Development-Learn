package com.casper.testdrivendevelopment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private int resourceID;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Book book = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID, parent, false);
        ImageView bookCoverImage = view.findViewById(R.id.image_view_book_cover);
        TextView bookTitleText = view.findViewById(R.id.text_view_book_title);
        bookCoverImage.setImageResource(book.getCoverResourceID());
        bookTitleText.setText(book.getStrTitle());
        return view;
    }

    public BookAdapter(Context context, int textViewResourceID, List<Book> objects) {
        super(context, textViewResourceID, objects);
        resourceID = textViewResourceID;


    }

}
