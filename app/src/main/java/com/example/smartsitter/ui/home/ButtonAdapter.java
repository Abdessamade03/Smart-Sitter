package com.example.smartsitter.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsitter.R;

import java.util.List;



public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {
    private List<String> buttons;
    private int selectedItemIndex = -1;

    public ButtonAdapter(List<String> buttons) {
        this.buttons = buttons;
    }
    public interface OnButtonLongClickListener {
        void onButtonLongClick(int position);
        void onButtonDelete(int position);
    }
    private OnButtonLongClickListener longClickListener;
    public void setOnButtonLongClickListener(OnButtonLongClickListener listener) {
        this.longClickListener = listener;
    }



    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_item, parent, false);
        return new ButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        String buttonText = buttons.get(position);
        holder.button.setText(buttonText);

        // Apply different background color to the selected item
        if (position == selectedItemIndex) {
            holder.button.setBackgroundResource(R.drawable.login_shape);
        } else {
            holder.button.setBackgroundResource(R.drawable.button_shape);
        }
    }

    @Override
    public int getItemCount() {
        return buttons.size();
    }

    public class ButtonViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonOfList);

            button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longClickListener != null) {
                        longClickListener.onButtonLongClick(getAdapterPosition());
                        return true;
                    }
                    return false;
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    if (clickedPosition != RecyclerView.NO_POSITION) {
                        int previousSelectedItemIndex = selectedItemIndex;
                        selectedItemIndex = clickedPosition;
                        notifyItemChanged(previousSelectedItemIndex);
                        notifyItemChanged(selectedItemIndex);
                    }
                }
            });

        }

    }


}
