package com.generalbrus.brightlockscreen;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        TextView content = (TextView)findViewById(R.id.about_content);
        content.setText(Html.fromHtml("<b>Bright Lockscreen Xposed Framework Module</b>  has been developed by Generalbrus@gmail.com. <br/><br/><hr>" +
                 "For further information or support please visit " +
                 "<a href=\"http://forum.xda-developers.com/xposed/modules/xposed-bright-lockscreen-dark-overlay-t3198020\">the official XDA thread.</a> "));
        content.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
