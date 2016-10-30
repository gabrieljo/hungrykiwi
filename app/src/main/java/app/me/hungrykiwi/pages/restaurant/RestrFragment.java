package app.me.hungrykiwi.pages.restaurant;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.me.hungrykiwi.R;
import app.me.hungrykiwi.component.viewutil.ViewUtil;
import app.me.hungrykiwi.service.LocationService;
import app.me.hungrykiwi.service.RestrService;

/**
 * restaurant fragment
 * Created by peterlee on 2016-09-13.
 */
public class RestrFragment extends Fragment implements View.OnClickListener{
    boolean isAgain = false;
    LocationService locationService;
    public static final int RC_GPS_REQUEST = 1;
    RestrAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.adapter = new RestrAdapter(this.getContext());
        RecyclerView recycler = (RecyclerView)this.getView().findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(this.adapter);

        this.getView().findViewById(R.id.fabSearch).setOnClickListener(this);
        this.getView().findViewById(R.id.fabFilter).setOnClickListener(this);
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(menuVisible && !this.isAgain) { // when this fragment is visible
            this.isAgain = true;
            LocationManager manager = (LocationManager)this.getContext().getSystemService(Context.LOCATION_SERVICE);
            this.locationService = new LocationService(this.getContext(), this);
            if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == false) {
                new ViewUtil().confirmDialog(this.getContext(), "GPS is Disabled", "Please turn on GPS to detect restaurant around you", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), RC_GPS_REQUEST);
                    }
                });
            }
        }
    }

    /**
     * refresh restaurant info
     */
    public void refresh() {
        this.adapter.clear();
        this.locationService.refetch();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_GPS_REQUEST && this.locationService != null && !this.locationService.inConnected()) {
            this.locationService.startService();
        }
    }

    /**
     * search restaurant by latitude and longitude
     * @param latitude
     * @param longitude
     */
    public void search(double latitude, double longitude) {
        if(this.isVisible() == true && this.adapter != null) new RestrService(this.getContext()).search(latitude, longitude, 10000, this.adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabSearch : // set criterial
                break;
            case R.id.fabFilter :
                break;
        }
    }
}
