# ms-user-video-manager
Microsserviço responsável pelo gerenciamento de usuários e videos do projeto hackaton (FIAP - Pós Tech).

## Contrato
  Acesse o endpoint `/user-video-manager/api/v1/swagger-ui/index.html` e você terá o detalhamento dos endpoints expostos na aplicação.

### Componentes Principais:
  - Banco de Dados: Armazenamento de dados dos videos e usuários no mongodb.
  - S3: Integração com o serviço da Amazon S3 para geração de URL pre-assinada para upload do video e download do arquivo .zip
  - Cognito: Integração com o serviço da Amazon Cognito para criação e autenticação do usuário.
    
### Qualidade - Sonar:
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Guimaj_ms-user-video-manager&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Guimaj_ms-user-video-manager)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Guimaj_ms-user-video-manager&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Guimaj_ms-user-video-manager)
