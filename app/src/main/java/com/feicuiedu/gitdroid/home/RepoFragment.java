package com.feicuiedu.gitdroid.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feicuiedu.gitdroid.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**  ViewPager中要切换的Fragment
 * Created by Administrator on 2016/6/30.
 */
public class RepoFragment extends Fragment {
  @Bind(R.id.lvRepos)ListView listView;
    @Bind(R.id.ptrClassicFrameLayout)PtrClassicFrameLayout ptrClassicFrameLayout;//下拉刷新
    private ArrayAdapter<String> adapter;
    private ArrayList<String>data;

    public static RepoFragment getInstance(String valus){
        RepoFragment repoFragment = new RepoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key_valus",valus);
        repoFragment.setArguments(bundle);
    return repoFragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data=new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            data.add("我是第"+i+"条数据");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                tianjia();
            }
        });
    }

    private void tianjia(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 66; i < 88; i++) {
                    data.add(0,"我有"+i+"个朋友");
                }
                ptrClassicFrameLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        ptrClassicFrameLayout.refreshComplete();
                    }
                });

            }
        }).start();




    }
}
