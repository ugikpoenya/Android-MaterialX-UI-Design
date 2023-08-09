package com.ugikpoenya.materialx.ui.design.holder

import android.view.View
import com.ugikpoenya.materialx.ui.design.R
import com.ugikpoenya.materialx.ui.design.databinding.ItemSuggestionBinding
import com.xwray.groupie.viewbinding.BindableItem


class SuggestionViewHolder(
    var title: String,
    val onDelete: (title: String) -> Unit
) : BindableItem<ItemSuggestionBinding>() {
    override fun getLayout(): Int = R.layout.item_suggestion
    override fun initializeViewBinding(view: View): ItemSuggestionBinding = ItemSuggestionBinding.bind(view)
    override fun bind(viewHolder: ItemSuggestionBinding, position: Int) {
        viewHolder.title.text = title
        viewHolder.btnDelete.setOnClickListener {
            onDelete(title)
        }
    }
}
