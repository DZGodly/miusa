package pers.fairy.miusa.common;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/3/24 15:32
 */
@Getter
@Setter
@Accessors(chain = true)
public class ZSetModel {
    @NonNull
    private String key;
    @NonNull
    private double score;
    @NonNull
    private String member;
}
