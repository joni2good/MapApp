package com.example.mapapp;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repo {
    private static Repo repo = new Repo();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION = "pins";
    public List<Marker> markers = new ArrayList();
    private Updatable activity;

    public static Repo r(){
        return repo;
    }

    public void setup(Updatable a, List<Marker> list){
        activity = a;
        markers = list;
        startListener();
    }

    public void startListener(){
        db.collection(COLLECTION).addSnapshotListener((values, error) -> {
            markers.clear();
            if (error == null) {
                for (DocumentSnapshot snap : values.getDocuments()) {
                    Object title = snap.get("Title");
                    GeoPoint pos = snap.getGeoPoint("position");
                    LatLng latLng = new LatLng(pos.getLatitude(), pos.getLongitude());
                    if (title != null) {
                        markers.add(new Marker(snap.getId(), title.toString(), latLng));
                    } else {
                        System.out.println("Error getting documents");
                    }
                }
            }
            activity.update(null);
        });
    }

    public void addMarker(LatLng latLng, String title){
        DocumentReference reference = db.collection(COLLECTION).document();
        Map<String, Object> map = new HashMap();
        map.put("Title", title);
        map.put("position", new GeoPoint(latLng.latitude, latLng.longitude));
        reference.set(map); // replaces previous values
        System.out.println("Inserted " + reference.getId());
    }

}
