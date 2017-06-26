package com.example.najm.annonce;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Test;

/**
 * Created by najm on 06/07/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    @Test
    public void textedit (){
        EditText et = (EditText)getActivity().findViewById(R.id.user_name);
        assertNotNull(et);

    }
    @Test
    public void buttun (){
        Button etb = (Button)getActivity().findViewById(R.id.button2);
        assertNotNull(etb);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
