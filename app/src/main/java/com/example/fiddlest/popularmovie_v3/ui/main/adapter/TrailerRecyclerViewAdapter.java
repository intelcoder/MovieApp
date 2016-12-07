package com.example.fiddlest.popularmovie_v3.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.fiddlest.popularmovie_v3.BuildConfig;
import com.example.fiddlest.popularmovie_v3.R;
import com.example.fiddlest.popularmovie_v3.data.model.Trailer;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fiddlest on 2016-03-25.
 */
public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.TrailerViewHolder> {
    private final String TAG ="TrailerViewAdapter";
    private ArrayList<Trailer> mTrailers;
    private Context mContext;
    private Map<YouTubeThumbnailView ,YouTubeThumbnailLoader> loaders ;

    public TrailerRecyclerViewAdapter(ArrayList<Trailer> Trailers){
        this.mTrailers = Trailers;
}
    public static class TrailerViewHolder extends RecyclerView.ViewHolder  {
        @Bind(R.id.thumbnail_view_trailer)
        YouTubeThumbnailView youTubeThumbnailView;
        public TrailerViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_trailer,parent,false);
        this.mContext = parent.getContext();
        loaders =  new HashMap<>();
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, final int position) {
        holder.youTubeThumbnailView.initialize(BuildConfig.GOOGLE_DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(mTrailers.get(position).getSource());
                //save loader in order to release it to prevent memory leak
                loaders.put(youTubeThumbnailView,youTubeThumbnailLoader);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {


            }
        });
        holder.youTubeThumbnailView.setOnClickListener(v -> {
            if (YouTubeIntents.canResolvePlayVideoIntentWithOptions(mContext)) {
                mContext.startActivity(YouTubeIntents.createPlayVideoIntentWithOptions(mContext, mTrailers.get(position).getSource(), false, true));
            }else{

                Toast.makeText(mContext,"Please install youtube to watch trailer",Toast.LENGTH_SHORT);
            }
        });
    }

    public void releaseLoader(){
        for(Map.Entry<YouTubeThumbnailView ,YouTubeThumbnailLoader> youtube : loaders.entrySet()){
            youtube.getValue().release();
        }
        mTrailers.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }


}
