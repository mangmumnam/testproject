package lomphong.androidthai.in.th.educationaccount;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{



    private Context context;
    private ArrayList <String> iconStringArrayList, nameStringArrayList, messageStringArrayList;
    private LayoutInflater layoutInflater;

    public UserAdapter(Context context,
                       ArrayList<String> iconStringArrayList,
                       ArrayList<String> nameStringArrayList,
                       ArrayList<String> messageStringArrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.iconStringArrayList = iconStringArrayList;
        this.nameStringArrayList = nameStringArrayList;
        this.messageStringArrayList = messageStringArrayList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView, messageTextView;
        private ImageView imageView;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.textViewName);
            messageTextView = itemView.findViewById(R.id.textViewMessage);
            imageView = itemView.findViewById(R.id.imageIcon);

        }
    }   //Second Class




}  //main class
