package app.me.hungrykiwi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import app.me.hungrykiwi.R;

public class WebDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_demo);
        String data = this.getIntent().getType();
        ((WebView)this.findViewById(R.id.web)).loadData(data, "text/html; charset=UTF-8", null);
    }
}
