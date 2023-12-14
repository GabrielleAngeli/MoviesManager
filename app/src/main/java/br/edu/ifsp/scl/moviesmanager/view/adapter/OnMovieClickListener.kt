package br.edu.ifsp.scl.moviesmanager.view.adapter

interface OnMovieClickListener {
    fun onMovieClick(position: Int)
    fun onRemoveMovieMenuItemClick(position: Int)
    fun onEditMovieMenuItemClick(position: Int)
    fun onDoneCheckBoxClick(position: Int, checked: Boolean)
}