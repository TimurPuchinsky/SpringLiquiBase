package com.example.springliquidbase.infrastructure.repository;

import io.ebean.Database;

public class DbModel {

    private Database db;

    public DbModel(Database database) {
        db = database;
    }

    public Database getDb() {
        return db;
    }

    public void setDb(Database db) {
        this.db = db;
    }
}
