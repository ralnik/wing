package ru.ralnik.wing.myseekbarrange;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Random;

import io.apptik.widget.MultiSlider;
import ru.ralnik.wing.R;

public class SeekbarRange extends LinearLayout {

    private LinearLayout ll_for_edits;
    private myEditText editMin;
    private myEditText editMax;
    private MultiSlider seekbar;
    private MultiSlider.Thumb thumb1;
    private MultiSlider.Thumb thumb2;

    //   ******* ATTRIBUTES ***************
    private int sbr_absoluteMinValue, sbr_absoluteMaxValue;
    private int sbr_textSizeOfEdits;
    private int sbr_textColorOfEdits;
    private Drawable sbr_bgSeekbarRange;
    private Drawable sbr_bgEditText;
    private int sbr_barColor;
    private Drawable sbr_ThumbLeft_HighlightColor;
    private Drawable sbr_ThumbRight_HighlightColor;
    private Drawable sbr_left_thumb_image;
    private Drawable sbr_left_thumb_image_pressed;
    private Drawable sbr_right_thumb_image;
    private Drawable sbr_right_thumb_image_pressed;
    private int sbr_edits_ems;
    private boolean sbr_show_edits;
    private int sbr_dataType;


    public SeekbarRange(Context context) {
        super(context);
        init(context);
    }

    public SeekbarRange(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SeekbarRange);
        try {
//            cornerRadius           = getCornerRadius(array);
//            minStartValue          = getMinStartValue(array);
//            maxStartValue          = getMaxStartValue(array);
//            steps                  = getSteps(array);
//            gap                    = getGap(array);
//            fixGap                 = getFixedGap(array);
//            leftThumbColorNormal   = getLeftThumbColor(array);
//            rightThumbColorNormal  = getRightThumbColor(array);
//            leftThumbColorPressed  = getLeftThumbColorPressed(array);
//            rightThumbColorPressed = getRightThumbColorPressed(array);
            this.sbr_textSizeOfEdits = typedArray.getInt(R.styleable.SeekbarRange_sbr_textSizeOfEdits, 8);
            this.sbr_textColorOfEdits = typedArray.getColor(R.styleable.SeekbarRange_sbr_textColorOfEdits, Color.BLACK);
            this.sbr_absoluteMinValue = typedArray.getInt(R.styleable.SeekbarRange_sbr_absoluteMinValue, 0);
            this.sbr_absoluteMaxValue = typedArray.getInt(R.styleable.SeekbarRange_sbr_absoluteMaxValue, 100);
            this.sbr_bgEditText = typedArray.getDrawable(R.styleable.SeekbarRange_sbr_bgEdits);
            this.sbr_bgSeekbarRange = typedArray.getDrawable(R.styleable.SeekbarRange_sbr_bgSeekbarRange);
            this.sbr_barColor = typedArray.getColor(R.styleable.SeekbarRange_sbr_barColor, Color.GRAY);
            this.sbr_ThumbLeft_HighlightColor = typedArray.getDrawable(R.styleable.SeekbarRange_sbr_ThumbLeft_HighlightColor);
            this.sbr_ThumbRight_HighlightColor = typedArray.getDrawable(R.styleable.SeekbarRange_sbr_ThumbRight_HighlightColor);
            this.sbr_left_thumb_image = typedArray.getDrawable(R.styleable.SeekbarRange_sbr_left_thumb_image);
            this.sbr_right_thumb_image = typedArray.getDrawable(R.styleable.SeekbarRange_sbr_right_thumb_image);
            this.sbr_left_thumb_image_pressed = typedArray.getDrawable(R.styleable.SeekbarRange_sbr_left_thumb_image_pressed);
            this.sbr_right_thumb_image_pressed = typedArray.getDrawable(R.styleable.SeekbarRange_sbr_right_thumb_image_pressed);
            this.sbr_edits_ems = typedArray.getInt(R.styleable.SeekbarRange_sbr_edits_ems, 4);
            this.sbr_show_edits = typedArray.getBoolean(R.styleable.SeekbarRange_sbr_show_edits, true);
            this.sbr_dataType = typedArray.getInt(R.styleable.SeekbarRange_sbr_data_type, 0);
        } finally {
            typedArray.recycle();
        }

        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        seekbar = new MultiSlider(context);
        seekbar.clearThumbs();
        thumb1 = seekbar.new Thumb();
        thumb2 = seekbar.new Thumb();


        ll_for_edits = new LinearLayout(context);
        ll_for_edits.setOrientation(HORIZONTAL);

        addView(seekbar);
        addView(ll_for_edits);

        editMin = new myEditText(context);
        editMin.setText("11");

        editMax = new myEditText(context);
        editMax.setText("100");

        //Устанавливаем размер шрифта
        editMin.setSBR_textSize(sbr_textSizeOfEdits);
        editMax.setSBR_textSize(sbr_textSizeOfEdits);

        //Устанавливаем цвет шрифта
        editMin.setSBR_TextColor(sbr_textColorOfEdits);
        editMax.setSBR_TextColor(sbr_textColorOfEdits);

        //задний фон
        editMin.setBackground(sbr_bgEditText);
        editMax.setBackground(sbr_bgEditText);

        editMin.setEms(sbr_edits_ems);
        editMax.setEms(sbr_edits_ems);


        FrameLayout fl = new FrameLayout(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        fl.setLayoutParams(layoutParams);
        setLayoutParams(layoutParams);

        ll_for_edits.addView(editMin);
        ll_for_edits.addView(fl);
        ll_for_edits.addView(editMax);

        //отображение едитов
        setShowEdits(sbr_show_edits);

        //Устанавливаем абсолютные значения seekbar-range
        seekbar.setMin(sbr_absoluteMinValue);
        seekbar.setMax(sbr_absoluteMaxValue);


        //Задний фон
        seekbar.setTrackDrawable(sbr_bgSeekbarRange);

        //Цвет выделенного
        thumb1.setRange(sbr_ThumbLeft_HighlightColor);
        thumb2.setRange(sbr_ThumbRight_HighlightColor);


        //thumb
        thumb1.setThumb(sbr_left_thumb_image);
        thumb2.setThumb(sbr_right_thumb_image);

        //thumb default position
        thumb1.setValue(sbr_absoluteMinValue);
        thumb2.setValue(sbr_absoluteMaxValue);
        editMin.setText(thumb1.getValue() + "");
        editMax.setText(thumb2.getValue() + "");

        seekbar.addThumb(thumb1);
        seekbar.addThumb(thumb2);

        seekbar.setOnThumbValueChangeListener(new seekBarOnChange());

        editMin.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //editMax.setText(keyCode+"");
                if (keyCode == 66) {
                        String result = null ;
                    switch (sbr_dataType){
                        case 0: result = editMin.getText().toString(); break;
                        case 1: result = ((int) Math.floor(Float.parseFloat(editMin.getText().toString()))) + ""; break;
                    }
                    thumb1.setValue(Integer.parseInt(result));
                }
                return false;
            }
        });

        editMax.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //проверка нажатие enter
                if (keyCode == 66) {
                        String result = null ;
                        switch (sbr_dataType){
                            case 0: result = editMax.getText().toString(); break;
                            case 1: result = ((int) Math.floor(Float.parseFloat(editMax.getText().toString()))) + ""; break;
                        }
                        thumb2.setValue(Integer.parseInt(result));
                }
                return false;
            }
        });

    }


    public class seekBarOnChange implements MultiSlider.OnThumbValueChangeListener {
        @Override
        public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
            //super.onValueChanged(multiSlider, thumb, thumbIndex, value);
            Float random  = getRandomFloat();

            //если двигается минимальный ползунок
            if (thumbIndex == 0) {
                //Если значение = 0 тогда 0 и оставляем, иначе выставится автоматом 0.0
                if (thumb.getValue() == multiSlider.getMin()) {
                    editMin.setText(multiSlider.getMin()+"");
                } else {
                    //проверяем если значение = максимальному значению тогда знач. максимального присваеваем значению минимальному, иначе значения будут отличаться после запятой
                    if (thumb.getValue() == thumb2.getValue()) {
                        editMin.setText(editMax.getText());
                    } else {
                        String result;
                            if(sbr_dataType == 0){
                                result = thumb.getValue()+"";
                            }else{
                                //Проверяем если целая часть == получиному значению от ползунка, значит ползунок не двигался, и значение в дробной части не должны менятьсяизменились
                                //т.е. при однократном нажатии на ползунок, не меняя его позиции, менялось значение в дробной части, а этого не должно быть, значения в дроб.части меняются только тогда года ползунок двигается
                                if(((int) Math.floor((float)getMinValue())) == value){
                                    result = getMinValue()+"";
                                }else {
                                    result = String.format("%.2f", (thumb.getValue() + random)).replace(",", ".");
                                }
                            }
                        editMin.setText(result);
                    }
                }
                //если двигается максимальный ползунок
            } else {
                //Если значение = 0 тогда 0 и оставляем, иначе выставится автоматом 0.0
                if (thumb.getValue() == multiSlider.getMax()) {
                    editMax.setText(multiSlider.getMax()+"");
                } else {
                    //проверяем если значение = минимальному значению тогда знач. минимального присваеваем значению максимальному, иначе значения будут отличаться после запятой
                    if (thumb.getValue() == thumb1.getValue()) {
                        editMax.setText(editMin.getText());
                    } else {
                        String result;
                        if(sbr_dataType == 0){
                            result = thumb.getValue()+"";
                        }else{
                            //Проверяем если целая часть == получиному значению от ползунка, значит ползунок не двигался, и значение в дробной части не должны менятьсяизменились
                            //т.е. при однократном нажатии на ползунок, не меняя его позиции, менялось значение в дробной части, а этого не должно быть, значения в дроб.части меняются только тогда года ползунок двигается
                                if(((int) Math.floor((float)getMaxValue())) == value){
                                    result = getMaxValue()+"";
                                }else {
                                    result = String.format("%.2f", (thumb.getValue() + random)).replace(",", ".");
                                }
                        }
                        editMax.setText(result);
                    }
                }
            }
        }
    }


        public Number getMinValue() {
            //return thumb1.getValue();
           switch (sbr_dataType){
               case 0: return Integer.parseInt(editMin.getText().toString());
               case 1: return Float.parseFloat(editMin.getText().toString());
            }
            return 0;
        }

        public Number getMaxValue() {
            //return thumb2.getValue();
            switch (sbr_dataType){
                case 0: return Integer.parseInt(editMax.getText().toString());
                case 1: return Float.parseFloat(editMax.getText().toString());
            }
            return 0;
        }

        public int getAbsoluteMinValue() {
            return seekbar.getMin();
        }

        public int getAbsoluteMaxValue() {
            return seekbar.getMax();
        }

        public void setAbsoluteMinValue(int min) {
            seekbar.setMin(min);
        }

        public void setAbsoluteMaxValue(int max) {
            seekbar.setMax(max);
        }

        public void setAbsoluteMinValue(Float min) {
            seekbar.setMin((int) Math.floor(min));
        }

        public void setAbsoluteMaxValue(Float max) {
            seekbar.setMax((int) Math.floor(max));
        }

        public void setMinValue(int min) {
            thumb1.setValue(min);
        }

        public void setMaxValue(int max) {
            thumb2.setValue(max);
        }

        public void setMinValue(float min) {
            thumb1.setValue((int) Math.floor(min));
        }

        public void setMaxValue(float max) {
            thumb2.setValue((int) Math.floor(max));
        }

        public void setShowEdits(Boolean flag) {
            if (flag == true) {
                ll_for_edits.setVisibility(VISIBLE);
            } else {
                ll_for_edits.setVisibility(GONE);
            }
        }

        private Float getRandomFloat() {
            //Проверяем если стоит dataType = Float, тогда преобразуем целое число в вещественное
            Float min = 0.1F;
            Float max = 0.9F;
            Random r = new Random();
            Float random;
            if (sbr_dataType > 0) {
                random = min + r.nextFloat() * (max - min);
            } else {
                random = 0F;
            }
            return random;
        }

        public void setDefaultValue(){
            setMinValue(seekbar.getMin());
            setMaxValue(seekbar.getMax());
        }
}