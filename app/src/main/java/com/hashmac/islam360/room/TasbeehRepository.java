package com.hashmac.islam360.room;

import android.app.Application;
import android.os.AsyncTask;

import com.hashmac.islam360.interfaces.Dao;
import com.hashmac.islam360.model.TasbeehModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TasbeehRepository {
    private Dao dao;


    public TasbeehRepository(Application application) {
        Islam360Database database = Islam360Database.getInstance(application);
        dao = database.dao();
    }

    public void deleteAllTasbeehs() {
        new DeleteAllTasbeehsAsyncTask(dao).execute();
    }

    // creating a method to insert the data to our database.
    public void insert(TasbeehModel model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }


    // creating a method to delete the data in our database.
    public void delete(TasbeehModel model) {
        new DeleteCourseAsyncTask(dao).execute(model);
    }

    public List<TasbeehModel> getAll() {
        try {
            return new GetAllTasbeehsAsyncTask(dao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // we are creating a async task method to insert new course.
    private static class InsertCourseAsyncTask extends AsyncTask<TasbeehModel, Void, Void> {
        private Dao dao;

        private InsertCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TasbeehModel... model) {
            // below line is use to insert our modal in dao.
            dao.insertTasbeeh(model[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteCourseAsyncTask extends AsyncTask<TasbeehModel, Void, Void> {
        private Dao dao;

        private DeleteCourseAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(TasbeehModel... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.deleteTasbeeh(models[0]);
            return null;
        }
    }

    private static class DeleteAllTasbeehsAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllTasbeehsAsyncTask(Dao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllCourses();
            return null;
        }
    }

    private class GetAllTasbeehsAsyncTask extends AsyncTask<Void, Void,List<TasbeehModel>> {
        private Dao dao;

        private GetAllTasbeehsAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected List<TasbeehModel> doInBackground(Void... url) {
            return dao.getAllTasbeeh();
        }
    }



}
