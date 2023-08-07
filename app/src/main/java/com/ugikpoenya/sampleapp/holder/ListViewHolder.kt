package com.ugikpoenya.sampleapp.holder

import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import com.ugikpoenya.appmanager.PostManager
import com.ugikpoenya.appmanager.model.PostModel
import com.ugikpoenya.materialx.ui.design.utils.Tools
import com.ugikpoenya.sampleapp.R
import com.ugikpoenya.sampleapp.databinding.ItemListViewsBinding
import com.xwray.groupie.viewbinding.BindableItem


class ListViewHolder(var postModel: PostModel, var activity: Activity) : BindableItem<ItemListViewsBinding>() {
    override fun getLayout(): Int = R.layout.item_list_views
    override fun initializeViewBinding(view: View): ItemListViewsBinding = ItemListViewsBinding.bind(view)
    override fun bind(viewHolder: ItemListViewsBinding, position: Int) {
        viewHolder.txtTitle.text = postModel.post_title
        Tools.displayImageRound(activity, viewHolder.imageView, postModel.post_image)

        val found = PostManager().getPostById(activity, postModel.post_id)
        if (found == null) {
            viewHolder.btnFavorit.setImageResource(R.drawable.ic_favorite_border)
            viewHolder.btnFavorit.setColorFilter(ContextCompat.getColor(activity, R.color.pink_200))
        } else {
            viewHolder.btnFavorit.setImageResource(R.drawable.ic_favorites)
            viewHolder.btnFavorit.setColorFilter(ContextCompat.getColor(activity, R.color.pink_500))
        }

        viewHolder.btnFavorit.setOnClickListener {
            if (found == null) {
                PostManager().addPost(activity, postModel, 0)
            } else {
                PostManager().deletePost(activity, postModel)
            }
            notifyChanged()
        }

    }
}
