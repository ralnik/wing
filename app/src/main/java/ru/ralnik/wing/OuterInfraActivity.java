package ru.ralnik.wing;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import ru.ralnik.wing.config.myConfig;
import ru.ralnik.wing.httpPlayer.HttpPlayerFactory;
import ru.ralnik.wing.httpPlayer.PlayerCommands;
import ru.ralnik.wing.myLibrary.NavigationButton.DemonsrationButton;

public class OuterInfraActivity {
    private final Activity activity;
    private View view;
    private AlertDialog alertDialog;
    private DemonsrationButton btnDoy;
    private DemonsrationButton btnTrc;
    private DemonsrationButton btnSchool;
    private DemonsrationButton btnSupermarket;
    private DemonsrationButton btnChildSection;
    private DemonsrationButton btnFitness;
    private DemonsrationButton btnInstitutes;
    private DemonsrationButton btnRestaurant;
    private DemonsrationButton btnHospital;
    private DemonsrationButton btnMovies;
    private DemonsrationButton btnDrugstore;
    private ImageView btnClose;

    private myConfig cfg;
    private PlayerCommands vvvv;

    public OuterInfraActivity(Activity activity) {
        this.activity = activity;
        view = activity.getLayoutInflater().inflate(R.layout.outer_infra_layout, null); // Получаем layout по его ID
        //initialize all components
        init();
        // show activity
        show();

        cfg = new myConfig(activity.getApplicationContext());

        vvvv = HttpPlayerFactory.getInstance(activity.getApplicationContext()).getCommand();
    }

    private void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setView(this.view);
        builder.setCancelable(true);
        builder.create();
        Dialog dialog = builder.show();
        btnClose.setOnClickListener(new btnCloseOnClick(dialog));
        /*
        //Если нужно добавить снизу диалогового окна 2 кнопки ОК и Отмена
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
            public void onClick(DialogInterface dialog, int whichButton) {
               // MainActivity.doSaveSettings(); // Переход в сохранение настроек MainActivity
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // Кнопка Отмена
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        */



    }

    private void init() {
        // Определяем SeekBar и привязываем к нему дельты настроек
        btnDoy = (DemonsrationButton) view.findViewById(R.id.btnDoy);
        btnTrc = (DemonsrationButton) view.findViewById(R.id.btnTrc);
        btnSchool = (DemonsrationButton) view.findViewById(R.id.btnSchool);
        btnSupermarket = (DemonsrationButton) view.findViewById(R.id.btnSupermarket);
        btnChildSection = (DemonsrationButton) view.findViewById(R.id.btnChildSection);
        btnFitness = (DemonsrationButton) view.findViewById(R.id.btnFitness);
        btnInstitutes = (DemonsrationButton) view.findViewById(R.id.btnInstitutes);
        btnRestaurant = (DemonsrationButton) view.findViewById(R.id.btnRestaurant);
        btnHospital = (DemonsrationButton) view.findViewById(R.id.btnHospital);
        btnMovies = (DemonsrationButton) view.findViewById(R.id.btnMovies);
        btnDrugstore = (DemonsrationButton) view.findViewById(R.id.btnDrugstore);


        btnClose = (ImageView) view.findViewById(R.id.btnClose);
//
          btnDoy.setOnClickListener(new onClick());
          btnTrc.setOnClickListener(new onClick());
          btnSchool.setOnClickListener(new onClick());
          btnSupermarket.setOnClickListener(new onClick());
          btnChildSection.setOnClickListener(new onClick());
          btnFitness.setOnClickListener(new onClick());
          btnInstitutes.setOnClickListener(new onClick());
          btnRestaurant.setOnClickListener(new onClick());
          btnHospital.setOnClickListener(new onClick());
          btnMovies.setOnClickListener(new onClick());
          btnDrugstore.setOnClickListener(new onClick());


    }

    private class onClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            btnDoy.setStatus(false);
            btnTrc.setStatus(false);
            btnSchool.setStatus(false);
            btnSupermarket.setStatus(false);
            btnChildSection.setStatus(false);
            btnFitness.setStatus(false);
            btnInstitutes.setStatus(false);
            btnRestaurant.setStatus(false);
            btnHospital.setStatus(false);
            btnMovies.setStatus(false);
            btnDrugstore.setStatus(false);
            switch (v.getId()){
                case R.id.btnDoy:
                    btnDoy.setStatus();
                    vvvv.selectBySubId(1);
                    break;
                case R.id.btnTrc:
                    btnTrc.setStatus();
                    vvvv.selectBySubId(2);
                    break;
                case R.id.btnSchool:
                    btnSchool.setStatus();
                    vvvv.selectBySubId(3);
                    break;
                case R.id.btnSupermarket:
                    btnSupermarket.setStatus();
                    vvvv.selectBySubId(4);
                    break;
                case R.id.btnChildSection:
                    btnChildSection.setStatus();
                    vvvv.selectBySubId(5);
                    break;
                case R.id.btnFitness:
                    btnFitness.setStatus();
                    vvvv.selectBySubId(6);
                    break;
                case R.id.btnInstitutes:
                    btnInstitutes.setStatus();
                    vvvv.selectBySubId(7);
                    break;
                case R.id.btnRestaurant:
                    btnRestaurant.setStatus();
                    vvvv.selectBySubId(8);
                    break;
                case R.id.btnHospital:
                    btnHospital.setStatus();
                    vvvv.selectBySubId(9);
                    break;
                case R.id.btnMovies:
                    btnMovies.setStatus();
                    vvvv.selectBySubId(10);
                    break;
                case R.id.btnDrugstore:
                    btnDrugstore.setStatus();
                    vvvv.selectBySubId(11);
                    break;
                default:
                    break;
            }
        }
    }

    private class btnCloseOnClick implements View.OnClickListener{
        Dialog dialog;
        public btnCloseOnClick(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View view) {
            //vvvv.selectBySubId(0);
            dialog.dismiss();
        }
    }


}
