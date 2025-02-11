# Курсовая работа

## Процедура запуска автотестов

Необходимое ПО: Docker и IntelliJ Idea.

1. Открыть проект в IntelliJ Idea.
2. в терминале IntelliJ Idea ввести команду `docker compose up -d`
3. в отдельном терминале запустить файл SUT командой `java -jar artifacts/aqa-shop.jar`
4. выполнить задачу в Gradle `gradlew test`
---

Документация

[план автоматизации](documentation/TestPlan.md)