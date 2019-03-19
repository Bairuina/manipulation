package org.wlgzs.manipulation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 胡亚星
 * @since 2019-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tuina_type")
public class TuinaType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 推拿种类id
     */
    @TableId("tuina_id")
    private Integer tuinaId;

    /**
     * 推拿种类
     */
    @TableField("tuina_name")
    private String tuinaName;

}
