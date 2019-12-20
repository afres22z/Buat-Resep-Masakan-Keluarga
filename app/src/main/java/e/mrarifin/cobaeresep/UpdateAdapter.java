package e.mrarifin.cobaeresep;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import e.mrarifin.cobaeresep.R;

/**
 * Created by Quoc Nguyen on 13-Dec-16.
 */

public class UpdateAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Food> foodsList2;

    public UpdateAdapter(Context context, int layout, ArrayList<Food> foodsList2) {
        this.context = context;
        this.layout = layout;
        this.foodsList2 = foodsList2;
    }

    @Override
    public int getCount() {
        return foodsList2.size();
    }

    @Override
    public Object getItem(int position) {
        return foodsList2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView edtName, edtPrice, edtcara;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.edtName = (TextView) row.findViewById(R.id.edtName);
            holder.edtPrice = (TextView) row.findViewById(R.id.edtPrice);
            holder.edtcara = (TextView) row.findViewById(R.id.edtcara);
            holder.imageView = (ImageView) row.findViewById(R.id.imageViewFood);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Food food = foodsList2.get(position);

        holder.edtName.setText(food.getName());
        holder.edtPrice.setText(food.getPrice());
        holder.edtcara.setText(food.getcara());


        byte[] foodImage = food.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
