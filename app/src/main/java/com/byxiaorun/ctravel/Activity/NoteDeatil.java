package com.byxiaorun.ctravel.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.byxiaorun.ctravel.Adapter.NoteAdapter;
import com.byxiaorun.ctravel.MainActivity;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;
import com.byxiaorun.ctravel.Utils.LoveDB;
import com.byxiaorun.ctravel.Utils.NoteDB;
import com.byxiaorun.ctravel.bean.NoteBean;

public class NoteDeatil extends AppCompatActivity implements View.OnClickListener {
    private TextView username,tv_time,tv_note,tv_username,title,tv_menu;
    LinearLayout main_title,content;
    Button love;
    int id;
    NoteBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_deatil);
        init();
    }

    private void init() {
        username=findViewById(R.id.tv_username);
        tv_username=findViewById(R.id.username);
        tv_time=findViewById(R.id.tv_time);
        tv_note=findViewById(R.id.tv_note);
        content=findViewById(R.id.content);
        main_title=findViewById(R.id.main_title);
        tv_menu=findViewById(R.id.tv_menu);
        love=findViewById(R.id.love);
        main_title.setVisibility(View.VISIBLE);
        tv_username.setVisibility(View.GONE);
        title=findViewById(R.id.title);
        title.setTextSize(30);
        title.setText("游记详情");
        Intent intent = getIntent();
        id=intent.getIntExtra("id",-1);
        NoteDB db=NoteDB.getInstance(this);
        bean=db.select(id);
        username.setText(String.valueOf(bean.username));
        tv_time.setText("时间:"+String.valueOf(bean.time));
        tv_note.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_note.setText(String.valueOf(bean.note));
        tv_menu.setOnClickListener(this);
        LoveDB lovedb = LoveDB.getInstance(this);
        love.setText(String.valueOf(lovedb.getList(id, true).size()));
        love(lovedb,id);
    }

    @Override
    public void onClick(View view) {
        LoveDB db = LoveDB.getInstance(view.getContext());
        //菜单
        PopupMenu popupMenu = new PopupMenu(view.getContext(), tv_menu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_user, popupMenu.getMenu());
        //查询是否点赞，如果查询到有，则显示取消点赞
        String lovetext = "";
        if (db.selectlove(DButils.getUsername(), id, true)) {
            love.setSelected(true);
            lovetext = "取消点赞";
        } else {
            love.setSelected(false);
            lovetext = "点赞";
        }
        popupMenu.getMenu().getItem(0).setVisible(false);
        popupMenu.getMenu().getItem(1).setTitle(lovetext);
        if (DButils.getUsername().equals(username.getText().toString().trim())) {
            //查询登录账号跟发布动态的账号是否相同，如果相同显示两个隐藏菜单
            popupMenu.getMenu().getItem(2).setVisible(true);
            popupMenu.getMenu().getItem(3).setVisible(true);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_look:
                        Intent intent = new Intent(view.getContext(), NoteDeatil.class);
                        intent.putExtra("id", id);
                        view.getContext().startActivity(intent);
                        break;
                    case R.id.menu_love:
                        //点赞及取消点赞操作
                        if (DButils.getIsLogin()) {
                            if (db.selectlove(DButils.getUsername(), id, true)) {
                                //如果查询到点赞
                                db.deletelove(DButils.getUsername(), id, true);
                                MainActivity.mAdapter.setnote();
                                love(db,id);
                                love.setText(String.valueOf(db.getList(id, true).size()));
                                Toast.makeText(view.getContext(), "取消点赞成功", Toast.LENGTH_SHORT).show();
                            } else {
                                db.saveLove(DButils.getUsername(), id, true);
                                MainActivity.mAdapter.setnote();
                                love(db,id);
                                love.setText(String.valueOf(db.getList(id, true).size()));
                                Toast.makeText(view.getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(view.getContext(), "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.menu_edit:
                        Intent intent2 = new Intent(view.getContext(), NoteActivity.class);
                        intent2.putExtra("option", "edit");
                        intent2.putExtra("note", String.valueOf(bean.note));
                        intent2.putExtra("id",String.valueOf(id));
                        view.getContext().startActivity(intent2);
                        break;
                    case R.id.menu_delete:
                        NoteDB notedb=NoteDB.getInstance(view.getContext());
                        if(notedb.deletenote(String.valueOf(id))){
                            MainActivity.mAdapter.setnote();
                            Intent intent1 = new Intent(view.getContext(), MainActivity.class);
                            view.getContext().startActivity(intent1);
                            Toast.makeText(view.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void love(LoveDB db, int id) {
        //点赞及取消点赞操作
        if (DButils.getIsLogin()) {
            if (db.selectlove(DButils.getUsername(), id, true)) {
                //如果查询到点赞
                love.setSelected(true);
                love.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.deletelove(DButils.getUsername(), id, true);
                        MainActivity.mAdapter.setnote();
                        love.setText(String.valueOf(db.getList(id, true).size()));
                        love(db,id);
                        Toast.makeText(view.getContext(), "取消点赞成功", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                love.setSelected(false);
                love.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.saveLove(DButils.getUsername(), id, true);
                        MainActivity.mAdapter.setnote();
                        love.setText(String.valueOf(db.getList(id, true).size()));
                        love(db,id);
                        Toast.makeText(view.getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        } else {
            Toast.makeText(NoteDeatil.this, "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
        }
    }

}
