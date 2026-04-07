# Delivery Service

배송 생성/조회/상태 수정/취소를 담당하는 마이크로서비스입니다.

---

## 📌 서비스 정보

- Service name: `delivery-service` (`spring.application.name`)
- Port: `8083` (`project-configs/configs/delivery-service/delivery-service.yml`)
- External API base path: `/api/v1/deliveries`
- Internal API base path: `/internal/v1/deliveries` (서비스 간 통신 전용, Gateway 라우팅 제외)

---

## 🏗️ API 엔드포인트 요약

### External (클라이언트 → Gateway → delivery-service)

| Method | URL | 기능 | 권한 |
|--------|-----|------|------|
| `GET` | `/api/v1/deliveries` | 배송 목록 조회 | ALL |
| `GET` | `/api/v1/deliveries/{deliveryId}` | 배송 단건 조회 | ALL |
| `PATCH` | `/api/v1/deliveries/{deliveryId}` | 배송 상태 수정 | MASTER, HUB_ADMIN, DELIVERY |
| `GET` | `/api/v1/deliveries/{deliveryId}/routes` | 배송경로 목록 조회 | MASTER |
| `GET` | `/api/v1/deliveries/{deliveryId}/routes/{sequence}` | 배송경로 단건 조회 | MASTER |

### Internal (서비스 ↔ 서비스)

| Method | URL | 기능 | 권한 |
|--------|-----|------|------|
| `POST` | `/internal/v1/deliveries` | 배송 생성 | MASTER |
| `DELETE` | `/internal/v1/deliveries/{deliveryId}` | 배송 삭제 | MASTER |

> ⚠️ 배송경로 생성/삭제는 별도 API 없이 배송 생성/취소 로직에 내장되어 있습니다.

---

## 🔐 인증/헤더 규칙 (Gateway 사용 시)

Gateway는 JWT 검증 후 아래 헤더를 하위 서비스로 전달합니다.

- `X-User-Id`: 사용자 UUID (Keycloak `sub`)
- `X-User-Email`: 사용자 이메일
- `X-User-Role`: 사용자 권한 (예: `MASTER`, `HUB_ADMIN`, `DELIVERY`, `COMPANY`)

클라이언트(Postman)는 Gateway로 요청할 때 보통 아래만 넣으면 됩니다.

```
Authorization: Bearer {JWT_TOKEN}
```

`delivery-service` 컨트롤러는 `X-User-Id` / `X-User-Role` 헤더를 사용합니다.  
(Gateway가 자동 주입. delivery-service를 직접 호출하는 경우에는 Postman에서 직접 넣어야 합니다.)

---

## 🧪 Postman 테스트 (복붙용)

### 1) Gateway로 호출 (추천)

**Base URL**
```
http://localhost:8080
```

**배송 생성 (Internal - 주문 서비스에서 호출)**
```
POST http://localhost:8080/internal/v1/deliveries
```

Headers:
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

Body:
```json
{
  "orderId": "550e8400-e29b-41d4-a716-446655440000",
  "supplierCompanyId": "f4ea4644-3c19-4444-a47e-f7d25378ada2",
  "receiverCompanyId": "538c6c21-9480-4f27-9194-f60f8a47b326"
}
```

**배송 목록 조회**
```
GET http://localhost:8080/api/v1/deliveries?page=0&size=10&sort=createdAt,desc
```

Headers:
```
Authorization: Bearer <JWT_TOKEN>
```

**배송 상태 수정**
```
PATCH http://localhost:8080/api/v1/deliveries/{deliveryId}
```

Headers:
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

Body:
```json
{
  "status": "HUB_IN_TRANSIT"
}
```

**배송경로 목록 조회**
```
GET http://localhost:8080/api/v1/deliveries/{deliveryId}/routes
```

Headers:
```
Authorization: Bearer <JWT_TOKEN>
```

---

### 2) delivery-service 직접 호출 (개발/디버깅용)

**Base URL**
```
http://localhost:8083
```

**배송 생성**
```
POST http://localhost:8083/internal/v1/deliveries
```

Headers:
```
Content-Type: application/json
X-User-Id: <USER_UUID>
X-User-Role: MASTER
```

**배송 목록/단건 조회**
```
GET http://localhost:8083/api/v1/deliveries
GET http://localhost:8083/api/v1/deliveries/<DELIVERY_ID_UUID>
```

Headers:
```
X-User-Id: <USER_UUID>
X-User-Role: MASTER
```

`X-User-Role` 가능한 값: `MASTER | HUB_ADMIN | DELIVERY | COMPANY`

---

## 🧩 의존 서비스

배송 생성 플로우에서 외부 서비스 호출이 포함되어 있습니다 (FeignClient).

- `company-service`: 공급업체/수령업체 허브 정보 조회
- `hub-service`: 허브 간 이동 경로 조회
- `delivery-manager-service`: 허브/업체 배송담당자 배정
- `slack-service`: 슬랙 알림 발송

> ⚠️ 위 서비스들이 내려가 있으면 배송 생성 시 실패할 수 있습니다.

---

## 📦 배송 상태 전이

```
HUB_WAITING → HUB_IN_TRANSIT → HUB_ARRIVED → DELIVERING → COMPLETE
                                                          ↘ CANCELLED
```

| 상태 | 설명 |
|------|------|
| `HUB_WAITING` | 허브 대기 중 |
| `HUB_IN_TRANSIT` | 허브 이동 중 |
| `HUB_ARRIVED` | 목적지 허브 도착 |
| `DELIVERING` | 배송 중 |
| `COMPLETE` | 배송 완료 |
| `CANCELLED` | 배송 취소 |

> ⚠️ `COMPLETE` 상태에서는 취소 불가합니다.
