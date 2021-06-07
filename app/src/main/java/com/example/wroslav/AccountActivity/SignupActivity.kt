package com.example.wroslav

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wroslav.dataClasses.Events
import com.example.wroslav.dataClasses.News
import com.example.wroslav.dataClasses.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.model.Place
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var nicknameEt: EditText

    private lateinit var signUpBtn: Button
    private lateinit var loginBtn: Button
    private lateinit var  fStore : FirebaseFirestore
    private lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        emailEt = findViewById(R.id.email_edt_text)
        passwordEt = findViewById(R.id.pass_edt_text)
        nicknameEt = findViewById(R.id.nickname_edt_text)

        loginBtn = findViewById(R.id.login_btn)
        signUpBtn = findViewById(R.id.signup_btn)

        signUpBtn.setOnClickListener {
            var nickname: String = nicknameEt.text.toString()
            var email: String = emailEt.text.toString()
            var password: String = passwordEt.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(nickname)) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG)
                                .show()

                            userId = auth.currentUser!!.uid
                            val docRef = fStore.collection("users").document(userId)
                            val  user:HashMap<String, Any> = HashMap()
                            val list:List<Events> = ArrayList()
                            val list2:List<Place> = ArrayList()
                            val list3:List<News> = ArrayList()
                            user.put("nickname", nickname)
                            user.put("email", email)
                            user.put("SavedEvents", list)
                            user.put("SavedPlace", list2)
                            user.put("SavedNews", list3)

                            docRef.set(user).addOnSuccessListener { }

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }

        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

