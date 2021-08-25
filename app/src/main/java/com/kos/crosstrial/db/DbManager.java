package com.kos.crosstrial.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.kos.crosstrial.items.Cut;
import com.kos.crosstrial.items.FabricItem;
import com.kos.crosstrial.items.NitNew;
import com.kos.crosstrial.items.StitchItem;

import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb() {
        db = dbHelper.getWritableDatabase();
    }

    public int setKolZapuskov() {
        String selection = Constants._ID + " = ?";
        String[] selectionArgs = {"1"};
        Cursor cur = db.query(Constants.TABLE_SET, null, selection, selectionArgs, null, null, null);
        int kolZap = 0;
        while (cur.moveToNext()) {

            kolZap = cur.getInt(cur.getColumnIndex(Constants.KOL_ZAP));
        }
        cur.close();

        kolZap++;
        ContentValues values = new ContentValues();
        values.put(Constants.KOL_ZAP, kolZap);

        String selection1 = Constants._ID + " LIKE ?";
        String[] selectionArgs1 = {"1"};

        int count = db.update(
                Constants.TABLE_SET,
                values,
                selection1,
                selectionArgs1);
        return kolZap;
    }

    public int getKolZapuskov() {
        String selection = Constants._ID + " = ?";
        String[] selectionArgs = {"1"};
        Cursor cur = db.query(Constants.TABLE_SET, null, selection, selectionArgs, null, null, null);
        int kolZap = 0;
        while (cur.moveToNext()) {

            kolZap = cur.getInt(cur.getColumnIndex(Constants.KOL_ZAP));
        }
        cur.close();
        return kolZap;
    }


    public void insertStitchToDb(String stitchName, String stitchNotes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.STITCH_NAME, stitchName);
        contentValues.put(Constants.STITCH_NOTES, stitchNotes);
        db.insert(Constants.TABLE_STITCHES, null, contentValues);
    }


    public void setFirstStartDate(long firstStart) {
        String selection = Constants._ID + " = ?";
        String[] selectionArgs = {"1"};

        ContentValues values = new ContentValues();
        values.put(Constants.FIRST_START, firstStart);

        int count = db.update(
                Constants.TABLE_SET,
                values,
                selection,
                selectionArgs);

    }

    public long getFirstStartDate() {
        String selection = Constants._ID + " = ?";
        String[] selectionArgs = {"1"};
        Cursor cur = db.query(Constants.TABLE_SET, null, selection, selectionArgs, null, null, null);
        long firstStart=0;
        while (cur.moveToNext()) {

            firstStart = cur.getLong(cur.getColumnIndex(Constants.FIRST_START));
        }
        cur.close();
        return firstStart;
    }

    public void insertThreadToDb(String numberNit, int colorNumber, String colorName, String firm, String nameStitch, double lengthOstatok) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NUMBER_NIT, numberNit);
        contentValues.put(Constants.COLOR_NUMBER, colorNumber);
        contentValues.put(Constants.COLOR_NAME, colorName);
        contentValues.put(Constants.FIRM, firm);
        contentValues.put(Constants.NAME_STITCH, nameStitch);
        contentValues.put(Constants.LENGTH_OSTATOK, lengthOstatok);
        db.insert(Constants.TABLE_THREADS, null, contentValues);
    }

    public void insertFabricToDb(String firmFabric, String nameFabric, String articulFabric, String kauntFabric, String colorFabric, String myNumberFabric) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.FIRM_FABRIC, firmFabric);
        contentValues.put(Constants.NAME_FABRIC, nameFabric);
        contentValues.put(Constants.ARTICUL_FABRIC, articulFabric);
        contentValues.put(Constants.KAUNT_FABRIC, kauntFabric);
        contentValues.put(Constants.COLOR_FABRIC, colorFabric);
        contentValues.put(Constants.MYNUMBER_FABRIC, myNumberFabric);

        db.insert(Constants.TABLE_FABRIC, null, contentValues);
    }

    public void insertCutToDb(String firmFabricCut, String nameFabricCut, String articulint, int cutLength, int cutWidth) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.FIRM_CUT_FABRIC, firmFabricCut);
        contentValues.put(Constants.NAME_CUT_FABRIC, nameFabricCut);
        contentValues.put(Constants.ARTICUL_CUT_FABRIC, articulint);

        contentValues.put(Constants.CUT_LENGTH, cutLength);
        contentValues.put(Constants.CUT_WIDTH, cutWidth);

        db.insert(Constants.TABLE_CUT_FABRIC, null, contentValues);
    }
    public void insertCutToDbLoad(int idCut,String firmFabricCut, String nameFabricCut, String articulint, int cutLength, int cutWidth) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants._ID, idCut);
        contentValues.put(Constants.FIRM_CUT_FABRIC, firmFabricCut);
        contentValues.put(Constants.NAME_CUT_FABRIC, nameFabricCut);
        contentValues.put(Constants.ARTICUL_CUT_FABRIC, articulint);

        contentValues.put(Constants.CUT_LENGTH, cutLength);
        contentValues.put(Constants.CUT_WIDTH, cutWidth);

        db.insert(Constants.TABLE_CUT_FABRIC, null, contentValues);
    }

    public void insertCurrenThreadToDb(String numberNit, int colorNumber, String colorName, String firm, String nameStitch, double lengthCurrent) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.NUMBER_NIT, numberNit);
        contentValues.put(Constants.COLOR_NUMBER, colorNumber);
        contentValues.put(Constants.COLOR_NAME, colorName);
        contentValues.put(Constants.FIRM, firm);
        contentValues.put(Constants.NAME_STITCH, nameStitch);
        contentValues.put(Constants.LENGTH_CURRENT, lengthCurrent);
        db.insert(Constants.TABLE_CURRENT_THREADS, null, contentValues);
    }

    public List<StitchItem> getStitchFromDb() {
        List<StitchItem> tempList = new ArrayList<>();
        Cursor cursor = db.query(Constants.TABLE_STITCHES, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            StitchItem item = new StitchItem();
            String stitchItemName = cursor.getString(cursor.getColumnIndex(Constants.STITCH_NAME));
            String stitchItemNote = cursor.getString(cursor.getColumnIndex(Constants.STITCH_NOTES));
            int stitchItemId = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            item.setStitchName(stitchItemName);
            item.setStitchNote(stitchItemNote);
            item.setStitchId(stitchItemId);
            tempList.add(item);
        }
        cursor.close();
        return tempList;
    }

    public ArrayList<FabricItem> getFabricFromDb() {
        ArrayList<FabricItem> tempList = new ArrayList<>();
        String selection = Constants._ID + " = ?";
        String[] selectionArgs = {"id"};
        Cursor cursor = db.query(Constants.TABLE_FABRIC, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            String firmFabric = cursor.getString(cursor.getColumnIndex(Constants.FIRM_FABRIC));
            String nameFabric = cursor.getString(cursor.getColumnIndex(Constants.NAME_FABRIC));
            String articulFabric = cursor.getString(cursor.getColumnIndex(Constants.ARTICUL_FABRIC));
            String kauntFabric = cursor.getString(cursor.getColumnIndex(Constants.KAUNT_FABRIC));
            String colorFabric = cursor.getString(cursor.getColumnIndex(Constants.COLOR_FABRIC));
            String myNumberFabric = cursor.getString(cursor.getColumnIndex(Constants.MYNUMBER_FABRIC));

            FabricItem item = new FabricItem(id, firmFabric, nameFabric, articulFabric, kauntFabric, colorFabric, myNumberFabric);

            tempList.add(item);
        }
        cursor.close();
        return tempList;
    }

    public Boolean isFabricIsEmpty(String articul) {
        ArrayList<Cut> tempList = new ArrayList<>();
        String selection = Constants.ARTICUL_CUT_FABRIC + " = ?";
        String[] selectionArgs = {articul};
        Cursor cursor = db.query(Constants.TABLE_CUT_FABRIC, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            String articulFabric = cursor.getString(cursor.getColumnIndex(Constants.ARTICUL_CUT_FABRIC));

            Cut item = new Cut(id, "", "", articulFabric, 1, 1);
            tempList.add(item);
        }
        cursor.close();

        Boolean isEmpty = false;
        if(tempList.size()==0){
            isEmpty=true;
        }
        return isEmpty;
    }

    public ArrayList<String> getThreadsListForSpinnerFromDb(String firm) {
        ArrayList<String> tempList = new ArrayList<>();
        String selection = Constants.FIRM + " = ?";
        String[] selectionArgs = {firm};
        Cursor cursor = db.query(Constants.TABLE_THREADS, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {

            String numberNit = cursor.getString(cursor.getColumnIndex(Constants.NUMBER_NIT));

            tempList.add(numberNit);
        }
        cursor.close();
        return tempList;
    }

    public List<NitNew> getSortThreadFromDb(Boolean nalich, String searchFirm, String sortOrder) {
        List<NitNew> tempList = new ArrayList<>();
        String selection = Constants.FIRM + " = ?";
        String[] selectionArgs = {searchFirm};
        String sort = Constants.LENGTH_OSTATOK + sortOrder;
        Cursor cur = db.query(Constants.TABLE_THREADS, null, selection, selectionArgs, null, null, sort);

        while (cur.moveToNext()) {

            int id = cur.getInt(cur.getColumnIndex(Constants._ID));
            String numberNit = cur.getString(cur.getColumnIndex(Constants.NUMBER_NIT));
            int colorNumber = cur.getInt(cur.getColumnIndex(Constants.COLOR_NUMBER));
            String colorName = cur.getString(cur.getColumnIndex(Constants.COLOR_NAME));
            String firm = cur.getString(cur.getColumnIndex(Constants.FIRM));
            String nameStitch = cur.getString(cur.getColumnIndex(Constants.NAME_STITCH));
            Double lengthOstatok = cur.getDouble(cur.getColumnIndex(Constants.LENGTH_OSTATOK));

            NitNew item = new NitNew(id, firm, numberNit, 0.2, 0.2, 0.2, lengthOstatok, nameStitch, colorName, colorNumber);

            item.setIdNit(id);
            item.setNumberNit(numberNit);
            item.setColorNumber(colorNumber);
            item.setColorName(colorName);
            item.setFirm(firm);
            item.setNameStitch(nameStitch);
            item.setLengthOstatok(lengthOstatok);
            tempList.add(item);

            List<NitNew> nalList = new ArrayList<>();
            if(nalich){
                for (NitNew it : tempList){
                    if (it.getLengthOstatok()>0){
                        nalList.add(it);
                    }
                }
                tempList.clear();
                tempList.addAll(nalList);
            }
        }
        cur.close();
        return tempList;
    }
    //TODO
    public List<NitNew> getSortByNalichieThreadFromDb(String searchFirm, String sortOrder) {
        List<NitNew> tempList = new ArrayList<>();

        String selection = Constants.LENGTH_OSTATOK + " > 0 AND "+ Constants.FIRM + " = " +searchFirm;
        String[] selectionArgs = {searchFirm};
        String sort = Constants.LENGTH_OSTATOK + sortOrder;

        Cursor cur = db.query(Constants.TABLE_THREADS, null, selection, null, null, null, sort);

        while (cur.moveToNext()) {

            int id = cur.getInt(cur.getColumnIndex(Constants._ID));
            String numberNit = cur.getString(cur.getColumnIndex(Constants.NUMBER_NIT));
            int colorNumber = cur.getInt(cur.getColumnIndex(Constants.COLOR_NUMBER));
            String colorName = cur.getString(cur.getColumnIndex(Constants.COLOR_NAME));
            String firm = cur.getString(cur.getColumnIndex(Constants.FIRM));
            String nameStitch = cur.getString(cur.getColumnIndex(Constants.NAME_STITCH));
            Double lengthOstatok = cur.getDouble(cur.getColumnIndex(Constants.LENGTH_OSTATOK));

            NitNew item = new NitNew(id, firm, numberNit, 0.2, 0.2, 0.2, lengthOstatok, nameStitch, colorName, colorNumber);

            item.setIdNit(id);
            item.setNumberNit(numberNit);
            item.setColorNumber(colorNumber);
            item.setColorName(colorName);
            item.setFirm(firm);
            item.setNameStitch(nameStitch);
            item.setLengthOstatok(lengthOstatok);
            tempList.add(item);
        }
        cur.close();
        return tempList;
    }

    public List<NitNew> getAllThredsOfFirmFromDb(Boolean nalich, String searchFirm, String sortOrder) {
        List<NitNew> tempList = new ArrayList<>();
        String selection = Constants.FIRM + " = ?";
        String[] selectionArgs = {searchFirm};
        String sort = Constants._ID + sortOrder;
        Cursor cursor = db.query(Constants.TABLE_THREADS, null, selection, selectionArgs, null, null, sort);

        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            String numberNit = cursor.getString(cursor.getColumnIndex(Constants.NUMBER_NIT));
            int colorNumber = cursor.getInt(cursor.getColumnIndex(Constants.COLOR_NUMBER));
            String colorName = cursor.getString(cursor.getColumnIndex(Constants.COLOR_NAME));
            String firm = cursor.getString(cursor.getColumnIndex(Constants.FIRM));
            String nameStitch = cursor.getString(cursor.getColumnIndex(Constants.NAME_STITCH));
            Double lengthOstatok = cursor.getDouble(cursor.getColumnIndex(Constants.LENGTH_OSTATOK));

            NitNew item = new NitNew(id, firm, numberNit, 0.2, 0.2, 0.2, lengthOstatok, nameStitch, colorName, colorNumber);

            item.setIdNit(id);
            item.setNumberNit(numberNit);
            item.setColorNumber(colorNumber);
            item.setColorName(colorName);
            item.setFirm(firm);
            item.setNameStitch(nameStitch);
            item.setLengthOstatok(lengthOstatok);
            tempList.add(item);

            List<NitNew> nalList = new ArrayList<>();
            if(nalich){
                for (NitNew it : tempList){
                    if (it.getLengthOstatok()>0){
                        nalList.add(it);
                    }
                }
                tempList.clear();
                tempList.addAll(nalList);
            }
        }
        cursor.close();
        return tempList;
    }

    public List<NitNew> getAllThredsFromDb() {
        List<NitNew> tempList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_THREADS, null, null, null, null, null, null);

        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            String numberNit = cursor.getString(cursor.getColumnIndex(Constants.NUMBER_NIT));
            int colorNumber = cursor.getInt(cursor.getColumnIndex(Constants.COLOR_NUMBER));
            String colorName = cursor.getString(cursor.getColumnIndex(Constants.COLOR_NAME));
            String firm = cursor.getString(cursor.getColumnIndex(Constants.FIRM));
            String nameStitch = cursor.getString(cursor.getColumnIndex(Constants.NAME_STITCH));
            Double lengthOstatok = cursor.getDouble(cursor.getColumnIndex(Constants.LENGTH_OSTATOK));

            NitNew item = new NitNew(id, firm, numberNit, 0.2, 0.2, 0.2, lengthOstatok, nameStitch, colorName, colorNumber);

            item.setIdNit(id);
            item.setNumberNit(numberNit);
            item.setColorNumber(colorNumber);
            item.setColorName(colorName);
            item.setFirm(firm);
            item.setNameStitch(nameStitch);
            item.setLengthOstatok(lengthOstatok);
            tempList.add(item);
        }
        cursor.close();
        return tempList;
    }

    public List<NitNew> getCurrentListFromDb(String nameStitch, String sortOrder) {
        List<NitNew> tempList = new ArrayList<>();
        String selection = Constants.NAME_STITCH + " = ?";
        String[] selectionArgs = {nameStitch};
        String sort = Constants._ID + sortOrder;
        Cursor cursor = db.query(Constants.TABLE_CURRENT_THREADS, null, selection, selectionArgs, null, null, sort);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            String numberNit = cursor.getString(cursor.getColumnIndex(Constants.NUMBER_NIT));
            int colorNumber = cursor.getInt(cursor.getColumnIndex(Constants.COLOR_NUMBER));
            String colorName = cursor.getString(cursor.getColumnIndex(Constants.COLOR_NAME));
            String firm = cursor.getString(cursor.getColumnIndex(Constants.FIRM));
            String nameStich = cursor.getString(cursor.getColumnIndex(Constants.NAME_STITCH));
            Double lengthCurrent = cursor.getDouble(cursor.getColumnIndex(Constants.LENGTH_CURRENT));

            NitNew item = new NitNew(id, firm, numberNit, lengthCurrent, 0.1, 0.2, 1.0, nameStich, colorName, colorNumber);

            item.setIdNit(id);
            item.setNumberNit(numberNit);
            item.setColorNumber(colorNumber);
            item.setColorName(colorName);
            item.setFirm(firm);
            item.setNameStitch(nameStich);
            item.setLengthCurrent(lengthCurrent);
            tempList.add(item);
        }
        cursor.close();


        for (NitNew item : tempList) {
            String num = item.getNumberNit();
            String firm = item.getFirm();

            String selection2 = Constants.NUMBER_NIT + " = ?";
            String[] selectionArgs2 = {num};
            Cursor cursor2 = db.query(Constants.TABLE_THREADS, null, selection2, selectionArgs2, null, null, null);
            while (cursor2.moveToNext()) {
                Double lengthOstatatok = cursor2.getDouble(cursor2.getColumnIndex(Constants.LENGTH_OSTATOK));
                String firm2 = cursor2.getString(cursor2.getColumnIndex(Constants.FIRM));

                if (firm2.equals(firm)) {
                    item.setLengthOstatok(lengthOstatatok);
                }
            }
            cursor2.close();
        }

        return tempList;
    }

    public List<NitNew> getAllCurrentListFromDb() {
        List<NitNew> tempList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_CURRENT_THREADS, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            String numberNit = cursor.getString(cursor.getColumnIndex(Constants.NUMBER_NIT));
            int colorNumber = cursor.getInt(cursor.getColumnIndex(Constants.COLOR_NUMBER));
            String colorName = cursor.getString(cursor.getColumnIndex(Constants.COLOR_NAME));
            String firm = cursor.getString(cursor.getColumnIndex(Constants.FIRM));
            String nameStich = cursor.getString(cursor.getColumnIndex(Constants.NAME_STITCH));
            Double lengthCurrent = cursor.getDouble(cursor.getColumnIndex(Constants.LENGTH_CURRENT));

            NitNew item = new NitNew(id, firm, numberNit, lengthCurrent, 0.1, 0.2, 1.0, nameStich, colorName, colorNumber);

            item.setIdNit(id);
            item.setNumberNit(numberNit);
            item.setColorNumber(colorNumber);
            item.setColorName(colorName);
            item.setFirm(firm);
            item.setNameStitch(nameStich);
            item.setLengthCurrent(lengthCurrent);
            tempList.add(item);
        }
        cursor.close();
        return tempList;
    }

    public ArrayList<Cut> getCutsFromDb() {
        ArrayList<Cut>  tempList = new ArrayList<>();
        String selection = Constants._ID + " = ?";
        String[] selectionArgs = {"id"};
        Cursor cursor = db.query(Constants.TABLE_CUT_FABRIC, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            String nameFabricCut = cursor.getString(cursor.getColumnIndex(Constants.NAME_CUT_FABRIC));
            String firmFabricCut = cursor.getString(cursor.getColumnIndex(Constants.FIRM_CUT_FABRIC));
            String articul = cursor.getString(cursor.getColumnIndex(Constants.ARTICUL_CUT_FABRIC));
            int lengthCut = cursor.getInt(cursor.getColumnIndex(Constants.CUT_LENGTH));
            int widthCut = cursor.getInt(cursor.getColumnIndex(Constants.CUT_WIDTH));

            Cut item = new Cut(id, nameFabricCut,firmFabricCut,articul,lengthCut,widthCut);

            tempList.add(item);
        }
        cursor.close();
        return tempList;
    }

    public ArrayList<Cut> getAllCutsFromDb( ) {
        ArrayList<Cut>  tempList = new ArrayList<>();
        Cursor cursor = db.query(Constants.TABLE_CUT_FABRIC, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            String nameFabricCut = cursor.getString(cursor.getColumnIndex(Constants.NAME_CUT_FABRIC));
            String firmFabricCut = cursor.getString(cursor.getColumnIndex(Constants.FIRM_CUT_FABRIC));
            String articul = cursor.getString(cursor.getColumnIndex(Constants.ARTICUL_CUT_FABRIC));
            int lengthCut = cursor.getInt(cursor.getColumnIndex(Constants.CUT_LENGTH));
            int widthCut = cursor.getInt(cursor.getColumnIndex(Constants.CUT_WIDTH));

            Cut item = new Cut(id, nameFabricCut,firmFabricCut,articul,lengthCut,widthCut);

            tempList.add(item);
        }
        cursor.close();
        return tempList;
    }

    public ArrayList<Cut> getArtCutsFromDb(String art) {
        ArrayList<Cut>  tempList = new ArrayList<>();
        String selection = Constants.ARTICUL_CUT_FABRIC + " = ?";
        String[] selectionArgs = {art};
        Cursor cursor = db.query(Constants.TABLE_CUT_FABRIC, null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            String nameFabricCut = cursor.getString(cursor.getColumnIndex(Constants.NAME_CUT_FABRIC));
            String firmFabricCut = cursor.getString(cursor.getColumnIndex(Constants.FIRM_CUT_FABRIC));
            String articul = cursor.getString(cursor.getColumnIndex(Constants.ARTICUL_CUT_FABRIC));
            int lengthCut = cursor.getInt(cursor.getColumnIndex(Constants.CUT_LENGTH));
            int widthCut = cursor.getInt(cursor.getColumnIndex(Constants.CUT_WIDTH));

            Cut item = new Cut(id, nameFabricCut,firmFabricCut,articul,lengthCut,widthCut);

            tempList.add(item);
        }
        cursor.close();
        return tempList;
    }

    public ArrayList<FabricItem> getSearchFabricFromDb(String searchText) {
        ArrayList<FabricItem>  tempList = new ArrayList<>();
        String [] select = {Constants.FIRM_FABRIC, Constants.NAME_FABRIC, Constants.ARTICUL_FABRIC, Constants.KAUNT_FABRIC, Constants.COLOR_FABRIC, Constants.MYNUMBER_FABRIC};

        for (String it : select){

            String selection = it + " LIKE ?";
            String[] selectionArgs = {"%"+searchText+"%"};
            Cursor cursor = db.query(Constants.TABLE_FABRIC, null, selection, selectionArgs, null, null, null);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
                String nameFabric = cursor.getString(cursor.getColumnIndex(Constants.NAME_FABRIC));
                String firmFabric = cursor.getString(cursor.getColumnIndex(Constants.FIRM_FABRIC));
                String articul = cursor.getString(cursor.getColumnIndex(Constants.ARTICUL_FABRIC));
                String kauntFabric = cursor.getString(cursor.getColumnIndex(Constants.KAUNT_FABRIC));
                String colorFabric = cursor.getString(cursor.getColumnIndex(Constants.COLOR_FABRIC));
                String myNumberFabric = cursor.getString(cursor.getColumnIndex(Constants.MYNUMBER_FABRIC));


                FabricItem item = new FabricItem(id, firmFabric,nameFabric,articul,kauntFabric,colorFabric,myNumberFabric);

                tempList.add(item);
            }
            cursor.close();

        }




        return tempList;
    }

    public int searchColorNumberFromDb(String numberNit, String firm) {
        String selection = Constants.NUMBER_NIT + " = ?";
        String[] selectionArgs = {numberNit};
        Cursor cursor = db.query(Constants.TABLE_THREADS, null, selection, selectionArgs, null, null, null);
        int colorNumber = 1;
        while (cursor.moveToNext()) {

            String currentFirm = cursor.getString(cursor.getColumnIndex(Constants.FIRM));
            if (firm.equals(currentFirm)) {
                colorNumber = cursor.getInt(cursor.getColumnIndex(Constants.COLOR_NUMBER));
            }
        }
        return colorNumber;
    }

    public String searchColorNameFromDb(String numberNit, String firm) {
        String selection = Constants.NUMBER_NIT + " = ?";
        String[] selectionArgs = {numberNit};
        Cursor cursor = db.query(Constants.TABLE_THREADS, null, selection, selectionArgs, null, null, null);
        String colorName = "";
        while (cursor.moveToNext()) {

            String currentFirm = cursor.getString(cursor.getColumnIndex(Constants.FIRM));
            if (firm.equals(currentFirm)) {
                colorName = cursor.getString(cursor.getColumnIndex(Constants.COLOR_NAME));
            }
        }
        return colorName;
    }

    public int searchIdThreadFromDb(String numberNit, String firm) {
        String selection = Constants.NUMBER_NIT + " = ?";
        String[] selectionArgs = {numberNit};
        Cursor cursor = db.query(Constants.TABLE_THREADS, null, selection, selectionArgs, null, null, null);

        int id = 0;
        while (cursor.moveToNext()) {

            String currentFirm = cursor.getString(cursor.getColumnIndex(Constants.FIRM));

            if (firm.equals(currentFirm)) {
                id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            }
        }
        cursor.close();
        return id;
    }

    public int searchIdThreadFromTableCurrentDb(String numberNit, String firm) {
        String selection = Constants.NUMBER_NIT + " = ?";
        String[] selectionArgs = {numberNit};
        Cursor cursor = db.query(Constants.TABLE_CURRENT_THREADS, null, selection, selectionArgs, null, null, null);
        int id = 1;
        while (cursor.moveToNext()) {

            String currentFirm = cursor.getString(cursor.getColumnIndex(Constants.FIRM));

            if (firm.equals(currentFirm)) {
                id = cursor.getInt(cursor.getColumnIndex(Constants._ID));
            }
        }

        return id;
    }

    public Double searchLengthThreadFromDb(String numberNit, String firm) {
        String selection = Constants.NUMBER_NIT + " = ?";
        String[] selectionArgs = {numberNit};
        Cursor cursor = db.query(Constants.TABLE_THREADS, null, selection, selectionArgs, null, null, null);
        Double length = 1.0;
        while (cursor.moveToNext()) {

            String currentFirm = cursor.getString(cursor.getColumnIndex(Constants.FIRM));

            if (firm.equals(currentFirm)) {
                length = cursor.getDouble(cursor.getColumnIndex(Constants.LENGTH_OSTATOK));
            }
        }

        return length;
    }

    public Boolean searchArticulFabricFromDb(String searchArt){
        String selection = Constants.ARTICUL_FABRIC + " = ?";
        String[] selectionArgs = {searchArt};
        Cursor cursor = db.query(Constants.TABLE_FABRIC, null, selection, selectionArgs, null, null, null);
        Boolean nalichieArt = false;
        String articul = "";
        while (cursor.moveToNext()) {

            articul = cursor.getString(cursor.getColumnIndex(Constants.ARTICUL_FABRIC));
            if (articul.equals(searchArt)){
                nalichieArt = true;
            }
        }

        return nalichieArt;
    }

    public void updateThreadOstatokToDb(Double lengthOstatok, String id) {
        ContentValues values = new ContentValues();
        values.put(Constants.LENGTH_OSTATOK, lengthOstatok);

// Which row to update, based on the title
        String selection = Constants._ID + " LIKE ?";
        String[] selectionArgs = {id};

        int count = db.update(
                Constants.TABLE_THREADS,
                values,
                selection,
                selectionArgs);
    }

    public void updateCutToDb(String id, String length, String width) {
        ContentValues values = new ContentValues();
        values.put(Constants.CUT_LENGTH, length);
        values.put(Constants.CUT_WIDTH, width);

        String selection = Constants._ID + " LIKE ?";
        String[] selectionArgs = {id};

        int count = db.update(
                Constants.TABLE_CUT_FABRIC,
                values,
                selection,
                selectionArgs);
    }

    public void updateFabricToDb(String id, String length) {
        ContentValues values = new ContentValues();
        values.put(Constants.MYNUMBER_FABRIC, length);

        String selection = Constants._ID + " LIKE ?";
        String[] selectionArgs = {id};

        int count = db.update(
                Constants.TABLE_FABRIC,
                values,
                selection,
                selectionArgs);
    }

    public void updateNameFabricToDb(String id, String firm) {
        ContentValues values = new ContentValues();
        values.put(Constants.FIRM_FABRIC, firm);

        String selection = Constants._ID + " LIKE ?";
        String[] selectionArgs = {id};

        int count = db.update(
                Constants.TABLE_FABRIC,
                values,
                selection,
                selectionArgs);
    }

    public void updateTitleFabricToDb(String id, String title) {
        ContentValues values = new ContentValues();
        values.put(Constants.NAME_FABRIC, title);

        String selection = Constants._ID + " LIKE ?";
        String[] selectionArgs = {id};

        int count = db.update(
                Constants.TABLE_FABRIC,
                values,
                selection,
                selectionArgs);
    }
    public void updateColorFabricToDb(String id, String color) {
        ContentValues values = new ContentValues();
        values.put(Constants.COLOR_FABRIC, color);

        String selection = Constants._ID + " LIKE ?";
        String[] selectionArgs = {id};

        int count = db.update(
                Constants.TABLE_FABRIC,
                values,
                selection,
                selectionArgs);
    }

    public void updateKauntFabricToDb(String id, String kaunt) {
        ContentValues values = new ContentValues();
        values.put(Constants.KAUNT_FABRIC, kaunt);

        String selection = Constants._ID + " LIKE ?";
        String[] selectionArgs = {id};

        int count = db.update(
                Constants.TABLE_FABRIC,
                values,
                selection,
                selectionArgs);
    }

    public void updateArticulFabricToDb(String id, String articul) {
        ContentValues values = new ContentValues();
        values.put(Constants.ARTICUL_FABRIC, articul);

        String selection = Constants._ID + " LIKE ?";
        String[] selectionArgs = {id};

        int count = db.update(
                Constants.TABLE_FABRIC,
                values,
                selection,
                selectionArgs);
    }

    public void updateThreadCurrentToDb(Double lengthCurrent, String id) {
        ContentValues values = new ContentValues();
        values.put(Constants.LENGTH_CURRENT, lengthCurrent);

// Which row to update, based on the title
        String selection = Constants._ID + " LIKE ?";
        String[] selectionArgs = {id};

        int count = db.update(
                Constants.TABLE_CURRENT_THREADS,
                values,
                selection,
                selectionArgs);
    }

    public void deleteFromDb(int id) {
        String selection = Constants._ID + "=" + id;
        //String[] selectionArgs = { "MyTitle" };
        db.delete(Constants.TABLE_STITCHES, selection, null);
    }

    public void deleteStitchFromDb(String stitchName) {
        String selection = Constants.STITCH_NAME + " LIKE ?";
        String[] selectionArgs = {stitchName};
        db.delete(Constants.TABLE_STITCHES, selection, selectionArgs);
    }

    public void deleteAllStitchFromDb() {
        String selection = Constants.STITCH_NAME + " LIKE ?";
        String[] selectionArgs = {"stitchName"};
        db.delete(Constants.TABLE_STITCHES, null, null);
    }

    public void deleteTreadFromCurrentStitch(int id) {
        String selection = Constants._ID + "=" + id;
        //String[] selectionArgs = { "MyTitle" };
        db.delete(Constants.TABLE_CURRENT_THREADS, selection, null);
    }

    public void deleteAllCurrentTreadFromDb() {
        //String selection = Constants._ID + "=" + id;
        //String[] selectionArgs = { "MyTitle" };
        db.delete(Constants.TABLE_CURRENT_THREADS, null, null);
    }

    public void deleteAllTreadsFromCurrentStitch(String stitchName) {
        String selection = Constants.NAME_STITCH + " LIKE ?";
        String[] selectionArgs = {stitchName};
        db.delete(Constants.TABLE_CURRENT_THREADS, selection, selectionArgs);
    }

    public void deleteCutFromDb(int id) {
        String selection = Constants._ID + "=" + id;
        //String[] selectionArgs = { "MyTitle" };
        db.delete(Constants.TABLE_CUT_FABRIC, selection, null);
    }

    public void deleteAllCutsFromDb() {
        //String selection = Constants._ID + "=" + id;
        //String[] selectionArgs = { "MyTitle" };
        db.delete(Constants.TABLE_CUT_FABRIC, null, null);
    }

    public void deleteFabricFromDb(String articul) {
        String selection = Constants.ARTICUL_FABRIC + " LIKE ?";
        String[] selectionArgs = {articul};
        db.delete(Constants.TABLE_FABRIC, selection, selectionArgs);
    }
    public void deleteAllFabricFromDb() {
        String selection = Constants.ARTICUL_FABRIC + " LIKE ?";
        String[] selectionArgs = {"articul"};
        db.delete(Constants.TABLE_FABRIC, null, null);
    }

    public void closeDb() {
        dbHelper.close();
    }

    public int getColorNumberFromDb(String id) {
        String selection = Constants._ID + " = ?";
        String[] selectionArgs = {id};

        Cursor cursor = db.query(Constants.TABLE_THREADS, null, selection, selectionArgs, null, null, null);

        int colorNumber = 0;
        while (cursor.moveToNext()) {

            colorNumber = cursor.getInt(cursor.getColumnIndex(Constants.COLOR_NUMBER));

        }
        cursor.close();
        return colorNumber;
    }
}
