package pers.fairy.miusa.rabbitmq;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/11 14:58
 */
@Getter
@Setter
public class MiusaMessage {

    private Long userId;

    private Long goodsId;

    private long miusaTime;
}
