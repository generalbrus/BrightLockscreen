package com.generalbrus.brightlockscreen;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        TextView content = (TextView)findViewById(R.id.about_content);
        content.setText(Html.fromHtml("<b>Bright Lockscreen Xposed Framework Module</b>  has been developed by Generalbrus@gmail.com. <br/>" +
                 "<i>Follow me on Twitter <a href='http://twitter.com/hypocryptic'>@Hypocryptic</a>.</i><br/><br/>"+
                 "For further information or support please visit " +
                 "<a href='http://forum.xda-developers.com/xposed/modules/xposed-bright-lockscreen-dark-overlay-t3198020\'>the official XDA thread.</a><br/><br/> "+
                 "If you wish to buy me a Cappuccino (wow, thank you!), you can do that <a href='https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=4QNPC8DE7YV3W'>here</a>."));
        content.setMovementMethod(LinkMovementMethod.getInstance());

        ImageView imageView = (ImageView) findViewById(R.id.coffee);
        imageView.setImageResource(R.drawable.coffee);
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
