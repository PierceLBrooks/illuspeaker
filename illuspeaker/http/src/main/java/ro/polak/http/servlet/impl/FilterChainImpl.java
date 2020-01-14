/**************************************************
 * Android Web Server
 * Based on JavaLittleWebServer (2008)
 * <p/>
 * Copyright (c) Piotr Polak 2008-2018
 **************************************************/

package ro.polak.http.servlet.impl;

import java.io.IOException;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ro.polak.http.exception.ServletException;
import ro.polak.http.servlet.Filter;
import ro.polak.http.servlet.FilterChain;
import ro.polak.http.servlet.HttpServletRequest;
import ro.polak.http.servlet.HttpServletResponse;

/**
 * Default FilterChain implementation.
 *
 * @author Piotr Polak piotr [at] polak [dot] ro
 * @since 201803
 */
public class FilterChainImpl implements FilterChain {

    private static final Logger LOGGER = Logger.getLogger(FilterChainImpl.class.getName());

    private Deque<Filter> filters;

    public FilterChainImpl(final Deque<Filter> filters) {
        this.filters = filters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException, ServletException {

        try {
            filters.pop().doFilter(request, response, this);
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.INFO, "Encountered IOException when doing filter", new Object[]{
                    e.getMessage()
            });
        }
    }
}
