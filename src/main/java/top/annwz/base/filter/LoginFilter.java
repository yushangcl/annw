/**
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package top.annwz.base.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public final class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        //因为request.getInputStream()只能读一次.所以这里封装一个wrapper类,将post提交的数据封装起来.
        ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if(null == requestWrapper) {
            chain.doFilter(request, res);
        } else {
            chain.doFilter(requestWrapper, res);
        }
    }

    @Override
    public void destroy() {

    }
}
