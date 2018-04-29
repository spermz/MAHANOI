package com.seniorproject.kabigonb.mahanoi.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.seniorproject.kabigonb.mahanoi.R;


public class CloseServiceListItem extends BaseCustomViewGroup {

    TextView list_service_name_close,list_service_serviceName_close,list_service_location_close;

    public CloseServiceListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public CloseServiceListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public CloseServiceListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public CloseServiceListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_close_service, this);
    }

    private void initInstances() {
        // findViewById here
        list_service_name_close = findViewById(R.id.list_service_name_close);
        list_service_serviceName_close = findViewById(R.id.list_service_serviceName_close);
        list_service_location_close = findViewById(R.id.list_service_location_close);

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width*1/4;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec,newHeightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    public void setList_service_name_close(String name)
    {
        list_service_name_close.setText(name);
    }

    public void setList_service_serviceName_close(String serviceName)
    {
        list_service_serviceName_close.setText(serviceName);
    }

    public void setList_service_location_close(String location)
    {
        list_service_location_close.setText(location);
    }

}
