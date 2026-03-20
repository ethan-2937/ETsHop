# AGENTS.md - ETsHop AI Agent Guide

## Project Overview
ETsHop is a multi-module Spring Boot 3.5.4 e-commerce platform with AI/ML capabilities. The project uses Java 21, Maven as build tool, and employs a modular architecture with four core modules: **ETsHop-common** (shared), **ETsHop-web** (customer-facing), **ETsHop-admin** (administration), and **ETsHop-mcp** (Model Context Protocol server).

---

## Architecture & Module Dependencies

### Dependency Graph
```
pom.xml (parent, manages versions)
├── ETsHop-common (base: Spring Boot + MyBatis + Redis + Elasticsearch)
│   └── No dependencies on other modules
├── ETsHop-web (port 6050, context: /api)
│   ├── Depends: ETsHop-common
│   └── Add-ons: Spring AI (OpenAI, Ollama), ES vector store, MCP client
├── ETsHop-admin (port 6061, context: /api)
│   └── Depends: ETsHop-common
└── ETsHop-mcp (MCP Server, no HTTP)
    ├── Depends: ETsHop-common (excludes web/actuator)
    └── Add-ons: Spring AI MCP server (WebFlux)
```

### Key Design Patterns
- **Common Module First**: ETsHop-common is the single source of truth for shared dependencies, entities, and utilities. Always check if a component should be added here for reuse.
- **Base Package Convention**: All Spring components use `@SpringBootApplication(scanBasePackages = "com.etshop")` - keep this consistent when creating new applications.
- **MapperScan**: MyBatis mapper scanning is declared with `@MapperScan` (no package specified defaults to base package).

---

## Critical Build & Deployment Workflows

### Build Command
```bash
mvn clean package -DskipTests
```
This creates JAR files in each module's `target/` directory:
- `ETsHop-web-1.0.0.jar` (executable, port 6050)
- `ETsHop-admin-1.0.0.jar` (executable, port 6061)
- `ETsHop-mcp-1.0.0.jar` (executable MCP server)

### Local Development Prerequisites
The application requires:
1. **MySQL 8.3**: Database `etshop` with user `root:root`
2. **Redis 6379**: Default localhost configuration
3. **Elasticsearch 9.2.1**: Running on `http://localhost:9200`

### Starting Modules
```bash
# Web module (AI/Vector Store features)
java -jar ETsHop-web/target/ETsHop-web-1.0.0.jar

# Admin module
java -jar ETsHop-admin/target/ETsHop-admin-1.0.0.jar

# MCP Server (separate from HTTP server)
java -jar ETsHop-mcp/target/ETsHop-mcp-1.0.0.jar
```

---

## Project-Specific Conventions

### Configuration Management
- **application.yml location**: `src/main/resources/application.yml` per module
- **Database mapping**: MyBatis converts snake_case columns to camelCase (`map-underscore-to-camel-case: true`)
- **Project folder property**: Hardcoded in configs as `project.folder: d:/Java_Backened_Project/ETsHop/`
- **Default credentials** (Admin): `admin:admin123` (see admin app config)

### Logging Configuration
- Logback v1.5.18 is standard across modules
- Config file: `logback-spring.xml` (located in `src/main/resources/`)
- Default root level: DEBUG

### Common Dependencies (Managed at Parent Level)
**Core Framework:**
- Spring Boot 3.5.4 with AOP, Redis, Validation, Actuator, Elasticsearch
- MyBatis Spring Boot 3.0.5
- MySQL Connector 8.3.0

**AI/ML Stack** (Web module exclusive):
- Spring AI 1.1.2 (OpenAI, Ollama starters)
- Spring AI ES Vector Store
- Spring AI MCP Client (WebFlux)

**Utilities:**
- Lombok (code generation)
- FastJSON2 2.0.58 (JSON serialization)
- OkHttp 4.12.0 (HTTP client)
- Apache Commons (Lang3, Codec, IO)
- Redisson 4.0.0 (Redis client)
- Alipay SDK 4.40.576 (payment integration)

---

## Integration Points & External Dependencies

### Data Layer
- **MySQL**: Primary database, configured in `datasource` section of app configs
- **Elasticsearch**: Vector store for AI search features (port 9200)
- **Redis**: Caching layer (port 6379, accessed via Redisson)

### AI/ML Integrations
- **OpenAI**: Via `spring-ai-starter-model-openai` (requires API key in environment)
- **Ollama**: Local LLM via `spring-ai-starter-model-ollama`
- **Vector Store**: Elasticsearch-backed with Spring AI integration
- **MCP Protocol**: Web module acts as MCP client; MCP module acts as MCP server

### Payment Integration
- **Alipay SDK**: Integrated for payment processing (configured at common level)

### Real-time Communication
- **WebSocket**: Custom WS server on port 6051 (Web module)
- **Netty 4.1.123**: Low-level networking (included for WebSocket/async support)

---

## Code Organization & Common Patterns

### Component Location Convention
- **Application Entry Points**: `com.etshop.ETsHopXxxApplication` (e.g., ETsHopWebApplication.java)
- **Common Utilities**: Place in ETsHop-common module for reuse across services
- **Service Logic**: Scan base package is `com.etshop` - components can be anywhere under this namespace

### Testing
- **Maven Property**: `maven.test.skip=true` (tests are skipped during builds by default)
- To run tests: Override this property or modify parent pom.xml

### Version Management
- **Current Version**: 1.0.0
- **Code Version Property**: 1.0
- **Java Source/Target**: 21

---

## When Adding New Features

1. **New Domain Models/Entities?** → Add to ETsHop-common
2. **New REST Endpoint?** → Add to ETsHop-web or ETsHop-admin (import from common)
3. **New AI Feature?** → Use Web module (has Spring AI dependencies); ensure ETsHop-common supports it
4. **New MCP Tool?** → Implement in ETsHop-mcp, export via MCP protocol
5. **New Utility?** → Add to ETsHop-common to keep services DRY
6. **Database Queries?** → Create MyBatis mapper in appropriate module, follow camelCase conversion rules

---

## Key Files for Reference
- `pom.xml` - Parent POM with dependency management
- `ETsHop-web/src/main/resources/application.yml` - AI-enabled service config
- `ETsHop-admin/src/main/resources/application.yml` - Admin service with default credentials
- `ETsHop-mcp/pom.xml` - MCP server setup (exclusions for actuator/web)

