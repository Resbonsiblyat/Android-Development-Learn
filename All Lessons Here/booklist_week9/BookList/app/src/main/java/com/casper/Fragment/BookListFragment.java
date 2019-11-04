package com.casper.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.casper.controller.BookController;
import com.casper.testdrivendevelopment.BookAdapter;
import com.casper.testdrivendevelopment.BookListMainActivity;
import com.casper.testdrivendevelopment.EditBookActivity;
import com.casper.testdrivendevelopment.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class BookListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private static final int EDITTITLE = 1;
    private static final int NEWBOOK = 2;
    // TODO: Rename and change types of parameters

    private BookAdapter bookAdapter;
    private BookController bookController;
    private int position;
    private OnFragmentInteractionListener mListener;

    public BookListFragment() {
    //This generator should be empty.
    }

    public static BookListFragment newInstance(/*String param1, String param2*/) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        ListView listViewBooks = view.findViewById(R.id.list_view_books);

        bookController = new BookController(getActivity());
        bookAdapter = new BookAdapter(getActivity(), R.layout.list_item_layout, bookController.getBookList());
        listViewBooks.setAdapter(bookAdapter);

        this.registerForContextMenu(listViewBooks);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateContextMenu(ContextMenu  menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.list_item_context_menu, menu);
    }

    AdapterView.AdapterContextMenuInfo info;
    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ct_create:
                Intent intent_newbook = new Intent(getActivity(), EditBookActivity.class);
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                position = info.position;
                startActivityForResult(intent_newbook,NEWBOOK);
                break;

            case R.id.ct_delete:
                final AlertDialog.Builder deleteDialog =
                        new AlertDialog.Builder(getActivity());
                deleteDialog.setTitle("删除");
                deleteDialog.setMessage("你确定要删除此内容吗?");
                deleteDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                                position = info.position;
                                bookController.delete(new BookController.OnDeleteListener() {
                                    @Override
                                    public void onComplete() {
                                        bookAdapter.notifyDataSetChanged();
                                        Toast.makeText(getActivity(), "删除成功",Toast.LENGTH_SHORT).show();
                                    }
                                },position);
                            }
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
                Toast.makeText(getActivity(), "图书列表",Toast.LENGTH_SHORT).show();
                break;

            case R.id.ct_edit:
                Intent intent = new Intent(getActivity(), EditBookActivity.class);
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                position = info.position;
                intent.putExtra("Title",bookController.getBookList().get(position).getStrTitle());
                startActivityForResult(intent,EDITTITLE);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case EDITTITLE:
                if(resultCode == RESULT_OK){
                    bookController.edit(new BookController.OnEditListener() {
                        @Override
                        public void onComplete() {
                            bookAdapter.notifyDataSetChanged();
                        }
                    },position,data.getStringExtra("Title"));
                }
                else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(getActivity(),"修改失败", Toast.LENGTH_SHORT).show();
                }
                break;

            case NEWBOOK:
                if(resultCode == RESULT_OK){
                    bookController.create(new BookController.OnCreateListener() {
                        @Override
                        public void onComplete() {
                            bookAdapter.notifyDataSetChanged();
                        }
                    },position,data.getStringExtra("Title"));
                }
                else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(getActivity(),"新建失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        bookController.save();
    }
}
