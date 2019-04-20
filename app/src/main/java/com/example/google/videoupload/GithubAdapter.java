package com.example.google.videoupload;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class GithubAdapter extends RecyclerView.Adapter<GithubAdapter.GithubViewHolder> {

    private Context context;
    private  Item[] data;
    public GithubAdapter(Context context, Item[] data){
        this.context = context;
        this.data =data;
    }

    @NonNull
    @Override

    public GithubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_card,viewGroup,false);
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubViewHolder githubViewHolder, int i) {
        final Item item = data[data.length-i-1];
        githubViewHolder.txtView.setText(item.getItemname());

        githubViewHolder.ImgView.setImageBitmap(setImage(item.getPhoto()));
        githubViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Enjoy!", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom);
                CircleImageView iPic;
                TextView iSeller,iName,iPhone,iAddress;
                Button makeCall,makeSms;
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                iSeller = dialog.findViewById(R.id.sellersNames);
                iName = dialog.findViewById(R.id.itemsName);
                iPhone =dialog.findViewById(R.id.sellersPhone);
                iAddress = dialog.findViewById(R.id.sellersAddress);
                iPic = dialog.findViewById(R.id.profile_image_item);
                makeCall = dialog.findViewById(R.id.sendCall);
                makeSms = dialog.findViewById(R.id.sendSms);
                VideoView vidView = (VideoView)dialog.findViewById(R.id.myVideo);
                iPic.setImageBitmap(setImage(item.getPhoto()));
                iName.setText(item.getItemname());
                iPhone.setText("Phone: "+item.getPhone());
                iAddress.setText("File Name: "+item.getLink());
                iSeller.setText("Publisher: "+item.getSeller());

                String vidAddress = "http://192.168.1.33/VideoUpload/images/"+item.getLink();
                Uri vidUri = Uri.parse(vidAddress);
                vidView.setVideoURI(vidUri);
                vidView.start();
                makeSms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = item.getPhone(); // The number on which you want to send SMS
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
                    }
                });

                makeCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.getPhone()));
                        context.startActivity(intent);
                    }
                });

                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder{
        ImageView ImgView;
        TextView txtView,txtPrice;
        public GithubViewHolder(@NonNull View itemView) {
            super(itemView);
            ImgView = itemView.findViewById(R.id.img_Item);
            txtView = itemView.findViewById(R.id.item_name);

        }
    }
    public Bitmap setImage(String imageString){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        return decodedImage;
    }
}
