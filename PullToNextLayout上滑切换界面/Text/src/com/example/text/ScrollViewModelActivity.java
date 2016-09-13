package com.example.text;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mingle.pulltonextlayout.OnItemSelectListener;
import com.mingle.pulltonextlayout.PullToNextLayout;
import com.mingle.pulltonextlayout.adapter.PullToNextModelAdapter;
import com.mingle.pulltonextlayout.model.PullToNextModel;

public class ScrollViewModelActivity extends Activity {
	public  PullToNextLayout pullToNextLayout;

    private List<PullToNextModel> list;

    private int  currentIndex=0;
    private PullToNextModelAdapter adapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scroll_view_model);

        pullToNextLayout= (PullToNextLayout) findViewById(R.id.pullToNextLayout);


        list=new ArrayList<PullToNextModel>();

        for (int i = 0; i < 4; i++) {

            list.add(new ScrollViewModel(currentIndex++));
        }

        adapter=  new PullToNextModelAdapter(this,list);
        pullToNextLayout.setAdapter(adapter);

        setTitle(1+".0这是一个demo");
	}


}
