package com.sinyuk.jianyimaterial.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sinyuk.jianyimaterial.R;
import com.sinyuk.jianyimaterial.api.JianyiApi;
import com.sinyuk.jianyimaterial.entity.YihuoProfile;
import com.sinyuk.jianyimaterial.feature.details.DetailsView;
import com.sinyuk.jianyimaterial.utils.FormatUtils;
import com.sinyuk.jianyimaterial.utils.FuzzyDateFormater;
import com.sinyuk.jianyimaterial.utils.StringUtils;
import com.sinyuk.jianyimaterial.utils.ToastUtils;
import com.sinyuk.jianyimaterial.widgets.LabelView;
import com.sinyuk.jianyimaterial.widgets.RatioImageView;



import java.text.ParseException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sinyuk on 16.4.19.
 */
public class LikeGoodsListAdapter extends ExtendedRecyclerViewAdapter<YihuoProfile, LikeGoodsListAdapter.LikeItemViewHolder> {

    private BitmapRequestBuilder<String, Bitmap> shotRequest;

    public LikeGoodsListAdapter(Context context) {
        super(context);

        shotRequest = Glide.with(mContext).fromString()
                .asBitmap()
                .error(mContext.getResources().getDrawable(R.drawable.image_placeholder_icon))
                .placeholder(mContext.getResources().getDrawable(R.drawable.image_placeholder_grey300))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.IMMEDIATE)
                .thumbnail(0.2f);
    }

    @Override
    public void footerOnVisibleItem() {

    }

    @Override
    public LikeItemViewHolder onCreateDataItemViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_goods_like, parent, false);
        return new LikeItemViewHolder(v);
    }

    @Override
    public void onBindDataItemViewHolder(LikeItemViewHolder holder, int position) {
        YihuoProfile itemData = null;
        if (!getData().isEmpty() && getData().get(position) != null) {
            itemData = getData().get(position);
        }
        if (itemData == null) { return; }

        final YihuoProfile finalItemData = itemData;
        holder.mCardView.setOnClickListener(v -> {
            holder.mCardView.setClickable(false); // prevent fast double tap
            Intent intent = new Intent(mContext, DetailsView.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(YihuoProfile.TAG, finalItemData);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
            holder.mCardView.postDelayed(() -> holder.mCardView.setClickable(true), 300);
        });

        holder.mTitleTv.setText(StringUtils.check(mContext, itemData.getName(), R.string.unknown_title));
        holder.mNewPriceLabelView.setText(FormatUtils.formatPrice(itemData.getPrice()));
        try {
            holder.mPubDateTv.setText(String.format(mContext.getString(R.string.common_prefix_from),
                    FuzzyDateFormater.getParsedDate(mContext, itemData.getTime())));

        } catch (ParseException e) {
            holder.mPubDateTv.setText(mContext.getString(R.string.unknown_date));
            e.printStackTrace();
        }
//        holder.locationTv.setText(StringUtils.check(mContext, itemData.getSchoolname(), R.string.unknown_location));


        shotRequest.load(JianyiApi.shotUrl(itemData.getPic())).into(holder.mShotIv);
        holder.mShotIv.setTag(R.id.shots_cover_tag, position);
    }

    public class LikeItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.shot_iv)
        RatioImageView mShotIv;
        @Bind(R.id.new_price_label_view)
        LabelView mNewPriceLabelView;
        @Bind(R.id.title_tv)
        TextView mTitleTv;
        @Bind(R.id.like_btn)
        ImageView mLikeBtn;
        @Bind(R.id.pub_date_tv)
        TextView mPubDateTv;
        @Bind(R.id.card_view)
        CardView mCardView;

        public LikeItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}