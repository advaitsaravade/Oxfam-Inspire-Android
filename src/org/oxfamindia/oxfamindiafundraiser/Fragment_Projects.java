package org.oxfamindia.oxfamindiafundraiser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Projects extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment__projects, container, false);
		
		RecyclerView recList = (RecyclerView) rootView.findViewById(R.id.cardList);
	      recList.setHasFixedSize(true);
	      LinearLayoutManager llm = new LinearLayoutManager(getActivity());
	      llm.setOrientation(LinearLayoutManager.VERTICAL);
	      recList.setLayoutManager(llm);
	      
		  RecyclerView.Adapter mAdapter = new ProjectsAdapter();
		  recList.setAdapter(mAdapter);
        
        return rootView;
	}
}
