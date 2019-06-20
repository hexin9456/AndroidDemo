package com.example.hexin.testdemo.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.hexin.testdemo.R;
import com.example.hexin.testdemo.home.presenter.HomePresenterCompl;
import com.example.hexin.testdemo.home.presenter.IHomePresenter;
import com.example.hexin.testdemo.home.view.IHomeView;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,IHomeView {

	private ListView listView;
	private IHomePresenter homePresenter;
	List<String> datas = new ArrayList<>();
	private BaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		//find view
		listView = (ListView) this.findViewById(R.id.list_home);

		//set listener
		listView.setOnItemClickListener(this);

		//init
		View loadingView = LayoutInflater.from(this).inflate(R.layout.item_empty_view, null);
		ViewGroup viewGroup = (ViewGroup) this.findViewById(R.id.layout_home);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		viewGroup.addView(loadingView, layoutParams);
		listView.setEmptyView(loadingView);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
		listView.setAdapter(adapter);
		homePresenter = new HomePresenterCompl(this,this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		homePresenter.loadDatas();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		homePresenter.onItemClick(position);
	}

	@Override
	public void onGetDataList(List<String> datas) {
		if (datas!=null&&datas.size()>0){
			this.datas.clear();
			this.datas.addAll(datas);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void toast(String msg) {
		Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_github) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("https://github.com/kaedea/"));
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
