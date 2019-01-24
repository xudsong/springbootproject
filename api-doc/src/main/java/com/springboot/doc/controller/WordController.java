package com.springboot.doc.controller;

import com.springboot.doc.dto.Model;
import com.springboot.doc.dto.Table;
import com.springboot.doc.service.TableService;
import com.springboot.doc.service.impl.DocCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;

@RestController
public class WordController {
    @Autowired
    private TableService tableService;

    @Autowired
    private DocCreateService docCreateService;

    @RequestMapping("/getWord")
    public String getJson(){
        try {
            Map<String,Object> map = tableService.tableList();
            List<Table> tableList = (List)map.get("table");
            List<Model> modelList = (List)map.get("model");
            //排序
            Collections.sort(tableList, new Comparator<Table>() {
                @Override
                public int compare(Table o1, Table o2) {
                    if (o1.getTitle().compareToIgnoreCase(o2.getTitle())>0){
                        return 1;
                    }else if (o1.getTitle().compareToIgnoreCase(o2.getTitle())<0){
                        return -1;
                    }
                    return 0;
                }
            });
            Map<String,List<Table>> listMap = new HashMap<>();
            TreeSet<String> titleSet = new TreeSet<>();
            for(Table table : tableList){
                titleSet.add(table.getTitle());
            }
            List<String> titleList = new ArrayList<String>();
            titleList.addAll(titleSet);
            for(String title : titleList){
                List<Table> newTitleList = new ArrayList<>();
                for(Table table : tableList){
                    if (title.equalsIgnoreCase(table.getTitle())){
                        newTitleList.add(table);
                    }
                }
                listMap.put(title,newTitleList);
            }
            docCreateService.creatDoc(listMap,modelList);
            return "word";
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
