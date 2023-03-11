package com.snackman.datnud11.filters.impl.response;

import com.snackman.datnud11.filters.impl.AbstractUserResponseFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResponseFilterFactory {
    private final List<AbstractUserResponseFilter> filters;

    public AbstractUserResponseFilter get(String userType){
        return filters.stream().filter(filter -> filter.get(userType)!=null).findFirst().orElse(null);
    }
}
