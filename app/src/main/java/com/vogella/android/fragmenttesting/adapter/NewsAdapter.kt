package com.vogella.android.fragmenttesting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.CardViewBindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.vogella.android.fragmenttesting.uttilities.DateParsingUtils
import com.vogella.android.fragmenttesting.entity.NewsItem
import com.vogella.android.fragmenttesting.R
import com.vogella.android.fragmenttesting.constants.loadCircularImage
import com.vogella.android.fragmenttesting.databinding.CardViewDesignBinding
import com.vogella.android.fragmenttesting.databinding.FragmentSecondBinding

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var mIOnItemClickListener: INewsItemClickListener? = null
    private var mNewsList: List<NewsItem>?  = null

    fun setOnItemClickListener(Listener: INewsItemClickListener?) {
        mIOnItemClickListener = Listener
    }

    fun setData(list: List<NewsItem>?) {
        mNewsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = CardViewDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val utils = DateParsingUtils()
        val itemsViewModel = mNewsList!![position]

        holder.textViewTime.text =
            itemsViewModel.publishedAt?.let { utils.parse(it, utils.getinputFormat(), utils.getOutputFormat()) }

        holder.imageView.loadCircularImage(itemsViewModel.urlToImage)
        holder.textViewDesc.text = itemsViewModel.description
        holder.textViewTitle.text = itemsViewModel.title

        holder.cardView.setOnClickListener{
            mIOnItemClickListener?.onItemClick(itemsViewModel)
        }
        holder.textViewTitle.setOnClickListener{
            mIOnItemClickListener?.onTitleClick(itemsViewModel.title)
        }


        holder.imageView.setOnClickListener{
            itemsViewModel.url?.let { it1 -> mIOnItemClickListener?.onImageClick(it1) }
        }
    }

    override fun getItemCount(): Int {
        return mNewsList?.size ?: 0
    }

    class ViewHolder(binding: CardViewDesignBinding) : RecyclerView.ViewHolder(binding.root) {

        val cardView: CardView = binding.cardLayout
        val imageView: ImageView = binding.imageview
        val textViewTitle: TextView = binding.textViewTitle
        val textViewDesc: TextView = binding.textViewDesc
        val textViewTime: TextView = binding.textViewTime

    }
}
