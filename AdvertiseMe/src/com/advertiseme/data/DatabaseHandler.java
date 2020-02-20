package com.advertiseme.data;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "ADVERTISEME_DB";

	// Favorite Seller table name
	private static final String TABLE_FAVORITE_SELLER = "ADVERT_FAVORITE_SELLER";
	private static final String TABLE_WATCH_LIST = "ADVERT_WATCH_LIST";
	private static final String TABLE_ACTIVE_LIST = "ADVERT_ACTIVE_LIST";
	private static final String TABLE_SAVED_SEARCH = "ADVERT_SAVED_SEARCH";

	// Favorite Seller Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_USER_ID = "userid";
	private static final String KEY_SELLER_ID = "sellerid";
	private static final String KEY_ADVERT_ID = "advertiD";
	private static final String KEY_SEARCH_QUERY = "searchQuery";
	private static final String KEY_CONDITION_FLG = "conditionFlg";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_FAVORITE_SELLER_TABLE = "CREATE TABLE "
				+ TABLE_FAVORITE_SELLER + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + KEY_USER_ID + " TEXT,"
				+ KEY_SELLER_ID + " TEXT" + ")";
		db.execSQL(CREATE_FAVORITE_SELLER_TABLE);

		String CREATE_WATCH_LIST_TABLE = "CREATE TABLE " + TABLE_WATCH_LIST
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID
				+ " TEXT," + KEY_ADVERT_ID + " TEXT" + ")";
		db.execSQL(CREATE_WATCH_LIST_TABLE);
		
		String CREATE_ACTIVE_LIST_TABLE = "CREATE TABLE " + TABLE_ACTIVE_LIST
		+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID
		+ " TEXT," + KEY_ADVERT_ID + " TEXT" + ")";
		db.execSQL(CREATE_ACTIVE_LIST_TABLE);
		
		String CREATE_SAVED_SEARCH_TABLE = "CREATE TABLE " + TABLE_SAVED_SEARCH
		+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID
		+ " TEXT," + KEY_SEARCH_QUERY + " TEXT," + KEY_CONDITION_FLG + " TEXT"+ ")";
		db.execSQL(CREATE_SAVED_SEARCH_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
		
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	/*
	 * -------------------------- Favorite Seller
	 * ---------------------------------
	 */

	// Adding new favorite seller
	public void addFavoriteSeller(String userID, String sellerID) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, userID); // User ID
		values.put(KEY_SELLER_ID, sellerID); // Seller ID

		// Inserting Row
		db.insert(TABLE_FAVORITE_SELLER, null, values);
		db.close(); // Closing database connection
	}

	// Getting favorite sellers
	public ArrayList<ArrayList<String>> getFavoriteSeller(String userID) {

		ArrayList<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
		ArrayList<String> resultRow = null;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db
				.query(TABLE_FAVORITE_SELLER, new String[] { KEY_ID,
						KEY_USER_ID, KEY_SELLER_ID }, KEY_USER_ID + "=?",
						new String[] { userID }, null, null,
						null, null);
		if (cursor.moveToFirst())

			do {
				resultRow = new ArrayList<String>();
				for (int count = 0; count < cursor.getColumnCount(); count++) {
					if (cursor.getString(count) != null) {
						resultRow.add(cursor.getString(count));
					}
				}
				// Adding result row too list
				resultList.add(resultRow);
			} while (cursor.moveToNext());

	
		return resultList;
	}

	// Deleting favorite sellers
	public void deleteFavoriteSeller(String userID, String sellerID) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_FAVORITE_SELLER, KEY_USER_ID + " = ?" + "AND "
				+ KEY_SELLER_ID + " = ?", new String[] {
				String.valueOf(userID), String.valueOf(sellerID) });
		db.close();
	}

	/* -------------------------- Watch List --------------------------------- */

	public void addWatchListItem(String userID, String advertID) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, userID); // User ID
		values.put(KEY_ADVERT_ID, advertID); // Seller ID

		// Inserting Row
		db.insert(TABLE_WATCH_LIST, null, values);
		db.close(); // Closing database connection
	}

	public ArrayList<ArrayList<String>> getWatchList(String userID) {

		ArrayList<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
		ArrayList<String> resultRow = null;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db
				.query(TABLE_WATCH_LIST, new String[] { KEY_ID, KEY_USER_ID,
						KEY_ADVERT_ID }, KEY_USER_ID + "=?",
						new String[] { String.valueOf(userID) }, null, null,
						null, null);
		if (cursor.moveToFirst())
   
			do {
				resultRow = new ArrayList<String>();
				for (int count = 0; count < cursor.getColumnCount(); count++) {
					if (cursor.getString(count) != null) {
						resultRow.add(cursor.getString(count));
					}
				}
				// Adding result row too list
				resultList.add(resultRow);
			} while (cursor.moveToNext());

		return resultList;
	}

	public void deleteFromWatchList(String userID, String advvertID) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_WATCH_LIST, KEY_USER_ID + " = ?" + "AND "
				+ KEY_ADVERT_ID + " = ?", new String[] {
				String.valueOf(userID), String.valueOf(advvertID) });
		db.close();
	}

	public int getWishListCount(String userID) {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db
				.query(TABLE_WATCH_LIST, new String[] { KEY_ID, KEY_USER_ID,
						KEY_ADVERT_ID }, KEY_USER_ID + "=?",
						new String[] { String.valueOf(userID) }, null, null,
						null, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		return cursor.getCount();
	}
	
	/* -------------------------- Active List --------------------------------- */

	public void addActiveListItem(String userID, String advertID) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, userID); // User ID
		values.put(KEY_ADVERT_ID, advertID); // Seller ID

		// Inserting Row
		db.insert(TABLE_ACTIVE_LIST, null, values);
		db.close(); // Closing database connection
	}
	
	public ArrayList<ArrayList<String>> getActiveList(String userID) {

		ArrayList<ArrayList<String>> resultList = new ArrayList<ArrayList<String>>();
		ArrayList<String> resultRow = null;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db
				.query(true,TABLE_ACTIVE_LIST, new String[] { KEY_ID, KEY_USER_ID,
						KEY_ADVERT_ID }, KEY_USER_ID + "=?",
						new String[] { String.valueOf(userID) }, null, null,
						null, null);
		if (cursor.moveToFirst())

			do {
				resultRow = new ArrayList<String>();
				for (int count = 0; count < cursor.getColumnCount(); count++) {
					if (cursor.getString(count) != null) {
						resultRow.add(cursor.getString(count));
					}
				}
				// Adding result row too list
				resultList.add(resultRow);
			} while (cursor.moveToNext());

		return resultList;
	}
	public void deleteFromActiveList(String userID, String advvertID) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ACTIVE_LIST, KEY_USER_ID + " = ?" + "AND "
				+ KEY_ADVERT_ID + " = ?", new String[] {
				String.valueOf(userID), String.valueOf(advvertID) });
		db.close();
	}
	public int getActiveListCount(String userID) {

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db
				.query(true,TABLE_ACTIVE_LIST, new String[] {  KEY_USER_ID,
						KEY_ADVERT_ID }, KEY_USER_ID + "=?",
						new String[] { String.valueOf(userID) }, null, null,
						null, null);

		// return count
		int count = cursor.getCount();
		cursor.close();
		return cursor.getCount();
	}
	/* -------------------------- Saved Search --------------------------------- */

	public void addSavedSearchItem(String userID, String qString) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, userID); // User ID
		values.put(KEY_SEARCH_QUERY, qString); // Seller ID
		values.put(KEY_CONDITION_FLG, "1");
		
		// Inserting Row
		db.insert(TABLE_SAVED_SEARCH, null, values);
		db.close(); // Closing database connection
	}
	
	public ArrayList<ArrayList> getSavedSearch(String userID) {

		ArrayList<ArrayList> resultList = new ArrayList<ArrayList>();
		ArrayList<String> resultRow = null;

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db
				.query(true,TABLE_SAVED_SEARCH, new String[] { KEY_USER_ID,
						KEY_SEARCH_QUERY,KEY_CONDITION_FLG }, KEY_USER_ID + "=?",
						new String[] { String.valueOf(userID) }, null, null,
						null, null);
		if (cursor.moveToFirst())

			do {
				resultRow = new ArrayList<String>();
				for (int count = 0; count < cursor.getColumnCount(); count++) {
					if (cursor.getString(count) != null) {
						resultRow.add(cursor.getString(count));
					}
				}
				// Adding result row too list
				resultList.add(resultRow);
			} while (cursor.moveToNext());

		return resultList;
	}
	public void deleteFromSavedSearchList(String userID, String qString) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SAVED_SEARCH, KEY_USER_ID + " = ?" + "AND "
				+ KEY_SEARCH_QUERY + " = ?", new String[] {
				String.valueOf(userID), String.valueOf(qString) });
		db.close();
	}
}