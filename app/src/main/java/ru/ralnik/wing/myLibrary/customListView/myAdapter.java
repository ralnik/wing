package ru.ralnik.wing.myLibrary.customListView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.ralnik.wing.R;
import ru.ralnik.wing.model.Flat;


public class myAdapter extends CursorAdapter {

    public Context context;
    private LayoutInflater mInflater;
    //private int size;
    View root;
    ViewHolder holder;
    ImageView imageBG;

    public myAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        //this.size = c.getCount();
        this.context = context;
        listviewItemSelected.ID = null;
    }
    /*
    public int getSize(){
        return this.size;
    }*/


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        root = mInflater.from(context).inflate(R.layout.listview_item, parent, false);
        holder = new ViewHolder();

        imageBG = (ImageView) root.findViewById(R.id.imgBG);
        LinearLayout row = (LinearLayout) root.findViewById(R.id.row);
        TextView column1 = (TextView) root.findViewById(R.id.colKorpus);
        TextView column2 = (TextView) root.findViewById(R.id.colFlat);
        TextView column3 = (TextView) root.findViewById(R.id.colFloor);
        TextView column4 = (TextView) root.findViewById(R.id.colRooms);
        TextView column5 = (TextView) root.findViewById(R.id.colSquare);
        TextView column6 = (TextView) root.findViewById(R.id.colCost);
        TextView column7 = (TextView) root.findViewById(R.id.colStatus);


        /*
        int ID = cursor.getColumnIndex(myDBHelper.KEY__ID);
        int COST = cursor.getColumnIndex(myDBHelper.KEY_COST);
        int COSTF = cursor.getColumnIndex(myDBHelper.KEY_COSTF);
        int TYPE= cursor.getColumnIndex(myDBHelper.KEY_TYPE);
        int SQUARE= cursor.getColumnIndex(myDBHelper.KEY_SQUARE);
        int NUMBER= cursor.getColumnIndex(myDBHelper.KEY_NUMBER);
        int CORPUS= cursor.getColumnIndex(myDBHelper.KEY_BUILDING);
        int FLOOR= cursor.getColumnIndex(myDBHelper.KEY_FLOOR);
        */



        holder.imageBG = imageBG;
        holder.column1 = column1;
        holder.column2 = column2;
        holder.column3 = column3;
        holder.column4 = column4;
        holder.column5 = column5;
        holder.column6 = column6;
        holder.column7 = column7;
        holder.selected = row;

        root.setTag(holder);
        return root;


        //return LayoutInflater.from(context).inflate(R.layout.table_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        int ID = cursor.getColumnIndex("_id");
        int KORPUS = cursor.getColumnIndex("corpus");
        int FLAT = cursor.getColumnIndex("nom_kv");
        int FLOOR = cursor.getColumnIndex("etag");
        int ROOMS= cursor.getColumnIndex("comnat");
        int SQUARE= cursor.getColumnIndex("ploshad");
        int COST= cursor.getColumnIndex("price");
        int Status= cursor.getColumnIndex("status");




        ViewHolder holder = (ViewHolder) view.getTag();
        //Log.d("myDebug","getTag: "+view.getTag());


        if(holder != null) {


            holder.position = cursor.getPosition();
            holder.column1.setText(cursor.getString(KORPUS));
            holder.column2.setText(cursor.getString(FLAT));
            holder.column3.setText(getCorrectType(cursor.getInt(FLOOR)));
            holder.column4.setText(String.valueOf(cursor.getInt(ROOMS)));
            holder.column5.setText(String.valueOf(cursor.getFloat(SQUARE)));
            holder.column6.setText(String.valueOf(Flat.makePrettyCost(cursor.getString(COST))));
            holder.column7.setText(Flat.setCorrectStatus(cursor.getInt(Status)));


            holder.classID = cursor.getString(ID);

            //Log.d("myDebug","id_fromTABLE="+cursor.getLong(ID)+" number="+cursor.getInt(NUMBER)+" corpus="+cursor.getString(CORPUS)+" cost="+makePrettyCost(String.valueOf(cursor.getInt(COST))));

            if(holder.position %2 == 0){
                holder.imageBG.setImageLevel(1);
            }else{
                holder.imageBG.setImageLevel(0);
            }


            holder.column1.setTextColor(context.getResources().getColor(R.color.colorTextListitems));
            holder.column2.setTextColor(context.getResources().getColor(R.color.colorTextListitems));
            holder.column3.setTextColor(context.getResources().getColor(R.color.colorTextListitems));
            holder.column4.setTextColor(context.getResources().getColor(R.color.colorTextListitems));
            holder.column5.setTextColor(context.getResources().getColor(R.color.colorTextListitems));
            holder.column6.setTextColor(context.getResources().getColor(R.color.colorTextListitems));
            holder.column7.setTextColor(context.getResources().getColor(R.color.colorTextListitems));

            /*
                Проверяем если запись выделенная то при прокрутки текст так и останется темным
                если проверку не делать, то текст при прокрутки опять меняется на белый
            */
        if(listviewItemSelected.ID != null) {
            if (listviewItemSelected.ID.equals(holder.classID)) {

                holder.column1.setTextColor(context.getResources().getColor(R.color.colorTextListitemSelected));
                holder.column2.setTextColor(context.getResources().getColor(R.color.colorTextListitemSelected));
                holder.column3.setTextColor(context.getResources().getColor(R.color.colorTextListitemSelected));
                holder.column4.setTextColor(context.getResources().getColor(R.color.colorTextListitemSelected));
                holder.column5.setTextColor(context.getResources().getColor(R.color.colorTextListitemSelected));
                holder.column6.setTextColor(context.getResources().getColor(R.color.colorTextListitemSelected));
                holder.column7.setTextColor(context.getResources().getColor(R.color.colorTextListitemSelected));
            }
        }

        }
    }



    private String getCorrectType(int type){
        switch (type){
            case 100:
                return "студия";

            case 200:
                return "офис";

            case 300:
                return "паркинг";

            default:
                return type+"";
        }
    }



    public static class ViewHolder {

        public int position;
        public ImageView imageBG;
        public TextView column1;
        public TextView column2;
        public TextView column3;
        public TextView column4;
        public TextView column5;
        public TextView column6;
        public TextView column7;
        public LinearLayout selected ;

        public String classID;
    }
}
