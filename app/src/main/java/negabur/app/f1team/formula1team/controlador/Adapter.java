package negabur.app.f1team.formula1team.controlador;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import negabur.app.f1team.formula1team.R;
import negabur.app.f1team.formula1team.model.Team;


/**
 * Created by Ruben on 5/2/15.
 */
public class Adapter extends ArrayAdapter<Team> implements Filterable{

    private ArrayList<Team>information;
    private Filter mFilter;
    private List<Team> itemsTemp;
    private ImageTool imgTool;

    public Adapter(Activity context, ArrayList<Team> information) {
        super(context, R.layout.listitem_team, information);
        this.information = information;
        this.itemsTemp = information;
        this.imgTool = new ImageTool();

    }

    @Override
    public int getCount() {
        return itemsTemp.size();
    }

    @Override
    public Team getItem(int position) {
        return itemsTemp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemsTemp.get(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        View element = inflater.inflate(R.layout.listitem_team, null);

        ImageView imgTeam = (ImageView) element.findViewById(R.id.imgTeam);
        imgTeam.setImageBitmap(imgTool.getPhoto(itemsTemp.get(position).getImageID()));


        return element;
    }


    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemsFilter();
        }
        return mFilter;
    }

    private class ItemsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                List<Team> tempList = new ArrayList<Team>();

                // search content in friend list
                for (Team team : information) {
                    if (team.getName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        tempList.add(team);
                    }
                }

                filterResults.values = tempList;
                filterResults.count = tempList.size();
            } else {
                filterResults.values = information;
                filterResults.count = information.size();
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                itemsTemp = (List<Team>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
