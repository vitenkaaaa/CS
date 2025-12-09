package ru.netology.CloudStorage.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.mockito.Mockito.*;

public class JwtFilterTest {

    private JwtUtil jwtUtil;
    private JwtFilter jwtFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        jwtUtil = mock(JwtUtil.class);
        jwtFilter = new JwtFilter(jwtUtil);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testDoFilterInternal_WithValidToken_ShouldSetAuthentication() throws ServletException, IOException {
        String token = "validToken";
        String username = "testUser";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        jwtFilter.doFilterInternal(request, response, filterChain);
        
        var auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        assert auth.getName().equals(username);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_WithInvalidToken_ShouldNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + "invalidToken");
        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        jwtFilter.doFilterInternal(request, response, filterChain);
        assert SecurityContextHolder.getContext().getAuthentication() == null;

        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testDoFilterInternal_NoAuthorizationHeader_ShouldNotSetAuthentication() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        assert SecurityContextHolder.getContext().getAuthentication() == null;

        verify(filterChain).doFilter(request, response);
    }
}
