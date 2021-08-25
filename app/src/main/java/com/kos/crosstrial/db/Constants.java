package com.kos.crosstrial.db;

public class Constants {
    public static final String INTENT_STITCH_NAME = "intentStitchName";

    public static final String SORT_ASC = " ASC";
    public static final String SORT_DESC = " DESC";

    public static final String TABLE_STITCHES = "table_Stitches";
    public static final String _ID = "_id";
    public static final String STITCH_NAME = "stitch_name";
    public static final String STITCH_NOTES = "stitch_notes";
    public static final String DB_NAME = "cross_stitch_db.db";
    public static final String KOL_ZAP = "kol_zap";
    public static final String FIRST_START = "first_start";
    public static final int DB_VERSION = 1;


    public static final String TABLE_STITCHES_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_STITCHES + " (" + _ID + " INTEGER PRIMARY KEY," +
            STITCH_NAME + " TEXT," +
            STITCH_NOTES + " TEXT)";
    public static final String  DROP_TABLE_STITCHES = "DROP TABLE IF EXISTS " + TABLE_STITCHES;



    public static final String TABLE_THREADS = "table_threads";

    public static final String NUMBER_NIT = "number_nit";
    public static final String COLOR_NUMBER = "color_number";
    public static final String COLOR_NAME = "color_name";
    public static final String FIRM = "firm";
    public static final String NAME_STITCH = "name_stitch";
    public static final String LENGTH_OSTATOK = "length_ostatok";
    public static final String LENGTH_CURRENT = "length_current";


    public static final String TABLE_THREADS_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_THREADS + " (" + _ID + " INTEGER PRIMARY KEY," +
            NUMBER_NIT + " TEXT," +
            COLOR_NUMBER + " INT," +
            COLOR_NAME + " TEXT," +
            FIRM + " TEXT," +
            NAME_STITCH + " TEXT," +
            LENGTH_OSTATOK + " DOUBLE)";


    public static final String  DROP_TABLE_THREADS = "DROP TABLE IF EXISTS " + TABLE_THREADS;



    public static final String TABLE_CURRENT_THREADS = "table_current_threads";

    public static final String TABLE_CURRENT_THREADS_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_CURRENT_THREADS + " (" + _ID + " INTEGER PRIMARY KEY," +
            NUMBER_NIT + " TEXT," +
            COLOR_NUMBER + " INT," +
            COLOR_NAME + " TEXT," +
            FIRM + " TEXT," +
            NAME_STITCH + " TEXT," +
            LENGTH_OSTATOK + " DOUBLE," +
            LENGTH_CURRENT + " DOUBLE)";

    public static final String  DROP_TABLE_CURRENT_THREADS = "DROP TABLE IF EXISTS " + TABLE_CURRENT_THREADS;


    public static final String TABLE_SET = "table_set";

    public static final String TABLE_SET_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_SET + " (" + _ID + " INTEGER PRIMARY KEY," +
            KOL_ZAP + " INT," +
            FIRST_START + " INT)";
    public static final String  DROP_TABLE_SET = "DROP TABLE IF EXISTS " + TABLE_SET;



    public static final String TABLE_FABRIC = "table_fabric";

    public static final String FIRM_FABRIC = "firm_fabric";
    public static final String NAME_FABRIC = "name_fabric";
    public static final String ARTICUL_FABRIC = "articul_fabric";
    public static final String KAUNT_FABRIC = "kaunt_fabric";
    public static final String COLOR_FABRIC = "color_fabric";
    public static final String MYNUMBER_FABRIC = "myNumber_fabric";



    public static final String TABLE_FABRIC_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_FABRIC + " (" + _ID + " INTEGER PRIMARY KEY," +
            FIRM_FABRIC + " TEXT," +
            NAME_FABRIC + " TEXT," +
            ARTICUL_FABRIC + " TEXT," +
            KAUNT_FABRIC + " TEXT," +
            COLOR_FABRIC + " TEXT," +
            MYNUMBER_FABRIC + " TEXT)";


    public static final String  DROP_TABLE_FABRIC = "DROP TABLE IF EXISTS " + TABLE_FABRIC;



    public static final String TABLE_CUT_FABRIC = "table_cutFabric";

    public static final String FIRM_CUT_FABRIC = "firm_cut_fabric";
    public static final String NAME_CUT_FABRIC = "name_cut_fabric";
    public static final String ARTICUL_CUT_FABRIC = "articul_cut_fabric";
    public static final String CUT_LENGTH = "cut_length";
    public static final String CUT_WIDTH = "cut_width";



    public static final String TABLE_CUT_FABRIC_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_CUT_FABRIC + " (" + _ID + " INTEGER PRIMARY KEY," +
            NAME_CUT_FABRIC + " TEXT," +
            FIRM_CUT_FABRIC + " TEXT," +
            ARTICUL_CUT_FABRIC + " TEXT," +
            CUT_LENGTH + " INT," +
            CUT_WIDTH + " INT)";


    public static final String  DROP_TABLE_CUT_FABRIC = "DROP TABLE IF EXISTS " + TABLE_CUT_FABRIC;
}
