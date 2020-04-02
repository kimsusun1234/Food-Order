package com.pro1121.foodorder.dao;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pro1121.foodorder.LibraryClass;
import com.pro1121.foodorder.model.UserModel;

import static com.pro1121.foodorder.LibraryClass.userModelList;

public class UserDao {
    private DatabaseReference db;
    private Context context;

    public UserDao(Context context) {
        this.db = FirebaseDatabase.getInstance().getReference();
        this.context = context;
    }

    public void insert(String id, String name, String birthday, String email, String password, String role, String image)
    {
        UserModel userModel = new UserModel(id, name, birthday, email, password, role, image);
        db.child("user").child(id).setValue(userModel);
    }

    public void getAllRuntime()
    {
        
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModelList.clear();
                //mỗi child trong dataSnapshot
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    //tạo đối tượng User và thêm vào List
                    userModelList.add(data.getValue(UserModel.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //hàm này chỉ được gọi một lần duy nhất vào lúc app chạy
        db.child("user").addListenerForSingleValueEvent(valueEventListener);

    }
}
