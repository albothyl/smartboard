package com.anyang.study.cgr.repo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="RTETNCTGRY")
public class CategoryVO {
    /** 카테고리ID	 */
    private String ctgryId;

    /** 카테고리명	 */
    //hibernate validation 적용
    private String ctgryNm;

    /** 카테고리 설명	 */
    private String dc;

    public String getCtgryId() {
        return ctgryId;
    }

    public void setCtgryId(String ctgryId) {
        this.ctgryId = ctgryId;
    }

    public String getCtgryNm() {
        return ctgryNm;
    }

    public void setCtgryNm(String ctgryNm) {
        this.ctgryNm = ctgryNm;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }
}
