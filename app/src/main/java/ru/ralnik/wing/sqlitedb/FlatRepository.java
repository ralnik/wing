package ru.ralnik.wing.sqlitedb;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.ralnik.wing.model.Flat;

public class FlatRepository {
    private FlatDao mFlatDao;
    public FlatRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mFlatDao = db.flatDao();
    }

    public Cursor getFlatsByQuery(String query){
        try {
            return new getFlatsByQueryAsyncTask(mFlatDao).execute(query).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void insert(final Flat flat){
        //new insertAsyncTask(mFlatDao).execute(flat);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mFlatDao.insert(flat);
            }
        }).start();

    }

    public void update(Flat flat){
        new updateAsyncTask(mFlatDao).execute(flat);
    }

    public List<Flat> findByIds(String[] strings){
        try {
            return new findByIdsAsyncTask(mFlatDao).execute(strings).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Flat findById(String ArticleId){
        try {
            return new findByIdAsyncTask(mFlatDao).execute(ArticleId).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Flat> getAll(){
        try {
            return new getAllAsyncTask(mFlatDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cursor getFlats(){
        try {
            return new getFlatsAsyncTask(mFlatDao).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Flat flat){
        new deleteAsyncTask(mFlatDao).execute(flat);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(mFlatDao).execute();
    }

    public Object getMin(String param){
        try {
            return new getMinAsyncTask(mFlatDao).execute(param).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getMax(String param){
        try {
            return new getMaxAsyncTask(mFlatDao).execute(param).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    //************--------============ PUBLIC CLASSES ==========-------------------*****************//
    public class getFlatsByQueryAsyncTask extends AsyncTask<String,Void, Cursor>{
        private FlatDao mAsyncFlatDao;
        public getFlatsByQueryAsyncTask(FlatDao mAsyncFlatDao) {
            this.mAsyncFlatDao = mAsyncFlatDao;
        }

        @Override
        protected Cursor doInBackground(String... param) {
            SimpleSQLiteQuery query = new SimpleSQLiteQuery(param[0]);
            return mAsyncFlatDao.getFlatsByQuery(query);
        }
    }


    public class getMinAsyncTask extends AsyncTask<String,Void,Object>{
        private FlatDao mAsyncFlatDao;

        public getMinAsyncTask(FlatDao mAsyncFlatDao) {
            this.mAsyncFlatDao = mAsyncFlatDao;
        }
        @Override
        protected Object doInBackground(String... param) {
            if(param[0].equals("Floor")){
                return mAsyncFlatDao.getMinFloor();
            }
            if(param[0].equals("Cost")){
                return mAsyncFlatDao.getMinCost();
            }
            if(param[0].equals("Square")){
                return mAsyncFlatDao.getMinSquare();
            }
            if(param[0].equals("Budget")){
                return mAsyncFlatDao.getMinBudget();
            }
            return 0;
        }
    }

    public class getMaxAsyncTask extends AsyncTask<String,Void,Object>{
        private FlatDao mAsyncFlatDao;

        public getMaxAsyncTask(FlatDao mAsyncFlatDao) {
            this.mAsyncFlatDao = mAsyncFlatDao;
        }
        @Override
        protected Object doInBackground(String... param) {
            if(param[0].equals("Floor")){
                return mAsyncFlatDao.getMaxFloor();
            }
            if(param[0].equals("Cost")){
                return mAsyncFlatDao.getMaxCost();
            }
            if(param[0].equals("Square")){
                return mAsyncFlatDao.getMaxSquare();
            }
            if(param[0].equals("Budget")){
                return mAsyncFlatDao.getMaxBudget();
            }
            return 0;
        }
    }

    public class getFlatsAsyncTask extends AsyncTask<Void,Void,Cursor>{
        private FlatDao mAsyncFlatDao;

        public getFlatsAsyncTask(FlatDao mAsyncFlatDao) {
            this.mAsyncFlatDao = mAsyncFlatDao;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mAsyncFlatDao.getFlats();
        }
    }

    public class insertAsyncTask extends AsyncTask<Flat, Void, Void>{
        private FlatDao mAsyncFlatDao;

        public insertAsyncTask(FlatDao mAsyncFlatDao) {
            this.mAsyncFlatDao = mAsyncFlatDao;
        }

        @Override
        protected Void doInBackground(final Flat... param) {
            mAsyncFlatDao.insertAll(param);
            return null;
        }
    }

    public class updateAsyncTask extends AsyncTask<Flat, Void, Void>{
        private FlatDao mAsyncFlatDao;

        public updateAsyncTask(FlatDao mAsyncFlatDao) {
            this.mAsyncFlatDao = mAsyncFlatDao;
        }

        @Override
        protected Void doInBackground(final Flat... param) {
            Flat flat =param[0];
            mAsyncFlatDao.update(
                    flat.getId(),
                    //flat.getId_flat(),
                    flat.getNom_kv(),
                    flat.getCorpus(),
                    flat.getEtag(),
                    flat.getComnat(),
                    flat.getPloshad(),
                    flat.getPrice(),
                    flat.getStatus(),
                    flat.getPlanirovka()
            );
            return null;
        }
    }

    private class findByIdsAsyncTask extends AsyncTask<String[], Void, List<Flat>> {
        private FlatDao mAsyncFlatDao;

        public findByIdsAsyncTask(FlatDao mAsyncFlatDao) {
            this.mAsyncFlatDao = mAsyncFlatDao;
        }

        @Override
        protected List<Flat> doInBackground(String[]... param) {
            return mAsyncFlatDao.FindByIds(param[0]);
        }
    }

    private class findByIdAsyncTask extends AsyncTask<String, Void, Flat> {
        private FlatDao mAsyncFlatDao;

        public findByIdAsyncTask(FlatDao mAsyncFlatDao) {
            this.mAsyncFlatDao = mAsyncFlatDao;
        }

        @Override
        protected Flat doInBackground(String... param) {
            return mAsyncFlatDao.findById(param[0]);
        }
    }

    private class getAllAsyncTask extends AsyncTask<Void, Void, List<Flat>>{
        private FlatDao mAsyncFlatDao;

        public getAllAsyncTask(FlatDao mAsyncFlatDao) {
            this.mAsyncFlatDao = mAsyncFlatDao;
        }

        @Override
        protected List<Flat> doInBackground(Void... voids) {
            return mAsyncFlatDao.getAll();
        }
    }

    private class deleteAsyncTask extends AsyncTask<Flat, Void, Void>{
        private FlatDao mAsyncFlatDao;

        public deleteAsyncTask(FlatDao mAsyncFlatDao) {
            this.mAsyncFlatDao = mAsyncFlatDao;
        }

        @Override
        protected Void doInBackground(Flat... param) {
            this.mAsyncFlatDao.delete(param[0]);
            return null;
        }
    }

    private class deleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private FlatDao mAsyncFlatDao;

        public deleteAllAsyncTask(FlatDao mAsyncFlatDao) {
            this.mAsyncFlatDao = mAsyncFlatDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            this.mAsyncFlatDao.deleteAll();
            return null;
        }
    }
}
