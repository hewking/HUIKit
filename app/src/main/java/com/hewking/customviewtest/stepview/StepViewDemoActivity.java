package com.hewking.customviewtest.stepview;

import android.app.Activity;
import android.os.Bundle;

import com.hewking.customviewtest.R;


public class StepViewDemoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stepview);
//		XLHStepView view =(XLHStepView) findViewById(R.id.zidingyi);
//		view.setStepChangedListener(new XLHStepView.OnStepChangedListener() {
//
//			@Override
//			public void onStepChanged(int currentStep) {
//				Log.i("i","change:"+currentStep);
//			}
//		});
	}


}
