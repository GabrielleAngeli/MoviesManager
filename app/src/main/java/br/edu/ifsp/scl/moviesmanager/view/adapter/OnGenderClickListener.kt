package br.edu.ifsp.scl.moviesmanager.view.adapter

interface OnGenderClickListener {
    fun onGenderClick(position: Int)
    fun onRemoveGenderMenuItemClick(position: Int)
    fun onEditGenderMenuItemClick(position: Int)
}