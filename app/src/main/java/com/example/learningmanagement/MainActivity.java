package com.example.learningmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.learningmanagement.databinding.ActivityMainBinding;
import com.example.learningmanagement.model.Category;
import com.example.learningmanagement.model.Course;
import com.example.learningmanagement.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // creating instances
    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<Category> categoriesList;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandler handlers;
    private Category selectedCategory;

    // RecyclerView
    private RecyclerView courseRecyclerView;
    private CourseAdapter courseAdapter;
    private ArrayList<Course> coursesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handlers = new MainActivityClickHandler();
        activityMainBinding.setClickHandlers(handlers);

        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoriesList = (ArrayList<Category>) categories;
                for (Category category: categories) {
                    Log.i("TAG", category.getCategoryName());
                }
                showOnSpinner();
            }
        });

        mainActivityViewModel.getCoursesOfSelectedCategory(1).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                for (Course course: courses) {
                    Log.v("TAG", course.getCourseName());
                }
            }
        });
    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                categoriesList
        );
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    public void loadCoursesArrayList(int categoryId) {
        mainActivityViewModel.getCoursesOfSelectedCategory(categoryId).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                coursesList = (ArrayList<Course>) courses;
                LoadRecyclerView();
            }
        });
    }

    private void LoadRecyclerView() {
        courseRecyclerView = activityMainBinding.secondaryLayout.recyclerView;
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseRecyclerView.setHasFixedSize(true);

        courseAdapter = new CourseAdapter();
        courseRecyclerView.setAdapter(courseAdapter);

        courseAdapter.setCourses(coursesList);
    }


    public class MainActivityClickHandler {
        public void onFabClicked(View view) {
            Toast.makeText(getApplicationContext(), "FAB Clicked", Toast.LENGTH_SHORT).show();
        }

        public void onSelectItem(AdapterView<?> parent,View view, int pos, long id) {
            selectedCategory = (Category) parent.getItemAtPosition(pos);
            String message = "id is: " + selectedCategory.getId() +
                    "\n name is " + selectedCategory.getCategoryName();
            Toast.makeText(parent.getContext(), message, Toast.LENGTH_SHORT).show();

            loadCoursesArrayList(selectedCategory.getId());
        }
    }
}