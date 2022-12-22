package com.zerobase.lms.admin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberParam {

    long pageIndex;
    long pageSize;

    String searchType;
    String searchValue;

    String userId;

    public long getStartPage() {

        init();

        return (pageIndex - 1) * pageSize;
    }

    public long getEndPage() {

        init();

        return pageSize;
    }

    public void init() {

        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageSize < 10) {
            pageSize = 10;
        }
    }

    public String getQueryString() {

        StringBuilder sb = new StringBuilder();
        if (searchType != null && searchType.length() > 0) {
            sb.append(String.format("searchType=%s", searchType));
        }

        if (searchValue != null && searchValue.length() > 0) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("searchValue=%s", searchValue));
        }

        return sb.toString();
    }
}
