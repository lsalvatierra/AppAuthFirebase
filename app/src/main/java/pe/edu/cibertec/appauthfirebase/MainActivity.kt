package pe.edu.cibertec.appauthfirebase

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


import com.google.firebase.auth.FirebaseAuth



import pe.edu.cibertec.appauthfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "¡Usuario registrado con éxito!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error en el registro: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Bienvenido: ${user?.email}", Toast.LENGTH_SHORT).show()
                    // AQUÍ: Puedes redirigir a tu Home/Dashboard usando Intent
                } else {
                    Toast.makeText(this, "Error de autenticación: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onClick(p0: View) {
        when (p0) {
            binding.btnLogin -> {
                loginUser(
                    binding.etEmail.text.toString().trim(),
                    binding.etPassword.text.toString().trim()
                )
            }
            binding.btnRegister -> {
                registerUser(
                    binding.etEmail.text.toString().trim(),
                    binding.etPassword.text.toString().trim()
                )
            }
        }
    }
}