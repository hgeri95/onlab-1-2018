package bme.cateringmobilapp.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bme.cateringmobilapp.model.CateringUnit;

public class CateringAdapter extends RecyclerView.Adapter<CateringAdapter.ViewHolder> {

    private Context context;
    private List<CateringUnit> cateringUnits;

    public CateringAdapter(Context context, List<CateringUnit> cateringUnits) {
        this.context = context;
        this.cateringUnits = cateringUnits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
