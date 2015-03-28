package com.jjakenichol.practice.interactivestory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;


public class MainActivity extends Activity
{
  private EditText mNameField;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mNameField = (EditText) findViewById(R.id.nameEditText);
  }
}
