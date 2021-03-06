package org.skywalking.apm.plugin.resin.v3.define;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.skywalking.apm.agent.core.plugin.interceptor.ConstructorInterceptPoint;
import org.skywalking.apm.agent.core.plugin.interceptor.InstanceMethodsInterceptPoint;
import org.skywalking.apm.agent.core.plugin.interceptor.enhance.ClassInstanceMethodsEnhancePluginDefine;
import org.skywalking.apm.plugin.resin.v3.ResinV3Interceptor;

import static net.bytebuddy.matcher.ElementMatchers.named;

/**
 * {@link ResinV3Instrumentation} presents that skywalking intercepts {@link com.caucho.server.dispatch.ServletInvocation#service(javax.servlet.ServletRequest,
 * javax.servlet.ServletResponse)} by using {@link ResinV3Interceptor}.
 *
 * @author baiyang
 */
public class ResinV3Instrumentation extends ClassInstanceMethodsEnhancePluginDefine {

    private static final String ENHANCE_CLASS = "com.caucho.server.dispatch.ServletInvocation";

    private static final String METHOD_INTERCET_CLASS = "org.skywalking.apm.plugin.resin.v3.ResinV3Interceptor";

    @Override
    protected ConstructorInterceptPoint[] getConstructorsInterceptPoints() {
        return null;
    }

    @Override
    protected InstanceMethodsInterceptPoint[] getInstanceMethodsInterceptPoints() {
        return new InstanceMethodsInterceptPoint[] {
            new InstanceMethodsInterceptPoint() {
                @Override
                public ElementMatcher<MethodDescription> getMethodsMatcher() {
                    return named("service");
                }

                @Override
                public String getMethodsInterceptor() {
                    return METHOD_INTERCET_CLASS;
                }
            }
        };
    }

    @Override
    protected String enhanceClassName() {
        return ENHANCE_CLASS;
    }

    @Override
    protected String[] witnessClasses() {
        return new String[] {"com.caucho.server.connection.AbstractHttpResponse"};
    }
}
