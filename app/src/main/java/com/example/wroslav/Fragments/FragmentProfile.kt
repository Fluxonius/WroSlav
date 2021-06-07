package com.example.wroslav.Fragments


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.wroslav.LoginActivity
import com.example.wroslav.R
import com.example.wroslav.SettingsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.w3c.dom.Text


/**
 * A simple [Fragment] subclass.
 */
class FragmentProfile : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore : FirebaseFirestore
    private lateinit var userId : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.i("Done", "ProfileFragment")

        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        if (auth.currentUser == null) {
            view.option4.setText(R.string.content_profil_LogIn)
            //view.option4desc.setText(R.string.content_profil_logIn_desc)
        } else {
            userId = auth.currentUser!!.uid
            val docRef = fStore.collection("users").document(userId)
            docRef.addSnapshotListener { snapshot, e ->
                view.nameText.text = snapshot!!.getString("nickname")
                view.loginText.text = snapshot.getString("email")
            }
        }

        view.logout_btn.setOnClickListener { view ->
            if (auth.currentUser == null) {
//                Toast.makeText(activity, "NO logged", Toast.LENGTH_LONG).show()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            } else {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(activity, "Successfully Logged Out", Toast.LENGTH_LONG).show()
                val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false)
                }
                ft.detach(this).attach(this).commit()

//                Toast.makeText(activity, "Already logged in", Toast.LENGTH_LONG).show()

            }
        }

        view.settings_btn.setOnClickListener { view ->
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Return the fragment view/layout
        return view
    }
}



