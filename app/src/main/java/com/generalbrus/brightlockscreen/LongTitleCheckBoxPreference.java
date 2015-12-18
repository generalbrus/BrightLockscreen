package com.generalbrus.brightlockscreen;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class LongTitleCheckBoxPreference extends CheckBoxPreference
{
    public LongTitleCheckBoxPreference(Context ctx, AttributeSet attrs, int defStyle)
    {
        super(ctx, attrs, defStyle);
    }

    public LongTitleCheckBoxPreference(Context ctx, AttributeSet attrs)
    {
        super(ctx, attrs);
    }

    public LongTitleCheckBoxPreference(Context ctx)
    {
        super(ctx);
    }

    @Override
    protected void onBindView(View view)
    {
        super.onBindView(view);

        TextView title= (TextView)view.findViewById(android.R.id.title);
        title.setSingleLine(false);
    }
}
