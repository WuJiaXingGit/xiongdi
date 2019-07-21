package io.xiongdi.common.validator.Group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup失败，则UpdateGroup不会在校验
 * @author wujiaxing
 * @date 2019-07-14
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public class Group {
}
