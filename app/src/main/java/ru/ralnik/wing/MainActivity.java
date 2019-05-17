package ru.ralnik.wing;

import android.content.DialogInterface;
import android.content.Intent;
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
import ru.ralnik.wing.myLibrary.NavigationButton.DemonsrationButton;
import ru.ralnik.wing.myLibrary.customListView.listviewItemSelected;
import ru.ralnik.wing.myLibrary.customListView.myAdapter;
import ru.ralnik.wing.myseekbarrange.SeekbarRange;
import ru.ralnik.wing.sqlitedb.FlatRepository;


public class MainActivity extends AppCompatActivity {
    //---------MAIN LAYOUT----------
    ScrollView mainPanel;
    LinearLayout resultPanel;
    LinearLayout settingsPanel;

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
    }

    private void initObjects() {
        mainPanel = (ScrollView) findViewById(R.id.mainLayout);
        resultPanel = (LinearLayout) findViewById(R.id.resultPanel);
        settingsPanel = (LinearLayout) findViewById(R.id.settingsLayout);
        mainPanel.setVisibility(View.VISIBLE);
        resultPanel.setVisibility(View.GONE);
        settingsPanel.setVisibility(View.GONE);

        textNoRow = (TextView) findViewById(R.id.textNoRow);
        listview = (ListView) findViewById(R.id.listview);

        btnOption = (ImageView) findViewById(R.id.btnOption);
        btnPause = (DemonsrationButton) findViewById(R.id.btnPause);
        btnVolume = (DemonsrationButton) findViewById(R.id.btnVolume);

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
        btn_corpus1.setTag(1);
        btn_corpus2.setTag(2);
        btn_corpus3.setTag(3);


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
                mainPanel.setVisibility(View.GONE);
                resultPanel.setVisibility(View.GONE);
                settingsPanel.setVisibility(View.VISIBLE);
                break;
            case R.id.btnPause:
                btnPause.setStatus();
                break;
            case R.id.btnVolume:
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
                break;
            case R.id.btnInfraRaion:
                btnInfraRaion.setStatus();
                break;
            case R.id.btnTransport:
                btnTransport.setStatus();
                break;
            case R.id.btnInfra:
                btnInfra.setStatus();
                break;
            case R.id.btnComfort:
                btnComfort.setStatus();
                break;
            case R.id.btnArchitecture:
                btnArchitecture.setStatus();
                break;
            case R.id.btnPlaning:
                btnPlaning.setStatus();
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
        query = "select * from flats where ";

        query = query + " (etag >= "+seekbarFloor.getMinValue() + " and etag <= "+seekbarFloor.getMaxValue()+") ";
        query = query + " and (ploshad >= " +((Float) seekbarSquare.getMinValue()-0.1) + " and ploshad <= "+((Float) seekbarSquare.getMaxValue()+0.1) + ") ";
        query = query + " and (price >= " + seekbarCost.getMinValue() + "*1000000" + " and price <= " + seekbarCost.getMaxValue() + "*1000000" + ") ";

        if (btn_corpus1.getStatus()){ query = query + " and corpus="+btn_corpus1.getTag(); }
        if (btn_corpus2.getStatus()){ query = query + " and corpus="+btn_corpus2.getTag(); }
        if (btn_corpus3.getStatus()){ query = query + " and corpus="+btn_corpus3.getTag(); }

        if(btnRoom1.getStatus()){ query = query + " and comnat="+btnRoom1.getTag(); }
        if(btnRoom2.getStatus()){ query = query + " and comnat="+btnRoom2.getTag(); }
        if(btnRoom3.getStatus()){ query = query + " and comnat="+btnRoom3.getTag(); }
        if(btnRoom4.getStatus()){ query = query + " and comnat="+btnRoom4.getTag(); }

        loadFromSqlite(query + " order by price");
    }

    private void loadFromSqlite(String sql) {
        Log.d(TAG,sql);
        //Log.d(TAG, "mas_size" + new FlatRepository(getApplicationContext()).getAll().size());
        myAdapter adapter = new myAdapter(this, new FlatRepository(this).getFlatsByQuery(sql),0);
        //Log.d("myDebug","adapter.size="+adapter.getCount());
        if(adapter.getCount() == 0 ){
            textNoRow.setVisibility(View.VISIBLE);
        }else{
            textNoRow.setVisibility(View.GONE);
        }
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new listviewItemSelected(this));

        mainPanel.setVisibility(View.GONE);
        settingsPanel.setVisibility(View.GONE);
        resultPanel.setVisibility(View.VISIBLE);
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
        if(settingsPanel.getVisibility() == View.VISIBLE || resultPanel.getVisibility() == View.VISIBLE) {
            if(settingsPanel.getVisibility() == View.VISIBLE){saveSettings();}
            mainPanel.setVisibility(View.VISIBLE);
            settingsPanel.setVisibility(View.GONE);
            resultPanel.setVisibility(View.GONE);
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
}
