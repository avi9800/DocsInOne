package android.javapapers.com.majorproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.javapapers.com.majorproject.ImageActivity.OnImageClickActivity;
import android.javapapers.com.majorproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewMostPopularAdapter extends RecyclerView.Adapter<RecyclerViewMostPopularAdapter.ViewHolder>{

    private ArrayList<String> mImageurls=new ArrayList<>();
    private Context context;

    public RecyclerViewMostPopularAdapter(Context context,ArrayList<String> mImageurls) {
        this.mImageurls = mImageurls;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.most_popular,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Glide.with(context)
                .asBitmap()
                .load(mImageurls.get(position))
                .into(holder.images);

        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = null;

                switch (position)
                {
                    case 0:
                        type="aadhar";
                        break;
                    case 1:
                        type="driver license";
                        break;
                    case 2:
                        type="registration certificate";
                        break;
                    case 3:
                        type="12th Certificate";
                        break;
                    case 4:
                        type="10th certificate";
                        break;
                    case 5:
                        type="voter card";
                        break;
                }

                Intent intent=new Intent(context,OnImageClickActivity.class);
                intent.putExtra("typeofdata",type);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageurls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView images;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images=itemView.findViewById(R.id.mostpopularimages);
        }
    }
}
