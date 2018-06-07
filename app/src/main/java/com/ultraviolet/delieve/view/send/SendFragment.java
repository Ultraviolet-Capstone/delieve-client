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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import com.ultraviolet.delieve.data.dto.DeliveryRequestDto;
import com.ultraviolet.delieve.data.repository.DeliveryRepository;
import com.ultraviolet.delieve.data.repository.UserRepository;
import com.ultraviolet.delieve.view.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

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


    @Inject
    UserRepository mUserRepository;

    @Inject
    DeliveryRepository mDeliveryRepository;

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

    @BindView(R.id.phoneNumberEdit)
    TextView editNumber;

    @BindView(R.id.seekBar)
    SeekBar mSeekBar;

    @BindView(R.id.weight)
    TextView weightText;

    @BindView(R.id.nameEdit)
    TextView editName;



    private final int PLACE_PICKER_REQUEST = 1;
    private int selectedTextbox = 0;
    private final String[] stuffTypeList={"특징 없음","깨지기 쉬움", "액체"};
    private float stuffWeight=(float)0.0;
    private final String[] sizeList={"S", "M","L"};

    private String type;
    private int typeC;
    private String size;
    private double bax;
    private double bay;
    private double fax;
    private double fay;
    private String beginTime;
    private String bdate;
    private String btime;
    private int Id;

    private String finishTime;
    private String fdate;
    private String ftime;

    private String phoneNumber;
    private String beginAddress;
    private String finishAdderes;
    private String name;


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
                ftime=String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute)+":00";
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
                mDatePickerTextEdit.setText(year + "-" + (month+1) + "-" +  dayOfMonth);
                fdate=String.valueOf(year) + "-" + String.valueOf(month+1) + "-" +  String.valueOf(dayOfMonth);
            }
        }, year, month,date );
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    @OnClick(R.id.submit)
    public void onSubmitClicked(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        beginTime= sdf.format(date);
        finishTime=fdate+" "+ ftime;

        phoneNumber=editNumber.getText().toString();
        name=editName.getText().toString();


        if (isRequestValid());
        {

            //'yyyy-mm-dd hh:mm:ss'
            //  new LatLng(location.getLatitude(),location.getLongitude()),
            mDeliveryRepository.postDeliveryRequest(new DeliveryRequestDto(
                    beginAddress, bax, bay,
                    finishAdderes, fax, fay,
                    beginTime, finishTime,
                    Id, phoneNumber,
                    name, size, stuffWeight, typeC))
                    .subscribe(res -> {
                        Log.d("credt", String.valueOf(res.message()));
                        Toast.makeText(getContext(),String.valueOf(res.message()),
                                Toast.LENGTH_SHORT).show();

                        // start waiting activity
                        Intent intent = new Intent(getActivity(), SendWaitingActivity.class);
                        Log.d("credt", "" + res.body().requestId);
                        intent.putExtra("requestId", res.body().requestId);
                        startActivity(intent);
                    }, throwable -> {
                        throwable.printStackTrace();
                    });
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        View rootView = layoutInflater.inflate(R.layout.fragment_send, viewGroup, false);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_activated_1, stuffTypeList);

        Id=mUserRepository.getUserId();


        Spinner mTypeSpinner= rootView.findViewById(R.id.Typespinner);
        mTypeSpinner.setPrompt("Select the stuff type.");
        mTypeSpinner.setAdapter(adapter);
        type = mTypeSpinner.getSelectedItem().toString();

        if(type.equals("특징 없음")){
            typeC=0;
        }
        else if(type.equals("깨지기 쉬움")){
            typeC=1;
        }
        else if(type.equals("액체")){
            typeC=2;
        }

        SeekBar mSeekBar= rootView.findViewById(R.id.seekBar);
        //TextView weightText= rootView.findViewById(R.id.weight);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarProgress = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarProgress = progress;
                stuffWeight=getConvertedValue(seekBarProgress);

                stuffWeight = Math.round(stuffWeight*100f) / 100f;
                weightText.setText(Float.toString(stuffWeight));

            }
            public float getConvertedValue(int intVal){
                float floatVal = (float) 0.0;
                floatVal = .1f * intVal;
                return floatVal;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getContext(),"SeekBar Touch Started",Toast.LENGTH_SHORT).show();
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //textView.setText("Progress: " + seekBarProgress + " / " + seekBar.getMax());
                //Toast.makeText(getApplicationContext(), "SeekBar Touch Stop ", Toast.LENGTH_SHORT).show();
            }

        });

        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_activated_1, sizeList);

        Spinner mSizeSpinner= rootView.findViewById(R.id.Sizespinner);
        mSizeSpinner.setPrompt("Select the stuff type.");
        mSizeSpinner.setAdapter(adapter2);
        size = mSizeSpinner.getSelectedItem().toString();




        if (mSupportMapFragment == null){
            initMap();
        }
        return rootView;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDiComponent().inject(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());


        if(mSupportMapFragment != null)
            initMap();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupSlidingUpPanel();
        }

    public boolean isRequestValid(){
        return true;
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
                    bay=place.getLatLng().longitude;
                    bax=place.getLatLng().latitude;
                    beginAddress=text;
                }
                else if (selectedTextbox == 2){
                    mToTextBox.setText(text);
                    fay=place.getLatLng().longitude;
                    fax=place.getLatLng().latitude;
                    finishAdderes=text;
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
                            205     // height of navigation bar
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
                locationRequest.setFastestInterval(1000);
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
