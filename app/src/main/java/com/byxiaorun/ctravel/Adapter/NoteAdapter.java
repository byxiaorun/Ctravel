package com.byxiaorun.ctravel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.byxiaorun.ctravel.Activity.NoteActivity;
import com.byxiaorun.ctravel.Activity.NoteDeatil;
import com.byxiaorun.ctravel.MainActivity;
import com.byxiaorun.ctravel.R;
import com.byxiaorun.ctravel.Utils.DButils;
import com.byxiaorun.ctravel.Utils.LoveDB;
import com.byxiaorun.ctravel.Utils.NoteDB;
import com.byxiaorun.ctravel.bean.NoteBean;

import java.util.List;

/**
 * Created by byxiaorun on 2020/1/6/0006.
 */
public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<NoteBean> notelist;
    private LayoutInflater inflater;
    private NoteDB notedb;

    public NoteAdapter(Context context, List<NoteBean> notelist, NoteDB db) {
        this.context = context;
        this.notelist = notelist;
        this.inflater = inflater;
        this.notedb = db;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return notelist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.note_list_item, null);
            holder.id = view.findViewById(R.id.tv_id);
            holder.ll_note = view.findViewById(R.id.ll_note);
            holder.username = view.findViewById(R.id.tv_username);
            holder.note = view.findViewById(R.id.tv_note);
            holder.time = view.findViewById(R.id.tv_time);
            holder.love = view.findViewById(R.id.love);
            holder.tv_menu = view.findViewById(R.id.tv_menu);
            holder.tv_all=view.findViewById(R.id.alltext);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        final NoteBean note = notelist.get(i);
        holder.id.setText(new String(String.valueOf(note.id)));
        holder.username.setText(note.username);
        holder.note.setText(note.note);
        holder.time.setText("时间:" + note.time);
        int id = note.id;
        String username = note.username;
        LoveDB db = LoveDB.getInstance(view.getContext());
        love(db, id, holder, view);
        //获取点赞列表数目
        holder.love.setText(String.valueOf(db.getList(id, true).size()));
        Paint paint = new Paint();

        float size=paint.measureText(holder.note.getText().toString());
        if (size>846){
            holder.tv_all.setVisibility(View.VISIBLE);
            MainActivity.mAdapter.setnote();
        }else if (size<=846){
            holder.tv_all.setVisibility(View.GONE);
            MainActivity.mAdapter.setnote();
        }
        holder.tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoteDeatil.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        holder.ll_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NoteDeatil.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
        Holder finalHolder = holder;
        holder.tv_menu.setOnClickListener(new View.OnClickListener() {
            //菜单按钮点击事件
            @Override
            public void onClick(View view1) {
                notemenu(view1,finalHolder,db,id,username,note.note);
            }
        });
        holder.ll_note.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                notemenu(view,finalHolder,db,id,username,note.note);
                return true;
            }
        });
        return view;
    }

    final class Holder {
        LinearLayout ll_note;
        TextView id, username, note, time, tv_menu,tv_all;
        Button love;
    }


    public void notemenu(View view1,Holder finalHolder,LoveDB db,int id,String username,String note){
        //菜单
        PopupMenu popupMenu = new PopupMenu(view1.getContext(), finalHolder.tv_menu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_user, popupMenu.getMenu());
        //查询是否点赞，如果查询到有，则显示取消点赞
        String lovetext = "";
        if (db.selectlove(DButils.getUsername(), id, true)) {
            lovetext = "取消点赞";
        } else {
            lovetext = "点赞";
        }
        popupMenu.getMenu().getItem(1).setTitle(lovetext);
        if (DButils.getUsername().equals(username)) {
            //查询登录账号跟发布动态的账号是否相同，如果相同显示两个隐藏菜单
            popupMenu.getMenu().getItem(2).setVisible(true);
            popupMenu.getMenu().getItem(3).setVisible(true);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_look:
                        Intent intent = new Intent(context, NoteDeatil.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_love:
                        //点赞及取消点赞操作
                        if (DButils.getIsLogin()) {
                            if (db.selectlove(DButils.getUsername(), id, true)) {
                                //如果查询到点赞
                                finalHolder.love.setSelected(true);
                                db.deletelove(DButils.getUsername(), id, true);
                                MainActivity.mAdapter.setnote();
                                Toast.makeText(view1.getContext(), "取消点赞成功", Toast.LENGTH_SHORT).show();
                            } else {
                                finalHolder.love.setSelected(false);
                                db.saveLove(DButils.getUsername(), id, true);
                                MainActivity.mAdapter.setnote();
                                Toast.makeText(view1.getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(view1.getContext(), "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.menu_edit:
                        Intent intent2 = new Intent(view1.getContext(), NoteActivity.class);
                        intent2.putExtra("option", "edit");
                        intent2.putExtra("note", String.valueOf(note));
                        intent2.putExtra("id",String.valueOf(id));
                        view1.getContext().startActivity(intent2);
                        break;
                    case R.id.menu_delete:
                        NoteDB notedb=NoteDB.getInstance(view1.getContext());
                        if(notedb.deletenote(String.valueOf(id))){
                            MainActivity.mAdapter.setnote();
                            Toast.makeText(view1.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
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


    public static void love(LoveDB db, int id, Holder holder, View view) {
        //点赞及取消点赞操作
        if (DButils.getIsLogin()) {
            if (db.selectlove(DButils.getUsername(), id, true)) {
                //如果查询到点赞
                holder.love.setSelected(true);
                holder.love.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.deletelove(DButils.getUsername(), id, true);
                        MainActivity.mAdapter.setnote();
                        love(db,id,holder,view);
                        holder.love.setText(String.valueOf(db.getList(id, true).size()));
                        Toast.makeText(view.getContext(), "取消点赞成功", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                holder.love.setSelected(false);
                holder.love.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.saveLove(DButils.getUsername(), id, true);
                        MainActivity.mAdapter.setnote();
                        love(db,id,holder,view);
                        holder.love.setText(String.valueOf(db.getList(id, true).size()));
                        Toast.makeText(view.getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        } else {
            Toast.makeText(view.getContext(), "你还未登录,请先登陆", Toast.LENGTH_SHORT).show();
        }
    }


    public void setlist(List<NoteBean> list) {
        this.notelist = list;
    }
}
