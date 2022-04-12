//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.recyclerview.widget.RecyclerView
//import com.example.schoolapp.R
//
//class Booksadapter(val items : List<Book>) : RecyclerView.Adapter<Booksadapter.BooksViewHolder>(),
//    Filterable {
//    var filtredBooks : List<Book> = ArrayList()
//    init {
//        filtredBooks=items
//    }
//    class BooksViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
//    {
//        var tvTitle : TextView
//        lateinit var tvAuthor : TextView
//        lateinit var tvSummary : TextView
//        lateinit var imageBook : ImageView
//        lateinit var ratingBar: RatingBar
//        init {
//            tvTitle= itemView.findViewById(R.id.Tvtitle)
//            tvAuthor=itemView.findViewById(R.id.Tvauthor)
//            tvSummary=itemView.findViewById(R.id.Tvsummary)
//            imageBook=itemView.findViewById(R.id.imagebook)
//            ratingBar=itemView.findViewById(R.id.ratingBar)
//
//
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
//        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_book,parent,false)
//        return BooksViewHolder(itemView )    }
//
//    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
//        val books = filtredBooks[position]
//        holder.tvTitle.text = books.title
//        holder.tvAuthor.text = books.author
//        holder.tvSummary.text=books.summary
//        holder.ratingBar.rating=books.rathing
//        holder.imageBook.setImageResource(books.image)
//    }
//
//    override fun getItemCount() = filtredBooks.size
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charSearch = constraint.toString()
//                if (charSearch.isEmpty()){
//                    filtredBooks=items
//                }
//                else
//                    filtredBooks = items.filter { book -> book.title.lowercase().contains(charSearch.lowercase())  }
//                val filterResults = FilterResults()
//                filterResults.values = filtredBooks
//                return filterResults
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                filtredBooks= results?.values as ArrayList<Book>
//                notifyDataSetChanged()
//            }
//
//        }
//    }
//  //  w hadi tandar fel fragment students
//  //  imagesearch.setOnClickListener {
//     //   val editsearch = edittext.text.toString()
//     //   booksAdapter.filter.filter(editsearch)
//  // }
//
//
//}