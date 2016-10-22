package dise.xplora_starterproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by EmmanuelAbiola on 14/05/16.
 */
public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder> {

    Context mContext;

//initiate setOnClickListener for placeHolder and implement the onClick override method.
    OnItemClickListener mItemClickListener;



    // 2
    public TravelListAdapter(Context context) {
        this.mContext = context;
    }

    // 3
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        public TextView placeName;
        public ImageView placeImage;

        //initiate setOnClickListener for placeHolder and implement the onClick override method.
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }


        public ViewHolder(View itemView) {

            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (TextView) itemView.findViewById(R.id.placeName);
            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
            //hook the two up by adding the following
            placeHolder.setOnClickListener(this);

        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    // 1
    @Override
//    getItemCount() returns the number of items from your data array. In this case,
//    you’re using the size of the PlaceData.placeList().
    public int getItemCount() {
        return new PlaceData().placeList().size();
    }

    // 2
    @Override
//    onCreateViewHolder(...) returns a new instance of your ViewHolder
//    by passing an inflated view of row_places.
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_places, parent, false);
        return new ViewHolder(view);
    }

    // 3
    @Override
//    onBindViewHolder(...) binds the Place object to the UI elements in ViewHolder.
//      You’ll use Picasso to cache the images for the list.
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Place place = new PlaceData().placeList().get(position);
        holder.placeName.setText(place.name);
        Picasso.with(mContext).load(place.getImageResourceId(mContext)).into(holder.placeImage);
        //By using generateAsync(...) to generate a color palette in the background,
        // you’ll receive a callback when the palette has been generated in the form of
        // onGenerated(...). Here you can access the generated color palette and set the background
        // color of holder.placeNameHolder. If the color doesn’t exist, the method will apply a fallback color
        // — in this case, android.R.color.black.

        Bitmap photo = BitmapFactory.decodeResource(mContext.getResources(), place.getImageResourceId(mContext));
        Palette.generateAsync(photo, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                int bgColor = palette.getDarkMutedColor(mContext.getResources().getColor(android.R.color.black));
                holder.placeNameHolder.setBackgroundColor(bgColor);
            }
        });


    }
}