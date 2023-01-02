import android.view.ViewGroup
import com.example.musicplayerapp.MusicList


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerapp.R
import com.example.musicplayerapp.SongChangeListener
import java.util.*
import java.util.concurrent.TimeUnit

class MusicAdapter: RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    val list: List<MusicList>
    val context: Context
    val songChangeListener:SongChangeListener
    var playingPosition = 0

    constructor(list: List<MusicList>, context: Context) : super() {
        this.list = list
        this.context = context
        this.songChangeListener = context as SongChangeListener
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rootLayout: RelativeLayout
        val title: TextView
        val artist: TextView
        val musicDuration: TextView

        init {
            rootLayout = itemView.findViewById(R.id.rootLayout)
            title = itemView.findViewById(R.id.musicTitle)
            artist = itemView.findViewById(R.id.musicArtist)
            musicDuration = itemView.findViewById(R.id.musicDuration)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.music_adapter_layout, null))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var list2: MusicList = list.get(position)

        if (list2.isPlaying) {
            songChangeListener.onChange(position);
            playingPosition = position
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_blue_10);
        } else {
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_10);
        }

        var generateDuration = String.format(Locale.getDefault(), "%02d:%02d",
                                TimeUnit.MILLISECONDS.toMinutes(list2.duration.toLong()),
                                TimeUnit.MILLISECONDS.toSeconds(list2.duration.toLong())
                                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(list2.duration.toLong())))

        holder.title.setText(list2.title)
        holder.artist.setText(list2.artist)
        holder.musicDuration.setText(generateDuration)

        holder.rootLayout.setOnClickListener {
            list.get(playingPosition).isPlaying = false
            list2.isPlaying = true

            notifyDataSetChanged()

        }
    }

    override fun getItemCount(): Int {
        return list.size

    }

}