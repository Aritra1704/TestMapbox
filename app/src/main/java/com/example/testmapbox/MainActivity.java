package com.example.testmapbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
//import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.RasterLayer;
import com.mapbox.mapboxsdk.style.sources.RasterSource;
import com.mapbox.mapboxsdk.style.sources.TileSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected MapView mapView;
    protected MapboxMap mMap;
    protected Style mStyle;

//    LatLng latlng = new LatLng(40.6892, -74.5447);// Test
    LatLng latlng = new LatLng(28.599958529400887, 77.23146715267998);//Delhi
//    LatLng latlng = new LatLng(18.594885721365845, 83.76653401161875);

    public static final float MIN_ZOOM_LEVEL = 18.0F;
    public static final float MAX_ZOOM_LEVEL = 5.0F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);
//        mapView.setStyleUrl("mapbox://styles/avuchi/ciovcq1mb0048djm89t5g94jq");
//        mapView.setStyleUrl("mapbox://styles/avuchi/cj3ygeje61a592spphkcpd3hb");
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mMap = mapboxMap;
//                setupWMSmap();
//
//                showCurrentLocation();
//                setCenter();
//                setupPolylines();
                mMap.setStyle( "mapbox://styles/aritra1704/cjzmsnx6413ja1coxu4m7i0nm", (@NonNull Style style) -> {
                    mStyle = style;
                    setupWMSmap();

                    showCurrentLocation();
                    setCenter();
                    setupPolylines();
                });
            }
        });
    }

    void setupWMSmap() {

//        String testWMS = "http://45.114.245.254/erdas-iws/ogc/wms?srs=EPSG:3857&layers=Worldview_palakonda.ecw&request=GetMap&version=1.1.1&width=256&height=256&format=image/png&service=WMS&styles=default&bbox={bbox-epsg-3857}";
//        RasterSource rasterSource = new RasterSource("wms_map_source", new TileSet("tileset", testWMS), 256);

//        String testUtl = "https://gis.ndmc.gov.in/arcgis/rest/services/NDMC/Satellite_Imagery/MapServer/export?dpi=96&transparent=true&format=png32&layers=show:0&bboxSR=102100&imageSR=102100&&size=518,588&f=image";
//        String testUtl = "https://gis.ndmc.gov.in/arcgis/rest/services/NDMC/Satellite_Imagery/MapServer/export?dpi=96&transparent=true&format=png32&layers=show%3A0&bbox={bbox-epsg-3857}&bboxSR=EPSG:3857&imageSR=EPSG:3857&size=256,256&f=image";

        String testUtl = "https://4.aerial.maps.api.here.com/maptile/2.1/maptile/newest/satellite.day/{z}/{x}/{y}/256/png8?app_id=Dxf2ZJAXlQRodflL2OfJ&app_code=-c6kLXeRC0RbvautuMUnHw";
//        String testUtl = "https://img.nj.gov/imagerywms/Natural2015?bbox={bbox-epsg-3857}&format=image/png&service=WMS&version=1.1.1&request=GetMap&srs=EPSG:3857&transparent=true&width=256&height=256&layers=Natural2015";

        RasterSource rasterSource = new RasterSource("wms_map_source", new TileSet("tileset", testUtl), 256);
//        mMap.addSource(rasterSource);
        mStyle.addSource(rasterSource);
        RasterLayer rasterLayer = new RasterLayer("wms_map_layer", "wms_map_source");
//        mMap.addLayerAt(rasterLayer, 1);
        mStyle.addLayerAt(rasterLayer, 1);

    }

    private void showCurrentLocation() {

        MarkerOptions currentMarker = new MarkerOptions()
                .position(latlng)
                .title("Your location");
        IconFactory iconFactory = IconFactory.getInstance(MainActivity.this);
        Icon icon = iconFactory.fromResource(R.drawable.ic_current_marker);
        currentMarker.setIcon(icon);
        mMap.removeMarker(currentMarker.getMarker());
        mMap.addMarker(currentMarker);

        setCenter();
    }

    private void setupPolylines() {
        List<LatLng> list = new ArrayList<>();
        LatLng ltlng = new LatLng(28.62738567889351,77.23404335982197);
        list.add(ltlng);
        ltlng = new LatLng(28.626937415407387,77.2344896793038);
        list.add(ltlng);

        ltlng = new LatLng(15.13027494,78.51097773);
        list.add(ltlng);
//        ltlng = new LatLng(15.13027579,78.51098897);
//        list.add(ltlng);
//        ltlng = new LatLng(15.13028382,78.51100041);
//        list.add(ltlng);


        PolylineOptions polylineOptions = new PolylineOptions().addAll(list);
        polylineOptions.color(getResources().getColor(R.color.colorAccent));
        polylineOptions.width(6f);
        mMap.addPolyline(polylineOptions);
    }

    void setCenter() {
        CameraPosition position = new CameraPosition.Builder()
                .target(latlng)
                .zoom(MIN_ZOOM_LEVEL) // Sets the zoom
//                .bearing(180) // Rotate the camera
//                .tilt(30) // Set the camera tilt
                .build(); //
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 7000);
        mMap.setMinZoomPreference(MAX_ZOOM_LEVEL);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
