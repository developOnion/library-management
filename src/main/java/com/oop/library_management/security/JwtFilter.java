package com.oop.library_management.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService userDetailsService;

	public JwtFilter(
			JwtUtil jwtUtil,
			CustomUserDetailsService userDetailsService
	) {

		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		try {

			if (authHeader != null && authHeader.startsWith("Bearer ")) {

				token = authHeader.substring(7);
				username = jwtUtil.extractUsername(token);
			}

			if (username != null &&
					SecurityContextHolder.getContext().getAuthentication() == null
			) {

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (jwtUtil.validateToken(token, userDetails)) {

					UsernamePasswordAuthenticationToken authToken =
							new UsernamePasswordAuthenticationToken(
									userDetails,
									null,
									userDetails.getAuthorities()
							);
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

			filterChain.doFilter(request, response);
		} catch (SignatureException e) {
			handleJwtException(response, "Invalid JWT signature");
		} catch (ExpiredJwtException e) {
			handleJwtException(response, "JWT token has expired");
		} catch (MalformedJwtException e) {
			handleJwtException(response, "Invalid JWT token");
		} catch (Exception e) {
			handleJwtException(response, "JWT authentication failed");
		}
	}

	private void handleJwtException(
			HttpServletResponse response,
			String message
	) throws IOException {

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write(
				"{\"timestamp\":\"" + LocalDateTime.now() + "\"," +
						"\"status\":401," +
						"\"message\":\"" + message + "\"}"
		);
	}
}
