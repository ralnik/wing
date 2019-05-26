package ru.ralnik.wing;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.ralnik.wing.config.myConfig;
import ru.ralnik.wing.httpPlayer.HttpPlayerFactory;
import ru.ralnik.wing.httpPlayer.PlayerCommands;
import ru.ralnik.wing.model.Flat;
import ru.ralnik.wing.myLibrary.NavigationButton.DemonsrationButton;
import ru.ralnik.wing.myLibrary.customListView.listviewItemSelected;
import ru.ralnik.wing.myLibrary.customListView.myAdapter;
import ru.ralnik.wing.myseekbarrange.SeekbarRange;
import ru.ralnik.wing.sqlitedb.CreateSQLQuery;
import ru.ralnik.wing.sqlitedb.FlatRepository;


public class MainActivity extends AppCompatActivity {
    //---------MAIN LAYOUT----------
    ScrollView mainPanel;
    LinearLayout resultPanel;
    LinearLayout settingsPanel;
    LinearLayout planPanel;

    int MAIN_PANEL = 0;
    int RESULT_PANEL = 1;
    int SETTINGS_PANEL = 2;
    int PLAN_PANEL = 3;

    //--------Controll PLAN--------
    private View viewPlanPanel;
    private TextView titleCountRooms;
    private TextView titlePrice;
    private TextView textCorpus;
    private TextView textNumberFlats;
    private TextView textFloor;
    private TextView textSquare;
    private TextView textStatus;
    private ImageView btnLast;
    private ImageView btnNext;
    private ImageView imgPlanFlat;
    private ImageView imgPlanFloor;

    //--------Controll Settings--------
    private View viewSettingPanel;
    private SeekBar musicSeekBar;
    private SeekBar effectSeekBar;
    private EditText editWaitTime;
    private ImageView switcherTimer;
    private EditText editIP;
    private ImageView btnSave;


    //--------Controll Settings--------
    private DemonsrationButton btnLocation;
    private DemonsrationButton btnInfraRaion;
    private DemonsrationButton btnTransport;
    private DemonsrationButton btnInfra;
    private DemonsrationButton btnComfort;
    private DemonsrationButton btnArchitecture;
    private DemonsrationButton btnPlaning;
    private ImageView btnOption;
    private DemonsrationButton btnPause;
    private DemonsrationButton btnVolume;
    private DemonsrationButton btnRoom1;
    private DemonsrationButton btnRoom2;
    private DemonsrationButton btnRoom3;
    private DemonsrationButton btnRoom4;
    private DemonsrationButton btn_corpus1;
    private DemonsrationButton btn_corpus2;
    private DemonsrationButton btn_corpus3;

    //---------Buttons filter---------
    private ImageView btnClear;
    private ImageView btnSearch;

    //------- SEEKBAR-------------
    private SeekbarRange seekbarFloor;
    private SeekbarRange seekbarCost;
    private SeekbarRange seekbarSquare;



    //---------OTHER VARIABLES--------------
    TextView textNoRow;
    ListView listview;
    myAdapter adapter;
    public static int adapterPosition;
    ArrayList<Integer> ListClearFilter = new ArrayList<>();

    myConfig cfg;
    PlayerCommands vvvv;
    myTimer timer;
    String TAG = "myDebug";
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
//initialize config
        cfg = new myConfig(this);
        cfg.setEmail("ralnik@mail.ru");

       vvvv = HttpPlayerFactory.getInstance(getApplicationContext()).getCommand();

        initObjects();
        initSettings();
        initPlans();
    }

    private void initObjects() {
        mainPanel = (ScrollView) findViewById(R.id.mainLayout);
        resultPanel = (LinearLayout) findViewById(R.id.resultPanel);
        settingsPanel = (LinearLayout) findViewById(R.id.settingsLayout);
        planPanel = (LinearLayout) findViewById(R.id.planPanel);
        changePanel(MAIN_PANEL);

        textNoRow = (TextView) findViewById(R.id.textNoRow);
        listview = (ListView) findViewById(R.id.listview);

        btnOption = (ImageView) findViewById(R.id.btnOption);
        btnPause = (DemonsrationButton) findViewById(R.id.btnPause);
        btnVolume = (DemonsrationButton) findViewById(R.id.btnVolume);
        btnVolume.setStatus(true);

        btnLocation = (DemonsrationButton) findViewById(R.id.btnLocation);
        btnInfraRaion = (DemonsrationButton) findViewById(R.id.btnInfraRaion);
        btnTransport = (DemonsrationButton) findViewById(R.id.btnTransport);
        btnInfra = (DemonsrationButton) findViewById(R.id.btnInfra);
        btnComfort = (DemonsrationButton) findViewById(R.id.btnComfort);
        btnArchitecture = (DemonsrationButton) findViewById(R.id.btnArchitecture);
        btnPlaning = (DemonsrationButton) findViewById(R.id.btnPlaning);

        btnRoom1 = (DemonsrationButton) findViewById(R.id.btnRoom1);
        btnRoom2 = (DemonsrationButton) findViewById(R.id.btnRoom2);
        btnRoom3 = (DemonsrationButton) findViewById(R.id.btnRoom3);
        btnRoom4 = (DemonsrationButton) findViewById(R.id.btnRoom4);
        btnRoom1.setTag(1);
        btnRoom2.setTag(2);
        btnRoom3.setTag(3);
        btnRoom4.setTag(4);

        btn_corpus1 = (DemonsrationButton) findViewById(R.id.btnCorpus1);
        btn_corpus2 = (DemonsrationButton) findViewById(R.id.btnCorpus2);
        btn_corpus3 = (DemonsrationButton) findViewById(R.id.btnCorpus3);
        btn_corpus1.setTag(1); btn_corpus1.setStatus(false);
        btn_corpus2.setTag(2); btn_corpus2.setStatus(false);
        btn_corpus3.setTag(3); btn_corpus3.setStatus(false);


        btnClear = (ImageView) findViewById(R.id.btnClear);
        btnSearch = (ImageView) findViewById(R.id.btnSearch);

        seekbarFloor  = (SeekbarRange) findViewById(R.id.seekbarFloor);
        seekbarCost   = (SeekbarRange) findViewById(R.id.seekbarCost);
        seekbarSquare = (SeekbarRange) findViewById(R.id.seekbarSquare);
        setValuesToSeekBar();
    }

    private void setValuesToSeekBar(){
        try {
            cfg.setMinCost((float) new FlatRepository(this).getMin("Cost"));
            cfg.setMaxCost((float) new FlatRepository(this).getMax("Cost"));
            cfg.setMinFloor((int) new FlatRepository(this).getMin("Floor"));
            cfg.setMaxFloor((int) new FlatRepository(this).getMax("Floor"));
            cfg.setMinSquare((float) new FlatRepository(this).getMin("Square"));
            cfg.setMaxSquare((float) new FlatRepository(this).getMax("Square"));
        }catch (Exception e){
            e.printStackTrace();
        }

        float min,max;
        min = cfg.getMinCost() / 1000000;
        max = cfg.getMaxCost() / 1000000;

        seekbarCost.setAbsoluteMinValue(min);
        seekbarCost.setAbsoluteMaxValue(max);
        seekbarCost.setMinValue(min);
        seekbarCost.setMaxValue(max);


        seekbarFloor.setAbsoluteMinValue(cfg.getMinFloor());
        seekbarFloor.setAbsoluteMaxValue(cfg.getMaxFloor());
        seekbarFloor.setMinValue(cfg.getMinFloor());
        seekbarFloor.setMaxValue(cfg.getMaxFloor());


        seekbarSquare.setAbsoluteMinValue(cfg.getMinSquare());
        seekbarSquare.setAbsoluteMaxValue(cfg.getMaxSquare());
        seekbarSquare.setMinValue(cfg.getMinSquare());
        seekbarSquare.setMaxValue(cfg.getMaxSquare());

    }

    private void initPlans(){
        viewPlanPanel = this.getLayoutInflater().inflate(R.layout.show_planning_activity, null);;
        titleCountRooms = (TextView) viewPlanPanel.findViewById(R.id.titleCountRooms);
        titlePrice = (TextView) viewPlanPanel.findViewById(R.id.titlePrice);
        textCorpus = (TextView) viewPlanPanel.findViewById(R.id.textCorpus);
        textNumberFlats = (TextView) viewPlanPanel.findViewById(R.id.textNumberFlats);
        textFloor = (TextView) viewPlanPanel.findViewById(R.id.textFloor);
        textSquare = (TextView) viewPlanPanel.findViewById(R.id.textSquare);
        textStatus = (TextView) viewPlanPanel.findViewById(R.id.textStatus);
        btnLast = (ImageView) viewPlanPanel.findViewById(R.id.btnLast);
        btnNext = (ImageView) viewPlanPanel.findViewById(R.id.btnNext);
        imgPlanFlat = (ImageView) viewPlanPanel.findViewById(R.id.imgPlanFlat);
        imgPlanFloor = (ImageView) viewPlanPanel.findViewById(R.id.imgPlanFloor);
        planPanel.addView(viewPlanPanel);
    }

    private void initSettings(){
        viewSettingPanel = this.getLayoutInflater().inflate(R.layout.activity_settings, null);
        musicSeekBar = (SeekBar) viewSettingPanel.findViewById(R.id.musicSeekBar);
        effectSeekBar = (SeekBar) viewSettingPanel.findViewById(R.id.effectSeekBar);
        editWaitTime = (EditText) viewSettingPanel.findViewById(R.id.editWaitTime);
        switcherTimer = (ImageView) viewSettingPanel.findViewById(R.id.switcherTimer);
        editIP = (EditText) viewSettingPanel.findViewById(R.id.editIP);
        btnSave = (ImageView) viewSettingPanel.findViewById(R.id.btnSave);
        settingsPanel.addView(viewSettingPanel);
        musicSeekBar.setProgress(cfg.getVolumeProgress());
        effectSeekBar.setProgress(cfg.getEffectProgress());
        editWaitTime.setText(String.valueOf(cfg.getTimer()));
        editIP.setText(cfg.getHost());

        if(cfg.getDisableTimer()) {
            switcherTimer.setTag("on");
            switcherTimer.setImageResource(R.drawable.btn_wait_on);
        }else {
            switcherTimer.setTag("off");
            switcherTimer.setImageResource(R.drawable.btn_wait_off);
        }

        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Log.d(TAG,String.valueOf(musicSeekBar.getProgress()));
                //GlobalVar.volume = String.valueOf(VolumeSeekBar.getProgress());
                //GlobalVar.lastLink = VVVV.fullLink();
                vvvv.volume(seekBar.getProgress());
            }
        });

        effectSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Log.d(TAG,String.valueOf(effectSeekBar.getProgress()));
                //GlobalVar.volEffects = String.valueOf(EffectSeekBar.getProgress());
                //GlobalVar.lastLink = VVVV.fullLink();
                vvvv.volEffect(seekBar.getProgress());
            }
        });
        switcherTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switcherTimer.getTag().equals("on")){
                    switcherTimer.setTag("off");
                    switcherTimer.setImageResource(R.drawable.btn_wait_off);
                }else{
                    switcherTimer.setTag("on");
                    switcherTimer.setImageResource(R.drawable.btn_wait_on);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
            }
        });
    }
    private void saveSettings() {
        if(!editIP.getText().toString().equals(cfg.getHost())) {
            showPasswordWindow();
        }

        //Log.d(TAG,"save config");
        if(switcherTimer.getTag().equals("on")) {
            cfg.setDisableTimer(true);
        }else{
            cfg.setDisableTimer(false);
        }

        cfg.setVolumeProgress(String.valueOf(musicSeekBar.getProgress()));
        cfg.setEffectProgress(String.valueOf(effectSeekBar.getProgress()));

        cfg.setTimer(String.valueOf(editWaitTime.getText()));
        settingsPanel.setVisibility(View.GONE);
        mainPanel.setVisibility(View.VISIBLE);
        setTimer(cfg.getTimer());

    }

    private void showPasswordWindow(){
        //Получаем вид с файла prompt.xml, который применим для диалогового окна:
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompt, null);

        //Создаем AlertDialog
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);

        //Настраиваем prompt.xml для нашего AlertDialog:
        mDialogBuilder.setView(promptsView);

        //Настраиваем отображение поля для ввода текста в открытом диалоге:
        final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);

        //Настраиваем сообщение в диалоговом окне:
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Вводим текст и отображаем в строке ввода на основном экране:
                                if(String.valueOf(userInput.getText()).equals("realred34")){
                                    cfg.setHost(editIP.getText().toString());
                                    vvvv.changeHost(cfg.getHost());
                                }else
                                {
                                    Toast toast2 = Toast.makeText(getApplicationContext(), "Неверный пароль!", Toast.LENGTH_LONG);
                                    toast2.setGravity(Gravity.CENTER, 0, 0);
                                    toast2.show();
                                }

                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        //Создаем AlertDialog:
        AlertDialog alertDialog = mDialogBuilder.create();

        //и отображаем его:
        alertDialog.show();
    }

    public void setTimer(int minute) {

        if (timer != null) {
            timer.cancel();
        }
        timer = new myTimer(this, minute * 60000, 1000);
        //номер трека в плейлисте на время простоя
        timer.setTimerTrack(0);
        if(cfg.getDisableTimer() == false){
            timer.start();
        }
    }

    public void btnMenuOnClick(View v){
        switch (v.getId()){
            case R.id.btnOption:
                changePanel(SETTINGS_PANEL);
                break;
            case R.id.btnPause:
                if (btnPause.getStatus()) {
                    vvvv.stop();
                }else{
                    vvvv.play();
                }
                btnPause.setStatus();
                break;
            case R.id.btnVolume:
                vvvv.volumeOnOff();
                btnVolume.setStatus();
                break;
        }
    }

    public void btnOnClick(View v){
        btnLocation.setStatus(false);
        btnInfraRaion.setStatus(false);
        btnTransport.setStatus(false);
        btnInfra.setStatus(false);
        btnComfort.setStatus(false);
        btnArchitecture.setStatus(false);
        btnPlaning.setStatus(false);

        switch(v.getId()){
            case R.id.btnLocation:
                btnLocation.setStatus();
                vvvv.selectById(1);
                btnPause.setStatus(true);
                break;
            case R.id.btnInfraRaion:
                btnInfraRaion.setStatus();
                vvvv.selectById(2);
                new OuterInfraActivity(this);
                btnPause.setStatus(true);
                break;
            case R.id.btnTransport:
                btnTransport.setStatus();
                vvvv.selectById(3);
                btnPause.setStatus(true);
                break;
            case R.id.btnInfra:
                btnInfra.setStatus();
                vvvv.selectById(4);
                btnPause.setStatus(true);
                break;
            case R.id.btnComfort:
                btnComfort.setStatus();
                vvvv.selectById(5);
                btnPause.setStatus(true);
                break;
            case R.id.btnArchitecture:
                btnArchitecture.setStatus();
                vvvv.selectById(6);
                btnPause.setStatus(true);
                break;
            case R.id.btnPlaning:
                btnPlaning.setStatus();
                vvvv.selectById(7);
                btnPause.setStatus(true);
                break;
        }
    }

    public void btnCountRoomsOnClick(View v){
        switch(v.getId()){
            case R.id.btnRoom1:
                btnRoom1.setStatus();
                break;
            case R.id.btnRoom2:
                btnRoom2.setStatus();
                break;
            case R.id.btnRoom3:
                btnRoom3.setStatus();
                break;
            case R.id.btnRoom4:
                btnRoom4.setStatus();
                break;
        }
    }

    public void btnClearOnClick(View v){
        btnRoom1.setStatus(false);
        btnRoom2.setStatus(false);
        btnRoom3.setStatus(false);
        btnRoom4.setStatus(false);

        btn_corpus1.setStatus(false);
        btn_corpus2.setStatus(false);
        btn_corpus3.setStatus(false);

        seekbarCost.setDefaultValue();
        seekbarFloor.setDefaultValue();
        seekbarSquare.setDefaultValue();

    }

    public void btnSearchOnClick(View v){
        CreateSQLQuery sql = new CreateSQLQuery("select * from flats where status>0 ");

        sql.whereRange("etag",
                seekbarFloor.getMinValue().toString(),
                seekbarFloor.getMaxValue().toString()
        );

        sql.whereRange("ploshad",
                ((Float) seekbarSquare.getMinValue()-0.1) + " ",
                ((Float) seekbarSquare.getMaxValue()+0.1) + " "
        );

        sql.whereRange("price",
                seekbarCost.getMinValue() + "*1000000",
                seekbarCost.getMaxValue() + "*1000000"
        );


        sql.whereIN("corpus",
                     btn_corpus1,
                     btn_corpus2,
                     btn_corpus3
        );

        sql.whereIN("comnat",
                btnRoom1,
                btnRoom2,
                btnRoom3,
                btnRoom4
        );

        query = sql.toString();
        loadFromSqlite(query + " order by price");
    }

    private void loadFromSqlite(String sql) {
        Log.d(TAG,sql);
        //Log.d(TAG, "mas_size" + new FlatRepository(getApplicationContext()).getAll().size());
        adapter = new myAdapter(this, new FlatRepository(this).getFlatsByQuery(sql),0);
        //Log.d("myDebug","adapter.size="+adapter.getCount());
        if(adapter.getCount() == 0 ){
            textNoRow.setVisibility(View.VISIBLE);
        }else{
            textNoRow.setVisibility(View.GONE);
        }
        adapterPosition = 0;
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new listviewItemSelected(this, planPanel, resultPanel,viewPlanPanel));


        changePanel(RESULT_PANEL);
    }


    public void titleOfListviewOnClick(View v){
        String order = null;
        switch (v.getId()){
            case R.id.colKorpus:
                order = " order by corpus";
                break;
            case R.id.colFlat:
                order = " order by nom_kv";
                break;
            case R.id.colFloor:
                order = " order by etag";
                break;
            case R.id.colRooms:
                order = " order by comnat";
                break;
            case R.id.colSquare:
                order = " order by ploshad";
                break;
            case R.id.colCost:
                order = " order by price";
                break;
            case R.id.colStatus:
                order = " order by status";
                break;
        }
        loadFromSqlite(query + order);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if(settingsPanel.getVisibility() == View.VISIBLE || resultPanel.getVisibility() == View.VISIBLE || planPanel.getVisibility() == View.VISIBLE) {
            if(settingsPanel.getVisibility() == View.VISIBLE){
                saveSettings();
                changePanel(MAIN_PANEL);
            }

            if(resultPanel.getVisibility() == View.VISIBLE){
                changePanel(MAIN_PANEL);
            }

            if(planPanel.getVisibility() == View.VISIBLE){
                changePanel(RESULT_PANEL);
            }
        }else {
            //если будет нажата кнопка назад на самом планшете, то программы свернется и не закроется
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    public void btnCorpusOnClick(View v){
        switch (v.getId()){
            case R.id.btnCorpus1:
                btn_corpus1.setStatus();
                break;
            case R.id.btnCorpus2:
                btn_corpus2.setStatus();
                break;
            case R.id.btnCorpus3:
                btn_corpus3.setStatus();
                break;
        }
    }

    public void changePanel(int namePanel){
        mainPanel.setVisibility(View.GONE);
        resultPanel.setVisibility(View.GONE);
        settingsPanel.setVisibility(View.GONE);
        planPanel.setVisibility(View.GONE);

        switch (namePanel){
            case 0:
                mainPanel.setVisibility(View.VISIBLE);
                break;
            case 1:
                resultPanel.setVisibility(View.VISIBLE);
                break;
            case 2:
                settingsPanel.setVisibility(View.VISIBLE);
                break;
            case 3:
                planPanel.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void btnControllingPlan(View v){
        switch(v.getId()){
            case R.id.btnNext:
                if(adapterPosition < adapter.getCount()){
                    adapterPosition++;
                }
                break;
            case R.id.btnLast:
                if (adapterPosition > 0){
                    adapterPosition--;
                }
                break;
        }

        loadDataInPlanPanel(adapterPosition);
        // первый элемент списка
        listview.performItemClick(listview.getAdapter().
                getView(adapterPosition, null, null), adapterPosition, listview.getAdapter().getItemId(adapterPosition));
        listview.setSelection(adapterPosition);
    }


    private void loadDataInPlanPanel(int positon){
        Cursor cursor = (Cursor) adapter.getItem(positon);
        String ID = cursor.getString(cursor.getColumnIndex("_id"));
        Flat flat = new FlatRepository(getApplicationContext()).findById(ID);
        switch (flat.getComnat()){
            case 1:
                titleCountRooms.setText(R.string.one_bedroom);
                break;
            case 2:
                titleCountRooms.setText(R.string.two_bedroom);
                break;
            case 3:
                titleCountRooms.setText(R.string.three_bedroom);
                break;
            case 4:
                titleCountRooms.setText(R.string.four_bedroom);
                break;
        }
        titlePrice.setText(Flat.makePrettyCost(flat.getPrice().toString())+" руб.");
        textCorpus.setText(flat.getCorpus()+"");
        textNumberFlats.setText(flat.getNom_kv()+"");
        textFloor.setText(flat.getEtag()+"");
        textSquare.setText(flat.getPloshad()+"");
        textStatus.setText(Flat.setCorrectStatus(flat.getStatus()));
        //***********Меняем планировку на ту, что нада*****************
        //Example:  plan_floor9_corpus2
        String fileNameFloor = "plan_floor"+flat.getEtag()+"_corpus"+flat.getCorpus();

        //Example: plan_flat95_floor6_corpus2
        String fileNameFlat = "plan_flat" + flat.getNom_kv() + "_floor" + flat.getEtag() + "_corpus"+flat.getCorpus();

        int floorResID=0;
        int flatResID=0;
        try {
            floorResID = getResources().getIdentifier(fileNameFloor, "drawable", getPackageName());
            flatResID =  getResources().getIdentifier(fileNameFlat, "drawable", getPackageName());
        }catch (Exception e){
            Log.d("myDebug"," "+e);
        }
        imgPlanFloor.setImageResource(floorResID);
        imgPlanFlat.setImageResource(flatResID);
        //*******************************************************
    }
}
