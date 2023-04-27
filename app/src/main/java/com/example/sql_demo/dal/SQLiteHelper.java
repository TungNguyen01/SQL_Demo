package com.example.sql_demo.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sql_demo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="ChiTieu.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="CREATE TABLE items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                " category TEXT, " +
                "price TEXT, " +
                "date TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //getAll order by date
    public List<Item> getAll(){
        List<Item> list= new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = st.query("items",null,null,null,null,null, order);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Item(id,title,category,price,date));
        }
        return list;
    }

    //add
    public long addItem(Item item){
        ContentValues values = new ContentValues();
        values.put("title",item.getTitle());
        values.put("category",item.getCategory());
        values.put("price",item.getPrice());
        values.put("date",item.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("items",null,values);
    }

    //update
    public int update(Item item){
        ContentValues values = new ContentValues();
        values.put("title",item.getTitle());
        values.put("category",item.getCategory());
        values.put("price",item.getPrice());
        values.put("date",item.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {item.getId()+""};
        return sqLiteDatabase.update("items",values,whereClause,whereArgs);
    }

    //delete
    public int delete(int id){
        String whereClause = "id = ?";
        String[] whereArgs = {id+""};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("items",whereClause,whereArgs);
    }

    //lay cac item theo date
    public List<Item> getByDate(String date){
        List<Item> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items",null,whereClause,whereArgs,null,null,null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            list.add(new Item(id,title,category,price,date));
        }
        return list;
    }

    // lay cac item theo title
    public List<Item> searchByTitle(String key){
        List<Item> list = new ArrayList<>();
        String whereClause = "title like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items",null,whereClause,whereArgs,null,null,null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date= rs.getString(4);
            list.add(new Item(id,title,category,price,date));
        }
        return list;
    }

    // lay cac item theo category
    public List<Item> searchByCategory(String category){
        List<Item> list = new ArrayList<>();
        String whereClause = "category like ?";
        String[] whereArgs = {category};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items",null,whereClause,whereArgs,null,null,null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String c = rs.getString(2);
            String price = rs.getString(3);
            String date= rs.getString(4);
            list.add(new Item(id,title,c,price,date));
        }
        return list;
    }

    // lay cac item trong khoang thoi gian
    public List<Item> searchByDate(String from, String to){
        List<Item> list = new ArrayList<>();
        String whereClause = "date between ? and ?";
        String[] whereArgs = {from.trim(),to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items",null,whereClause,whereArgs,null,null,null);
        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String c = rs.getString(2);
            String price = rs.getString(3);
            String date= rs.getString(4);
            list.add(new Item(id,title,c,price,date));
        }
        return list;
    }
}
