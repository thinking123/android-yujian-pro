package com.yujian.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;

import com.yujian.R;
import com.yujian.utils.Common;

import java.util.Objects;

import timber.log.Timber;

public class TextInputLayoutEx extends TextInputLayout {

    private TextInputEditText textInputEditText;
    private Context context;
    private AttributeSet attributeSet;
    public TextInputLayoutEx(Context context){
        this(context , null , 0);
    }

    public TextInputLayoutEx(Context context , AttributeSet attributeSet){
        this(context , attributeSet , 0);
    }

    public TextInputLayoutEx(Context context , AttributeSet attributeSet , int defStyleAttr){
        super(context , attributeSet , defStyleAttr);
        this.context = context;
        this.attributeSet = attributeSet;
        try {
            init();
        }catch (Exception e){
            Timber.e(e.getMessage());
        }

//        init();
    }

    private void init() throws Exception{
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.widget_text_input_layout_ex , this , true);
        FrameLayout frameLayout = (FrameLayout) getChildAt(0);
        if(frameLayout == null){
            throw new Exception("no child view");
        }










        textInputEditText = (TextInputEditText)frameLayout.getChildAt(0);


        TypedArray typedArray = context.obtainStyledAttributes(attributeSet , R.styleable.TextInputLayoutEx , 0 , 0);

//
//        String textHint = "", textLabel = "" , textHint1 = "";
//        float width , height , textSize;
//        int inputType;
//        int count = typedArray.getIndexCount();
//
//        for(int i = 0 ; i < count ; i++){
//            int attr = typedArray.getIndex(i);
//            switch (attr){
//                case R.styleable.TextInputLayoutEx_android_inputType:
//                    inputType = typedArray.getInt(R.styleable.TextInputLayoutEx_android_inputType , EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
//                    break;
//
//                case R.styleable.TextInputLayoutEx_textHintEx:
//                    textHint = typedArray.getString(R.styleable.TextInputLayoutEx_textHintEx);
//                    break;
//                case R.styleable.TextInputLayoutEx_textSizeEx:
//                    textSize = typedArray.getDimension(R.styleable.TextInputLayoutEx_textSizeEx , R.dimen.input_text_size);
//                    break;
//                case R.styleable.TextInputLayoutEx_mytext:
//                    textHint1 = typedArray.getString(R.styleable.TextInputLayoutEx_mytext);
//                    break;
//                case R.styleable.TextInputLayoutEx_width:
//                    width = typedArray.getDimension(R.styleable.TextInputLayoutEx_width , R.dimen.input_text_width);
//                    break;
//                case R.styleable.TextInputLayoutEx_height:
//                    typedArray.getDimension(R.styleable.TextInputLayoutEx_height , R.dimen.input_text_height);
//                    break;
//            }
//        }

        float width = typedArray.getDimension(R.styleable.TextInputLayoutEx_width , R.dimen.input_text_width);



        float height = typedArray.getDimension(R.styleable.TextInputLayoutEx_height , R.dimen.input_text_height);

        setLayoutParams(new LinearLayoutCompat.LayoutParams(
                Common.dpToPx(width) ,
                Common.dpToPx(height)));

        String textHint1 = typedArray.getString(R.styleable.TextInputLayoutEx_mytext);
//
//        textInputEditText.setHint(textHint);



        String textHint = typedArray.getString(R.styleable.TextInputLayoutEx_textHintEx);

        textInputEditText.setHint("");

        float textSize = typedArray.getDimension(R.styleable.TextInputLayoutEx_textSizeEx , R.dimen.input_text_size);
        textInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX , textSize);

        int inputType = typedArray.getInt(R.styleable.TextInputLayoutEx_android_inputType , EditorInfo.TYPE_TEXT_VARIATION_NORMAL);

        textInputEditText.setInputType(inputType);


        String textLabel = getHint().toString();
        if(!Objects.equals(textHint , textLabel)){
            textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        setHint(textLabel);
                        textInputEditText.setHint(textHint);
                    }else{
                        if(!getText().isEmpty()){

                        }else{
                            setHint(textHint);
                            textInputEditText.setHint("");
                        }

                    }
                }
            });
        }

//        this.setEndIconOnClickListener

//        setEndIconMode
        typedArray.recycle();

    }

    public String getText(){
        return textInputEditText.getText().toString().trim();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public TextInputLayoutEx(Context context , AttributeSet attributeSet , int defStyleAttr , int defStyleRes){
//        super(context , attributeSet , defStyleAttr , defStyleRes);
//    }

}
