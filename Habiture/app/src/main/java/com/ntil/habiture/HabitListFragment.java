package com.ntil.habiture;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.habiture.Habiture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GawinHsu on 5/7/15.
 */
public class HabitListFragment extends Fragment {


    private ListView item_list;
    private static List<Habiture> habitures;
    private HabitureAdapter habitureAdapter;

    private static final boolean DEBUG = true;
    private void trace(String message) {
        if(DEBUG)
            Log.d("HabitListFragment", message);
    }


    public HabitListFragment() {}

    public static HabitListFragment newInstance(List<Habiture> habitures){
        HabitListFragment fragment = new HabitListFragment();

        HabitListFragment.habitures = habitures;

        return fragment;
    }

    public static class HabitureAdapter extends BaseAdapter {
        private List<Item> items;
        private LayoutInflater inflater;
        private Listener listener;

        private static final boolean DEBUG = true;
        private void trace(String message) {
            if(DEBUG)
                Log.d("HabitListFragment", message);
        }

        public HabitureAdapter(Context context, List<Habiture> habitures){
            inflater = LayoutInflater.from(context);
            items = new ArrayList<>(habitures.size());
            for(Habiture habiture : habitures){
                Item item = new Item();
                item.habiture = habiture;
                items.add(item);
            }
            listener = (Listener) context;
        }

        public class Item {
            Habiture habiture;

            public Habiture getHabiture() {
                return habiture;
            }
        }
        @Override
        public int getCount() { return items.size(); }

        @Override
        public Object getItem(int position)  {
            return items.get(position);
        }

        @Override
        public long getItemId(int position)  {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_habit, parent, false);
                holder = new ViewHolder();
                holder.tvSwear = (TextView) convertView.findViewById(R.id.tvSwear);
                holder.tvPunishment = (TextView) convertView.findViewById(R.id.tvPunishment);
                holder.tvRemain = (TextView) convertView.findViewById(R.id.tvRemain);
                holder.btnMore = (Button) convertView.findViewById(R.id.btnMore);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }
            Item item = (Item) getItem(position);
            holder.tvSwear.setText(item.habiture.getSwear());
            holder.tvPunishment.setText("做不到的話就 " + item.habiture.getPunishment());
            holder.tvRemain.setText("本週剩餘 " + item.habiture.getRemain() + " 次數");
            holder.btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trace("onClick, pid = " + ((Item) getItem(position)).getHabiture().getId());
                    int pid = ((Item) getItem(position)).getHabiture().getId();
                    //String url = ((Item) getItem(position)).getHabiture().getUrl();
                    String url = "http://140.124.144.121/Habiture/profile/10176068_726992954019352_539454252837054186_n.jpg";
                    listener.onClickHabitSingleItem(pid, url);
                }
            });

            return convertView;
        }

        private class ViewHolder {
            TextView tvSwear;
            TextView tvPunishment;
            TextView tvRemain;
            Button btnMore;
        }

        public interface Listener {
            public void onClickHabitSingleItem(int pid, String url);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        trace("onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        item_list = (ListView) getActivity().findViewById(R.id.lvHabitureList);

        habitureAdapter = new HabitureAdapter(getActivity(), habitures);
        item_list.setAdapter(habitureAdapter);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        trace("onCreateView");
        return inflater.inflate(R.layout.fragment_habiture, container, false);
    }
}
