package com.sinyuk.jianyimaterial.feature.details;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sinyuk.jianyimaterial.R;
import com.sinyuk.jianyimaterial.activities.PhotoViewActivity;
import com.sinyuk.jianyimaterial.api.JianyiApi;
import com.sinyuk.jianyimaterial.common.Constants;
import com.sinyuk.jianyimaterial.entity.YihuoDetails;
import com.sinyuk.jianyimaterial.entity.YihuoProfile;
import com.sinyuk.jianyimaterial.glide.CropCircleTransformation;
import com.sinyuk.jianyimaterial.managers.SnackBarFactory;
import com.sinyuk.jianyimaterial.mvp.BaseActivity;
import com.sinyuk.jianyimaterial.ui.trans.AccordionTransformer;
import com.sinyuk.jianyimaterial.utils.FormatUtils;
import com.sinyuk.jianyimaterial.utils.FuzzyDateFormater;
import com.sinyuk.jianyimaterial.utils.StringUtils;
import com.sinyuk.jianyimaterial.utils.ToastUtils;
import com.sinyuk.jianyimaterial.widgets.CheckableImageView;
import com.sinyuk.jianyimaterial.widgets.ExpandableTextView;
import com.sinyuk.jianyimaterial.widgets.InkPageIndicator;
import com.sinyuk.jianyimaterial.widgets.MyCircleImageView;
import com.sinyuk.jianyimaterial.widgets.RatioImageView;
import com.sinyuk.jianyimaterial.widgets.RoundCornerIndicator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cimi.com.easeinterpolator.EaseSineInInterpolator;

/**
 * Created by Sinyuk on 16.3.19.
 */
public class DetailsView extends BaseActivity<DetailsPresenterImpl> implements IDetailsView {

    public static final String YihuoProfile = "YihuoProfile";
    @Bind(R.id.placeholder)
    RatioImageView placeholder;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.scrim)
    View scrim;
    @Bind(R.id.page_indicator)
    InkPageIndicator pageIndicator;
    @Bind(R.id.page_indicator_compat)
    RoundCornerIndicator roundCornerIndicator;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.anchor)
    Space anchor;
    @Bind(R.id.new_price_tv)
    TextView newPriceTv;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.expandable_text)
    TextView expandableText;
    @Bind(R.id.expand_collapse)
    ImageButton expandCollapse;
    @Bind(R.id.description_tv)
    ExpandableTextView descriptionTv;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.avatar)
    MyCircleImageView avatar;
    @Bind(R.id.user_name_tv)
    TextView userNameTv;
    @Bind(R.id.pub_date_tv)
    TextView pubDateTv;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.like_checkable_iv)
    CheckableImageView likeCheckableIv;
    @Bind(R.id.view_count_tv)
    TextView viewCountTv;
    @Bind(R.id.share_tv)
    TextView shareTv;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.comment_et)
    EditText commentEt;
    @Bind(R.id.comment_input_layout)
    TextInputLayout commentInputLayout;
    @Bind(R.id.comment_btn)
    CheckableImageView commentBtn;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    private YihuoProfile profileData;

    private List<YihuoDetails.Pics> shotsList = new ArrayList<>();
    private int oldX;
    private int newX;
    // only show the scrim animation when first enter
    private boolean isEnterActivity = true;
    private DrawableRequestBuilder<String> avatarRequest;
    private BitmapRequestBuilder<String, Bitmap> shotRequest;
    private ShotsAdapter viewPagerAdapter;

    @Override
    protected boolean isUseEventBus() {
        return false;
    }

    /**
     * Receive the yihuo profile data passed by the previous activity
     */
    @Override
    protected void beforeInflate() {
        profileData = getIntent().getExtras().getParcelable(YihuoProfile);


    }

    @Override
    protected DetailsPresenterImpl createPresenter() {
        return new DetailsPresenterImpl();
    }


    private void handleException() {

    }

    @Override
    protected boolean isNavAsBack() {
        return true;
    }

    /**
     * init some views that don't need to request data from server
     */
    @Override
    protected void onFinishInflate() {
        if (profileData == null) {
            handleException();
            return;
        }
        // load yihuo details form server
        mPresenter.loadYihuoDetails(profileData.getId());

        // glide load request
        avatarRequest = Glide.with(this).fromString()
                .dontAnimate()
                .placeholder(this.getResources().getDrawable(R.drawable.ic_avatar_placeholder))
                .error(this.getResources().getDrawable(R.drawable.ic_avatar_placeholder))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .priority(Priority.NORMAL)
                .bitmapTransform(new CropCircleTransformation(this)).thumbnail(0.2f);

        shotRequest = Glide.with(this).fromString()
                .asBitmap()
                .placeholder(this.getResources().getDrawable(R.drawable.image_placeholder_black))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.IMMEDIATE)
                .thumbnail(0.2f);

        setupUsername();
        setupAvatar();
        setupToolbarTitle();
        setupPubdate();
        setYihuoTitle();
        setupPrice();
        setupViewPager();

    }

    private void setYihuoTitle() {
        titleTv.setText(StringUtils.check(this, profileData.getName(), R.string.untable));
    }

    private void setupUsername() {
        userNameTv.setText( String.format(StringUtils.getRes(this, R.string.details_username),
                StringUtils.check(this, profileData.getUsername(), R.string.untable)));
    }

    private void setupAvatar() {
        avatarRequest.load(profileData.getHeadImg()).into(avatar);
    }

    private void setupToolbarTitle() {
        collapsingToolbarLayout.setTitle(
                StringUtils.check(this, profileData.getName(), R.string.activity_product_details));
    }

    private void setupPubdate() {
        try {
            pubDateTv.setText(
                    StringUtils.check(this, FuzzyDateFormater.getParsedDate(this, profileData.getTime()), R.string.empty));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setupPrice() {
        newPriceTv.setText(StringUtils.check(this, FormatUtils.formatPrice(profileData.getPrice()), R.string.untable));
    }


    private void setupViewPager() {
        // TODO:  use view-stub instead
        pageIndicator.setVisibility(View.GONE);

        viewPagerAdapter = new ShotsAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        viewPager.setPageTransformer(false, new AccordionTransformer());
        viewPager.setOnTouchListener((v, ev) -> {

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    oldX = (int) ev.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    newX = (int) ev.getX();
                    if (Math.abs(oldX - newX) < ViewConfiguration.get(this).getScaledTouchSlop()) {
                        oldX = 0;
                        newX = 0;
                        onShotClick(viewPager.getCurrentItem());
                        break;
                    }
            }
            return false;
        });
    }

    private void onShotClick(int shotIndex) {
        if (shotsList == null)
            return;
        Intent intent = new Intent(DetailsView.this, PhotoViewActivity.class);
        Bundle bundle = new Bundle();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < shotsList.size(); i++) {
            list.add(JianyiApi.shotUrl(shotsList.get(i).getPic()));
        }
        bundle.putStringArrayList("shot_urls", list);
        bundle.putString("product_id", profileData.getId());
        bundle.putInt("selected_page_index", shotIndex);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constants.Request_Code_Page_Index);
    }

    // update the current viewPager item when return
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.Request_Code_Page_Index:
                viewPager.setCurrentItem(data.getIntExtra("selected_page_index", 0));
                break;
        }
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_details;
    }


    @Override
    public void setupLikeButton(boolean isAdded) {
        likeCheckableIv.setChecked(isAdded);
    }

    @OnClick(R.id.like_checkable_iv)
    public void onClickLikeBtn() {

    }

    @Override
    public void showDescription(String description) {
        descriptionTv.setText(StringUtils.check(this, description, R.string.untable));
    }

    @Override
    public void showShots(List<YihuoDetails.Pics> shots) {
        shotsList = shots;
        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(viewPagerAdapter);

        // TODO: use view-stub instead
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pageIndicator.setViewPager(viewPager);
            pageIndicator.setVisibility(View.VISIBLE);
        } else {
            roundCornerIndicator.setViewPager(viewPager, shotsList.size());
            roundCornerIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showViewCount(@NonNull String count) {
        viewCountTv.setText(String.format(StringUtils.getRes(this, R.string.details_view_count), count));
    }

    @Override
    public void showComments() {

    }

    @Override
    public void hintNoComment() {

    }

    @Override
    public void sendComment(String comment) {

    }

    @Override
    public void hintAddToLikes() {

    }

    @Override
    public void hintRemoveFromLikes() {

    }

    @Override
    public void hintRequestLogin() {
        SnackBarFactory.requestLogin(this, coordinatorLayout).show();
    }


    /**
     * ViewPager Adapter
     */
    private class ShotsAdapter extends PagerAdapter {
        private Context context;


        ShotsAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            // if the shotList is empty load a placeholder
            if (shotsList.isEmpty())
                return 1;
            return shotsList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View shot = getPage(position, container);
            container.addView(shot);
            return shot;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        private View getPage(final int position, final ViewGroup container) {
            // in shot loading progress
            showLoadingProgress();

            final RatioImageView imageView = new RatioImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            if (shotsList.isEmpty()) {
                hideLoadingProgress();
                imageView.setImageResource(R.drawable.image_placeholder_black);
                return imageView;
            }

            imageView.setTag(R.id.shot_tag, shotsList.get(position).getId());

            shotRequest.load(JianyiApi.shotUrl(shotsList.get(position).getPic())).listener(new RequestListener<String, Bitmap>() {
                @Override
                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                    hideLoadingProgress();
                    ToastUtils.toastSlow(DetailsView.this, R.string.hint_failed_to_loading_shot);
                    return false;
                }

                @Override
                public boolean onResourceReady(final Bitmap bitmap, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    hideLoadingProgress();
                    if (isEnterActivity) {
                        scrim.animate().alpha(0).setDuration(100).setStartDelay(150).setInterpolator(new EaseSineInInterpolator()).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                scrim.setAlpha(0);
                                scrim.setVisibility(View.GONE);
                            }
                        }).start();
                        isEnterActivity = false;
                    }
                    return false;
                }
            }).into(imageView);

            return imageView;
        }
    }

    public void showLoadingProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoadingProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
