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

public class RecyclerViewCentralGovernmentAdapter extends RecyclerView.Adapter<RecyclerViewCentralGovernmentAdapter.ViewHolder>{

    private ArrayList<String> central_government;
    private Context context;
    int aadhar=0;
    SharedPreferences preferences;

    public RecyclerViewCentralGovernmentAdapter(Context context,ArrayList<String> mImageurls) {
        this.central_government = mImageurls;
        this.context = context;
        preferences=context.getSharedPreferences("imageurls",context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.central_government,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String aadhar_url=preferences.getString("aadhar","https://uidai.gov.in/templates/tjbase/static/media/aadharlogo.png");
        central_government.set(aadhar,aadhar_url);

        Glide.with(context)
                .asBitmap()
                .load(central_government.get(position))
                .into(holder.images);

        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String type=null;
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
        return central_government.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView images;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            images=itemView.findViewById(R.id.centralgovernmentimages);
            images.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
