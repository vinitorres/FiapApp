package app.vini.com.br.fiapapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.vini.com.br.fiapapp.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    static final LatLng FIAP = new LatLng(-23.5719488, -46.6541457);

    private Context mContext;
    private SupportMapFragment supportMapFragment;
    private GoogleMap map;

    private SupportMapFragment mMapFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map, null, false);

        getActivity().setTitle("Map");


        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);


        mMapFragment.getMapAsync(this);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();

        FragmentManager fm = getActivity().getSupportFragmentManager();
        supportMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng fiap = new LatLng(FIAP.latitude, FIAP.longitude);
        map.addMarker(new MarkerOptions().position(fiap).title("FIAP"));
        //map.animateCamera(CameraUpdateFactory.newLatLng(fiap));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(FIAP, 15));

        //map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

    }
}