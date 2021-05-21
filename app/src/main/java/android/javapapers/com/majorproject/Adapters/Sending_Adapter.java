package android.javapapers.com.majorproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.javapapers.com.majorproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sending_Adapter extends RecyclerView.Adapter<Sending_Adapter.ViewHolder> {

    public List<String> urls;
    public List<String> names;
    public Context m_context;
    public Map<String, Boolean> selection;

    public Sending_Adapter(List<String> urls,List<String> names, Context m_context) {
        this.urls = urls;
        this.names=names;
        this.m_context = m_context;
        selection =new HashMap<>();
        for (int i=0;i<urls.size();i++)
        {
                selection.put(urls.get(i),false);
        }
    }

    @NonNull
    @Override
    public Sending_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.images_show,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Sending_Adapter.ViewHolder holder, final int position) {
        final String url=urls.get(position);
        final String name=names.get(position);
       // Toast.makeText(m_context, name+" - "+url, Toast.LENGTH_SHORT).show();
        Glide.with(m_context)
                .asBitmap()
                .load(url)
                .into(holder.imageView);
        holder.myTextView.setText(name);


    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         TextView myTextView;
         ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextView=itemView.findViewById(R.id.Available_items);
            imageView=itemView.findViewById(R.id.Images);

        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
