/*
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ffast.core.annotations;

import java.lang.annotation.*;

/**
 * @description: 登录验证
 * @copyright:
 * @createTime: 2017/11/14 14:12
 * @author：dzy
 * @version：1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logined {
    /**
     * 是否对自己生效（false只对基类生效）
     * @return
     */
    boolean notEffectSelf() default false;
}
