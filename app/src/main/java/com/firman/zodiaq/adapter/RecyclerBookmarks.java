package com.firman.zodiaq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firman.zodiaq.R;
import com.firman.zodiaq.database.model.RMDataLahiran;

import java.util.List;

/**
 * Created by Firman on 3/24/2017.
 */

public class RecyclerBookmarks extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RMDataLahiran> mDataLahiranList;

    private Context mContext;
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;

    private int panjangData = 0;

    private static final int TAG_TIPE_ISI_DATA = 1;

    private OnButtonDeleteClickListener mOnButtonDeleteClickListener;

    public RecyclerBookmarks(Context context, List<RMDataLahiran> dataLahiranList) {

        mContext.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);

        int kodeview = TAG_TIPE_ISI_DATA;

        return kodeview;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        view = LayoutInflater.from(mContext).inflate(R.layout.row_data_lahiran, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolderLahiran(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int kodeview = getItemViewType(position);

        switch (kodeview) {
            case TAG_TIPE_ISI_DATA:

                final RMDataLahiran rmDataLahiran = mDataLahiranList.get(position);
                String nama = rmDataLahiran.getNama();
                String lahir = rmDataLahiran.getLahir();
                String usia = rmDataLahiran.getUsia();
                String zodiak = rmDataLahiran.getZodiak();

                ViewHolderLahiran viewHolderLahiran = (ViewHolderLahiran) holder;
                viewHolderLahiran.mTextViewNama.setText(nama);
                viewHolderLahiran.mTextViewHariLahir.setText(lahir);
                viewHolderLahiran.mTextViewUsia.setText(usia);
                viewHolderLahiran.mTextViewZodiak.setText(zodiak);

                final int posisiKlik = holder.getAdapterPosition();

                viewHolderLahiran.mButtonHapus.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mOnButtonDeleteClickListener != null) {
                                    mOnButtonDeleteClickListener.onButtonDeleteClick(posisiKlik, rmDataLahiran);
                                }
                            }
                        }
                );
                break;
        }
    }

    @Override
    public int getItemCount() {
        return panjangData;
    }

    protected class ViewHolderLahiran extends RecyclerView.ViewHolder {
        public View mViewContainer;
        public TextView mTextViewNama;
        public TextView mTextViewHariLahir;
        public TextView mTextViewUsia;
        public TextView mTextViewZodiak;

        public Button mButtonHapus;

        public ViewHolderLahiran(View itemView) {
            super(itemView);

            mViewContainer = itemView;
            mTextViewNama = (TextView) itemView.findViewById(R.id.teks_nama);
            mTextViewHariLahir = (TextView) itemView.findViewById(R.id.teks_hari_lahiran);
            mTextViewUsia = (TextView) itemView.findViewById(R.id.teks_usia);
            mTextViewZodiak = (TextView) itemView.findViewById(R.id.teks_zodiak);

            mButtonHapus = (Button) itemView.findViewById(R.id.tombol_hapus);
        }
    }


    //INTERFACE JIKA TOMBOL KLIK DIPENCET
    public interface OnButtonDeleteClickListener {
        void onButtonDeleteClick(int posisi, RMDataLahiran rmDataLahiran);
    }

    //INTERFACE TAMBAH DATA
    public void tambahData(List<RMDataLahiran> list) {
        mDataLahiranList.addAll(list);
        panjangData = mDataLahiranList.size();
    }

    //INTERFACE HAPUS DATA
    public void hapusData() {
        mDataLahiranList.clear();
        panjangData = mDataLahiranList.size();
    }

    //INTERFACE SEGARKAN DATA
    public void segarkanData() {
        RecyclerBookmarks.this.notifyDataSetChanged();
    }

    public void setOnButtonDeleteClickListener(OnButtonDeleteClickListener onButtonDeleteClickListener) {
        mOnButtonDeleteClickListener = onButtonDeleteClickListener;
    }
}
