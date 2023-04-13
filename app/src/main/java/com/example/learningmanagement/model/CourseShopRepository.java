package com.example.learningmanagement.model;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// make each model repository separately

public class CourseShopRepository {

    // instances
    private CategoryDAO categoryDAO;
    private CourseDAO courseDAO;

    // instances of live data
    private LiveData<List<Category>> categories;
    private LiveData<List<Course>> courses;

    // constructor
    public CourseShopRepository(Application application) {
        // db instances
        CourseDatabase courseDatabase = CourseDatabase.getInstance(application);
        categoryDAO = courseDatabase.categoryDAO();
        courseDAO = courseDatabase.courseDAO();
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDAO.getAllCategories();
    }

    public LiveData<List<Course>> getCourses(int categoryId) {
        return courseDAO.getCourses(categoryId);
    }

    // method to insert category
    public void insertCategory(Category category) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background execution
                // Insert categories
                categoryDAO.insert(category);
                // post execution - do after background is executed
            }
        });
    }

    // method to insert course
    public void insertCourse(Course course) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background execution
                // Insert categories
                courseDAO.insert(course);
                // post execution - do after background is executed
            }
        });
    }

    // method to insert course
    public void deleteCategory(Category category) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background execution
                // Insert categories
                categoryDAO.delete(category);
                // post execution - do after background is executed
            }
        });
    }

    // method to insert course
    public void deleteCourse(Course course) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background execution
                // Insert categories
                courseDAO.delete(course);
                // post execution - do after background is executed
            }
        });
    }

    // method to insert course
    public void updateCategory(Category category) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background execution
                // Insert categories
                categoryDAO.update(category);
                // post execution - do after background is executed
            }
        });
    }

    // method to insert course
    public void updateCourse(Course course) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background execution
                // Insert categories
                courseDAO.update(course);
                // post execution - do after background is executed
            }
        });
    }
}
