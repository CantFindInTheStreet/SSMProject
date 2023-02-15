package com.cantfindinthestreet.bean.commons;

import org.apache.poi.hssf.usermodel.HSSFCell;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class GongJv {
    //登录状态
    public static final String CHENGGONG="1";
    public static final String SHIBAI="0";
    //数据是否被修改过
    public static final String FALSECHANGE="0";//0没修改过
    public static final String TRUECHANGE="1";//1修改过
    //保存当前用户
    public static final String USERNAME="user";//session中保存的user
    public static String DateGeShi1(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
    public static String cellToString(HSSFCell cell){
        if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
            return cell.getStringCellValue()+"";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
            return cell.getNumericCellValue()+"";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
            return cell.getBooleanCellValue()+"";
        }else if(cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
            return cell.getCellFormula()+"";
        }else{
            return "";
        }
    }
}


