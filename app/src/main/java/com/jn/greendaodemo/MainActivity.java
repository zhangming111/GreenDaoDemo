package com.jn.greendaodemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import joker.com.DaoMaster;
import joker.com.DaoSession;
import joker.com.Father;
import joker.com.FatherDao;
import joker.com.Son;
import joker.com.SonDao;

public class MainActivity extends AppCompatActivity {

    private SonDao sonDao;
    private FatherDao fatherDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDb();
        addPerson();
getPerson();
    }

    private void openDb(){
        SQLiteDatabase writableDatabase = new DaoMaster.DevOpenHelper(this, "person.db", null).getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        DaoSession daoSession = daoMaster.newSession();
        sonDao = daoSession.getSonDao();
        fatherDao = daoSession.getFatherDao();



    }


    private void addPerson(){
        Son son = new Son();
        son.setAge(18);
        son.setName("joker");

        Father father = new Father();
        father.setAge(45);
        father.setName("haven");


        long fatherId = fatherDao.insert(father);

        son.setFatherId(fatherId);


        sonDao.insert(son);

    }

    private void getPerson(){
        SQLiteDatabase readableDatabase = new DaoMaster.DevOpenHelper(this, "person.db", null).getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(readableDatabase);
        DaoSession daoSession = daoMaster.newSession();
        SonDao sonDao = daoSession.getSonDao();
        QueryBuilder<Son> sonQueryBuilder = sonDao.queryBuilder();
        List<Son> list = sonQueryBuilder.list();
        for (Son son : list) {
            Log.e("son","name="+son.getName()+" age="+son.getAge());
        }
    }

}
