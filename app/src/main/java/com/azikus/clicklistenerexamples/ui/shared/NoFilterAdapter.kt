package com.azikus.clicklistenerexamples.ui.shared

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.LayoutRes
import com.azikus.clicklistenerexamples.R

class NoFilterAdapter(
    context: Context,
    val items: Array<String>,
    @LayoutRes resource: Int = R.layout.drop_down_item
) : ArrayAdapter<String>(context, resource, items) {

    override fun getFilter(): Filter = DisabledFilter()

    private inner class DisabledFilter : Filter() {
        override fun performFiltering(arg0: CharSequence?): FilterResults {
            val result = FilterResults()
            result.values = items
            result.count = items.size
            return result
        }
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            notifyDataSetChanged()
        }
    }

}
