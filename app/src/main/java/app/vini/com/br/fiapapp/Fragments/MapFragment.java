package app.vini.com.br.fiapapp.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import app.vini.com.br.fiapapp.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    static final LatLng FIAP = new LatLng(-23.5719488, -46.6541457);
    private GoogleMap map;
    private MapView mapView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map, null, false);

        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);


        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng fiap = new LatLng(FIAP.latitude, FIAP.longitude);
        map.addMarker(new MarkerOptions().position(fiap).title("FIAP"));
        map.animateCamera(CameraUpdateFactory.newLatLng(fiap));
        map.setMapType(GoogleMap.MAP_TYPE_NONE);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(FIAP, 15));

        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
}