package tech.nimbbl.checkout.sdk;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaymentCancelOptionsAdapter extends RecyclerView.Adapter<PaymentCancelOptionsAdapter.ViewHolder> {

    Context context;
    String[] options;
    private List<Integer> selectCheck;

    PaymentCancelOptionsAdapter(Context ctx, String[] options){
        this.context = ctx;
        this.options = options;
        selectCheck = Arrays.asList(0,0,0,0,0);
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

        if (selectCheck.get(position) == 1) {
            holder.radioOption.setChecked(true);
            ((NimbblCheckoutMainActivity)context).updateCancelOption(option);
        } else {
            holder.radioOption.setChecked(false);
        }

        holder.radioOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int k=0; k<selectCheck.size(); k++) {
                    if(k==position) {
                        selectCheck.set(k,1);
                    } else {
                        selectCheck.set(k,0);
                    }
                }
                notifyDataSetChanged();

            }
        });
        holder.radioOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    //Do whatever you want to do with selected value

                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox radioOption;

        public ViewHolder(View itemView) {
            super(itemView);
            radioOption = (CheckBox) itemView.findViewById(R.id.radioOption);
            //radioOption.setOnClickListener(v -> ((NimbblCheckoutMainActivity)context).updateCancelOption(options[getAdapterPosition()]));

        }


    }
}
