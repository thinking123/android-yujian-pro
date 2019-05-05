package com.yujian.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yujian.R;
import com.yujian.utils.Common;

import java.util.Objects;

import timber.log.Timber;

public class TextInputLayoutEx extends TextInputLayout {


    public interface CountDownListener {
        public void onStartCountDown();

        public void onEndCountDown();
    }

    private Common.Callback callback;
    private CountDownTimer countDownTimer;
    private CountDownListener countDownListener;
    private TextView textView;
    private int counter;
    private boolean isTouchEnable;
    private TextInputEditText textInputEditText;
    private Context context;
    private AttributeSet attributeSet;


    private String textHint = "", textLabel = "", tipText = "";
    private float width = 0, height = 0, textSize = 0, tipTextWidth = 0, tipTextSize = 0;
    private int inputType = 0, countDown = 0, tipTextColor = 0;

    public TextInputLayoutEx(Context context) {
        this(context, null, 0);
    }

    public TextInputLayoutEx(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextInputLayoutEx(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        this.context = context;
        this.attributeSet = attributeSet;
        countDownListener = null;
        countDownTimer = null;
        isTouchEnable = true;
        try {
            init();
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }

//        init();
    }

    private void init() throws Exception {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.widget_text_input_layout_ex, this, true);
        FrameLayout frameLayout = (FrameLayout) getChildAt(0);
        if (frameLayout == null) {
            throw new Exception("no child view");
        }


        textInputEditText = (TextInputEditText) frameLayout.getChildAt(0);


        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TextInputLayoutEx, 0, 0);


        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.TextInputLayoutEx_android_inputType:
                    inputType = typedArray.getInt(R.styleable.TextInputLayoutEx_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
                    break;

                case R.styleable.TextInputLayoutEx_textHintEx:
                    textHint = typedArray.getString(R.styleable.TextInputLayoutEx_textHintEx);
                    break;
                case R.styleable.TextInputLayoutEx_textSizeEx:
                    textSize = typedArray.getDimension(R.styleable.TextInputLayoutEx_textSizeEx, R.dimen.input_text_size);
                    break;
                case R.styleable.TextInputLayoutEx_width:
                    width = typedArray.getDimension(R.styleable.TextInputLayoutEx_width, R.dimen.input_text_width);
                    break;
                case R.styleable.TextInputLayoutEx_height:
                    typedArray.getDimension(R.styleable.TextInputLayoutEx_height, R.dimen.input_text_height);
                    break;
                case R.styleable.TextInputLayoutEx_countDown:
                    countDown = Integer.parseInt(typedArray.getString(R.styleable.TextInputLayoutEx_countDown));
                    break;

                case R.styleable.TextInputLayoutEx_tipText:
                    tipText = typedArray.getString(R.styleable.TextInputLayoutEx_tipText);
                    break;

                case R.styleable.TextInputLayoutEx_tipTextColor:
                    tipTextColor = typedArray.getColor(
                            R.styleable.TextInputLayoutEx_tipTextColor,
                            ContextCompat.getColor(context, R.color.text_black));
                    break;
                case R.styleable.TextInputLayoutEx_tipTextWidth:
                    tipTextWidth = typedArray.getDimension(R.styleable.TextInputLayoutEx_tipTextWidth, 0);
                    break;
                case R.styleable.TextInputLayoutEx_tipTextSize:
                    tipTextSize = typedArray.getDimension(R.styleable.TextInputLayoutEx_tipTextSize, R.dimen.input_text_size);
                    break;
            }
        }


        if (!TextUtils.isEmpty(tipText)) {
            textView = new TextView(context);

            textView.setTextColor(tipTextColor);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    tipTextWidth == 0 ? FrameLayout.LayoutParams.WRAP_CONTENT : (int) tipTextWidth,
                    FrameLayout.LayoutParams.MATCH_PARENT
            );

            layoutParams.gravity = Gravity.RIGHT;
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setText(tipText);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tipTextSize == 0 ? getResources().getDimension(R.dimen.tip_text_size) : tipTextSize);
            textView.setMaxLines(1);
            frameLayout.addView(textView);
//            frameLayout.setClipChildren(false);
//            setClipChildren(false);
            textView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    Common.showMsg("you touch");
//                    if (countDownTimer != null && isTouchEnable) {
//
//                        TypedValue value = new TypedValue();
//                        context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
//                        int colorAccent = value.data;
//                        textView.setTextColor(colorAccent);
//                        isTouchEnable = false;
//                        countDownTimer.start();
//                    }

                    if (callback != null) {
                        callback.callback();
                    }

//                    if(inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD ||
//                    inputType == InputType.TYPE_NUMBER_VARIATION_PASSWORD){
//                        passwordVisibilityToggleRequested();
//                    }

                    if (isPasswordVisibilityToggleEnabled()) {
                        passwordVisibilityToggleRequested(false);
                    }

                    return true;
                }
            });

            if (countDown > 0) {
                counter = countDown;
                countDownTimer = new CountDownTimer(
                        countDown * 1000,
                        1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        counter--;
                        textView.setText(Integer.toString(counter));
                    }

                    @Override
                    public void onFinish() {
                        isTouchEnable = true;
                        counter = countDown;
                        textView.setTextColor(tipTextColor);
                        textView.setText(tipText);
                    }
                };
            }
        }
//        float width = typedArray.getDimension(R.styleable.TextInputLayoutEx_width , R.dimen.input_text_width);
//
//
//
//        float height = typedArray.getDimension(R.styleable.TextInputLayoutEx_height , R.dimen.input_text_height);

        setLayoutParams(new LinearLayoutCompat.LayoutParams(
                Common.dpToPx(width),
                Common.dpToPx(height)));

//        String textHint1 = typedArray.getString(R.styleable.TextInputLayoutEx_mytext);
//
//        textInputEditText.setHint(textHint);


//        String textHint = typedArray.getString(R.styleable.TextInputLayoutEx_textHintEx);

        textInputEditText.setHint(textHint);

//        float textSize = typedArray.getDimension(R.styleable.TextInputLayoutEx_textSizeEx , R.dimen.input_text_size);
        textInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

//        int inputType = typedArray.getInt(R.styleable.TextInputLayoutEx_android_inputType , EditorInfo.TYPE_TEXT_VARIATION_NORMAL);

        textInputEditText.setInputType(inputType);


        textLabel = getHint().toString();
        setHint("");
        if (!Objects.equals(textHint, textLabel)) {
            textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        setHint(textLabel);
                        textInputEditText.setHint(textHint);
                    } else {
                        if (!getText().isEmpty()) {

                        } else {
                            setHint(textHint);
                            textInputEditText.setHint("");
                        }

                    }
                }
            });
        }


        typedArray.recycle();

    }

    public void startCount() {
        if (countDownTimer != null && isTouchEnable) {

            TypedValue value = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
            int colorAccent = value.data;
            textView.setTextColor(colorAccent);
            isTouchEnable = false;
            countDownTimer.start();
        }
    }

    public String getText() {
        return textInputEditText.getText().toString().trim();
    }

    public void setCountDownListener(CountDownListener countDownListener) {
        this.countDownListener = countDownListener;
    }

    public void setTapTipListener(Common.Callback callback){
        this.callback = callback;
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public TextInputLayoutEx(Context context , AttributeSet attributeSet , int defStyleAttr , int defStyleRes){
//        super(context , attributeSet , defStyleAttr , defStyleRes);
//    }

}
