package example.com.phonecontactsapp.feature.home.ui

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import example.com.phonecontactsapp.R
import example.com.phonecontactsapp.feature.home.domain.entity.ContactDetail
import example.com.phonecontactsapp.utill.extensions.inflate
import kotlinx.android.synthetic.main.item_contact.view.*

class HomeAdapter constructor(val contactDetails: List<ContactDetail>, val listener: (ContactDetail, ImageView) -> Unit): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val contact = contactDetails[position]

        viewHolder.itemView.txtName.text = contact.name

        Glide.with(viewHolder.itemView.context)
                .load(contact.imgUrl)
                .centerCrop()
                .into(viewHolder.itemView.imgContact)

        viewHolder.itemView.setOnClickListener {
            listener.invoke(contact, viewHolder.itemView.imgContact)
        }
    }

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_contact))

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(p0)
    override fun getItemCount() = contactDetails.count()

}
