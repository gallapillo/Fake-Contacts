package com.gallapillo.fakecontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gallapillo.fakecontacts.databinding.ActivityMainBinding
import com.gallapillo.fakecontacts.model.User
import com.gallapillo.fakecontacts.model.UserActionListener
import com.gallapillo.fakecontacts.model.UserService
import com.gallapillo.fakecontacts.model.UsersListener

/* Главный активити */
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAdapter: UsersAdapter

    private val usersService: UserService
        get() = (applicationContext as App).userService

    /* Метод при создании активити */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mAdapter = UsersAdapter(object : UserActionListener {
            override fun onUserMove(user: User, moveBy: Int) {
                usersService.moveUser(user, moveBy)
            }

            override fun onUserDelete(user: User) {
                usersService.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                Toast.makeText(this@MainActivity, "User name: ${user.name}", Toast.LENGTH_SHORT).show()
            }

        })

        val layoutManager = LinearLayoutManager(this)
        mBinding.rvContacts.layoutManager = layoutManager
        mBinding.rvContacts.adapter = mAdapter

        usersService.addListener(usersListener)
    }

    /* Метод при уничтожение активити */
    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener(usersListener)
    }

    /* Запись элементов из слушателя в адаптер */
    private val usersListener: UsersListener = {
        mAdapter.users = it
    }
}