🛍️ Projeto Controle de Estoque
Este é um projeto Full-Stack de um sistema de gerenciamento de estoque e vendas, ideal para pequenas oficinas ou comércios. A aplicação é dividida em um backend robusto construído com Spring Boot e um frontend reativo desenvolvido em Angular.

✨ Funcionalidades Principais
O sistema permite um controle completo sobre produtos, categorias e vendas, oferecendo as seguintes funcionalidades:

📦 Gerenciamento de Produtos: Cadastro, edição, visualização e exclusão de produtos, com informações detalhadas como nome, descrição, quantidade em estoque e valores de compra/venda.

🗂️ Categorização: Organize os produtos em categorias, facilitando a busca e a gestão do estoque.

🛒 Registro de Vendas: Realize vendas de forma simples, selecionando produtos e informando a quantidade. O estoque é atualizado automaticamente após cada venda.

📜 Histórico de Vendas: Consulte todas as vendas realizadas, com detalhes sobre cliente, data, itens e valor total.

📄 Geração de PDF: Gere um comprovante de venda detalhado em formato PDF, que pode servir como nota de garantia para o cliente.

🔍 Busca Avançada: Filtre produtos por nome, categoria ou quantidade exata em estoque.

🚀 Tecnologias Utilizadas
O projeto foi construído utilizando um conjunto de tecnologias modernas para garantir performance e escalabilidade.

Backend (estoque-api)
Tecnologia	Descrição
Java 17	Linguagem de programação principal, robusta e de alta performance.
Spring Boot 3	Framework para criação de aplicações Java de forma rápida e configurável.
Spring Data JPA	Facilita a persistência de dados e a comunicação com o banco de dados.
Maven	Gerenciador de dependências e automação de build do projeto.
Lombok	Reduz a verbosidade do código Java com anotações.
OpenPDF	Biblioteca utilizada para a geração dinâmica dos comprovantes de venda em PDF.

Exportar para as Planilhas
Frontend (estoque-frontend)
Tecnologia	Descrição
Angular 17	Framework para a construção de interfaces de usuário dinâmicas e reativas.
TypeScript	Superset do JavaScript que adiciona tipagem estática ao código.
SCSS	Pré-processador CSS que adiciona funcionalidades como variáveis e aninhamento.
RxJS	Biblioteca para programação reativa, utilizada para gerenciar fluxos de dados.

Exportar para as Planilhas
Banco de Dados & DevOps
Tecnologia	Descrição
MySQL 8.0	Sistema de gerenciamento de banco de dados relacional para armazenar os dados da aplicação.
Docker & Docker Compose	Ferramentas de contêinerização para criar, implantar e executar a aplicação em ambientes isolados.
Nginx	Servidor web de alta performance, utilizado para servir a aplicação Angular de forma otimizada.

## 🚀 Tecnologias Utilizadas

Esta é a stack completa utilizada no desenvolvimento do projeto:

<div align="center">
  <img src="https://img.shields.io/badge/Java-17-blue?logo=openjdk&logoColor=white" alt="Java 17" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-green?logo=spring&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Angular-17-red?logo=angular&logoColor=white" alt="Angular 17" />
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql&logoColor=white" alt="MySQL 8.0" />
  <img src="https://img.shields.io/badge/TypeScript-blue?logo=typescript&logoColor=white" alt="TypeScript" />
  <img src="https://img.shields.io/badge/SCSS-pink?logo=sass&logoColor=white" alt="SCSS" />
  <img src="https://img.shields.io/badge/Docker-blue?logo=docker&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/Nginx-green?logo=nginx&logoColor=white" alt="Nginx" />
  <img src="https://img.shields.io/badge/Maven-red?logo=apachemaven&logoColor=white" alt="Maven" />
</div>

---

Exportar para as Planilhas
⚙️ Como Executar o Projeto
Com o Docker e o Docker Compose instalados, o projeto pode ser executado com um único comando, pois toda a configuração de ambiente já está definida nos arquivos Dockerfile e docker-compose.yml.

Clone o repositório:

Bash

git clone https://github.com/arthursilva007/projeto-estoque.git
Navegue até a pasta raiz do projeto:

Bash

cd projeto-estoque
Execute o Docker Compose:

Bash

docker-compose up --build -d
O comando --build garante que as imagens Docker para o backend e frontend serão construídas.

O -d (detached) faz com que os contêineres rodem em segundo plano.

Acesse as aplicações:

Frontend (Angular): Abra seu navegador e acesse http://localhost:4200

Backend (Spring Boot): A API estará disponível em http://localhost:8080

🏗️ Estrutura do Projeto
O projeto está organizado em uma estrutura monorepo, separando claramente as responsabilidades do backend e do frontend.

projeto-estoque/
├── 📂 estoque-api/      # Backend em Spring Boot
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── 📂 estoque-frontend/ # Frontend em Angular
│   ├── src/
│   ├── angular.json
│   └── Dockerfile
└── 🐳 docker-compose.yml # Orquestra a subida dos contêineres

