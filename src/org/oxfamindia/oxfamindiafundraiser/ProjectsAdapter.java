package org.oxfamindia.oxfamindiafundraiser;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.CustomViewHolder> {

    @Override
    public int getItemCount() {
          return 3;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder viewHolder, int i) {
        viewHolder.vTitle.setText("Rural Classrooms");
        viewHolder.vBrief.setText("Education is one of the key instruments for social change and empowerment of marginalised communities.");
        viewHolder.vCateg.setText("EDUCATION");
        viewHolder.vi.setId(12121); //project URL's ID
        viewHolder.vi.setContentDescription("put the url here"); // Or if you feel lazy enough to hard code it, you can place the url here
   }// Whatever floats your boat

   @Override
   public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
	   View itemView = null;
	   /*Random randomGenerator = new Random();
	    int rint = randomGenerator.nextInt(100);
	    if(rint <= 33)*/
	    //{
        itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_layout, viewGroup, false);
	    //}
	    /*else if(rint<=66)
	    {
	    	itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_layout2, viewGroup, false);
	    }
	    else
	    {
	    	itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.card_layout3, viewGroup, false);
	    }
*/
        return new CustomViewHolder(itemView);
   }
	public static class CustomViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
	     protected TextView vTitle;
	     protected TextView vBrief;
	     protected TextView vCateg;
	     protected TextView vID;
	     protected View vi;

	     public CustomViewHolder(View v) {
	          super(v); 
	          vi = v;
	          vTitle =  (TextView) v.findViewById(R.id.title);
	          vBrief = (TextView)  v.findViewById(R.id.brief);
	          vCateg = (TextView)  v.findViewById(R.id.category);
	          vID = (TextView) v.findViewById(R.id.vID);
	          v.setOnClickListener(this);
	      }

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
	         String packageName = "org.oxfamindia.oxfamindiafundraiser";
	         String className = "org.oxfamindia.oxfamindiafundraiser.IndividualProjectsActivity";
	         intent.setClassName(packageName, className);
	         intent.putExtra("url", v.getId());
	         intent.putExtra("origin_activity", "Projects"); // Change this if this method is being used dynamically with other activities
	         v.getContext().startActivity(intent);
		}
	 }
}
