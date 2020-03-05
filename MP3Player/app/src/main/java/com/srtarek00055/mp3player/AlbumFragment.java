package com.srtarek00055.mp3player;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment {

    String[] items;

    ArrayList<File> mySongs;

    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_album, container, false);
        return rootView;
//        ListView albumList = rootView.findViewById(R.id.album_list);
//        SongFragment songFragment = new SongFragment();
//        mySongs = findSongs(Environment.getExternalStorageDirectory());
//        items = new String[mySongs.size()];
//        for (int i=0;i<mySongs.size();i++){
//            toast(mySongs.get(i).getName().toString());
//            items[i] = mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav","");

        }
//        ArrayAdapter<String> adp = new ArrayAdapter<String>(getContext(),R.layout.album_list,R.id.textView,items);
//        Set List Mp3 file name
//        albumList.setAdapter(adp);
//        albumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(AlbumFragment.this.getActivity(),Player.class);
//                intent.putExtra("pos",position);
//                intent.putExtra("songlist",mySongs);
////                startActivity(new Intent(getApplicationContext(),MainActivity.class).putExtra("pos",position).putExtra("songlist",mySongs));
//                startActivity(intent);
//                toast(items[position]);
//
//            }
//        });


//    }
    public ArrayList<File> findSongs(File root){
        ArrayList<File> al = new ArrayList<File>();
        File[] files = root.listFiles();
        for (File singleFile : files){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                al.addAll(findSongs(singleFile));
            }else {
                if (singleFile.getName().endsWith(".mp3")||singleFile.getName().endsWith(".wav")){
                    al.add(singleFile);
                }
            }
        }
        return al;
    }
    public void toast(String text){
        Toast.makeText(getContext(),text,Toast.LENGTH_LONG).show();
    }
}
