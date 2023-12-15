package br.edu.ifsp.scl.moviesmanager.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.edu.ifsp.scl.moviesmanager.model.entity.Gender
import br.edu.ifsp.scl.sdm.moviesmanager.R

class GenderAdapter(
    private val context: Context,
    private var genders: List<Gender>,
    private val onGenderClickListener: OnGenderClickListener
) : BaseAdapter() {

    override fun getCount(): Int = genders.size

    override fun getItem(position: Int): Any = genders[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val gender = getItem(position) as Gender

        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item_layout, parent, false)

        val genderNameTextView: TextView = view.findViewById(R.id.genderNameTextView)
        val editButton: Button = view.findViewById(R.id.editButton)
        val deleteButton: Button = view.findViewById(R.id.deleteButton)

        genderNameTextView.text = gender.name
        // Adicione a lógica de seleção aqui
        view.setOnClickListener {
            // Notifique o ouvinte externo sobre a seleção do gênero
            onGenderClickListener.onGenderClick(position)
        }

        return view
    }
}