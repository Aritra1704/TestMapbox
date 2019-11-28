package com.example.testmapbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.layers.WmsLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.portal.Portal;
import com.esri.arcgisruntime.portal.PortalItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArcgisActivity extends AppCompatActivity {

    private MapView mMapView;
    private FeatureLayer mFeatureLayer;
//    double latitude = 34.09042;
//    double longitude = -118.71511;
    double latitude = 28.599958529400887;// Delhi
    double longitude = 77.23146715267998;
//    double latitude = 18.594885721365845;
//    double longitude = 83.76653401161875;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcgis);

        mMapView = findViewById(R.id.mapView);
        setupMap();
    }

    private void setupMap() {
        if (mMapView != null) {
//            Basemap.Type basemapType = Basemap.Type.STREETS_VECTOR; // For Road maps
//            Basemap.Type basemapType = Basemap.Type.TOPOGRAPHIC;// For topography
            Basemap.Type basemapType = Basemap.Type.LIGHT_GRAY_CANVAS;// For topography

            int levelOfDetail = 11;
            ArcGISMap map = new ArcGISMap(basemapType, latitude, longitude, levelOfDetail);
            mMapView.setMap(map);

//            addLayer(map);
//            addTrailheadsLayer();
            addWMSlayer(map);
        }
    }

    private void addLayer(final ArcGISMap map) {
        String itemID = "2e4b3df6ba4b44969a3bc9827de746b3";
        Portal portal = new Portal("http://www.arcgis.com");
        final PortalItem portalItem = new PortalItem(portal, itemID);
        mFeatureLayer = new FeatureLayer(portalItem,0);
        mFeatureLayer.addDoneLoadingListener(new Runnable() {
            @Override
            public void run() {
                if (mFeatureLayer.getLoadStatus() == LoadStatus.LOADED) {
                    map.getOperationalLayers().add(mFeatureLayer);
                }
            }
        });
        mFeatureLayer.loadAsync();
    }

    private void addTrailheadsLayer() {
//        String url = "https://services3.arcgis.com/GVgbJbqm8hXASVYi/arcgis/rest/services/Trailheads/FeatureServer/";
        String url = "https://gis.ndmc.gov.in/arcgis/rest/services/NDMC/Satellite_Imagery/MapServer/export?dpi=96&transparent=true&format=png32&layers=show:0&bboxSR=102100&imageSR=102100&&size=518,588&f=image";
        ServiceFeatureTable serviceFeatureTable = new ServiceFeatureTable(url);
        FeatureLayer featureLayer = new FeatureLayer(serviceFeatureTable);
        ArcGISMap map = mMapView.getMap();
        map.getOperationalLayers().add(featureLayer);
    }

    private void addWMSlayer(final ArcGISMap map) {
//        String url = "http://sampleserver1.arcgisonline.com/ArcGIS/services/Specialty/ESRI_StatesCitiesRivers_USA/MapServer/WMSServer";
//        ArrayList<String> initialLayers = new ArrayList<String>(
//                Arrays.asList(new String[] { "0", "1", "2" }));
//        ArrayList<String> initialStyles = new ArrayList<String>(
//                Arrays.asList(new String[] { "default", "default", "default" }));
//
//        // create and add the WMS layer
//        WmsDynamicMapServiceLayer wmsLayer =
//                new WmsDynamicMapServiceLayer(url, initialLayers, initialStyles);
//        mMapView.getMap().getLayers().add(wmsLayer);

        List<String> wmsLayerNames = new ArrayList<>();
        wmsLayerNames.add("1");

//        String url = "https://gis.ndmc.gov.in/arcgis/rest/services/NDMC/Satellite_Imagery/MapServer/";//  export?dpi=96&transparent=true&format=png32&layers=show:0&bboxSR=102100&imageSR=102100&&size=518,588&f=image
        String url = "https://gis.ndmc.gov.in/arcgis/rest/services/NDMC/Satellite_Imagery/MapServer/export?request=GetCapabilities&amp;service=WMS&bbox=8586096.228650564%2C3319615.3610256063%2C8602882.243398743%2C3330652.1657225345";
//        String url = "http://45.114.245.254/erdas-iws/ogc/wms?srs=EPSG:3857&layers=Worldview_palakonda.ecw&request=GetMap&version=1.1.1&width=256&height=256&format=image/png&service=WMS&styles=default&bbox={bbox-epsg-3857}";
        // create a new WMS layer displaying the specified layers from the service
        WmsLayer wmsLayer = new WmsLayer(url, wmsLayerNames);

        // add the layer to the map
        map.getOperationalLayers().add(wmsLayer);
    }

    @Override
    protected void onPause() {
        if (mMapView != null) {
            mMapView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (mMapView != null) {
            mMapView.dispose();
        }
        super.onDestroy();
    }
}
