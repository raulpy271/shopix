
# Back end do Ecommerce Shopix

## Instalação

Instalação do banco de dados:

```sh
docker compose build
```

Instalação de dependências:

```sh
./mvnw install
```

## Execução

Execução do banco de dados e migrations:
```sh
docker compose up
./mvnw flyway:migrate
```

Execução da aplicação:
```sh
./mvnw spring-boot:run
```

Execução de testes:
```sh
./mvnw test
```

Execução de testes unitários:
```sh
./mvnw test -DexcludedGroups=integration
```
