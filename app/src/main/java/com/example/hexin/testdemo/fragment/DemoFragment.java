package com.example.hexin.testdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import com.example.hexin.testdemo.R;
import com.example.hexin.testdemo.fragment.adapter.MyAdapter;
import com.example.hexin.testdemo.fragment.event.FragmentGetDatasEvent;
import com.example.hexin.testdemo.fragment.presenter.FragmentPresenterCompl;
import com.example.hexin.testdemo.fragment.presenter.IFragmentPresenter;
import com.example.hexin.testdemo.fragment.view.IFragmentView;


public class DemoFragment extends Fragment implements IFragmentView{
	private static final String BUNDLE_INDEX = "BUNDLE_INDEX";

	private int index;

	private MyAdapter adapter;
	private IFragmentPresenter iFragmentPresenter;


	public static DemoFragment newInstance(int index) {
		DemoFragment fragment = new DemoFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_INDEX, index);
		fragment.setArguments(args);
		return fragment;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			index = getArguments().getInt(BUNDLE_INDEX);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_demo, container, false);

		//register
		EventBus.getDefault().register(this);

		//find view
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_home);

		//init
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		iFragmentPresenter = new FragmentPresenterCompl(this);
		adapter = new MyAdapter(iFragmentPresenter);
		recyclerView.setAdapter(adapter);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}


	@Override
	public void onItemClick(int position) {
		Toast.makeText(getActivity(),"Tab "+index+" : "+(String)adapter.getItem(position), Toast.LENGTH_SHORT).show();
	}

	// EventBus Subscribe
	public void onEvent(FragmentGetDatasEvent getDatasEvent){
		if (getDatasEvent!=null && getDatasEvent.getDatas()!=null && getDatasEvent.getDatas().size()>0){
			adapter.setDatas(getDatasEvent.getDatas());
		}
	}



}
