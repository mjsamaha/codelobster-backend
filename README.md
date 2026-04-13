# 🦞 Codelobster API — Endpoint Reference

> **Base URL:** `http://localhost:8080`

> **Auth scheme:** JWT Bearer token (stateless). Tokens are obtained via the `/api/auth` endpoints.

---

## 🔐 Auth — `/api/auth`

> **Access:** Public (all endpoints)

| Method | Path | Description | Body |
|--------|------|-------------|------|
| `POST` | `/api/auth/register` | Register a new user account | `{ username, email, password }` |
| `POST` | `/api/auth/login` | Login with username or email | `{ usernameOrEmail, password }` |
| `POST` | `/api/auth/refresh` | Exchange a refresh token for new tokens | `{ refreshToken }` |
| `POST` | `/api/auth/logout` | Invalidate a refresh token | `{ refreshToken }` *(optional)* |
| `GET` | `/api/auth/verify-email?token=` | Verify email address via token link | — |

**Successful auth responses** return: `tokenType`, `accessToken`, `refreshToken`, `accessTokenExpiresInSeconds`, `userId`, `username`, `role`.

---

## 👤 Users — `/api/users`

> **Access:** All endpoints require authentication (any role).

| Method | Path | Auth Required | Description | Notes |
|--------|------|--------------|-------------|-------|
| `GET` | `/api/users/me` | ✅ Any authenticated | Get the currently logged-in user's profile | — |
| `PATCH` | `/api/users/me` | ✅ Any authenticated | Update current user's bio and country | `{ bio?, country? }` |
| `GET` | `/api/users/{username}` | ✅ Any authenticated | Get a user's public profile by username | — |
| `GET` | `/api/users/{username}/stats` | ✅ Any authenticated | Get a user's problem-solving stats | — |
| `GET` | `/api/users/leaderboard?limit=` | ✅ Any authenticated | Get top users ranked by rating | Default `limit=50`, max `200` |

> ⚠️ **Note:** `/api/users/**` is not explicitly listed under public permissions in `SecurityConfig`, so it falls under `.anyRequest().authenticated()`.

---

## 📚 Problems (Public) — `/api/problems/public`

> **Access:** Fully public — no authentication required.

| Method | Path | Description | Query Params |
|--------|------|-------------|--------------|
| `GET` | `/api/problems/public` | Paginated list of all published problems | `category`, `difficulty`, `tagIds`, `minRating`, `maxRating`, `titleKeyword`, `page`, `size`, `sort` |
| `GET` | `/api/problems/public/{id}` | Full detail of a single published problem | — |
| `GET` | `/api/problems/public/tags` | All available tags | `type` *(optional, filters by TagType)* |
| `GET` | `/api/problems/public/categories` | All available problem categories | — |

---

## 🛠️ Problems (Admin) — `/api/admin/problems`

> **Access:** Requires `ROLE_PROBLEM_SETTER` or `ROLE_ADMIN`.

| Method | Path | Extra `@PreAuthorize` | Description |
|--------|------|-----------------------|-------------|
| `POST` | `/api/admin/problems` | — | Create a new problem draft (caller becomes author) |
| `GET` | `/api/admin/problems` | — | Paginated list of all problems (drafts + published) with full filters |
| `GET` | `/api/admin/problems/{id}` | — | Full admin detail view of any problem |
| `PATCH` | `/api/admin/problems/{id}` | — | Partial update of a problem (non-null fields only) |
| `PATCH` | `/api/admin/problems/{id}/publish` | `ROLE_PROBLEM_SETTER` or `ROLE_ADMIN` | Mark problem as published |
| `PATCH` | `/api/admin/problems/{id}/unpublish` | `ROLE_PROBLEM_SETTER` or `ROLE_ADMIN` | Mark problem as unpublished |
| `DELETE` | `/api/admin/problems/{id}` | — | Permanently delete a problem |

---

## 🔒 Security Rules Summary (`SecurityConfig`)

| Path Pattern | Required Role(s) |
|---|---|
| `/api/auth/**` | 🌍 Public |
| `/api/problems/public/**` | 🌍 Public |
| `/swagger-ui/**`, `/v3/api-docs/**` | 🌍 Public |
| `/api/submissions/**` | `USER`, `MODERATOR`, `PROBLEM_SETTER`, `CONTEST_MANAGER`, `ADMIN` |
| `/api/admin/reports/**` | `MODERATOR`, `ADMIN` |
| `/api/admin/problems/**` | `PROBLEM_SETTER`, `ADMIN` |
| `/api/admin/contests/**` | `CONTEST_MANAGER`, `ADMIN` |
| `/api/admin/users/**` | `ADMIN` only |
| All other requests | Any authenticated user |

> The security layer is **stateless JWT** — no sessions are created. Unauthorized requests return `401`, forbidden ones return `403`.

---
