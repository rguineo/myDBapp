 package com.example.mydbapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

 class MainActivity : AppCompatActivity() {
     lateinit var databaseHelper: DatabaseHelper
     lateinit var recyclerView: RecyclerView
     lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnAddUser = findViewById<Button>(R.id.btnAddUser)
        val btnUpdateUser = findViewById<Button>(R.id.btnUpdateUser)
        val btnDeleteUser = findViewById<Button>(R.id.btnDeleteUser)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        databaseHelper = DatabaseHelper(this)

        // Obtener la lista de usuarios de la base de datos
        val userList = databaseHelper.getAllUsers()

        // Crear el adaptador y asociarlo con el RecyclerView
        userAdapter = UserAdapter(userList)
        recyclerView.adapter = userAdapter

        // Agregar usuario
        btnAddUser.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val id = databaseHelper.addUser(name, email, password)
            tvResult.text = "Usuario registrado con ID: $id , $name, $email"
            loadUsers();
        }


        // Actualizar usuario (Ejemplo: actualiza el usuario con ID 1)
        btnUpdateUser.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val updatedRows = databaseHelper.updateUser(1, name, email, password)
            tvResult.text = "$updatedRows fila(s) actualizada(s)"
        }

        // Eliminar usuario (Ejemplo: elimina el usuario con ID 1)
        btnDeleteUser.setOnClickListener {
            val deletedRows = databaseHelper.deleteUser(1)
            tvResult.text = "$deletedRows fila(s) eliminada(s)"
        }

        loadUsers();
    }

     private fun loadUsers() {
         val userList = databaseHelper.getAllUsers()
         userAdapter = UserAdapter(userList)
         recyclerView.adapter = userAdapter
     }
}