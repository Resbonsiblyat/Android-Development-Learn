package com.casper.testdrivendevelopment;


import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.casper.Fragment.BookListFragment;
import com.casper.controller.BookController;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class BookListMainActivity extends AppCompatActivity implements BookListFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        BookListFragment bookListFragment = new BookListFragment();

       // FragmentManager fm = getSupportFragmentManager();
       // fm.beginTransaction().add(bookListFragment,"book list fragment").commit();

        //非常重要的问题：通过ViewPager绑定的Fragment不需要通过ft绑定，否则会引起IllegalStateException：Can't change tag of fragment 的 bug


        BookFragmentPagerAdapter bookFragmentPagerAdapter = new BookFragmentPagerAdapter(getSupportFragmentManager(),BookFragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        //Tab View Setting
        titles.add("图书");
        fragments.add(bookListFragment);


        bookFragmentPagerAdapter.setFragments(fragments);
        bookFragmentPagerAdapter.setTitles(titles);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(bookFragmentPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //call back interface
        //do something here
    }
}
