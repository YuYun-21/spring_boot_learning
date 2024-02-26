package com.yuyun.design.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
class GatewayEntity {

    private Integer handlerId;
    /**
     * 拦截者名称
     */
    private String name;
    /**
     * 全限定类名
     */
    private String conference;
    /**
     * 上一个handler
     */
    private Integer preHandlerId;
    /**
     * 下一个handler
     */
    private Integer nextHandlerId;
}

@Getter
enum GatewayEnum {
    // handlerId, 拦截者名称，全限定类名，上一个handler，下一个handler
    API_HANDLER(new GatewayEntity(1, "api接口限流", "com.yuyun.design.chain.ApiLimitGatewayHandler", null, 2)),
    BLACKLIST_HANDLER(new GatewayEntity(2, "黑名单拦截", "com.yuyun.design.chain.BlacklistGatewayHandler", 1, 3)),
    SESSION_HANDLER(new GatewayEntity(3, "用户会话拦截", "com.yuyun.design.chain.SessionGatewayHandler", 2, null)),
    ;

    GatewayEntity gatewayEntity;

    GatewayEnum(GatewayEntity gatewayEntity) {
        this.gatewayEntity = gatewayEntity;
    }
}


interface GatewayDao {

    /**
     * 根据 handlerId 获取配置项
     *
     * @param handlerId
     * @return
     */
    GatewayEntity getGatewayEntity(Integer handlerId);

    /**
     * 获取第一个处理者
     *
     * @return
     */
    GatewayEntity getFirstGatewayEntity();
}

class GatewayImpl implements GatewayDao {

    /**
     * 初始化，将枚举中配置的handler初始化到map中，方便获取
     */
    private static Map<Integer, GatewayEntity> gatewayEntityMap = new HashMap<>();

    static {
        GatewayEnum[] values = GatewayEnum.values();
        for (GatewayEnum value : values) {
            GatewayEntity gatewayEntity = value.getGatewayEntity();
            gatewayEntityMap.put(gatewayEntity.getHandlerId(), gatewayEntity);
        }
    }

    @Override
    public GatewayEntity getGatewayEntity(Integer handlerId) {
        return gatewayEntityMap.get(handlerId);
    }

    @Override
    public GatewayEntity getFirstGatewayEntity() {
        for (Map.Entry<Integer, GatewayEntity> entry : gatewayEntityMap.entrySet()) {
            GatewayEntity value = entry.getValue();
            //  没有上一个handler的就是第一个
            if (value.getPreHandlerId() == null) {
                return value;
            }
        }
        return null;
    }
}

abstract class GatewayHandler {
    protected GatewayHandler next;

    public void setNext(GatewayHandler next) {
        this.next = next;
    }

    /**
     * 服务
     */
    public abstract void service();

}

class ApiLimitGatewayHandler extends GatewayHandler {
    @Override
    public void service() {
        System.out.println("ApiLimitGatewayHandler service");
        if (Objects.nonNull(this.next)) {
            this.next.service();
        }
    }
}

class BlacklistGatewayHandler extends GatewayHandler {
    @Override
    public void service() {
        System.out.println("BlacklistGatewayHandler service");
        if (Objects.nonNull(this.next)) {
            this.next.service();
        }
    }
}

class SessionGatewayHandler extends GatewayHandler {
    @Override
    public void service() {
        System.out.println("SessionGatewayHandler service");
        if (Objects.nonNull(this.next)) {
            this.next.service();
        }
    }
}

class GatewayHandlerEnumFactory {

    private static GatewayDao gatewayDao = new GatewayImpl();

    /**
     * 提供静态方法，获取第一个handler
     *
     * @return {@link GatewayHandler}
     */
    public static GatewayHandler getFirstGatewayHandler() {

        GatewayEntity firstGatewayEntity = gatewayDao.getFirstGatewayEntity();
        GatewayHandler firstGatewayHandler = newGatewayHandler(firstGatewayEntity);
        if (firstGatewayHandler == null) {
            return null;
        }

        GatewayEntity tempGatewayEntity = firstGatewayEntity;
        Integer nextHandlerId = null;
        GatewayHandler tempGatewayHandler = firstGatewayHandler;
        // 迭代遍历所有handler，以及将它们链接起来
        while ((nextHandlerId = tempGatewayEntity.getNextHandlerId()) != null) {
            GatewayEntity gatewayEntity = gatewayDao.getGatewayEntity(nextHandlerId);
            GatewayHandler gatewayHandler = newGatewayHandler(gatewayEntity);
            tempGatewayHandler.setNext(gatewayHandler);
            tempGatewayHandler = gatewayHandler;
            tempGatewayEntity = gatewayEntity;
        }
        // 返回第一个handler
        return firstGatewayHandler;
    }

    /**
     * 反射实体化具体的处理者
     *
     * @param firstGatewayEntity
     * @return
     */
    private static GatewayHandler newGatewayHandler(GatewayEntity firstGatewayEntity) {
        // 获取全限定类名
        String className = firstGatewayEntity.getConference();
        try {
            // 根据全限定类名，加载并初始化该类，即会初始化该类的静态段
            Class<?> clazz = Class.forName(className);
            return (GatewayHandler) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


}

/**
 * 责任链模式抽象工厂
 *
 * @author hyh
 * @since 2024-02-23
 */
public class GatewayClient {
    public static void main(String[] args) {

        GatewayHandler firstGatewayHandler = GatewayHandlerEnumFactory.getFirstGatewayHandler();
        if (Objects.nonNull(firstGatewayHandler)) {
            firstGatewayHandler.service();
        }
    }
}
