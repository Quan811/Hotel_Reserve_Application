package com.example.hotelbooking.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hotelbooking.R;
import com.example.hotelbooking.viewmodel.AccommodationViewModel;
import com.example.hotelbooking.model.Accommodation;
import com.example.hotelbooking.model.Order;
import com.example.hotelbooking.model.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReserveRoomActivity extends AppCompatActivity {
    TextView roomType, numberAvailable, price,roomDes,numberOfRoom,total, datePicker,accommodationName;
    MaterialButton buttonReserve;
    Spinner spinnerDurationStay, spinnerPaymentMethod;
    ImageView roomImg, minusButton, addButton, buttonBack;
    String durationStaySelected, paymentMethodSelected;
    Room room;
    Accommodation accommodation;
    Boolean dateCheck;
    Calendar calendar;
    EditText edtName, edtPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_room);

        calendar = Calendar.getInstance();
        initView();
        room = getRoomFromIntent();
        accommodation = getAccommodation();

        setRoomData();

        buttonBackCLick();

        minusButtonClick();

        addButtonClick();

        //set 2 spinner
        setSpinnerDurationStay();
        setSpinnerPaymentMethod();

        setCurrentTime();

        onClickDatePicker();

        buttonReserveClick();

    }



    public Order getOrder(){

        //lay thong tin can thiet
        String orderID = UUID.randomUUID().toString();
        String clientName = String.valueOf(edtName.getText()).trim();
        String clientPhoneNumber = String.valueOf(edtPhoneNumber.getText()).trim();
        String total = getTotal();
        String paymentMethod = getPaymentMethod();
        String numberOfRoom = String.valueOf(getNumberOfRoom());

        //lay thoi gian order
        Date date1 = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String timeOrder = dateFormat.format(date1);
        if(accommodation == null){
            Log.d(TAG, "getOrder: acc gui di rong");
        }else {
            Log.d(TAG, "getOrder: acc gui di co du lieu");
        }
        //set du lieu cho Order
        Order order = new Order();
        order.setOrderID(orderID);
        order.setClientName(clientName);
        order.setClientPhoneNumber(clientPhoneNumber);
        order.setTimeOrder(timeOrder);
        order.setTotal(total);
        order.setRoom(room);
        order.setAccommodation(accommodation);
        order.setPaymentMethod(paymentMethod);
        order.setOrderStatus("Processing");
        order.setNumberOfRoom(numberOfRoom);

        return order;
    }
    private int getNumberOfRoom(){
        CharSequence charSequence = numberOfRoom.getText();
        int number = Integer.parseInt(charSequence.toString());
        return number;
    }

    private void minusButtonClick(){
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = getNumberOfRoom();
                if(number == 1){
                    Toast.makeText(getApplicationContext(),"Min is 1", Toast.LENGTH_SHORT).show();
                } else {
                    numberOfRoom.setText(String.valueOf(number - 1));
                    setTotalTextView();
                }
            }
        });
    }

    private void addButtonClick(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = getNumberOfRoom();
                if(number == Integer.parseInt(room.getNumberAvailable())){
                    Toast.makeText(getApplicationContext(),
                            "Max is "+room.getNumberAvailable(),
                            Toast.LENGTH_SHORT).show();
                }else {
                    numberOfRoom.setText(String.valueOf(number + 1));
                    setTotalTextView();
                }
            }
        });
    }
    private void setRoomData(){
        roomType.setText(room.getRoomType());
        roomDes.setText(room.getRoomDescription());
        accommodationName.setText(accommodation.getAccommodationName());

        String roomPrice = room.getPrice();
        StringBuilder stringBuilder = new StringBuilder(roomPrice).reverse();
        for(int i=3; i<stringBuilder.length(); i+=4){
            stringBuilder.insert(i, ".");
        }
        roomPrice = stringBuilder.reverse().toString();
        price.setText(roomPrice);
        numberAvailable.setText(room.getNumberAvailable());
        Glide.with(this).load(room.getRoomImg()).into(roomImg);
    }
    private void initView() {
        roomType = findViewById(R.id.room_type);
        numberAvailable = findViewById(R.id.number_available);
        price = findViewById(R.id.price);
        roomDes = findViewById(R.id.room_des);
        numberOfRoom = findViewById(R.id.number_of_room);
        total = findViewById(R.id.total);
        datePicker = findViewById(R.id.date_picker);
        buttonReserve = findViewById(R.id.button_reserve);
        spinnerDurationStay = findViewById(R.id.spinner_duration_stay);
        spinnerPaymentMethod = findViewById(R.id.spinner_payment_method);
        roomImg = findViewById(R.id.room_img);
        minusButton = findViewById(R.id.minus_button);
        addButton = findViewById(R.id.add_button);
        buttonBack = findViewById(R.id.button_back);
        accommodationName = findViewById(R.id.accommodation_name);
        edtName = findViewById(R.id.edt_name);
        edtPhoneNumber = findViewById(R.id.edt_phonenumber);

    }
    private Room getRoomFromIntent(){
        Room room = new Room();
        Intent intent = getIntent();
        if(intent != null){
            Serializable serializable = intent.getSerializableExtra("room_from_accommodation");
            if(serializable != null){
                room = (Room) serializable;
            }
        }
        if(room == null){
            Log.d(TAG, "getRoomFromIntent: room del co gi" );
        }else {
            Log.d(TAG, "getRoomFromIntent: room co du lieu" );
        }
        return room;
    }
    public void setCurrentTime(){
        ZonedDateTime currentTime = getCurrentTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = currentTime.format(formatter);

        datePicker.setText(formattedDateTime);
    }

    public ZonedDateTime getCurrentTime(){
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneId.of("GMT+7"));
        return currentTime;
    }


    private void setSpinnerPaymentMethod(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_payment_method, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerPaymentMethod.setAdapter(adapter);
        spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                paymentMethodSelected = spinnerPaymentMethod.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có item nào được chọn (nếu cần)
            }
        });
    }
    private void setSpinnerDurationStay(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_duration_stay, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinnerDurationStay.setAdapter(adapter);
        spinnerDurationStay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                durationStaySelected = spinnerDurationStay.getSelectedItem().toString();
                setTotalTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có item nào được chọn (nếu cần)
            }
        });
    }
    private void onClickDatePicker(){
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateTimePicker();
            }
        });
    }
    private void showUpdateTimePicker(){
        ZonedDateTime currentTime = getCurrentTime();
        int currentYear = currentTime.getYear();
        int currentMonth = currentTime.getMonthValue();
        int currentDay = currentTime.getDayOfMonth();
        Log.d(TAG, "currentTime: "+currentDay+"/"+currentMonth+"/"+currentYear);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Log.d(TAG, "choose date: "+day+"/"+month+"/"+year);
                dateCheck = checkValidDate(year, month+1, day);

                if(dateCheck){
                    ZonedDateTime chooseTime = ZonedDateTime.of(year, month+1, day,0,0,0,0,ZoneId.of("GMT+7"));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedDateTime = chooseTime.format(formatter);
                    datePicker.setText(formattedDateTime);
                }
                else {
                    datePicker.setText("Choose again");
                    FancyToast.makeText(getApplicationContext(),
                                    "Date is invalid",
                                    FancyToast.LENGTH_SHORT,
                                    FancyToast.ERROR,
                                    false)
                            .show();
                }
            }
        },currentYear, currentMonth - 1, currentDay);
        datePickerDialog.show();

    }

    private String getTotal(){
        int durationStay = Character.getNumericValue(durationStaySelected.charAt(0));
        int numberOfRoom= getNumberOfRoom();
        int price = Integer.parseInt(room.getPrice());
        int totalResult = price*numberOfRoom*durationStay;
        return String.valueOf(totalResult);
    }
    private String getPaymentMethod(){
        String paymentMethod = spinnerPaymentMethod.getSelectedItem().toString();
        return paymentMethod;
    }
    private void setTotalTextView(){
        String totalResult = getTotal();
        StringBuilder stringBuilder = new StringBuilder(totalResult).reverse();
        for(int i=3; i<stringBuilder.length(); i+=4){
            stringBuilder.insert(i, ".");
        }
        totalResult = stringBuilder.reverse().toString();
        total.setText(totalResult);
    }

    private void buttonBackCLick(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean checkValidDate(int year, int month, int day) {
        ZonedDateTime currentTime = getCurrentTime();
        int currentYear = currentTime.getYear();
        int currentMonth = currentTime.getMonthValue();
        int currentDay = currentTime.getDayOfMonth();
        if (    year > currentYear ||
                (year == currentYear && month > currentMonth) ||
                (year == currentYear && month == currentMonth && day >= currentDay)) {
            return true;
        } else {
            return false;
        }
    }

    private void buttonReserveClick(){
        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientName, phoneNumber;
                clientName = edtName.getText().toString().trim();
                phoneNumber = edtPhoneNumber.getText().toString();
                if(TextUtils.isEmpty(clientName)){
                    FancyToast.makeText(getApplicationContext(),
                                    "Please Enter Your Name",
                                    FancyToast.LENGTH_SHORT,
                                    FancyToast.WARNING,
                                    false)
                            .show();
                } else if (TextUtils.isEmpty(phoneNumber)) {
                    FancyToast.makeText(getApplicationContext(),
                                    "Please Enter Phone Number",
                                    FancyToast.LENGTH_SHORT,
                                    FancyToast.WARNING,
                                    false)
                            .show();
                }else{
                    Order order = getOrder();
                    String orderID = order.getOrderID();

                    //add order to orders
                    DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("orders");
                    orderRef.child(orderID).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "onComplete: Add order success");
                        }
                    });
                    //add order to client
                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference clientRef = FirebaseDatabase.getInstance()
                            .getReference("clients")
                            .child(userID)
                            .child("orders");
                    Map<String, Object> orderMap = new HashMap<>();
                    orderMap.put(orderID, order);
                    clientRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "cap nhat order cho client: thanh cong");
                            }
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), ClientHomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),
                                    "Reverse Successful !!!",
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
    private Accommodation getAccommodation(){
        Accommodation accommodation = AccommodationViewModel.getInstance().getDataToPass();
        if(accommodation == null){
            Log.d(TAG, "getAccommodation: du lieu nhan ve trong");
        }else {
            Log.d(TAG, "getAccommodation: co du lieu nhan ve");
        }
        return  accommodation;
    }

}