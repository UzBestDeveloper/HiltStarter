package com.example.hiltstarter.utils

fun interface OnClickListener<T> {
    fun onClick(item: T)
}

fun interface OnLongClickListener<T> {
    fun onLongClick(item: T): Boolean
}

fun interface OnCheckedChangedListener<T> {
    fun onCheckedChange(item: T, isChecked: Boolean)
}