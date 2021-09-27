package com.gallapillo.fakecontacts

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gallapillo.fakecontacts.databinding.ContactItemBinding
import com.gallapillo.fakecontacts.model.User
import com.gallapillo.fakecontacts.model.UserActionListener

/* Адаптер для RecyclerView */
class UsersAdapter(
    private val actionListener: UserActionListener
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), View.OnClickListener {

    /* Список с сетторм который умедовляет слушатель о измение */
    var users: List<User> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    /* Для слушание всех действий на View */
    override fun onClick(v: View) {
        val user = v.tag as User
        when (v.id) {
            R.id.iv_more_button -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onUserDetails(user)
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context
        val user = view.tag as User
        val position = users.indexOfFirst { it.id == user.id }

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).apply {
            isEnabled = position > 0
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down)).apply {
            isEnabled = position < users.size - 1
        }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))


        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> {
                    actionListener.onUserMove(user, -1)
                }
                ID_MOVE_DOWN -> {
                    actionListener.onUserMove(user, 1)
                }
                ID_REMOVE -> {
                    actionListener.onUserDelete(user)
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    /* View Holder для адаптер надува макета */
    class UsersViewHolder(
        val binding: ContactItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    /* Метод при создании ViewHolder */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.ivMoreButton.setOnClickListener(this)

        return UsersViewHolder(binding)
    }

    /* Вытягивание елемента View Holder */
    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding) {
            holder.itemView.tag = user
            ivMoreButton.tag = user

            tvUserName.text = user.name
            tvUserCompany.text = user.company
            if (user.photo.isNotBlank()) {
                Glide.with(ivPhoto.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_avatar)
                    .error(R.drawable.ic_user_avatar)
                    .into(ivPhoto)
            } else {
                ivPhoto.setImageResource(R.drawable.ic_user_avatar)
            }
        }
    }

    /* Получение количество элементов RecyclerView */
    override fun getItemCount(): Int = users.size

    /* Константы для перемещение элементов */
    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}