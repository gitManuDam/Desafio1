package com.example.desafio_app

import Api.UsuarioViewModel
import Modelo.UsuarioLogIn
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.desafio_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var usuarioViewModel: UsuarioViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        usuarioViewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)

        usuarioViewModel.myResponse.observe(this, Observer { user ->
            user?.let {
                if(user.rolId ==0){
                    val intent = Intent(this, ActivityVader::class.java)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(this, ActivityPiloto::class.java)
                    startActivity(intent)
                }
                usuarioViewModel.limpiarRespuesta()

            }
        })

        usuarioViewModel.errorCode.observe(this, Observer { code ->
            if (code != null) {
                when (code) {
                    200 -> Toast.makeText(this, "Sesion Iniciada", Toast.LENGTH_SHORT).show()
                    400 -> Toast.makeText(this,"Error 400: Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    404 -> Toast.makeText(this, "Error 404: El usuario no existe", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(this, "Error Desconocido", Toast.LENGTH_SHORT).show()
                }
                usuarioViewModel.limpiarError()
            }
        })



        binding.btnLogin.setOnClickListener {
            if (binding.etUsuario.text.toString().isEmpty() || binding.etpContrasenia.text.toString().isEmpty()) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                usuarioViewModel.loginVM(UsuarioLogIn(binding.etUsuario.text.toString(), binding.etpContrasenia.text.toString()))
            }
        }




    }
}