package com.yujian.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yujian.R;
import com.yujian.utils.Common;

import java.util.Objects;

import timber.log.Timber;

public class TextInputLayoutPhoneCodeEx extends TextInputLayout {

    private TextInputEditText textInputEditText;
    private TextView textView;
    private Context context;
    private AttributeSet attributeSet;
    public TextInputLayoutPhoneCodeEx(Context context){
        this(context , null , 0);
    }

    public TextInputLayoutPhoneCodeEx(Context context , AttributeSet attributeSet){
        this(context , attributeSet , 0);
    }

    public TextInputLayoutPhoneCodeEx(Context context , AttributeSet attributeSet , int defStyleAttr){
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

        inflater.inflate(R.layout.widget_text_input_phone_code , this , true);
        FrameLayout frameLayout = (FrameLayout) getChildAt(0);
        if(frameLayout == null){
            throw new Exception("no child view");
        }










        textInputEditText = (TextInputEditText)frameLayout.getChildAt(0);
        textView = (TextView)getChildAt(1);

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0,
                LayoutParams.MATCH_PARENT,
                1.0f
        );
        frameLayout.setLayoutParams(layoutParams);




        TypedArray typedArray = context.obtainStyledAttributes(attributeSet , R.styleable.TextInputLayoutPhoneCodeEx , 0 , 0);

        float width = typedArray.getDimension(R.styleable.TextInputLayoutPhoneCodeEx_width , R.dimen.input_text_width);



        float height = typedArray.getDimension(R.styleable.TextInputLayoutPhoneCodeEx_height , R.dimen.input_text_height);

        setLayoutParams(new LinearLayoutCompat.LayoutParams(
                Common.dpToPx(width) ,
                Common.dpToPx(height)));

//        String textHint1 = typedArray.getString(R.styleable.TextInputLayoutPhoneCodeEx_mytext);




        String textHint = typedArray.getString(R.styleable.TextInputLayoutPhoneCodeEx_textHintEx);

        textInputEditText.setHint("");

        float textSize = typedArray.getDimension(R.styleable.TextInputLayoutPhoneCodeEx_textSizeEx , R.dimen.input_text_size);
        textInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX , textSize);

        int inputType = typedArray.getInt(R.styleable.TextInputLayoutPhoneCodeEx_android_inputType , EditorInfo.TYPE_TEXT_VARIATION_NORMAL);

        textInputEditText.setInputType(inputType);


        String textLabel = getHint().toString();
        if(!Objects.equals(textHint , textLabel)){
            textInputEditText.setOnFocusChangeListener(new OnFocusChangeListener(){
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

        
        String tip = typedArray.getString(R.styleable.TextInputLayoutPhoneCodeEx_tip);
        textView.setText(tip);
//        this.setEndIconOnClickListener

//        setEndIconMode
        typedArray.recycle();

    }

    public String getText(){
        return textInputEditText.getText().toString().trim();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public TextInputLayoutPhoneCodeEx(Context context , AttributeSet attributeSet , int defStyleAttr , int defStyleRes){
//        super(context , attributeSet , defStyleAttr , defStyleRes);
//    }

}
