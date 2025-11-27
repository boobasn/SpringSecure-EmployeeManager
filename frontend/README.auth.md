Angular authentication: backend login endpoint

This frontend now calls the backend directly at http://127.0.0.1:8080/auth/login when users authenticate.

Key points:
- The API base URL is defined in `src/app/config.ts` (API_BASE). Change it if your backend is on a different host.
- The login flow uses `sessionStorage` to keep `isUserLoggedIn` and `auth_token` after a successful login.
- The `AuthenticateGuard` checks for `auth_token` presence to allow protected routes.

How to test locally
1) Run backend: `./mvnw spring-boot:run` (ensure it listens on 127.0.0.1:8080)
2) From `frontend/`, run `ng serve` (or start SSR dev server if configured)
3) Open http://localhost:4200/login and login with valid backend credentials

Notes
- If your backend is in Docker and the frontend runs in the browser, `127.0.0.1:8080` will point to your host. If both are containerized you should use proper docker networking.
- For production you should use HTTPS, secure storage, and refresh tokens.
