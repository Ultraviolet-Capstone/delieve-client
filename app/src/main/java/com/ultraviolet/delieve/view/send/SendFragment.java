package com.ultraviolet.delieve.view.send;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.ultraviolet.delieve.R;
import com.ultraviolet.delieve.view.base.BaseFragment;
import com.ultraviolet.delieve.view.base.BaseSupportMapFragment;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.subjects.BehaviorSubject;

import static android.app.Activity.RESULT_OK;
import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.ANCHORED;
import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED;

public class SendFragment extends BaseFragment {

    /**
     * Map
     */
    private SupportMapFragment mSupportMapFragment;
    private BehaviorSubject<GoogleMap> mapSubject;
    private GoogleMap mGoogleMap;
    LocationRequest locationRequest;
    Location lastLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    Marker currentLocationMaker;


    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;

    @BindView(R.id.send_map)
    FrameLayout mapLayout;

    @BindView(R.id.send_timepicker)
    EditText mTimePickerTextEdit;

    @BindView(R.id.send_datepicker)
    EditText mDatePickerTextEdit;

    @BindView(R.id.send_textbox_from)
    TextView mFromTextBox;

    @BindView(R.id.send_textbox_to)
    TextView mToTextBox;

    private final int PLACE_PICKER_REQUEST = 1;
    private int selectedTextbox = 0;

    @OnClick({R.id.send_textbox_to, R.id.send_textbox_from})
    public void onSendTextBoxClicked(View textbox) {
        if (slidingUpPanelLayout.getPanelState() == COLLAPSED) {
            slidingUpPanelLayout.setPanelState(ANCHORED);
            return;
        }
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent intent = builder.build(getActivity());
            switch (textbox.getId()) {
                case R.id.send_textbox_from:
                    selectedTextbox = 1;
                    break;
                case R.id.send_textbox_to:
                    selectedTextbox = 2;
                    break;

            }
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.send_timepicker)
    public void onClickTimepicker(View v){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mTimePickerTextEdit.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    @OnClick(R.id.send_datepicker)
    public void onClickDatepicker(View v){
        Calendar mcurrentDate = Calendar.getInstance();
        int date = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        int month = mcurrentDate.get(Calendar.MONTH);
        int year = mcurrentDate.get(Calendar.YEAR);
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mDatePickerTextEdit.setText(year + "/" + month + "/" +  dayOfMonth);
            }
        }, year, month,date );
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        View rootView = layoutInflater.inflate(R.layout.fragment_send, viewGroup, false);


        if (bundle == null) Log.d("credt", "bundle is null");
        if (mSupportMapFragment == null){
            initMap();
        }

        return rootView;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

    }


    @Override
    public void onPause() {
        super.onPause();
        if (mFusedLocationClient != null) {
            //mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupSlidingUpPanel();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                String text = place.getName().toString();
                if (text.startsWith(String.valueOf(place.getLatLng().latitude).substring(0,2))){
                    text = place.getAddress().toString();
                }
                if (selectedTextbox == 1){
                    mFromTextBox.setText(text);
                }
                else if (selectedTextbox == 2){
                    mToTextBox.setText(text);
                }
            }
        }
    }
    private void setGoogleMapLocationEnabled(boolean b){
        try {
            mGoogleMap.setMyLocationEnabled(b);
        }catch (SecurityException e){
            Log.d("delieve", "security exception");
        }
    }

    private void setupSlidingUpPanel(){
        if (slidingUpPanelLayout != null) {
            slidingUpPanelLayout.setPanelHeight(
                    slidingUpPanelLayout.getPanelHeight() +
                            //getActivity().findViewById(R.id.navigation).getHeight()
                            195     // height of navigation bar
            );
            slidingUpPanelLayout.setParallaxOffset(500);
            //TODO : it must be normalized
            slidingUpPanelLayout.setAnchorPoint(0.65f);
            slidingUpPanelLayout.setCoveredFadeColor(0);
            slidingUpPanelLayout.invalidate();
        }
    }

    private void initMap(){
        mSupportMapFragment = SupportMapFragment.newInstance();
        mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mGoogleMap = googleMap;
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                locationRequest = new LocationRequest();
                locationRequest.setInterval(60000);
                locationRequest.setFastestInterval(2000);
                locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        setGoogleMapLocationEnabled(true);
                    } else {
                        checkLocationPermission();
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        setGoogleMapLocationEnabled(true);
                    }
                } else {
                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    setGoogleMapLocationEnabled(true);

                }

            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.send_map, mSupportMapFragment).commit();


    }
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last lastLocation in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("locationCallback", "Location: " + location.getLatitude() + " " + location.getLongitude());
                if (lastLocation == null){
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(location.getLatitude(),location.getLongitude()),
                            15));
                }
                lastLocation = location;
                if (currentLocationMaker != null) {
                    currentLocationMaker.remove();
                }

            }
        }
    };

    final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use lastLocation functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }


}
