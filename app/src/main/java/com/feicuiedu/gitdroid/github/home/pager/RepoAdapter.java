package com.feicuiedu.gitdroid.github.home.pager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.github.home.pager.model.Repo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/7.
 */
public class RepoAdapter extends BaseAdapter {

    private final List<Repo> data;
    public RepoAdapter() {
        data=new ArrayList<Repo>();
    }
   public void addAll(Collection<Repo>  repos){
       data.addAll(repos);
       notifyDataSetChanged();
   }
    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Repo getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.layout_item_repo,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder)convertView.getTag();
        Repo repo = getItem(position);
        viewHolder.tvRepoName.setText(repo.getFullName());
        viewHolder.tvRepoInfo.setText(repo.getDescription());
        viewHolder.tvRepoStars.setText(repo.getStargazersCount()+"");
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(), viewHolder.ivIcon);//设置图标
        return convertView;
    }
   static class ViewHolder{
        @Bind(R.id.ivIcon) ImageView ivIcon; //图标
        @Bind(R.id.tvRepoName) TextView tvRepoName;//名字
        @Bind(R.id.tvRepoInfo) TextView tvRepoInfo;//描述
        @Bind(R.id.tvRepoStars) TextView tvRepoStars;//关注的数量

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
