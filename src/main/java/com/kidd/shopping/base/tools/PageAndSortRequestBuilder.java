package com.kidd.shopping.base.tools;

import com.kidd.shopping.base.entity.SortCriterial;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageAndSortRequestBuilder {

//    public static Pageable createPageRequest(Integer pageIndex,
//                                             Integer pageSize,
//                                             String sortBy,
//                                             String sortType,
//                                             long limitPageSize) {
//        List<SortCriterial> sortCriterials = new ArrayList<>(1);
//        sortCriterials.add(new SortCriterial(sortBy, sortType));
//        return createPageRequest(pageIndex, pageSize, sortCriterials, limitPageSize);
//    }

    public static Pageable createPageRequest(Integer pageIndex,
                                             Integer pageSize,
                                             String sortBy,
                                             String sortType,
                                             long limitPageSize) {
        Sort.Direction sortDirection;
        switch (sortType.toLowerCase()) {
            case "desc": {
                sortDirection = Sort.Direction.DESC;
            }
            break;

            default: {
                sortDirection = Sort.Direction.ASC;
            }
        }

        if (pageSize == null || pageSize < 1 || pageSize > limitPageSize) {
            if (limitPageSize < 1) {
                pageSize = 1;
            } else {
                pageSize = (int) limitPageSize;
            }
        }

        if (pageIndex == null || pageIndex < 0) {
            pageIndex = 0;
        }

        if (sortBy == null) {
            return new PageRequest(pageIndex, pageSize);
        } else {
            return new PageRequest(pageIndex, pageSize, sortDirection, sortBy);
        }
    }

    public static Pageable createPageRequest(Integer pageIndex,
                                             Integer pageSize,
                                             List<SortCriterial> sortCriterials,
                                             long limitPageSize) {
        if (pageSize == null || pageSize < 1 || pageSize > limitPageSize) {
            if (limitPageSize < 1) {
                pageSize = 1;
            } else {
                pageSize = (int) limitPageSize;
            }
        }

        if (pageIndex == null || pageIndex < 0) {
            pageIndex = 0;
        }

        if (sortCriterials == null) {
            return new PageRequest(pageIndex, pageSize);
        } else {
            Sort.Order[] orders = new Sort.Order[sortCriterials.size()];
            int size = sortCriterials.size();
            for (int i = 0; i < size; i++) {
                SortCriterial sortCriterial = sortCriterials.get(i);
                Sort.Direction direction;
                switch (sortCriterial.getSortType().toLowerCase()) {
                    case "desc": {
                        direction = Sort.Direction.DESC;
                    }
                    break;

                    default: {
                        direction = Sort.Direction.ASC;
                        break;
                    }
                }
                orders[i] = new Sort.Order(direction, sortCriterial.getSortBy());
            }
            Sort sort = new Sort(orders);
            return new PageRequest(pageIndex, pageSize, sort);
        }
    }

    public static String formatSortType(String sortType) {
        if (sortType == null) {
            return "asc";
        }
        switch (sortType.toLowerCase()) {
            case "desc": {
                return "desc";
            }

            default: {
                return "asc";
            }
        }
    }

    public static Sort createSortRequest(String sortBy, String sortType) {
        Sort.Direction sortDirection;
        switch (sortType.toLowerCase()) {
            case "desc": {
                sortDirection = Sort.Direction.DESC;
            }
            break;

            default: {
                sortDirection = Sort.Direction.ASC;
            }
        }
        return new Sort(sortDirection, sortBy);
    }
}
