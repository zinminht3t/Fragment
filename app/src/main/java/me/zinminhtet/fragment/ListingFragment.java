package me.zinminhtet.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class ListingFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list, container, false);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), Item.getParts(),
                android.R.layout.simple_list_item_2,
                new String[]{"id", "description"},
                new int[]{android.R.id.text1, android.R.id.text2});
        setListAdapter(adapter);
        return (v);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Item s = (Item) getListAdapter().getItem(position);
        if (getActivity().findViewById(R.id.detailsframe) == null) { // there is no defined detailsframe in activity_main (portrait)
            // single-pane
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("details", s);
            startActivity(intent);
        } else
            // multi-pane
            display(s);
    }

    void display(Item details) {
        final String TAG = "DETAILS_FRAG";
        FragmentManager fm = getFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();

        Fragment frag = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", details);
        frag.setArguments(args);
        if (fm.findFragmentByTag(TAG) == null)
            // fragment not found -- to be added
            trans.add(R.id.detailsframe, frag, TAG);
        else
            // fragment found -- to be replaced
            trans.replace(R.id.detailsframe, frag, TAG);
        trans.commit();
    }

}
