# Spring Security & CSRF Notes

---

## 1. Default Behavior of Spring Security

As soon as `spring-boot-starter-security` is added to your project:
1. **All Endpoints are Locked**: Every HTTP request requires authentication.
2. **Default Credentials**: Default username is `user` (or configured via `application.yaml` under `spring.security.user.name` and `password`).
3. **CSRF Protection is ON by Default**:
   - Safe HTTP methods (**`GET`**, `HEAD`, `OPTIONS`, `TRACE`) are allowed without CSRF tokens.
   - State-modifying HTTP methods (**`POST`**, **`PUT`**, **`DELETE`**, **`PATCH`**) **require a valid CSRF token**.

---

## 2. Why POST Calls Fail from Postman (`403 Forbidden`)

When you attempt to test a `POST` request (e.g. `POST /api/foods`) from Postman:
* Even if your **Username** and **Password** are 100% correct in Basic Auth, the server responds with **`403 Forbidden`**.
* **Reason**: Spring Security expects a CSRF token header (`X-CSRF-TOKEN`) along with the request. Postman does not send this by default.

---

## 3. How to Fix: Disable CSRF with a Custom `SecurityFilterChain`

For stateless REST APIs (microservices, mobile backends, API testing via Postman), CSRF protection is typically **disabled** because REST clients do not rely on automatic browser cookie submissions.

### Implementation:

Create a security configuration class (e.g., `SecurityConfig.java`):

```java
package org.example.foodstore.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Disable CSRF for stateless REST APIs (Allows POST/PUT/DELETE from Postman)
            .csrf(csrf -> csrf.disable())

            // 2. Configure endpoint authorization
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui/index.html",
                    "/v3/api-docs/**",
                    "/api/foods/health"
                ).permitAll() // Public endpoints
                .anyRequest().authenticated() // All other endpoints require login
            )

            // 3. Enable HTTP Basic Authentication (for Postman credentials)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
```

---

## 4. Alternative: Keeping CSRF Enabled (If Needed)

If you decide to keep CSRF enabled, clients must fetch and send the token:

### Step 1: Create an endpoint to retrieve the token
```java
@RestController
public class CRSFController {

    @GetMapping("/api/csrf")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
}
```

### Step 2: In Postman
1. Call `GET http://localhost:8081/api/csrf` using Basic Auth to obtain the token.
2. For subsequent `POST` / `PUT` / `DELETE` requests, add the header:
   - **Header Name:** `X-CSRF-TOKEN`
   - **Header Value:** `<token_from_step_1>`
3. Ensure Postman retains the `JSESSIONID` session cookie.

---

## 5. Quick Reference Summary

| Request Method | Default Spring Security | With `csrf.disable()` |
| :--- | :--- | :--- |
| **`GET`** | Works with Basic Auth | Works with Basic Auth |
| **`POST`** | Returns `403 Forbidden` (Needs CSRF Token) | Works directly with Basic Auth |
| **`PUT` / `DELETE`** | Returns `403 Forbidden` (Needs CSRF Token) | Works directly with Basic Auth |

---

## 6. Why Swagger Needs Both `/swagger-ui/**` and `/v3/api-docs/**`

```text
1. You open in browser:
   http://localhost:8081/swagger-ui/index.html
          │
          ▼
2. Server returns the HTML, CSS, and JS (The UI Shell)
   (Covered by "/swagger-ui/**")
          │
          ▼
3. The JavaScript executes inside your browser:
   It runs: fetch("/v3/api-docs") to get the list of your APIs
          │
          ▼
4. Browser calls: http://localhost:8081/v3/api-docs
   (Covered by "/v3/api-docs/**")
          │
          ▼
5. SpringDoc returns the JSON specification of all your controllers & models.
          │
          ▼
6. Swagger UI uses that JSON to draw the interactive UI (the green GET, blue POST buttons, etc.)!
```

---

## 7. `httpBasic()` vs `formLogin()` (Browser Pop-up vs Login Page)

* **`.httpBasic(Customizer.withDefaults())`**:
  * Server responds with `WWW-Authenticate: Basic realm="Realm"`.
  * The browser intercepts this header and shows a **native browser pop-up dialog** at the top of the window asking for Username and Password.
  * Designed for APIs, Postman, mobile apps, and scripts.
* **`.formLogin(Customizer.withDefaults())`**:
  * Server redirects (`302 Redirect`) to `/login`.
  * The browser renders an **HTML web page** with input fields and a submit button.
  * Designed for human users navigating a web application.

---

## 8. How Basic Authentication Works Behind the Scenes

### Client-Side (Postman / Browser):
1. Combines credentials with a colon: `"krishna:krishna07"`
2. Encodes to Base64: `"a3Jpc2huYTprcmlzaG5hMDc="`
3. Attaches header: `Authorization: Basic a3Jpc2huYTprcmlzaG5hMDc=`

### Server-Side (Spring Security `BasicAuthenticationFilter`):
```text
1. Reads: "Authorization: Basic a3Jpc2huYTprcmlzaG5hMDc="
      │
      ▼
2. Decodes Base64: "a3Jpc2huYTprcmlzaG5hMDc=" ──► "krishna:krishna07"
      │
      ▼
3. Splits at ':':
   • Username: "krishna"
   • Password: "krishna07"
      │
      ▼
4. Compares with application.yaml:
   • Matches! ──► Grants access (HTTP 200 / 201)
```

### Direct Header Usage in Postman:
You do not have to use Postman's "Basic Auth" tab. You can directly send the Base64 header in the **Headers** tab for both `GET` and `POST` calls:
* **Key:** `Authorization`
* **Value:** `Basic a3Jpc2huYTprcmlzaG5hMDc=`

Both `GET /api/foods` (200 OK) and `POST /api/foods` (201 Created) will work directly.


