package tech.nimbbl.checkout.sdk;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentCancelOptionsAdapter extends RecyclerView.Adapter<PaymentCancelOptionsAdapter.ViewHolder> {

    Context context;
    String[] options;

    PaymentCancelOptionsAdapter(Context ctx, String[] options){
        this.context = ctx;
        this.options = options;
    }

    @Override
    public int getItemCount() {
        return options.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String option = options[position];
        holder.radioOption.setText(option);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public RadioButton radioOption;

        public ViewHolder(View itemView) {
            super(itemView);
            radioOption = (RadioButton)itemView.findViewById(R.id.radioOption);
            radioOption.setOnClickListener(v -> ((NimbblCheckoutMainActivity)context).updateCancelOption(options[getAdapterPosition()]));

        }


    }
}
