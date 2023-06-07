package com.example.smartsitter.ui.home;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsitter.R;
import com.example.smartsitter.ui.home.ButtonAdapter;
import com.example.smartsitter.ui.home.SpaceItemDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ButtonAdapter.OnButtonLongClickListener{

    private List<String> buttons;
    private RecyclerView recyclerView;
    private ButtonAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        int spaceDp = 10;
        SpaceItemDecoration itemDecoration = new SpaceItemDecoration(requireContext(), spaceDp);
        recyclerView.addItemDecoration(itemDecoration);

        restoreButtonsFromSharedPreferences(); // Restore the buttons from SharedPreferences

        adapter = new ButtonAdapter(buttons);
        recyclerView.setAdapter(adapter);
        adapter = new ButtonAdapter(buttons);
        adapter.setOnButtonLongClickListener(this); // Définir l'écouteur de longs clics sur l'adaptateur
        recyclerView.setAdapter(adapter);



        FloatingActionButton addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Give a name to your room");

                final EditText editText = new EditText(getActivity());
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(editText);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String buttonText = editText.getText().toString().trim();
                        if (!TextUtils.isEmpty(buttonText)) {
                            buttons.add(buttonText);
                            adapter.notifyItemInserted(buttons.size() - 1);
                            recyclerView.scrollToPosition(buttons.size() - 1);
                            saveButtonsToSharedPreferences(); // Save the buttons to SharedPreferences
                        }
                    }
                });

                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void saveButtonsToSharedPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("ButtonData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String jsonButtons = gson.toJson(buttons);

        editor.putString("buttons", jsonButtons);
        editor.apply();
    }

    private void restoreButtonsFromSharedPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("ButtonData", Context.MODE_PRIVATE);
        String jsonButtons = sharedPreferences.getString("buttons", null);

        if (jsonButtons != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {}.getType();
            buttons = gson.fromJson(jsonButtons, type);
        } else {
            buttons = new ArrayList<>();
        }
    }

    @Override
    public void onButtonLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Voulez-vous supprimer ce bouton ?");

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buttons.remove(position);
                adapter.notifyItemRemoved(position);
                saveButtonsToSharedPreferences(); // Enregistrer les boutons dans SharedPreferences
            }
        });

        builder.setNegativeButton("Non", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onButtonDelete(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Voulez-vous supprimer ce bouton ?");

        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buttons.remove(position);
                adapter.notifyItemRemoved(position);
                saveButtonsToSharedPreferences(); // Enregistrer les boutons dans SharedPreferences
            }
        });

        builder.setNegativeButton("Non", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
