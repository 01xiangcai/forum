package com.yao.forum.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    private Integer page;
    //一次展示的页数
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;


    public void setPagination(Integer totalCount, Integer page, Integer size) {



        //总页数展示，例如，总数据totalCount为12，size为5，12取模（%）5为2，不为0，需要三页来展示，为12/5再加上1，若是10%5则为0，两页。
        if (totalCount % size !=0){
            totalPage = totalCount/size+1;
        }else {
            totalPage = totalCount/size;
        }

        //判断传进来页码是否超过实际页码,页码越界的问题
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        //不加this这句传不进参数
        this.page=page;

        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }

        //是否展示前一页
        if (page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }
        //是否展示后一页
        if (page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }
        //是否展示第一页
        if (pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }

    }

    @Override
    public String toString() {
        return "PaginationDTO{" +
                "questions=" + questions +
                ", showPrevious=" + showPrevious +
                ", showNext=" + showNext +
                ", showFirstPage=" + showFirstPage +
                ", showEndPage=" + showEndPage +
                ", page=" + page +
                ", pages=" + pages +
                '}';
    }

}
