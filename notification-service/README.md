# Notification Service

## Configuration
**smtpUrl** and **smtpPort** environment properties are required.

---
Set
```$java
SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
```
Because of @Async lost security context. It will cause Feign errors by null Authentication object. The interceptor cannot be calle.