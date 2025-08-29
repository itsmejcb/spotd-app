package com.reyson.spotd.Class.Components.Adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reyson.spotd.Class.Activity.Profile.Profile;
import com.reyson.spotd.Data.Model.Notify;
import com.reyson.spotd.Data.Model.PhotosData;
import com.reyson.spotd.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class NotifyChildAdapter extends RecyclerView.Adapter<NotifyChildAdapter.ViewHolder> {

    private ArrayList<Notify> notifications;
    private LayoutInflater inflater;
    private Context context;
    public final String CONTEXT_LIKE = "liked your photo";
    public final String CONTEXT_LIKES = "liked your photos";
    public final String CONTEXT_MENTION = "mentioned you in a comment.";
    public final String CONTEXT_REPLIED_HER = "replied to your comment on her posts";
    public final String CONTEXT_REPLIED_HIS = "replied to your comment on his posts";
    public final String CONTEXT_SHARED_POST = "shared";
    public final String CONTEXT_REPOST = "repost";
    public final String CONTEXT_FOLLOW = "started following you.";
    public final String CONTEXT_FOLLOW_BACK = "followed you back.";
    private PhotosData photosData;
    private String[] uid = {""};
    private Calendar cal = Calendar.getInstance();
    private String ms = "";
    private boolean[] isFollow = {false};
    int[] num = {0};
    public NotifyChildAdapter(Context context, ArrayList<Notify> notifications) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.fragment_child_notify, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Notify notification = notifications.get(position);
        Log.i("username", notification.getUsername());
        Log.i("fullname", notification.getFull_name());
        Log.i("context", notification.getContext());
        holder.intentProfile(notification.getUid());

        //
        if (notification.getContext() != null) {
            int data = (int) holder.toNumber(notification.getContext());
            holder.snrcnl_context.setText(boldText(notification.getUsername(), data, ""));
        } else {
            // Handle the case when notification.getContext() is null
            holder.snrcnl_context.setText("");
        }

        holder.loadTime(notification.getMs());
        if (notification.getFollower() != null && notification.getFollowing() != null) {
            if (notification.getFollower().equals(0)) {
                holder.snrcnl_follow.setText("Follow Back");
                num[0] = 2;
            } else {
                holder.snrcnl_follow.setText("Unfollow");
            }
            holder.snrcnl_follow.setVisibility(View.VISIBLE);
            holder.snrcnl_follow.setOnClickListener(v -> {
                // try {
                //     ms = String.valueOf((long) (cal.getTimeInMillis()));
                //     if (isFollow[0]) {
                //         String ms = String.valueOf(cal.getTimeInMillis());
                //         String data = "uid=" + URLEncoder.encode(loadString(context, KEYs.UID, ""), "UTF-8") +
                //                 "&user=" + URLEncoder.encode(notification.getUid(), "UTF-8");
                //         new AXON(context, URLs.URL_USER_UNFOLLOW, data) {
                //             @Override
                //             protected void onPostExecute(String result) {
                //                 // Handle the result if needed
                //                 Log.i("result", result);
                //
                //             }
                //         }.execute();
                //         holder.snrcnl_follow.setText("Follow");
                //         isFollow[0] = false;
                //     } else {
                //         // String ms = String.valueOf(cal.getTimeInMillis());
                //         String data = "uid=" + URLEncoder.encode(loadString(context, KEYs.UID, ""), "UTF-8") +
                //                 "&user=" + URLEncoder.encode(notification.getUid(), "UTF-8") +
                //                 "&ms=" + URLEncoder.encode(ms, "UTF-8") +
                //                 "&stats=" + URLEncoder.encode(String.valueOf(num[0]), "UTF-8");
                //         new AXON(context, URLs.URL_USER_FOLLOW, data) {
                //             @Override
                //             protected void onPostExecute(String result) {
                //                 // Handle the result if needed
                //                 Log.i("result", result);
                //
                //             }
                //         }.execute();
                //         holder.snrcnl_follow.setText("Unfollow");
                //         isFollow[0] = true;
                //     }
                //
                // } catch (UnsupportedEncodingException e) {
                //     throw new RuntimeException(e);
                // }
            });
        } else {
            // Hide the follow holder if follower is null
            holder.snrcnl_follow.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView snrcnl_context;
        TextView snrcnl_time;
        TextView snrcnl_follow;
        ImageView snrcnl_profile;
        LinearLayout snrcnl_layout_1;

        public ViewHolder(@NonNull View v) {
            super(v);
            snrcnl_profile = v.findViewById(R.id.snrcnl_profile);
            snrcnl_context = v.findViewById(R.id.snrcnl_context);
            snrcnl_time = v.findViewById(R.id.snrcnl_time);
            snrcnl_follow = v.findViewById(R.id.snrcnl_follow);
            snrcnl_layout_1 = v.findViewById(R.id.snrcnl_layout_1);
        }

        void loadTime(String str) {
            if (str != null) {
                currentTime(Double.parseDouble(str), snrcnl_time);
            } else {
                snrcnl_time.setText(null);
            }
        }

        void currentTime(final double position, final TextView textview) {
            Calendar cal = Calendar.getInstance();
            double postTime = cal.getTimeInMillis() - position;
            long secondsAgo = (long) (postTime / 1000);

            if (secondsAgo < 3600) {
                long minutesAgo = secondsAgo / 60;
                textview.setText(minutesAgo + "m");
            } else if (secondsAgo < 86400) {
                long hoursAgo = secondsAgo / 3600;
                textview.setText(hoursAgo + "h");
            } else if (secondsAgo < 604800) {
                long daysAgo = secondsAgo / 86400;
                textview.setText(daysAgo + "d");
            } else if (secondsAgo < 31536000) {
                long weeksAgo = secondsAgo / 604800;
                textview.setText(weeksAgo + "w");
            } else if (secondsAgo < 3153600000L) {
                long yearsAgo = secondsAgo / 31536000;
                textview.setText(yearsAgo + "y");
            } else {
                long decadesAgo = secondsAgo / 3153600000L;
                textview.setText(decadesAgo + "dec");
            }
        }

        double toNumber(String string) {
            return Double.parseDouble(string);
        }

        void intentProfile(String key) {
            uid[0] = key;
            snrcnl_profile.setOnClickListener(v -> {
                // saveString(context, USER_UID, uid[0]);
                // intent(Profile.class, R.anim.right_in, R.anim.left_out);
            });
        }

        void intent(Class<?> targetActivityClass, int enterAnimationResId, int exitAnimationResId) {
            Intent i = new Intent(context, targetActivityClass);
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(context, enterAnimationResId, exitAnimationResId);
            context.startActivity(i, options.toBundle());
        }
    }

    public SpannableStringBuilder boldText(String username, int num, String array) {
        String sentence = null;

        switch (num) {
            case 1:
                sentence = username + " " + CONTEXT_FOLLOW;
                break;
            case 2:
                sentence = username + " " + CONTEXT_FOLLOW_BACK + " ";
                break;
            case 3:
                sentence = username + " " + CONTEXT_LIKES;
                break;
            case 4:
                sentence = username + " SDSAD";
                break;
            case 5:
                sentence = username + " shared " + array + "'s post.";
                break;
        }

        String[] boldStrings = {username, array};
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(sentence);

        for (String boldString : boldStrings) {
            if (!boldString.isEmpty()) {
                int startIndex = sentence.indexOf(boldString);
                while (startIndex >= 0) {
                    int endIndex = startIndex + boldString.length();
                    spannableStringBuilder.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    startIndex = sentence.indexOf(boldString, endIndex);
                }
            }
        }

        return spannableStringBuilder;
    }

}