package ru.ralnik.wing.myseekbarrange;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;


@SuppressLint("AppCompatCustomView")
public class myEditText extends EditText {
    //   ******* ATTRIBUTES ***************



    public myEditText(Context context) {
        super(context);
        init(context);
    }

    public myEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,10,10,10);
        setLayoutParams(layoutParams);
    }


   public void setSBR_textSize(int size){
       if (size >= 8){
           setEms((int)size/2);
           setTextSize(size);
       }else{
           setEms(4);
           setTextSize(8);
       }
   }

   public void setSBR_TextColor(int color){
       setTextColor(color);
   }



}

